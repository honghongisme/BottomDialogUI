package com.example.uitest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import androidx.annotation.Nullable;

public class InterceptorScrollListView extends ListView {

    public InterceptorScrollListView(Context context) {
        super(context);
    }

    public InterceptorScrollListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptorScrollListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }
}
