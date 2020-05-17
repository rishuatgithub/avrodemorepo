package demo.pkg.avro.streaming.build;

import com.google.protobuf.ByteString;
import demo.pkg.avro.build.ReadAvroFile;
import demo.pkg.avro.streaming.build.model.User;
import org.apache.avro.generic.GenericRecord;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AvroStreamingApplication {

    final static String JSON_USERDATA = "src/main/resources/user_data.json";
    final static String AVRO_OUTPUT_FILENAME="src/main/resources/generated_output/user.avro";

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, ParseException {

        System.out.println("====> STEP 0: Avro Streaming Application - Started");

        System.out.println("====> STEP 1: Generating Avro Model from .avsc");
        new GenerateAvroModelClasses().generateavroclasses();


        System.out.println("====> STEP 2: Generating a list of users");
        ArrayList<User> user_list = new GenerateUserData().generateUserDataFromFile(JSON_USERDATA);
        System.out.println(user_list);


        // serialize avro file
        System.out.println("====> STEP 3: (serialization) Writing .avro file : "+AVRO_OUTPUT_FILENAME);
        new SerializeAvroRequest().serializeavrotofile(user_list,AVRO_OUTPUT_FILENAME);

        // deserialize avro file
        ArrayList<GenericRecord> readAvroFile = new ReadAvroFile().readAvrofile(User.getClassSchema(),AVRO_OUTPUT_FILENAME);

        System.out.println("====> Step 4: (deserialization) Parsing the .avro file to read the data");
        for(GenericRecord g: readAvroFile) {
            System.out.println("From File. Record: "+g.get("id")+" is :"+g);
        }


        System.out.println("====> STEP 5: (serialization) Generating byte messages for pub/sub");

        for (User u : user_list) {
            ByteString data = new SerializeAvroRequest().serializeavro(u);
            new PublishAvroMessages().publishAvroMessages(data);
        }

        System.out.println("====> STEP 6: Subscribing Messages from GCP Topic");
        new PublishAvroMessages().subscriptAvroMessages();


    }
}
