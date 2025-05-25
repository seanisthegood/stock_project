package com.mac286.project;

import java.util.Vector;

/*
Tester will receive a file and path as well as a riskFactor or
how many days until exit
1- In the constructor create a Vector of all symbols (the Vector should be
a member variable of the class)
2- In the constructor, create a Vector of trades, to collect all trades
Have a method run() that will do the follwing:
1- If the Vector of symbols or Trades is empty create again or exit
2- Go through the Vector of symbols one by one and test the symbol using
a SymbolTester class, that you would have modified.
3- collect the trades from the SymbolTester each time a symbol
is tested.
4- hve a method reset() that resets the Vector of symbols and trades
if needed.
 */
public class Tester {
    private Vector<String> vSymbols;
    private Vector<Trade> mTrades;
    private String mPath, mFile;
    private int mRisk;
    public Tester(String path, String file, int risk){
        //set path and file
        mPath = path;
        mFile = file;
        mRisk = risk;
        //create a vector of Trades
        mTrades = new Vector<Trade>();
        //set risk to risk
        //opend the file and create a Vector of symbols. Using Helpers
        vSymbols = Helper.loadSymbols(mPath, mFile);
        // Added Line to See if the vSymbols was working.
//        System.out.println(vSymbols);
    }
    public void setPath(String p){
        mPath = p;
    }
    public void setFile(String f){
        mFile = f;
    }
    public boolean run(){
        //if mSymbols is empty or null, create a new one
        //if mTrades null create a new one.
        //go through the vSymbols, for each symbol,
        // create a symbol Tester with appropriate parameters
        //Loop for the Symbols
        for (String Symb: vSymbols) {


            SymbolTester S = new SymbolTester(Symb,mPath,mRisk,true);
//            System.out.println(S.riskFactor);
//            System.out.println("------");
//            System.out.println(Symb);
//            System.out.println(S.mSymbol);
//            Loop for the Symbols
//            Test the symbol (calling .test() method)

            S.test();

//            System.out.println(S.mSymbol);
//            System.out.println("----");
//            System.out.println();
//            System.out.println(S.mData);
//            System.out.println(mData.size());
//            System.out.println("---------");
//            System.out.println("Size Before Get Trades");
//            System.out.println(mTrades.size());
            //collect the trades by calling getTrades() method of the SymbolTester
            mTrades.addAll(S.getTrades());
//            System.out.println("---------");
//
//            System.out.println("Size After Get Trades");
//            System.out.println(mTrades.size());


        }
//        System.out.print(S.size());
        System.out.print(mTrades.size());
        return true;
    }
    public Vector<Trade> getTrades(){
        return mTrades;
    }
//    public Vector<Bar> getBars(){
//        return mData;
//    }
    public void reset(){
        vSymbols = null;
        mTrades = null;
    }
}