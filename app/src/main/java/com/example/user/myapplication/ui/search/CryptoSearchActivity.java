package com.example.user.myapplication.ui.search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.myapplication.R;
import com.example.user.myapplication.models.SearchResult;
import com.example.user.myapplication.ui.CryptoChartActivity;
import com.example.user.myapplication.ui.MainActivity;
import com.example.user.myapplication.util.recyclerview.ClickListener;
import com.example.user.myapplication.util.recyclerview.ItemTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.patriques.AlphaVantageConnector;
import org.patriques.DigitalCurrencies;
import org.patriques.input.digitalcurrencies.Market;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.digitalcurrencies.IntraDay;
import org.patriques.output.digitalcurrencies.data.SimpelDigitalCurrencyData;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class CryptoSearchActivity extends AppCompatActivity {

    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_wheel)
    ProgressBar progressBar;
    Intent i;

    private SearchAdapter adapter;
    private List<SearchResult> searchResults;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        i= new Intent(this,CryptoChartActivity.class);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRxSearch(this.searchView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }

    public void init(){
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar()
                    .setTitle("Search");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        compositeDisposable = new CompositeDisposable();
        searchResults = new ArrayList<>();
        adapter = new SearchAdapter(searchResults);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,1,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(this,1));

        recyclerView.addOnItemTouchListener(new ItemTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final SearchResult finalResult = searchResults.get(position);
                Map<String, String> data = finalResult.getMeta();
                String symbol = data.get("2. Digital Currency Code");
                System.out.println(symbol);
                i.putExtra("symbol", symbol);
                startActivity(i);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        SearchView.SearchAutoComplete searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.colorPrimary)); // set the text color
        searchEditText.setHintTextColor(getResources().getColor(R.color.colorPrimaryDark)); // set the hint color

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    return true;
                }
                return false;
            }


        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void initRxSearch(SearchView searchView) {
        /*compositeDisposable.add(
                RxSearch.fromSearchView(searchView)
                        .debounce(300, TimeUnit.MILLISECONDS)
                        .filter(item -> item.length() > 1)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::performSearch,
                                throwable -> {}
                        )
        );*/

    }


    private void performSearch(String string){
        System.out.println("query" + string);
        getJson json = new getJson();
        json.execute(string.toUpperCase());


    }

    private class getJson extends AsyncTask<String, Void ,List<SearchResult>> {


        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }


        @Override
        protected List<SearchResult> doInBackground(String... voids) {

            String apiKey = "JTKHFR9OMD9ECWOW";
            int timeout = 10000;
            String work = voids[0];
            AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
            DigitalCurrencies digitalCurrencies = new DigitalCurrencies(apiConnector);

            List<SearchResult> results = new ArrayList<>();

            try {
                IntraDay response = digitalCurrencies.intraDay(work.toUpperCase(), Market.USD);
                Map<String, String> metaData = response.getMetaData();
                System.out.println("Information: " + metaData.get("1. Information"));
                System.out.println("Currency: " + metaData.get("2. Digital Currency Code"));

                List<SimpelDigitalCurrencyData> cryptoData = response.getDigitalData();
                SearchResult s = new SearchResult();
                s.setCryptoData(cryptoData);
                s.setMeta(metaData);
                results.add(s);
                return results;

            } catch (AlphaVantageException e) {
                System.out.println("something went wrong");
                return results;
            }
        }


        @Override
        protected void onPostExecute(List<SearchResult> results) {
            super.onPostExecute(results);

            searchResults = results;

            adapter.updateList(searchResults);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.INVISIBLE);


        }
    }




}
