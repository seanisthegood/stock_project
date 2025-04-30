package com.mac286.project;

import java.util.Vector;

public class Helper {
    //Write a static method that accepts a path and a file name
    //it opend the file (file symbols) and reads it and creates a
    //Vector of strings of all files.
    public static Vector<String> loadSymbols(String path, String file){
        //TODO: Create a Vector symbols
        Vector<String> V = new Vector<>();
        //Open the input file and read line by line, trim the string

        //and add it to the Vector symbols
        //return the vector
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