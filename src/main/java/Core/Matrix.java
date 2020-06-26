package Core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;


public class Matrix {
    HashMap<String, double[][]> allMatrices = new HashMap<String, double[][]>();
    String cwd = System.getProperty("user.dir");
    String fullPath = cwd+"/src/main/java/Settings/Matrices.json";
    char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    Gson gson;

    public Matrix(){
        gson = new GsonBuilder().setPrettyPrinting().create();

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fullPath));
            Type matrixType = new TypeToken<HashMap<String, double[][]>>(){}.getType();
            allMatrices = gson.fromJson(bufferedReader, matrixType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getMatrixUpdate(){
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fullPath));
            Type matrixType = new TypeToken<HashMap<String, double[][]>>(){}.getType();
            allMatrices = gson.fromJson(bufferedReader, matrixType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, double[][]> getAllMatrices(){
        return allMatrices;
    }

    //TODO initalize all to 0
    public void createMatrix(int columns, int rows){
        double[][] matrix = new double[rows][columns];
        int count = (int)allMatrices.get("Count")[0][0];
        String key = Character.toString(alphabet[count]);
        double[][] countArr = {{count+1*1.0}};
        allMatrices.replace("Count", countArr);
        allMatrices.put(key, matrix);
        saveMatrix();
    }

    public void addRow(String ID, int change){
        double[][] matrix = getMatrix(ID);
        int rows = matrix.length;
        int columns = matrix[0].length;
        double[][] newMat = new double[rows+change][columns];

        for(int r = 0; r < rows+change; r++){
            for(int c = 0; c < columns; c++){
                if(r >= rows){
                    newMat[r][c] = 0;
                } else{
                    newMat[r][c] = matrix[r][c];
                }
            }
        }
        allMatrices.replace(ID, newMat);
        saveMatrix();
    }

    public void addColumn(String ID, int change){
        double[][] matrix = getMatrix(ID);
        int rows = matrix.length;
        int columns = matrix[0].length;
        double[][] newMat = new double[rows][columns+change];

        for(int r = 0; r < rows; r++){
            for(int c = 0; c < columns+change; c++){
                if(c >= columns){
                    newMat[r][c] = 0;
                } else{
                    newMat[r][c] = matrix[r][c];
                }
            }
        }
        allMatrices.replace(ID, newMat);
        saveMatrix();
    }

    //Possibly have MatrixController and Core.Matrix class for each new matrix
    public void editMatrix(String ID, int column, int row, double newVal){
        allMatrices.get(ID)[row][column] = newVal;
        saveMatrix();
    }

    public void saveMatrix(){
        try (FileWriter writer = new FileWriter(fullPath, false)) {
            gson.toJson(allMatrices, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //getMatrixUpdate();
    }

    public double[][] getMatrix(String ID){
        getMatrixUpdate();
        return allMatrices.get(ID);
    }
}