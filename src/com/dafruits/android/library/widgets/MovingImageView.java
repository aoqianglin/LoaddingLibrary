package com.dafruits.android.library.widgets;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * 
 * @description TODO
 * @autor Ryze 2015-5-25 下午2:40:49
 * 
 */
public class MovingImageView extends ImageView implements AnimatorUpdateListener, AnimatorListener {


  private Drawable drawable;

  private ValueAnimator valueAnimator;

  int width = 0;
  int height = 0;

  float dela = 0;
  boolean repeat = false;


  /**
   * @param context
   * @param attrs
   * @param defStyle
   */
  public MovingImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  /**
   * @param context
   * @param attrs
   */
  public MovingImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  /**
   * @param context
   */
  public MovingImageView(Context context) {
    super(context);
    init();
  }


  private void init() {
    setScaleType(ScaleType.CENTER_CROP);
  }


  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    drawable = getDrawable();
    if (drawable != null) {

      width = drawable.getIntrinsicWidth();
      height = drawable.getIntrinsicHeight();
      if (height > getHeight()) {
        drawable.setBounds(0, 0, width, getHeight());
        startAnimation();
      }

    }

  }

  @Override
  protected void onDraw(Canvas canvas) {
    // super.onDraw(canvas);

    if (drawable != null) {

      canvas.translate(0, -(dela - getHeight()));


      drawable.draw(canvas);

    }

  }


  private void startAnimation() {
    if (valueAnimator == null) {
      valueAnimator = ValueAnimator.ofFloat(getHeight(), height);
      valueAnimator.addUpdateListener(this);
      valueAnimator.addListener(this);
      valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
      valueAnimator.setRepeatMode(ValueAnimator.INFINITE);
      valueAnimator.setDuration(20000);
      valueAnimator.setStartDelay(200);
      valueAnimator.setInterpolator(new LinearInterpolator());
    }
    valueAnimator.start();

  }

  private void stopAnimation() {
    if (valueAnimator != null) {
      valueAnimator.cancel();
      valueAnimator.removeAllListeners();
      valueAnimator.removeAllUpdateListeners();
      valueAnimator = null;
    }
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    stopAnimation();
  }

  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    if (visibility != VISIBLE) {
    	if (valueAnimator != null && valueAnimator.isStarted()) {
            valueAnimator.cancel();
          }
    } else {
      if (valueAnimator != null && !valueAnimator.isStarted()) {
        valueAnimator.start();
      }
    }
  }

  @Override
  public void onAnimationUpdate(ValueAnimator animation) {
    dela = (Float) animation.getAnimatedValue();
    invalidate();
  }


  @Override
  public void onAnimationStart(Animator animation) {

  }


  @Override
  public void onAnimationEnd(Animator animation) {

  }


  @Override
  public void onAnimationCancel(Animator animation) {

  }


  @Override
  public void onAnimationRepeat(Animator animation) {

  }


}
