package Core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class History {
    String cwd = System.getProperty("user.dir");
    String fullPath = cwd+"\\src\\Settings\\History.json";
    HashMap<String, ArrayList<String>> historyDict = new HashMap<>();
    Gson gson;

    public History(){
        gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fullPath));
            String fileString = "";

            for(Object i : bufferedReader.lines().toArray()){
                fileString = fileString.concat(i.toString());
            }

            historyDict = gson.fromJson(fileString, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void updateDict(){
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fullPath));
            String fileString = "";

            for(Object i : bufferedReader.lines().toArray()){
                fileString = fileString.concat(i.toString());
            }

            historyDict = gson.fromJson(fileString, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void updateJSON(){
        try (FileWriter writer = new FileWriter(fullPath, false)) {
            gson.toJson(historyDict, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getEQ(String index){
        updateDict();
        return historyDict.get(index).get(0);
    }

    public String getAnswer(String index){
        updateDict();
        return historyDict.get(index).get(1);
    }

    public int getLatestIndex(){
        updateDict();
        return historyDict.keySet().size();
    }

    public String getLatestIndexStr(){
        updateDict();
        return Integer.toString(historyDict.keySet().size());
    }

    public void putEQ(String eq, String answer){
        ArrayList<String> eqArr = new ArrayList<>();
        eqArr.add(eq);
        eqArr.add(answer);
        String index = getLatestIndexStr();
        historyDict.put(index, eqArr);
        updateJSON();
    }
}
