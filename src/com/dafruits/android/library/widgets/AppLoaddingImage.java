package com.dafruits.android.library.widgets;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.dafruits.android.library.R;

/**
 * 
 * @description 利用图片方式实现
 * @autor Ryze 2015-5-20 下午1:55:57
 * @see AppLoaddingImage2 通过直接绘制实现
 */
public class AppLoaddingImage extends ImageView implements AnimatorListener, AnimatorUpdateListener {


  private Drawable bg_Drawable;
  private Drawable line_Drawable;


  private int width;

  private int height;

  private boolean isStart = false;

  private float degree = 0;

  private ValueAnimator valueAnimator;

  /**
   * @param context
   * @param attrs
   * @param defStyleAttr
   * @param defStyleRes
   */
  @SuppressLint("NewApi")
  public AppLoaddingImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  /**
   * @param context
   * @param attrs
   * @param defStyle
   */
  public AppLoaddingImage(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  /**
   * @param context
   * @param attrs
   */
  public AppLoaddingImage(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  /**
   * @param context
   */
  public AppLoaddingImage(Context context) {
    super(context);
    init();
  }


  private void init() {
    bg_Drawable = getResources().getDrawable(R.drawable.global_loading_cycle_bg);
    line_Drawable = getResources().getDrawable(R.drawable.global_loading_cycle_line);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    width = w;
    height = h;

    int dr_width = width;
    int dr_height = height;


    if (dr_width > bg_Drawable.getIntrinsicWidth()) {
      dr_width = bg_Drawable.getIntrinsicWidth();
    }
    if (dr_height > bg_Drawable.getIntrinsicHeight()) {
      dr_height = bg_Drawable.getIntrinsicHeight();
    }

    bg_Drawable.setBounds(width / 2 - dr_width / 2, height / 2 - dr_height / 2, width / 2
        + dr_width / 2, height / 2 + dr_height / 2);
    line_Drawable.setBounds(width / 2 - dr_width / 2, height / 2, width / 2, height / 2 + dr_height
        / 2);

  }



  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);

    if (visibility == VISIBLE) {
      if (!isStart) {
        isStart = true;
        valueAnimator = ValueAnimator.ofFloat(0, 360);
        valueAnimator.setDuration(1500);
        valueAnimator.setRepeatMode(ValueAnimator.INFINITE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addListener(this);
        valueAnimator.addUpdateListener(this);
        valueAnimator.start();
      }
    } else {
      if (isStart) {
        isStart = false;
        valueAnimator.cancel();
        valueAnimator.removeAllListeners();
        valueAnimator.removeAllUpdateListeners();
        isStart = false;
        valueAnimator = null;
        postInvalidate();
      }

    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (isStart) {
      bg_Drawable.draw(canvas);
      canvas.rotate(degree, width / 2, height / 2);
      line_Drawable.draw(canvas);
      canvas.restore();
    }

  }


  @Override
  public void onAnimationUpdate(ValueAnimator animation) {

    degree = (Float) animation.getAnimatedValue();
    invalidate();

  }


  @Override
  public void onAnimationStart(Animator animation) {
    // TODO Auto-generated method stub

  }


  @Override
  public void onAnimationEnd(Animator animation) {
    // TODO Auto-generated method stub

  }


  @Override
  public void onAnimationCancel(Animator animation) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onAnimationRepeat(Animator animation) {
    // TODO Auto-generated method stub

  }
}
