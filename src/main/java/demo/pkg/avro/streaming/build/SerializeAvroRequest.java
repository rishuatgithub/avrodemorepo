package demo.pkg.avro.streaming.build;

import com.google.protobuf.ByteString;
import demo.pkg.avro.streaming.build.model.User;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.beam.sdk.coders.AvroCoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SerializeAvroRequest {

    private static final AvroCoder<User> avrocoder = AvroCoder.of(User.class);

    /**
     * This returns a byte stream of the avro output
     * @param request
     * @return
     */
    public ByteString serializeavro(User request){

        DatumWriter<User> datumWriter = new SpecificDatumWriter<User>(User.class);
        byte[] data = new byte[0];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Encoder encoder = null;

        try{
            encoder = EncoderFactory.get().jsonEncoder(User.getClassSchema(),byteArrayOutputStream);
            datumWriter.write(request,encoder);

            encoder.flush();
            data = byteArrayOutputStream.toByteArray();

        }catch (IOException e){
            System.out.println("Serialization Error.");
        }


        return ByteString.copyFrom(data);
    }


    /**
     * This method write the schema to an avro file.
     * @param request
     * @param filename
     * @throws IOException
     */
    public void serializeavrotofile(ArrayList<User> request, String filename) throws IOException {

        DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
        DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
        dataFileWriter.create(User.getClassSchema(), new File(filename));
        for (User u: request){
            dataFileWriter.append(u);
        }

        dataFileWriter.close();
    }


    /**
     * Convert data to avro bytestring
     * @param request
     * @return
     */
    public ArrayList<ByteString> serializeavrotobytestring(ArrayList<User> request){

        //DatumWriter<User> datumWriter = new SpecificDatumWriter<User>(User.class);
        ArrayList<ByteString> bslist = new ArrayList<>();

        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for(User u: request){
                avrocoder.encode(u,byteArrayOutputStream);
                ByteString byteString = ByteString.copyFrom(byteArrayOutputStream.toByteArray());
                bslist.add(byteString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bslist;

    }


}
