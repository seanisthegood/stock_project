package com.mac286.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class SymbolTester {
    public int riskFactor; //TODO: Change this to suit your need
    public String mSymbol;
    public String dataPath; //= "C:\Users\oaith\Courses\MAC286\Fall2023\Data\";

    public Vector<Bar> mData;
    public Vector<Trade> mTrades;
    private boolean loaded = false;

    //Tests one symbol for specific risk factor.
    public SymbolTester(String s, String p, int risk) {
        riskFactor = risk;
        mSymbol = s;
        dataPath = p;
        mData = new Vector<Bar>(3000, 100);
        mTrades = new Vector<Trade>(200, 100);
        loaded = false;
    }

    public Vector<Trade> getTrades() {
        return mTrades;
    }

    public Vector<Bar> getBar() {
        return mData;
    }

    public void loadData() {
        //create file name
        String fileName = dataPath + mSymbol + "_Daily.csv";
        //"C:\Users\oaith\Courses\MAC286\Fall2023\Data\AAPL_Daily.csv"
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                //create a bar using the constructor that accepts the data as a String
                Bar b = new Bar(line);
                //add the bar to the Vector
//                System.out.println(b);
                mData.add(b);
            }
            loaded = true;
            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("Something is wrong: " + e.getMessage());
            loaded = false;
            return;
        }
    }

    private boolean xDaysLow(int ind, int days) {
        for (int i = ind - 1; i > ind - days; i--) {
            if (mData.elementAt(i).getLow() < mData.elementAt(ind).getLow())
                return false;
        }
        return true;
    }

    private boolean xDaysHigh(int ind, int days) {
        for (int i = ind - 1; i > ind - days; i--) {
            if (mData.elementAt(i).getHigh() > mData.elementAt(ind).getHigh())
                return false;
        }
        return true;
    }

    public boolean test() {
        if (!loaded) {
            loadData();
            if (!loaded) {
                System.out.println("cannot load data");
                return false;
            }
        }

        //display the first 120 bars
        /*As an example let's test the following pattern
         * 1- today makes a 10 days low
         * 2- today is an outside bar (reversal) today's low is smaller than yesterday's low and today's high is larger than yesterday's high.
         * 3- today's close near the high (within less than 10%) (high-close)/(high-low)<0.1;
         * 4- buy at open tomorrow and stop today's low and target factor*risk
         */

        //TODO: Code your pattern here !!!

        for (int i = 20; i < mData.size() - 10; i++) {
            if (xDaysLow(i, 20)
                    && mData.elementAt(i).getLow() < mData.elementAt(i - 1).getLow()
                    && mData.elementAt(i).getHigh() > mData.elementAt(i - 1).getHigh()
                    && (mData.elementAt(i).getHigh() - mData.elementAt(i).getClose()) / (mData.elementAt(i).range()) < 0.1) {
                //we have a trade, buy at opne of i+1 (tomorrow) stoploss i.low, target = entry+factor*risk
                float entryprice = mData.elementAt(i + 1).getOpen();
                Trade T = new Trade();
                T.open(mSymbol, mData.elementAt(i + 1).getDate(), entryprice, mData.elementAt(i + 1).getLow(), entryprice * riskFactor, Direction.LONG);
                T.close(mData.elementAt(i + riskFactor).getDate(), mData.elementAt(i + riskFactor).getClose(), riskFactor);
                //add the trade to the Trade vector
                mTrades.add(T);

                //Short for reverse trade change low to high, high to low larger to smaller and smaller to larger
            } else if (xDaysHigh(i, 20)
                    && mData.elementAt(i).getHigh() > mData.elementAt(i - 1).getHigh()
                    && mData.elementAt(i).getLow() < mData.elementAt(i - 1).getLow()
                    && (mData.elementAt(i).getClose() - mData.elementAt(i).getLow()) / (mData.elementAt(i).getHigh() - mData.elementAt(i).getLow()) < 0.1) {
                //we have a trade, buy at opne of i+1 (tomorrow) stoploss i.low, target = entry+factor*risk
                float entryprice = mData.elementAt(i + 1).getOpen();
//
                Trade T = new Trade();
                T.open(mSymbol, mData.elementAt(i + 1).getDate(), entryprice, 0, 0, Direction.SHORT);
                T.close(mData.elementAt(i + riskFactor).getDate(), mData.elementAt(i + riskFactor).getClose(), riskFactor);
//                T.close(mSymbol;mData.elementAt(i+2).getDate(),);
//                //close the trade as in long trade.
//                //add the trade to the Trade vector
                mTrades.add(T);
            }
        }
        System.out.println(mTrades);

        return true;
    }
}

