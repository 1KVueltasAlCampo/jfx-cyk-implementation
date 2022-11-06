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
    }

    private void initialValues(){
        for(int i=0;i<stringLength;i++){
            matrix[i][0] = findValue(string.charAt(i)+"");
        }
    }

    private String findValue(String value){
        String results = "";
        for(int i=0;i<grammar.size();i++){
            for(int j=0;j<CONTENTS_SIZE;j++){
                if(grammar.get(i)[j].equals(value)){
                    results += heads.get(i)+",";
                }
            }
        }
        return results;
    }

    private void fillMatrixIndex(){
        for(int i = 0;i<=stringLength;i++,stringLength--){
            int count = 1;
            for(int k = stringLength;k>0;k--){
                int times = 0;
                for(int j = count;j<stringLength;j++){
                    System.out.println(i+""+j+" = "+i+(stringLength-k)+" "+(count+i)+times);
                    times++;
                }
                count++;
            }

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





}
