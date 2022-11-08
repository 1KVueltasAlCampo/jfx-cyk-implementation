package com.edu.icesi.jfxcykimplementation.model;

import java.util.ArrayList;
import java.util.Hashtable;

public class CYKAlgorithm {

    private ArrayList<String[]> grammar;
    private ArrayList<String> heads;
    private char startupChar;
    private String string;
    private String[][] matrix;
    private int stringLength;

    private Hashtable<String, String> indexes = new Hashtable<>();
    private final static int CONTENTS_SIZE = 2;

    public CYKAlgorithm(ArrayList<String[]> grammar,ArrayList<String> heads, char startupChar,String string){
        this.grammar = grammar;
        this.startupChar=startupChar;
        this.string=string;
        this.heads=heads;
        stringLength = string.length();
        matrix = new String[stringLength][stringLength];
        initialValues();
        fillMatrixIndex();
        fillMatrix();
    }

    /**
     * <br> Initializes the cyk matrix <br><br>
     */
    private void initialValues(){
        for(int i=0;i<stringLength;i++){
            matrix[i][0] = findValue(string.charAt(i)+"");
            for(int j=1;j<stringLength;j++){
                matrix[i][j]="";
            }
        }
    }

    /**
     * <br> Confirms whether or not the variable contains the searched value <br><br>
     * @param value The value or values sought. They can be terminals or variables
     * @return The variable that returns the searched value
     */
    private String findValue(String value){
        String results = "";
        for(int i=0;i<grammar.size();i++){
            for(int j=0;j<grammar.get(i).length;j++){
                String valueFound = grammar.get(i)[j];
                if(valueFound.equals(value) && !results.contains(valueFound)){
                    results += heads.get(i)+",";
                }
            }
        }
        if(results.length()>0){
            results = results.substring(0,results.length()-1);
        }
        return results;
    }


    /**
     * <br> Performs the process of finding a value in a variable for all combinations of the position of an array. <br><br>
     * @param value The values sought. They can be terminals or variables
     * @return The variables that returns the searched values
     */
    private String findMultipleValues(String value){
        String results = "";
        String[] values = value.split(",");
        for(int i=0;i<values.length;i++){
            if(!results.contains(findValue(values[i]))){
                results+=findValue(values[i])+",";
            }
        }
        return results;
    }


    //
    /**
     * <br> Joins the values of two sets <br><br>
     * @param first The first set
     * @param second The second set
     * @return The merge of the sets
     */
    private String fusion(String first,String second){
        String result = "";
        first = first.replaceAll("^\\s*","");
        second = second.replaceAll("^\\s*","");
        if(first!=null && second!=null && !first.equals("") && !second.equals("")){
            String[] firstArray = first.split(",");
            String[] secondArray = second.split(",");
            for(int i=0;i<firstArray.length;i++){
                for(int j=0;j<secondArray.length;j++){
                    result += firstArray[i]+secondArray[j]+",";
                }
            }
        }
        if(result.length()>0){
            result = result.substring(0,result.length()-1);
        }
        return result;
    }

    /**
     * <br> Places the combinations between indexes of the matrices in the respective places. <br><br>
     */
    private void fillMatrixIndex(){
        int size = stringLength;
        for(int i = 0;i<=size;i++){
            int count = 1;
            for(int k = size;k>0;k--){
                int times = 0;
                for(int j = count;j<size;j++){
                    fillIndexes(i+""+j,i+"-"+(size-k)+"-"+(count+i)+"-"+times);
                    times++;
                }
                count++;
            }

        }
    }


    /**
     * <br> Fill in the matrix by making the necessary combinations for each index.
     */
    private void fillMatrix(){
        for(int j =1;j<stringLength;j++){
            for(int i=0;i<stringLength-j;i++){
                String result = indexes.get(i+""+j);
                matrix[i][j] = generateMatrixValue(result);
            }
        }
    }


    /**
     * <br> Extracts the indices of the combinations, merges the sets and searches in the grammar for the variables that generate the obtained merge.
     * @param result The combination of indices obtained from the combination matrix
     * @return The variable(s) that contain the merge of the sets
     */
    private String generateMatrixValue(String result){
        String matrixValue="";
        if(result!=null){
            String[] resultArray = result.split(",");
            for(int i=0;i<resultArray.length;i++){
                String[] indexArray = resultArray[i].split("-");
                int i1Index = Integer.parseInt(String.valueOf(indexArray[0]));
                int j1Index = Integer.parseInt(String.valueOf(indexArray[1]));
                int i2Index = Integer.parseInt(String.valueOf(indexArray[2]));
                int j2Index = Integer.parseInt(String.valueOf(indexArray[3]));
                String first = matrix[i1Index][j1Index];
                String second = matrix[i2Index][j2Index];
                String valueToSearch = fusion(first,second);
                if(!matrixValue.contains(findMultipleValues(valueToSearch))){
                    matrixValue += findMultipleValues(valueToSearch);
                }
                matrixValue =  matrixValue.replaceAll("(.)\\1", "$1");
            }
        }
        return matrixValue;
    }

    /**
     * <br> Creates a dictionary with the combinations of the indexes
     * @param key The index containing the combination
     * @param value The combination
     * @return The variable(s) that contain the merge of the sets
     */
    private void fillIndexes(String key,String value){
        if(indexes.get(key)!=null){
            String contained = indexes.get(key);
            indexes.put(key,contained+","+value);
        }
        else{
            indexes.put(key,value);
        }
    }

    /**
     * <br> Checks if the chain can be arrived at from the first element of the grammar
     * @return True if the grammar contains the string, false if not
     */
    public boolean confirmStringInGrammar(){
        if(matrix[0][stringLength-1].contains(startupChar+"")){
            return true;
        }
        return false;
    }





}
