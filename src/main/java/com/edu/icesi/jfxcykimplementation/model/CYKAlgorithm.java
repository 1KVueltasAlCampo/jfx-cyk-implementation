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
            for(int j=0;j<CONTENTS_SIZE;j++){
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

    private String findMultipleValues(String value){
        String results = "";
        String[] values = value.split(",");
        for(int i=0;i<values.length;i++){
            if(!results.contains(findValue(values[i]))){
                results+=findValue(values[i])+",";
            }
        }
        if(results.length()>0){
            results=results.substring(0,results.length()-1);
        }
        return results;
    }

    private String fusion(String first,String second){
        String result = "";
        if(first!=null && second!=null){
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

    private void fillMatrixIndex(){
        int size = stringLength;
        for(int i = 0;i<=size;i++,size--){
            int count = 1;
            for(int k = size;k>0;k--){
                int times = 0;
                for(int j = count;j<size;j++){
                    //System.out.println(i+""+j+" = "+i+(stringLength-k)+" "+(count+i)+times);
                    fillIndexes(i+""+j,i+""+(size-k)+(count+i)+times);
                    //System.out.println(indexes.get(i+""+j));
                    times++;
                }
                count++;
            }

        }
    }

    private void fillMatrix(){
        for(int j =1;j<stringLength;j++){
            for(int i=0;i<stringLength-j;i++){
                String result = indexes.get(i+""+j);
                matrix[i][j] = generateMatrixValue(result);
                //System.out.println(i+""+j);
            }
        }
    }

    private String generateMatrixValue(String result){
        String matrixValue="";
        if(result!=null){
            String[] resultArray = result.split(",");
            for(int i=0;i<resultArray.length;i++){
                int i1Index = Integer.parseInt(String.valueOf(resultArray[i].charAt(0)));
                int j1Index = Integer.parseInt(String.valueOf(resultArray[i].charAt(1)));
                int i2Index = Integer.parseInt(String.valueOf(resultArray[i].charAt(2)));
                int j2Index = Integer.parseInt(String.valueOf(resultArray[i].charAt(3)));
                String first = matrix[i1Index][j1Index];
                String second = matrix[i2Index][j2Index];
                //System.out.println("individuals:"+first+" "+second);
                String valueToSearch = fusion(first,second);
                //System.out.println("fusion: "+valueToSearch);
                if(!matrixValue.contains(findMultipleValues(valueToSearch))){
                    matrixValue += findMultipleValues(valueToSearch);
                }
                matrixValue =  matrixValue.replaceAll("(.)\\1", "$1");
                //System.out.println("founded:"+matrixValue);
            }
        }
        return matrixValue;
    }

    private void fillIndexes(String key,String value){
        if(indexes.get(key)!=null){
            String contained = indexes.get(key);
            indexes.put(key,contained+","+value);
        }
        else{
            indexes.put(key,value);
        }
    }

    public void watchMatrix(){
        for(int i=0;i<stringLength;i++){
            for(int j=0;j<stringLength;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println("");
        }
    }

    public boolean confirmStringInGrammar(){
        if(matrix[0][stringLength-1].contains(startupChar+"")){
            return true;
        }
        return false;
    }





}
