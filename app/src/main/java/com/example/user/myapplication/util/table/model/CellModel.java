package com.example.user.myapplication.util.table.model;

import com.evrencoskun.tableview.sort.ISortableModel;

/**
 * Created by user on 31/03/2018.
 */

public class CellModel implements ISortableModel {
    private String mId;
    private Object mData;

    public CellModel(String pId, Object mData) {
        this.mId = pId;
        this.mData = mData;
    }

    public Object getData() {
        return mData;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public Object getContent() {
        return mData;
    }

}