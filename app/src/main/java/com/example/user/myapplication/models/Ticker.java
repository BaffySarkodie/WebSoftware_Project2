package com.example.user.myapplication.models;

/**
 * Created by user on 31/03/2018.
 */

public class Ticker {

    private String symbol;
    private String companyName;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "Ticker{" +
                "symbol='" + symbol + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
