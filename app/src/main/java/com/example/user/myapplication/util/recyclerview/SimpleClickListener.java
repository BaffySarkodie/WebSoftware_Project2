package com.example.user.myapplication.util.recyclerview;

import android.view.View;

/**
 *
 *
 *  Handles just single taps for recycler view items. Since it is a functional interface
 *  It can be used with lambda expressions
 *
 *  To support both long clicks and single taps,
 *  @see ItemTouchListener
 *  @see ClickListener
 *
 */

public interface SimpleClickListener {

    void onSingleTap(View view, int position);

}
