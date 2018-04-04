package com.example.user.myapplication.ui;

/**
 * Created by user on 01/04/2018.
 */

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.user.myapplication.R;
import com.example.user.myapplication.models.SearchResult;
import com.example.user.myapplication.util.table.TableViewAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import android.os.AsyncTask;

import java.util.ArrayList;

import org.patriques.AlphaVantageConnector;
import org.patriques.TimeSeries;
import org.patriques.input.timeseries.Interval;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.timeseries.IntraDay;
import org.patriques.output.timeseries.data.StockData;

import java.util.List;
import java.util.Map;


public class ChartActivity extends AppCompatActivity {

    private LineChart lChart;
    private LineChart lChart2;

    String ticker;
    private  ProgressDialog pDialog;
    private TableViewAdapter mTableAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle searchActivityData=getIntent().getExtras();

        if (searchActivityData != null) {
            ticker = searchActivityData.getString("symbol");
        }

        int i = 0;
        Handler handler = new Handler();

        
        new GetJsonData().execute(ticker);



    }



    private  class GetJsonData extends AsyncTask<String, Void, List<SearchResult>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChartActivity.this);
            pDialog.setMessage("Retrieving Data........");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected List<SearchResult> doInBackground(String... voids) {
            String apiKey = "LDDA8WN6KQSMC7VI";
            int timeout = 3000;
            AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
            TimeSeries stockTimeSeries = new TimeSeries(apiConnector);
            List<SearchResult> results = new ArrayList<>();

            String data = voids[0];

            try {
                IntraDay response = stockTimeSeries.intraDay(data.toUpperCase(), Interval.ONE_MIN, OutputSize.COMPACT);
                Map<String, String> metaData = response.getMetaData();
                System.out.println("Information: " + metaData.get("1. Information"));
                System.out.println("Currency: " + metaData.get("2. Symbol"));

                List<StockData> stockData = response.getStockData();

                SearchResult s = new SearchResult();
                s.setData(stockData);
                s.setMeta(metaData);
                results.add(s);
                return results;

            } catch (AlphaVantageException e) {
                System.out.println("something went wrong");
                return results;
            }
        }

        @Override
        protected void onPostExecute(List<SearchResult> result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
            {
                pDialog.dismiss();
            }

            setupLineChart(result);
            setupText();

        }


    }



    private void setupLineChart(List<SearchResult> results)
    {
        lChart=(LineChart)findViewById(R.id.linechart_prices);
        lChart.setBackgroundColor(Color.GRAY);
        lChart.setDrawGridBackground(false);

        ArrayList<Entry> yVals2=new ArrayList<>();
        int x=0;
        for (SearchResult result : results){
            List<StockData> intermediate = result.getData();

            for (StockData stock: intermediate){
                yVals2.add(new Entry(x, (float) stock.getClose()));
                x++;
            }
        }



        LineDataSet set1,set2;
        set2=new LineDataSet(yVals2,"Share Price In US Dollars");
        set2.setColor(Color.YELLOW);
        set2.setDrawCircles(false);
        set2.setLineWidth(5f);

        LineData data=new LineData(set2);

        lChart.setData(data);

        lChart.animateX(10000);

        Description description=new Description();
        description.setText("");
        description.setTextSize(15);
        lChart.setDescription(description);



        lChart2=(LineChart)findViewById(R.id.linechart_volume);
        lChart2.setBackgroundColor(Color.GRAY);
        lChart2.setDrawGridBackground(false);
        ArrayList<Entry> yVals1=new ArrayList<>();
        int i=0;
        for (SearchResult result : results){
            List<StockData> intermediate = result.getData();

            for (StockData stock: intermediate){
                yVals1.add(new Entry(i, stock.getVolume()));
                i++;
            }
        }

        set1=new LineDataSet(yVals1,"Volume Of Shares Traded");
        set1.setColor(Color.GREEN);
        set1.setDrawCircles(false);
        set1.setLineWidth(3f);

        LineData data2=new LineData(set1);

        lChart2.setData(data2);

        lChart2.animateX(10000);

        Description description2=new Description();
        description2.setText("");
        description2.setTextSize(15);
        lChart2.setDescription(description2);


    }


    private void setupText()
    {
        TextView companyname=(TextView)findViewById(R.id.companyname);
        companyname.setText(ticker);

    }

}
