package demo.pkg.avro.streaming.build;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

public class BuildAvroSchema {

    public Schema buildschema(){

        Schema saddress = SchemaBuilder.record("address").doc("schema address information")
                .fields()
                .name("house_no").type("string").noDefault()
                .name("county").type("string").noDefault()
                .name("postal_code").type("string").noDefault()
                .endRecord();

        Schema schema = SchemaBuilder.record("User").doc("Schema generated manually")
                .fields()
                .name("id").type("int").noDefault()
                .name("name").type("string").noDefault()
                .name("age").type("int").noDefault()
                //.name("user_address").type(saddress).noDefault()
                .endRecord();

        return schema;

    }

}
