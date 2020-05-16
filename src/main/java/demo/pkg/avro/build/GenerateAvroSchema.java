package demo.pkg.avro.build;

import org.apache.avro.Schema;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;

import java.io.File;
import java.io.IOException;

/**
 * Generate avro schema
 */
public class GenerateAvroSchema {

    /**
     * Get the avro schema from file in case of missing .avsc file
     * @param filename
     * @return Schema
     * @throws IOException
     */
    public Schema getSchemaFromFile(String filename) throws IOException {

        DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>();

        CodecFactory codecFactory = new GetCodecCompression().getCodec("snappy");

        DataFileReader<GenericRecord> datafilereader = new DataFileReader<GenericRecord>(new File(filename),reader);
        Schema schema = datafilereader.getSchema();

        return schema;
    }



}
