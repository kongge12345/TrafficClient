/**
 * 
 */
package com.mad.trafficclient.fragment;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class PaneEnableSlidingLayout extends SlidingPaneLayout
{
	private boolean ProhibitPaneSliding=false;

	public boolean isProhibitPaneSliding() {
		return ProhibitPaneSliding;
	}

	public void setProhibitPaneSliding(boolean prohibitPaneSliding) {
		ProhibitPaneSliding = prohibitPaneSliding;
	}

	public PaneEnableSlidingLayout(Context context) {
		super(context,null);
	}

	public PaneEnableSlidingLayout(Context context, AttributeSet attrs) {
		super(context, attrs,0);
	}

	public PaneEnableSlidingLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (MotionEventCompat.getActionMasked(ev)){
			case MotionEvent.ACTION_MOVE:
				if (ProhibitPaneSliding){
					return false;
				}
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (MotionEventCompat.getActionMasked(ev)){
			case MotionEvent.ACTION_MOVE:
				if (ProhibitPaneSliding){
					return false;
				}
		}
		return super.onTouchEvent(ev);
	}
}
