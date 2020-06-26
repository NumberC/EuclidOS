package Core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class SystemConfigurations {
    String cwd = System.getProperty("user.dir");
    String fullPath = cwd+"/src/main/java/Settings/config.json";
    public HashMap<String, Object> configDict = new HashMap<String, Object>();
    Gson gson;

    public SystemConfigurations(){
        gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fullPath));
            String fileString = "";

            for(Object i : bufferedReader.lines().toArray()){
                fileString = fileString.concat(i.toString());
            }

            configDict = gson.fromJson(fileString, HashMap.class);
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

            configDict = gson.fromJson(fileString, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void updateJSON(){
        try (FileWriter writer = new FileWriter(fullPath, false)) {
            gson.toJson(configDict, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isRadians(){
        updateDict();
        return configDict.get("Angle").equals("Radians");
    }

    public void setIsRadians(boolean isRadians){
        if(isRadians){
            configDict.replace("Angle", "Radians");
        } else{
            configDict.replace("Angle", "Degrees");
        }
        updateJSON();
    }

    public double[] Xs(){
        updateDict();
        return new double[]{(double) configDict.get("Min-X"), (double) configDict.get("Max-X")};
    }

    public double[] Ys(){
        updateDict();
        return new double[]{(double) configDict.get("Min-Y"), (double) configDict.get("Max-Y")};
    }

    public void setAxis(String axis, boolean isMin, double newVal){
        String key;
        if(axis.toLowerCase().equals("x")){
            key = isMin ? "Min-X":"Max-X";
        } else{
            key = isMin ? "Min-Y":"Max-Y";
        }
        configDict.replace(key, newVal);
        updateJSON();
    }
}
