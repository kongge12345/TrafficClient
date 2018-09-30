package com.mad.trafficclient.fragment;

import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 123 on 2018/4/23.
 */

public class PagerEnablePaneSlidingLayout extends SlidingPaneLayout {
    private boolean ProhibitSideslib=false;

    public boolean getProhibitSideslib() {
        return ProhibitSideslib;
    }

    public void setProhibitSideslib(boolean prohibitSideslib) {
        ProhibitSideslib = prohibitSideslib;
    }

    public PagerEnablePaneSlidingLayout(Context context) {
        super(context,null);
    }

    public PagerEnablePaneSlidingLayout(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public PagerEnablePaneSlidingLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (MotionEventCompat.getActionMasked(ev)){
            case MotionEvent.ACTION_MOVE:
                if (ProhibitSideslib){
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (MotionEventCompat.getActionMasked(ev)){
            case MotionEvent.ACTION_MOVE:
                if (ProhibitSideslib){
                    return false;
                }
        }
        return super.onTouchEvent(ev);
    }
}
