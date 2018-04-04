//package com.example.user.myapplication.ui.search;
//
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.SearchView;
//import android.util.Log;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import gh.com.cmg.data.entities.Item;
//import gh.com.cmg.http.Router;
//import gh.com.cmg.http.callback.ResponseHandler;
//import gh.com.cmg.utils.ImageLoader;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.CompositeDisposable;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
///**
// * @author crazzyghost.
// *         Created on 1/23/2018
// */
//
//public class SearchPresenter implements SearchMVP.Presenter, ResponseHandler<ArrayList<Item>> {
//
//
//    private CompositeDisposable compositeDisposable;
//    private SearchMVP.View view;
//    private SearchMVP.Model model;
//
//    private List<Item> itemList;
//    private Router router;
//
//    public SearchPresenter(CompositeDisposable compositeDisposable, SearchMVP.Model model,
//                           Router router){
//        this.model = model;
//        this.compositeDisposable = compositeDisposable;
//        this.itemList = new ArrayList<>();
//        this.router = router;
//    }
//
//
//    @Override
//    public void attach(SearchMVP.View view) {
//        this.view = view;
//    }
//
//    @Override
//    public void detach() {
//        this.view = null;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder view, int position) {
//
//        final Item item = get(position);
//
//        if(view instanceof SearchItem){
//            ((SearchItem)view).setItemName(item.getName());
//            ((SearchItem)view).setItemPrice("GHC " + String.valueOf(item.getPrice()));
//            ((SearchItem)view).setItemSubcategoryName(item.getSubcategory().getName());
//            ((SearchItem)view).setItemImageByUrl(ImageLoader.loadThumbnailUrl(item.getImages()));
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return itemList.size();
//    }
//
//    @Override
//    public void setView(SearchMVP.View view) {
//        this.view = view;
//    }
//
//    @Override
//    public Item get(int position) {
//        return itemList.get(position);
//    }
//
//    @Override
//    public void initRxSearch(SearchView searchView) {
//        compositeDisposable.add(
//                RxSearch.fromSearchView(searchView)
//                        .debounce(300, TimeUnit.MILLISECONDS)
//                        .filter(item -> item.length() > 2)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                this::performSearch,
//                                throwable -> {},
//                                this::handleComplete
//                        )
//        );
//
//    }
//
//    @Override
//    public CompositeDisposable getCompositeDisposable() {
//        return compositeDisposable;
//    }
//
//    private void updateList(ArrayList<Item> itemList){
//        this.itemList = itemList;
//    }
//
//    private void performSearch(String string){
//
//
//        if(view != null){
//            view.showLoading(true);
//
//            Call<List<Item>> searchRequest = router.search(string);
//
//            searchRequest.enqueue(new Callback<List<Item>>() {
//
//                @Override
//                public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
//
//                    if(response.isSuccessful()){
//                        handleSuccess((ArrayList<Item>)response.body());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<Item>> call, Throwable t) {
//                    handleFailure(t);
//                }
//
//            });
//        }
//
//    }
//
//    @Override
//    public void handleFailure(Throwable t) {
//        if(view != null){
//            Log.i("Search Activity Error: ",t.toString());
//            view.showLoading(false);
//        }
//    }
//
//    @Override
//    public void handleSuccess(ArrayList<Item> items) {
//        if(view != null){
//            this.itemList = items;
//            view.showLoading(false);
//            view.onDataSetChanged();
//            updateList(items);
//        }
//
//    }
//
//    public void handleComplete(){
//        if (view != null){
//            view.searchQueryTextSubmitted(this.itemList);
//        }
//    }
//
//    /**
//     * TODO work on loading recent foods
//     */
//    @Override
//    public void resetMeals(){
//        itemList = new ArrayList<>();
//    }
//
//
//
//}
