package demo.pkg.avro.streaming.build;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import demo.pkg.avro.streaming.build.model.User;
import org.apache.beam.sdk.coders.AvroCoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Publish Messages
 *
 */
public class PublishAvroMessages {

    final String PROJECT_ID = "gcpproject-rks";
    final String TOPIC_NAME = "Avro-demo-test-topic";
    final String SUBSCRIPTION_NAME = "avro-demo-test-topic-sub";

    private static final AvroCoder<User> avrocoder = AvroCoder.of(User.class);

    /**
     * Publishing messages to pub-sub topic
     * @param messages
     * @throws IOException
     */
    public void publishAvroMessages(ArrayList<ByteString> messages) throws IOException{

        List<ApiFuture<String>> futures = new ArrayList<>();

        ProjectTopicName topicName = ProjectTopicName.of(PROJECT_ID, TOPIC_NAME);

        System.out.println("Topic :"+ topicName.getTopic());

        Publisher publisher = null;
        try {
            publisher = Publisher.newBuilder(topicName).build();

            //ByteString byteString = ByteString.copyFrom(messages);

            for(ByteString bytedata : messages){

                PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(bytedata).build();
                publisher.publish(pubsubMessage);

            }

        }finally {

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

    static class GetMessages implements MessageReceiver {
        @Override
        public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {

            System.out.println("Message Id:["+message.getMessageId()+"]; Data:"+message.getData().toStringUtf8());
            consumer.ack();
        }
    }



}
