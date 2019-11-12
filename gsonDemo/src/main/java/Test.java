import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Test {
//RUN WITH GRADLE;

    public static void main(String[] args) {

        Employee e1 = new Employee("John", "Wayne");
        Employee e2 = new Employee("Maria", "Calas");

        Employee [] database = {e1,e2};
        ArrayList<Employee> database2 = new ArrayList<>(Arrays.asList(database));

        //simple gson and all;

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String test1 = gson.toJson(e1);
        System.out.println("Json: " + test1);

        Employee e3 = gson.fromJson(test1, Employee.class);
        System.out.println("From Json: " + e3 + '\n');

        System.out.println("Conversion from object list to Json format: ");
        System.out.println(gson.toJson(database));

        //save *.json file

        File file = new File("D:\\245149\\gsonDemo\\json1.json");
        try (FileWriter fileWriter = new FileWriter(file)){
            gson.toJson(database, fileWriter);
        }
        catch (IOException e){
            System.out.println("IO error");
        }

        //read *.json file

        Employee [] testDatabase = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            testDatabase = gson.fromJson(bufferedReader, Employee[].class);
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("From Jason: " + Arrays.toString(testDatabase) + '\n');

        //Display all .json object attributes;
        Map m = gson.fromJson(test1, Map.class);
        System.out.println("Object's got: " + m.size() + " attributes");

        for (Object key: m.keySet()
        ) {
            System.out.println("key: " + key);
        }

        //Field read
        System.out.println(m.get("firstName"));
        System.out.println(m.get("lastName"));

        //URL&response

        StringBuffer response = new StringBuffer();
        String url = "http://api.open-notify.org/iss-now.json";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            System.out.println("Response: " + responseCode);

            String inputLine;
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            System.out.println("Response :" + response);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //FIX
        String iss = gson.toJson(response);

        Map m2 = gson.fromJson(iss, Map.class);
        System.out.println("Object response's got: " + m2.size() + " attributes");

        File file2 = new File("D:\\245149\\gsonDemo\\iss.json");
        try (FileWriter fileWriter = new FileWriter(file2)){
            gson.toJson(iss, fileWriter);
        }
        catch (IOException e){
            System.out.println("IO error");
        }

        for (Object key: m2.keySet()
        ) {
            System.out.println("key: " + key);
        }


    }

}
