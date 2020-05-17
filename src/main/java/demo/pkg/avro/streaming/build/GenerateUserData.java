package demo.pkg.avro.streaming.build;

import demo.pkg.avro.streaming.build.model.Address;
import demo.pkg.avro.streaming.build.model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class GenerateUserData {

    public ArrayList<User> generateUserData(){

        ArrayList<User> userdata = new ArrayList<>();

        for(int i=0; i<10; i++){
            Address address = new Address().newBuilder()
                    .setHouseNo("Flat "+i+", 50 Belgrave")
                    .setCountry("County "+i)
                    .setPostalCode("PC"+i+"ABC")
                    .build();

            User user = new User().newBuilder()
                    .setId(i)
                    .setName("User "+String.valueOf((char)(i + 65)))
                    .setAge(i+20)
                    .setAddress(address)
                    .build();

            userdata.add(user);
        }

        return userdata;

    }


    public ArrayList<User> generateUserDataFromFile(String filename) throws IOException, ParseException {

        JSONObject jsondata = new ReadingJsonData().parseJsonFile(filename);


        ArrayList<User> user_list = new ArrayList<>();

        Iterator<JSONObject> itr = ((JSONArray) jsondata.get("definitions")).iterator();
        while (itr.hasNext()){

            User user = new User();
            Address address = new Address();

            JSONObject record = itr.next();
            JSONObject jsonObject_address = new JSONObject();
            JSONParser jsonParser = new JSONParser();

            user.setId(Integer.parseInt(record.get("id").toString()));
            user.setName(record.get("name").toString());
            user.setAge(Integer.parseInt(record.get("age").toString()));

            jsonObject_address = (JSONObject) jsonParser.parse(record.get("address").toString());

            address.setHouseNo(jsonObject_address.get("house_no").toString());
            address.setCountry(jsonObject_address.get("country").toString());
            address.setPostalCode(jsonObject_address.get("postal_code").toString());;

            user.setAddress(address);

            user_list.add(user);
        }

        return user_list;
    }
}
