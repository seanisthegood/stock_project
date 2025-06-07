package com.mac286.project;

import java.util.Vector;
import java.io.FileWriter;
import java.io.IOException;

/*
 * The Tester class handles:
 * - Loading symbols from a file
 * - Running the SymbolTester on each symbol
 * - Collecting and exporting all resulting trades
 */
public class Tester {
    private Vector<String> vSymbols;
    private Vector<Trade> mTrades;
    private String mPath, mFile;
    private int mRisk;

    public Tester(String path, String file, int risk) {
        mPath = path;
        mFile = file;
        mRisk = risk;
        mTrades = new Vector<Trade>();
        vSymbols = Helper.loadSymbols(mPath, mFile);
    }

    public void setPath(String p) {
        mPath = p;
    }

    public void setFile(String f) {
        mFile = f;
    }

    public boolean run() {
        if (vSymbols == null || vSymbols.isEmpty()) {
            System.err.println("❌ Symbol list is empty.");
            return false;
        }

        for (String symbol : vSymbols) {
            SymbolTester tester = new SymbolTester(symbol, mPath, mRisk, true);
            tester.test();
            mTrades.addAll(tester.getTrades());
        }

        return true;
    }

    public void exportTradesToCSV(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("symbol,entryDate,entryPrice,stopLoss,target,direction,exitDate,exitPrice,holdingPeriod,percentPL\n");

            for (Trade t : mTrades) {
                writer.write(t.getSymbol() + "," +
                        t.getEntryDate() + "," +
                        t.getEntryPrice() + "," +
                        t.getStopLoss() + "," +
                        t.getTarget() + "," +
                        t.getDir() + "," +
                        t.getExitDate() + "," +
                        t.getExitPrice() + "," +
                        t.getHoldingPeriod() + "," +
                        String.format("%.2f", t.percentPL()) + "\n");
            }

            System.out.println("✅ Trades exported to: " + filename);
        } catch (IOException e) {
            System.err.println("❌ Failed to write to file: " + filename);
            e.printStackTrace();
        }
    }

    public Vector<Trade> getTrades() {
        return mTrades;
    }

    public void reset() {
        vSymbols = null;
        mTrades = null;
    }
}
