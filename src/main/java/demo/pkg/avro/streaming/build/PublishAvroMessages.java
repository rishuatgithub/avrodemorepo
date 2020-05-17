package demo.pkg.avro.streaming.build;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import demo.pkg.avro.streaming.build.model.User;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Publish Messages
 *
 */
public class PublishAvroMessages {

    final String PROJECT_ID = "gcpproject-rks";
    final String TOPIC_NAME = "avro-demo-test-topic";
    final String SUBSCRIPTION_NAME = "avro-demo-test-topic-sub";

    /**
     * Publishing messages to pub-sub topic
     * @param messages
     * @throws IOException
     */
    public void publishAvroMessages(ByteString messages) throws IOException, ExecutionException, InterruptedException {

        List<ApiFuture<String>> futures = new ArrayList<>();

        ProjectTopicName topicName = ProjectTopicName.of(PROJECT_ID, TOPIC_NAME);

        Publisher publisher = null;
        try {
            publisher = Publisher.newBuilder(topicName).build();

            //ByteString byteString = ByteString.copyFrom(messages);
            //System.out.println("Sending Messages : "+messages.toStringUtf8());
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(messages).build();

            ApiFuture<String> future = publisher.publish(pubsubMessage);
            futures.add(future);

        }finally {

            List<String> messageIds = ApiFutures.allAsList(futures).get();

            for (String messageId : messageIds) {
                System.out.println("Published Messages with MessagesID: "+messageId);
            }

            if (publisher != null) {
                publisher.shutdown();
            }
        }


    }

    /**
     * Subcribing topic
     */
    public void subscriptAvroMessages(){

        ProjectSubscriptionName projectSubscriptionName = ProjectSubscriptionName.of(PROJECT_ID,SUBSCRIPTION_NAME);

        System.out.println("Subscription Name :"+ projectSubscriptionName.getSubscription());

        Subscriber subscriber = Subscriber.newBuilder(projectSubscriptionName,new GetMessages()).build();
        subscriber.startAsync().awaitRunning();

        subscriber.awaitTerminated();

    }

    class GetMessages implements MessageReceiver {
        @Override
        public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {

            BinaryDecoder decoderFactory = DecoderFactory.get().binaryDecoder(message.getData().toByteArray(),null);
            DatumReader<User> reader = new SpecificDatumReader<User>(User.getClassSchema());

            try {
                GenericRecord payload = reader.read(null,decoderFactory);

                System.out.println("Message ID:[ "+message.getMessageId()+" ]; Data: "+payload.toString());
                consumer.ack();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

}
