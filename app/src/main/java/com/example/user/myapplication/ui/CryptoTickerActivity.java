package com.example.user.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.evrencoskun.tableview.TableView;
import com.example.user.myapplication.R;
import com.example.user.myapplication.models.Ticker;
import com.example.user.myapplication.ui.search.CryptoSearchActivity;
import com.example.user.myapplication.util.table.TableViewAdapter;
import com.example.user.myapplication.util.table.model.CellModel;
import com.example.user.myapplication.util.table.model.ColumnHeaderModel;
import com.example.user.myapplication.util.table.model.RowHeaderModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CryptoTickerActivity extends AppCompatActivity {

    private List<Ticker> tickers = new ArrayList<>();

    private List<List<CellModel>> mCellList;
    private List<ColumnHeaderModel> mColumnHeaderList;
    private List<RowHeaderModel> mRowHeaderList;

    private TableViewAdapter mTableAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_ticker);
        TableView tableView = findViewById(R.id.content_container2);
        mTableAdapter = new TableViewAdapter(this);
        this.tickers = readData();
        populatedTableView(this.tickers);
        tableView.setAdapter(mTableAdapter);


    }


    private List<Ticker> readData() {
        InputStream is = getResources().openRawResource(R.raw.cypto);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        List<Ticker> tickers = new ArrayList<>();

        try {
            while ((line = reader.readLine()) != null) {
                // Split the line into different tokens (using the comma as a separator).
                String[] tokens = line.split(",");

                // Read the data and store it in the WellData POJO.
                Ticker t  = new Ticker();
                t.setSymbol(tokens[0]);
                t.setCompanyName(tokens[1]);
                tickers.add(t);
            }
            return tickers;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ticker_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.search:
                Intent i = new Intent(this, CryptoSearchActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void populatedTableView(List<Ticker> userInfoList) {
        // create Models
        mColumnHeaderList = createColumnHeaderModelList();
        mCellList = loadCellModelList(userInfoList);
        mRowHeaderList = createRowHeaderList();

        // Set all items to the TableView
        mTableAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
    }


    private List<ColumnHeaderModel> createColumnHeaderModelList() {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        list.add(new ColumnHeaderModel("Symbol"));
        list.add(new ColumnHeaderModel("Currency"));

        return list;
    }

    private List<List<CellModel>> loadCellModelList(List<Ticker> userInfoList) {
        List<List<CellModel>> lists = new ArrayList<>();

        // Creating cell model list from UserInfo list for Cell Items
        // In this example, UserInfo list is populated from web service

        for (int i = 0; i < userInfoList.size(); i++) {
            Ticker userInfo = userInfoList.get(i);

            List<CellModel> list = new ArrayList<>();

            // The order should be same with column header list;
            list.add(new CellModel("1-" + i, userInfo.getSymbol()));       // "Id"
            list.add(new CellModel("2-" + i, userInfo.getCompanyName()));     // "Name"

            // Add
            lists.add(list);
        }

        return lists;
    }

    private List<RowHeaderModel> createRowHeaderList() {
        List<RowHeaderModel> list = new ArrayList<>();
        for (int i = 0; i < mCellList.size(); i++) {
            // In this example, Row headers just shows the index of the TableView List.
            list.add(new RowHeaderModel(String.valueOf(i + 1)));
        }
        return list;
    }
}
