package com.example.uitest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class BottomDialog extends Dialog implements View.OnTouchListener {

    private Activity mContext;
    private View mView;

    private float mLastY;


    public BottomDialog(Context context) {
        super(context);
        this.mContext = (Activity) context;
        init();
        initData();
    }

    public void init() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.bottom_dialog_detail, null);
        mView.setOnTouchListener(this);
        setContentView(mView);
        setCanceledOnTouchOutside(true);

        DisplayMetrics outMetrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int height = outMetrics.heightPixels;

        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.height = (int) (height * 0.8);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);
        win.setWindowAnimations(R.style.BottomDialogAnim);
        win.setBackgroundDrawableResource(android.R.color.background_light);

        Button btn = mView.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Button", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @SuppressLint("ClickableViewAccessibility")
    private void initData() {
        ArrayList<String> data = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            data.add("第" + i + "条数据");
        }
        ListAdapter adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, data);
        ListView listView = mView.findViewById(R.id.list);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float offsetY;
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float y = motionEvent.getY();
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                offsetY = motionEvent.getY() - mLastY;
                if(offsetY > 0) { // 下滑隐藏
                    dismiss();
                } else if (offsetY < 0){ // 上滑切换
                    mContext.startActivity(new Intent(mContext, ReaderActivity.class));
                }
                break;
        }
        return true;
    }
}
