package com.mac286.project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;
import java.io.FileReader;


public class Helper {
    //Write a static method that accepts a path and a file name
    //it opend the file (file symbols) and reads it and creates a
    //Vector of strings of all files.
    public static Vector<String> loadSymbols(String path, String file) {
        //TODO: Create a Vector symbols
        Vector<String> V = new Vector<>();
        String fileName = path + "/"+ file;

        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                V.add(line);
            }
//

            //Open the input file and read line by line, trim the string
            // get the sybols
            //and add it to the Vector symbols

            //return the vector
            return V;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return V;
    }

        //Write a Static method that accepts a Vector of Trades and goes
    //through it to compute all statistics, return the statistics as
    //an object.
    public static Statistics computeStats(Vector<Trade> trades){
        //Create a Statistics object
        Statistics stat = new Statistics();
        //go through Vector trades one by one and compute all the Stats
        for(int i = 0; i < trades.size(); i++) {
            //compute all the stats


        }
        //return your object.
        return stat;
    }
}