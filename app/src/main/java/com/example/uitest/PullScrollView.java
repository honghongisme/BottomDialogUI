package com.example.uitest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/**
 *
 */
public class PullScrollView extends NestedScrollView implements View.OnScrollChangeListener, View.OnTouchListener{

    // 顶部隐藏部分的高度
    public static final int HIDDEN_HEIGHT = 150;
    // 这个高度以上 toolbar可以显示
    public static final int TOOLBAR_SHOW_HEIGHT = 500;

    // 上一次手指触碰的y位置
    private float mLastY;
    // 是否第一次触顶
    private boolean mIsFirstReachTop;
    // 是否触碰到屏幕
    private boolean mIsTouch;
    // 是否是点击事件
    private boolean mIsClick;

    private OnScrollListener mOnScrollListener;


    public PullScrollView(@NonNull Context context) {
        super(context);
    }

    public PullScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnScrollChangeListener(this);
        setOnTouchListener(this);
    }

    public PullScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnPullScrollListener(OnScrollListener listener) {
        mOnScrollListener = listener;
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
   //     System.out.println(scrollY + " , " + oldScrollY);
        // 按理说加了mIsTouch判断后一次滑动事件里只会触发一次，但实际上onSlideUp和onSlideDown触发都不止一次。。
        if (scrollY > oldScrollY && mIsTouch) { // 上滑
            System.out.println("隐藏toolbar");
            if (mOnScrollListener != null) {
                mOnScrollListener.onSlideUp();
            }
        } else { // 下滑
            if(scrollY > TOOLBAR_SHOW_HEIGHT && mIsTouch) {
                if (mOnScrollListener != null) {
                    mOnScrollListener.onSlideDownOnContent();
                }
            } else if (scrollY <= TOOLBAR_SHOW_HEIGHT){
                if (mOnScrollListener != null) {
                    mOnScrollListener.onSlideToHeader();
                }
            }
        }
        if (scrollY <= HIDDEN_HEIGHT) {
            if (!mIsTouch) { // 惯性滑动触碰到顶部
                scrollTo(0, HIDDEN_HEIGHT);
            } else {
                // 缓慢滑动 调整滚动速度  待解决
            }
            mIsFirstReachTop = true;
        } else {
            mIsFirstReachTop = false;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float offsetY;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float y = event.getY();
                mLastY = y;
                mIsTouch = true;
                mIsClick = true;
                break;
            case MotionEvent.ACTION_MOVE:
                mIsClick = false;
                break;
            case MotionEvent.ACTION_UP:
                if (mIsClick && getScrollY() > TOOLBAR_SHOW_HEIGHT && mOnScrollListener != null) {
                    mOnScrollListener.onClickContent();
                }
                offsetY = event.getY() - mLastY;
                if(offsetY > 0 && mIsFirstReachTop ) { // 下拉隐藏
                    System.out.println("下拉隐藏");
                    if (mOnScrollListener != null) {
                        mOnScrollListener.onSlideDownAfterReachTop();
                    }
                }
                mIsTouch = false;
                break;
        }
        return false;
    }
}

 interface OnScrollListener {

    // 触顶后手动下滑 按下抬起过程中只触发一次
    void onSlideDownAfterReachTop();
    // header以下下滑  触发多次
    void onSlideDownOnContent();
     // header以上下滑  触发多次
    void onSlideToHeader();
    // 上滑 触发多次
    void onSlideUp();
    // 点击内容
    void onClickContent();
}
