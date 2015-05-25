package com.dafruits.android.library.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dafruits.android.library.R;

/**
 * 
 * @description TODO
 * @autor Ryze 2015-5-21 上午10:43:25
 * 
 */
public class PullToRefreshView extends ListView implements OnScrollListener {


  private static final int N = 5;

  private static final int IDLE = -1;

  private static final int PULLING = 0;

  private static final int ONREFRESH = 1;

  private int status = IDLE;


  private View pullHeaderView;

  private ImageView arrow_iv;

  private ProgressBar loadding_pb;
  private Animation animation0;
  private Animation animation1;
  private Animation animation2;


  private TextView pull_tip_tv;


  private int headerHeight = 0;


  private float downY = 0;
  private float mDis = 0;

  /**
   * @param context
   * @param attrs
   * @param defStyle
   */
  @SuppressLint("NewApi")
  public PullToRefreshView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  /**
   * @param context
   * @param attrs
   */
  public PullToRefreshView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  /**
   * @param context
   */
  public PullToRefreshView(Context context) {
    super(context);
    init();
  }


  private void init() {

    pullHeaderView =
        LayoutInflater.from(getContext()).inflate(R.layout.pull_headerview_layout, null);

    arrow_iv = (ImageView) pullHeaderView.findViewById(R.id.pull_arrow);
    pull_tip_tv = (TextView) pullHeaderView.findViewById(R.id.pull_text);
    loadding_pb = (ProgressBar) pullHeaderView.findViewById(R.id.pull_progress);

    animation1 =
        new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
    animation1.setDuration(250);
    animation1.setFillAfter(true);

    animation2 =
        new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
    animation2.setDuration(250);
    animation2.setFillAfter(true);
    pullHeaderView.measure(0, 0);
    headerHeight =
        pullHeaderView.getMeasuredHeight() + pullHeaderView.getPaddingTop() + getPaddingTop();
    pullHeaderView.setPadding(0, -1 * headerHeight, 0, 0);

    setOnScrollListener(this);

    status = PULLING;

    addHeaderView(pullHeaderView);

  }



  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    float y = ev.getY();
    switch (ev.getAction()) {
      case MotionEvent.ACTION_DOWN:
        downY = ev.getY();

        break;
      case MotionEvent.ACTION_MOVE:

        if (getFirstVisiblePosition() == 0) {
          pullHeaderView.setPadding(pullHeaderView.getPaddingLeft(),
              (int) ((-1 * headerHeight) + (y - downY)) / N, pullHeaderView.getPaddingRight(),
              pullHeaderView.getPaddingBottom());
        }
        if (pullHeaderView.getPaddingTop() > headerHeight) {
          arrow_iv.startAnimation(animation2);
        } else {
          arrow_iv.startAnimation(animation1);
        }

        // downY = y;

        break;
      case MotionEvent.ACTION_UP:
        if (pullHeaderView.getBottom() > 0) {
          setSelection(1);
        }
        downY = y;
        break;
      default:
        break;
    }
    return super.onTouchEvent(ev);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * android.widget.AbsListView.OnScrollListener#onScrollStateChanged(android.widget.AbsListView,
   * int)
   */
  @Override
  public void onScrollStateChanged(AbsListView view, int scrollState) {


  }

  /*
   * (non-Javadoc)
   * 
   * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int,
   * int)
   */
  @Override
  public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
      int totalItemCount) {


  }



}
