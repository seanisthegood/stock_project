package com.mac286.project;

import java.io.*;
import java.util.Vector;


public class Helper {
    //Write a static method that accepts a path and a file name
    //it opend the file (file symbols) and reads it and creates a
    //Vector of strings of all files.
    public static Vector<String> loadSymbols(String path, String file) {
        //TODO: Create a Vector symbols
        Vector<String> V = new Vector<>();
        String fileName = path + "/" + file;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();

            String str;


            while ((str = reader.readLine()) != null) {
                V.add(line);
                line = reader.readLine();
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
//        for(int i = 0; i <  trades.size(); i++) {
            //compute all the stats

            // overall stats
            double profitSum = 0.0;
            double totalDays = 0.0;
            int wins = 0;

            // long and short stats
            double profitSumLong = 0.0; // profit percentage for long trades
            double sumDaysLong = 0.0; // total days held for the longs
            int winsLong = 0; // how many longs are winners
            int countLongs = 0; // total amount of long trades

            double profitSumShort = 0.0; // profit percentage for short trades
            double sumDaysShort = 0.0; // total days held for the short
            int winsShort = 0; // how many shorts are winners
            int countShort = 0; // total amount of shorts trades

            // loop through every trade
//            System.out.println(trades.size());
            for (Trade t : trades){
                double profitPercentage = t.percentPL();
                double daysHeld = t.getHoldingPeriod();

                // update overall stats first to know what we're dealing with so that
                // later we can assign them as long or shorts
                profitSum += t.percentPL();
                totalDays += t.getHoldingPeriod();

                if (profitPercentage > 0){
                    wins++;
                }

                // if the trade is a long, then we add a count to the longs, add the
                // profit percentage to the profit sum, adds the days and counts the win if
                // the profit percentage is profitable
                if (t.getDir() == Direction.LONG){
                    countLongs++;
//                    profitSum += profitPercentage;
                    sumDaysLong += daysHeld;
                    profitSumLong += profitPercentage;

                    if (profitPercentage > 0){
                        winsLong++;
                    }
                    // same thing applies here but if the trade t is short
                } else if (t.getDir() == Direction.SHORT) {
                    countShort++;
                    profitSumShort += profitPercentage;
                    sumDaysShort += daysHeld;
                    if (profitPercentage > 0){
                        winsShort++;
                    }
                }


                int totalCount = trades.size();

                // now we assign the overall stats for average profit and the like
                stat.averageProfit = (totalCount > 0 ? profitSum / totalCount : 0.0);
                // amount of days held over total amount of days
                stat.averageHoldingPeriod = (totalCount > 0 ? totalDays / totalCount : 0.0);
                // if total days is greater than zero, then the total sum div days is teh average profit per day
                stat.averageProfitPerDay = (totalDays > 0 ? profitSum / totalDays : 0.0);
                // calculates the overall winning pecentage ratio by turing the decimal to
                // a whole number over the size of the trade
                stat.winningPercent = (totalCount > 0 ? (wins * 100.00) / totalCount : 0.0);

                // LONG and SHORT specific stats, basically follows the same logic as the pervious ones
                stat.averageProfitLong = (countLongs > 0 ? profitSumLong / countLongs : 0.0);
                stat.averageHoldingPeriodLong = (countLongs > 0 ? sumDaysLong / countLongs: 0.0);
                stat.averageProfitPerDayLong = (sumDaysLong > 0 ? profitSumLong / sumDaysLong : 0);
                stat.winningPercentLong = (countLongs > 0 ? (winsLong * 100.00) / countLongs : 0.0);

                stat.averageProfitShort = (countShort > 0 ? profitSumShort / countShort : 0.0);
                stat.averageHoldingPeriodShort = (countShort > 0 ? sumDaysShort / countShort : 0.0);
                stat.averageProfitPerDayShort = (sumDaysShort > 0 ? profitSumShort / sumDaysShort : 0.0);
                stat.winningPercentShort = (countShort > 0 ? (winsShort * 100.00) / countShort : 0.0);
//            }
//            System.out.println(profitSum);

        }
        //return your object.
        return stat;
    }
    public static void exportStatsCSV(String filePath, Vector<Statistics> statsList,
                                      Vector<String> labels, Vector<Integer> riskLevels,boolean reversed) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("Label,RiskLevel,AvgProfit,AvgHoldDays,ProfitPerDay,WinRate," +
                    "AvgProfitLong,AvgHoldLong,ProfitPerDayLong,WinRateLong," +
                    "AvgProfitShort,AvgHoldShort,ProfitPerDayShort,WinRateShort");

            for (int i = 0; i < statsList.size(); i++) {
                Statistics s = statsList.get(i);

                String row;
                if (reversed) {
                    // Swap long/short stats when reversed
                    row = String.join(",",
                            labels.get(i),
                            String.valueOf(riskLevels.get(i)),
                            String.valueOf(s.averageProfit),
                            String.valueOf(s.averageHoldingPeriod),
                            String.valueOf(s.averageProfitPerDay),
                            String.valueOf(s.winningPercent),
                            String.valueOf(s.averageProfitShort),  // SHORT becomes LONG
                            String.valueOf(s.averageHoldingPeriodShort),
                            String.valueOf(s.averageProfitPerDayShort),
                            String.valueOf(s.winningPercentShort),
                            String.valueOf(s.averageProfitLong),   // LONG becomes SHORT
                            String.valueOf(s.averageHoldingPeriodLong),
                            String.valueOf(s.averageProfitPerDayLong),
                            String.valueOf(s.winningPercentLong));
                } else {
                    // Normal export
                    row = String.join(",",
                            labels.get(i),
                            String.valueOf(riskLevels.get(i)),
                            String.valueOf(s.averageProfit),
                            String.valueOf(s.averageHoldingPeriod),
                            String.valueOf(s.averageProfitPerDay),
                            String.valueOf(s.winningPercent),
                            String.valueOf(s.averageProfitLong),
                            String.valueOf(s.averageHoldingPeriodLong),
                            String.valueOf(s.averageProfitPerDayLong),
                            String.valueOf(s.winningPercentLong),
                            String.valueOf(s.averageProfitShort),
                            String.valueOf(s.averageHoldingPeriodShort),
                            String.valueOf(s.averageProfitPerDayShort),
                            String.valueOf(s.winningPercentShort));
                }

                writer.println(row);

            System.out.println("✅ Stats exported to: " + filePath);

        }
        }catch (IOException e) {
            e.printStackTrace();
    }

}}