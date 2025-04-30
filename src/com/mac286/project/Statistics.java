package com.mac286.project;

public class Statistics {
    double averageProfit;
    double averageHoldingPeriod;
    double averageProfitPerDay;
    double winningPercent;
    double averageProfitLong;
    double averageHoldingPeriodLong;
    double averageProfitPerDayLong;
    double winningPercentLong;
    //Do the same fgor shorts
    double averageProfitShort;
    double averageHoldingPeriodShort;
    double averageProfitPerDayShort;
    double winningPercentShort;

    @Override
    public String toString() {
        return "Statistics{" +
                "averageProfit=" + averageProfit +
                ", averageHoldingPeriod=" + averageHoldingPeriod +
                ", averageProfitPerDay=" + averageProfitPerDay +
                ", winningPercent=" + winningPercent + "\n" +
                ", averageProfitLong=" + averageProfitLong +
                ", averageHoldingPeriodLong=" + averageHoldingPeriodLong +
                ", averageProfitPerDayLong=" + averageProfitPerDayLong +
                ", winningPercentLong=" + winningPercentLong + "\n" +
                ", averageProfitShort=" + averageProfitShort +
                ", averageHoldingPeriodShort=" + averageHoldingPeriodShort +
                ", averageProfitPerDayShort=" + averageProfitPerDayShort +
                ", winningPercentShort=" + winningPercentShort +
                '}';
    }
}