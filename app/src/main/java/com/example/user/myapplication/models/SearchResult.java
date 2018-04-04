package com.example.user.myapplication.models;

import org.patriques.output.timeseries.data.StockData;
import org.patriques.output.digitalcurrencies.data.SimpelDigitalCurrencyData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 31/03/2018.
 */

public class SearchResult {

    private Map<String,String> meta;
    private List<StockData> data;
    private List<SimpelDigitalCurrencyData> cryptoData;

    public Map<String, String> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, String> meta) {
        this.meta = meta;
    }

    public List<StockData> getData() {
        return data;
    }

    public void setData(List<StockData> data) {
        this.data = data;
    }

    public List<SimpelDigitalCurrencyData> getCryptoData() {
        return cryptoData;
    }

    public void setCryptoData(List<SimpelDigitalCurrencyData> cryptoData) {
        this.cryptoData = cryptoData;
    }
}
