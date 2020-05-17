package demo.pkg.avro.streaming.build;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ReadingJsonData {

    public JSONObject parseJsonFile(String filename) throws IOException, ParseException {

        FileReader fileReader = new FileReader(filename);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);

        return jsonObject;
    }
}
