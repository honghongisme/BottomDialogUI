package com.example.uitest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BottomPopActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_pop);

        initData();
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("第2话/更新至第8话");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("back");
                // finish();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.detail_btn:
                        System.out.println("详情");
                }
                return true;
            }
        });
        hideToolbar();

        View transparent = findViewById(R.id.transparent_view);
        transparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 设置初始化滚动的位置  隐藏顶部
        final PullScrollView scrollView = findViewById(R.id.scroll_view);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, PullScrollView.HIDDEN_HEIGHT);
            }
        });
        scrollView.setOnPullScrollListener(new OnScrollListener() {
            @Override
            public void onSlideDownAfterReachTop() {
                // 关闭
                finish();
            }

            @Override
            public void onSlideDownOnContent() {
                // 显示toolbar
                showToolbar();
            }

            @Override
            public void onSlideToHeader() {
                hideToolbar();
            }

            @Override
            public void onSlideUp() {
                // 隐藏toolbar
                hideToolbar();
            }

            @Override
            public void onClickContent() {
                toggleToolbar();
            }
        });

        Button comicDetailBtn = findViewById(R.id.comic_detail_btn);
        comicDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("detail");
            }
        });

        Button markBtn = findViewById(R.id.mark_btn);
        markBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("mark");
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initData() {
        ArrayList<String> data = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            data.add("第" + i + "条数据");
        }
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        final ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
//        listView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                toggleToolbar();
//                return false;
//            }
//        });
    }

    private void hideToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null && actionBar.isShowing()) {
            actionBar.hide();
        }
    }

    private void showToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null && !actionBar.isShowing()) {
            actionBar.show();
        }
    }

    private void toggleToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (actionBar.isShowing()) {
                actionBar.hide();
            } else {
                actionBar.show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fast_read_toolbar, menu);
        return true;
    }
}
