package demo.pkg.avro.build;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Read .avro file
 *
 * @author rishushrivastava
 */
public class ReadAvroFile {

    /**
     * read data from avro file and return a list
     * @param schema
     * @param filename
     * @return
     * @throws IOException
     */
    public ArrayList<GenericRecord> readAvrofile(Schema schema, String filename) throws IOException {

        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);

        DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(new File(filename),datumReader);

        GenericRecord data = null;
        ArrayList<GenericRecord> filedata = new ArrayList<>();

        while (dataFileReader.hasNext()){
            data = dataFileReader.next();
            filedata.add(data);
        }

        return filedata;
    }
}
