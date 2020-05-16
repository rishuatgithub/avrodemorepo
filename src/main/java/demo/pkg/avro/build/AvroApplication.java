package demo.pkg.avro.build;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Demo Avro application
 * It generates the avro schema from the .avro file and prints out the data.
 *
 * @author rishushrivastava
 */

public class AvroApplication {

    public static void main(String[] args) throws IOException {

        System.out.println("Avro Demo Application - Reading Avro File");

        final String FILENAME = "/Users/rishushrivastava/Documents/GitHub/avrodemorepo/src/main/resources/user_data.avro";

        // generate schema from .avro file (in case of missing .avsc file)
        Schema file_avro_schema = new GenerateAvroSchema().getSchemaFromFile(FILENAME);

        System.out.println("Defined Schema : "+file_avro_schema);


        // read the .avro file
        ArrayList<GenericRecord> filedata_output = new ReadAvroFile().readAvrofile(file_avro_schema,FILENAME);

        System.out.println("Data Output: ");
        // iterate over the records in file and print the data
        for(GenericRecord gr: filedata_output){
            System.out.println(gr);
        }


    }
}
