package Core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class GraphEquations {
    String cwd = System.getProperty("user.dir");
    String fullPath = cwd+"/src/main/java/Settings/GraphEquations.json";
    HashMap<String, String> varsDict = new HashMap<String, String>();
    Gson gson;

    //TODO create interface to use for this, VarOrganizer, and possibly Core.Matrix

    public GraphEquations(){
        gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fullPath));
            String fileString = "";

            for(Object i : bufferedReader.lines().toArray()){
                fileString = fileString.concat(i.toString());
            }

            varsDict = gson.fromJson(fileString, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void store(String key, String value){
        if(varsDict.containsKey(key)){
            varsDict.replace(key, value);
        } else{
            varsDict.put(key, value);
        }

        try (FileWriter writer = new FileWriter(fullPath, false)) {
            gson.toJson(varsDict, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String useData(String color){
        //TODO make more efficient
        return useAllData().get(color);
    }

    public String[] getAllEQS(){
        System.out.println(Arrays.toString(useAllData().entrySet().toArray()));
        return useAllData().values().toArray(new String[0]);
    }

    public HashMap<String, String> useAllData(){
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fullPath));
            String fileString = "";

            for(Object i : bufferedReader.lines().toArray()){
                fileString = fileString.concat(i.toString());
            }

            varsDict = gson.fromJson(fileString, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return varsDict;
    }
}
