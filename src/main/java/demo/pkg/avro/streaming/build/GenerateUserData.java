package demo.pkg.avro.streaming.build;

import demo.pkg.avro.streaming.build.model.Address;
import demo.pkg.avro.streaming.build.model.User;

import java.util.ArrayList;

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
}
