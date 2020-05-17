package demo.pkg.avro.streaming.build;

import org.apache.avro.Schema;
import org.apache.avro.compiler.specific.SpecificCompiler;

import java.io.File;
import java.io.IOException;

public class GenerateAvroModelClasses {

    /**
     * Generates the avro models schema from the .avsc file
     * @throws IOException
     */
    public void generateavroclasses() throws IOException {

        final String AVSC_SCHEMA_FILE = "src/main/resources/user.avsc";
        final String AVSC_SCHEMA_COMPILE_DEST = "src/main/java/";

        SpecificCompiler compiler = new SpecificCompiler(new Schema.Parser().parse(new File(AVSC_SCHEMA_FILE)));
        compiler.compileToDestination(new File("src/main/resources"), new File(AVSC_SCHEMA_COMPILE_DEST));


    }
}
