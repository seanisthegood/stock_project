package com.mac286.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.io.FileWriter;



public class SymbolTester {
    private boolean reverse;
    private int riskFactor; //TODO: Change this to suit your need
    private String mSymbol;
    private String dataPath; //= "C:\Users\oaith\Courses\MAC286\Fall2023\Data\";

    private Vector<Bar> mData;
    private Vector<Trade> mTrades;
    private boolean loaded = false;

    //Tests one symbol for specific risk factor.
    public SymbolTester(String s, String p, int risk) {
        this(s, p, risk, false);
    }

    // Full constructor with reverse option
    public SymbolTester(String s, String p, int risk, boolean reverse) {
        this.riskFactor = risk;
        this.mSymbol = s;
        this.dataPath = p;
        this.reverse = reverse;
        this.mData = new Vector<Bar>(3000, 100);
        this.mTrades = new Vector<Trade>(200, 100);
        this.loaded = false;
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


            for (int i = 20; i < mData.size() - 10; i++) {
                boolean isLongPattern = xDaysLow(i, 20)
                        && mData.elementAt(i).getLow() < mData.elementAt(i - 1).getLow()
                        && mData.elementAt(i).getHigh() > mData.elementAt(i - 1).getHigh()
                        && (mData.elementAt(i).getHigh() - mData.elementAt(i).getClose()) / mData.elementAt(i).range() < 0.1;

                boolean isShortPattern = xDaysHigh(i, 20)
                        && mData.elementAt(i).getHigh() > mData.elementAt(i - 1).getHigh()
                        && mData.elementAt(i).getLow() < mData.elementAt(i - 1).getLow()
                        && (mData.elementAt(i).getClose() - mData.elementAt(i).getLow()) / mData.elementAt(i).range() < 0.1;

                float entryPrice = mData.elementAt(i + 1).getOpen();
                String entryDate = mData.elementAt(i + 1).getDate();
                String exitDate = mData.elementAt(i + riskFactor).getDate();
                float exitPrice = mData.elementAt(i + riskFactor).getClose();

                if (!reverse) {
                    if (isLongPattern) {
                        Trade T = new Trade();
                        T.open(mSymbol, entryDate, entryPrice, mData.elementAt(i + 1).getLow(), entryPrice * riskFactor, Direction.LONG);
                        T.close(exitDate, exitPrice, riskFactor);
                        mTrades.add(T);
                    } else if (isShortPattern) {
                        Trade T = new Trade();
                        T.open(mSymbol, entryDate, entryPrice, 0, 0, Direction.SHORT);
                        T.close(exitDate, exitPrice, riskFactor);
                        mTrades.add(T);
                    }
                } else {
                    if (isShortPattern) {
                        Trade T = new Trade();
                        T.open(mSymbol, entryDate, entryPrice, mData.elementAt(i + 1).getLow(), entryPrice * riskFactor, Direction.LONG);
                        T.close(exitDate, exitPrice, riskFactor);
                        mTrades.add(T);
                    } else if (isLongPattern) {
                        Trade T = new Trade();
                        T.open(mSymbol, entryDate, entryPrice, 0, 0, Direction.SHORT);
                        T.close(exitDate, exitPrice, riskFactor);
                        mTrades.add(T);
                    }
                }

            }

            return true;
        }}