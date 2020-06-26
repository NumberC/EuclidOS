package Core;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class VariableOrganizer {
    String cwd = System.getProperty("user.dir");
    String fullPath = cwd+"/src/main/java/Settings/Variables.json";
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    HashMap<String, Double> varsDict = new HashMap<String, Double>();
    Gson gson;

    VariableOrganizer(){
        gson = new GsonBuilder().setPrettyPrinting().create();

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

    void store(String key, double value){
        varsDict.replace(key, value);

        try (FileWriter writer = new FileWriter(fullPath, false)) {
            gson.toJson(varsDict, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void retrieveData(){
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

    String useData(String eq){
        //TODO make more efficient
        retrieveData();

        for(String i : varsDict.keySet()){
            if(eq.contains(i)){
                eq = eq.replaceAll(i, "("+ varsDict.get(i) +")");
            }
        }
        return eq;
    }
}
