package com.dafruits.android.library.widgets;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 通过绘制方式实现，圆形加载
 * 
 * @author Ryze
 * @see AppLoaddingImage 利用两张图片实现
 */
@SuppressLint("NewApi")
public class CircleLoaddingView extends View implements AnimatorUpdateListener{

	private int width = 0;

	private int height = 0;
	
	private int radius=0;

	private Paint bg_paint;
	private RectF bg_RectF;
	

	private Paint moving_paint;
	private Path moving_Path;
	private ValueAnimator moving_Animator;
	
	private int degree=0;

	public CircleLoaddingView(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	public CircleLoaddingView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public CircleLoaddingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CircleLoaddingView(Context context) {
		super(context);
		init();
	}

	private void init() {

		bg_paint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
		bg_paint.setColor(Color.parseColor("#555566"));
		bg_paint.setStyle(Style.STROKE);
		bg_paint.setStrokeWidth(15);
		bg_RectF=new RectF();
		
		moving_paint= new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
		moving_paint.setColor(Color.parseColor("#ff5566"));
		moving_paint.setStyle(Style.STROKE);
		moving_paint.setStrokeWidth(15);
		moving_Path=new Path();
		
		
	}
	
	private void startAnimation(){
		if (moving_Animator!=null) {
			moving_Animator.cancel();
			moving_Animator.removeAllListeners();
			moving_Animator.removeAllUpdateListeners();
			moving_Animator=null;
		}
		moving_Animator=ValueAnimator.ofInt(1,360);
		moving_Animator.setDuration(2000);
		moving_Animator.setInterpolator(new LinearInterpolator());
		moving_Animator.setRepeatMode(ValueAnimator.INFINITE);
		moving_Animator.setRepeatCount(ValueAnimator.INFINITE);
		moving_Animator.addUpdateListener(this);
		moving_Animator.start();
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width=getWidth();
		height=getHeight();
		
		if (width==0 || height==0) {
			return ;
		}
		bg_RectF.setEmpty();
		moving_Path.reset();
		
		if (width>height) {
			radius=height/4;
		}else {
			radius=height/4;
		}
		
		bg_RectF.set(width/2-radius, height/2-radius, width/2+radius, height/2+radius);
		moving_Path.addArc(bg_RectF, 0, 45);
		
		startAnimation();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawCircle(width/2, height/2, radius, bg_paint);
		canvas.rotate(degree,width/2,height/2);
		canvas.drawPath(moving_Path, moving_paint);
//		canvas.restore();
	}

	@Override
	public void onAnimationUpdate(ValueAnimator animation) {
     degree=(Integer) animation.getAnimatedValue();
     Log.e(VIEW_LOG_TAG, "degree: "+degree);
     invalidate();
	}
	
	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		if (visibility==VISIBLE) {
			if (moving_Animator!=null) {
				moving_Animator.start();;
			}
		}else {
			if (moving_Animator!=null) {
				moving_Animator.cancel();;
			}
		}
	}

}
