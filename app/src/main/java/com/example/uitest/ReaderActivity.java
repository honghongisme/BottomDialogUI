package com.example.uitest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ReaderActivity extends AppCompatActivity {

    private ListView mListView;
    private float mLastY;
    private boolean mIsTop = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        initData();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initData() {
        ArrayList<String> data = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            data.add("第" + i + "条数据");
        }
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        mListView = findViewById(R.id.list);
        mListView.setAdapter(adapter);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            mListView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    if (mListView.getFirstVisiblePosition() == 0) {
//                        mIsTop = true;
//                    }
//                }
//            });
//        }
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE && mListView.getFirstVisiblePosition() == 0) {
                    mIsTop = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float offsetY;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        float y = event.getY();
                        mLastY = y;
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetY = event.getY() - mLastY;
                        if(offsetY > 0) { // 下滑隐藏
                            if (mListView.getFirstVisiblePosition() == 0) {
                                if (mIsTop) {
                                    finish();
                                } else {
                                    mIsTop = true;
                                }
                            } else {
                                mIsTop = false;
                            }
                        } else if (offsetY < 0) {
                            mIsTop = false;
                        }
                        break;
                }
                return false;
            }
        });
    }

    public boolean isListViewReachTopEdge() {
        boolean result=false;
        if (mListView.getFirstVisiblePosition() == 0) {
            final View topChildView  = mListView.getChildAt(0);
            result= topChildView.getTop() == 0;
        }
        return result;
    }
}
