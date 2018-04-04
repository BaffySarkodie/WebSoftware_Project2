package com.example.user.myapplication.util.pager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class PinnableViewPager extends ViewPager {
    private boolean swipeEnabled;


    public PinnableViewPager(Context context, AttributeSet attrs){
        super(context,attrs);
        this.swipeEnabled = true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        
        if(this.swipeEnabled){
            return super.onTouchEvent(ev);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(this.swipeEnabled){
            return  super.onInterceptTouchEvent(ev);
        }
        
        return false;
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,false);
    }
    
    public void enableSwipe(boolean swipeEnabled){
        this.swipeEnabled = swipeEnabled;
    }
}
