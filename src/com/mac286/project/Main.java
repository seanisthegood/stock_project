package com.mac286.project;

import java.util.Vector;

/*
In this class you tesing you main to test All stocks and
ETFs
1- create a Tester with the appropriate path and file name and risk factor
2- Call run
3- Get the trades
4- Call helper function that will compute all statistics from the vector trades
 */
public class Main {
    public static void main(String[] args) {
        //If you are testing a risk based on stoploss and target
        int[] riskFactor = {1, 2, 3, 5, 5};
        //set path to appropriate path ("C:\ProfOmarMAC286\Spring2025_3076\Data")
        String path = "/Users/seanyboy/Documents/IntellliJ/Class_Project/Data/";


        //loop through your risk array and do the following
        for(int i = 0; i < 5; i++) {
            //set file to appropriate file (stocks.txt)
            String fileName = "stocks.txt";
            // Create a Tester with with path file and riskFactor[i]
            Tester stockTester = new Tester(path, fileName, riskFactor[i]);
            //Call run method on the tester
            // get the trades vector getTrades()
            stockTester.run();
            Vector<Trade> stockTrades = stockTester.getTrades();
//            System.out.println(stockTester.getTrades());
            System.out.println(stockTrades.get(1).symbol);


            //call the helper method computerStates with the trade vector as input
            Statistics stats = Helper.computeStats(stockTrades);
            //display the results using the toString of the Statistics method
            System.out.println("--------stats for stocks: risk: " + riskFactor + "-------------");
            System.out.println(stats.toString());
            //Change the filename to ETFs.txt and do the same.
            fileName = "ETFs.txt";
            //do all exactely the same.
            //create a new Tester object for ETFs.
            Tester etfTester = new Tester(path, fileName, riskFactor[i]);
            //run the tester
            etfTester.run();
            Vector<Trade> etfTrades = etfTester.getTrades();
            stats = Helper.computeStats(etfTrades);
            System.out.println("--------stats for etfs: risk: " + riskFactor + "-------------");
//            System.out.println(stats.toString());

            //create a Vector for all trades conbined stocks and etfs
            Vector<Trade> mTrades = stockTrades;
            mTrades.addAll(etfTrades);
            stats = Helper.computeStats(mTrades);
            System.out.println("--------stats combined stocks and etfs: risk: " + riskFactor + "-------------");
            System.out.println(stats.toString());
        }
    }
}