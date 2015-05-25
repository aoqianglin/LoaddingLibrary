package com.dafruits.android.library.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * 
 * @description TODO
 * @autor Ryze 2015-5-20 下午2:53:12
 * 
 */
public class AppNumLoaddingImage extends View {


  private int width;

  private int height;

  private int bg_color = Color.parseColor("#ff5566");

  private Paint bg_Paint;

  private RectF bg_RectF;


  private int draw_color = Color.parseColor("#ffddbb");

  private Paint draw_Paint;

  private RectF draw_RectF;
  private Path path;


  private Paint text_Paint;


  private float progressValue = 0;

  private float current_value = 0;



  /**
   * @param context
   * @param attrs
   * @param defStyleAttr
   * @param defStyleRes
   */
  @SuppressLint("NewApi")
  public AppNumLoaddingImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  /**
   * @param context
   * @param attrs
   * @param defStyleAttr
   */
  public AppNumLoaddingImage(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  /**
   * @param context
   * @param attrs
   */
  public AppNumLoaddingImage(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  /**
   * @param context
   */
  public AppNumLoaddingImage(Context context) {
    super(context);
    init();
  }


  private void init() {


    bg_Paint = new Paint();
    bg_Paint.setColor(bg_color);
    bg_Paint.setAntiAlias(true);
    bg_Paint.setStrokeWidth(10);
    bg_Paint.setStyle(Style.STROKE);
    bg_RectF = new RectF();

    draw_Paint = new Paint();
    draw_Paint.setColor(draw_color);
    draw_Paint.setAntiAlias(true);
    draw_Paint.setStrokeWidth(10);
    draw_Paint.setStyle(Style.STROKE);
    draw_RectF = new RectF();
    path = new Path();

    text_Paint = new Paint();
    text_Paint.setColor(Color.parseColor("#000000"));
    text_Paint.setTextSize(40);
    text_Paint.setAntiAlias(true);
    text_Paint.setTypeface(Typeface.DEFAULT_BOLD);

  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    this.width = getWidth();
    this.height = getHeight();
    
    int r=0;
    if (width>height) {
		r=height/4;
	}else{
		r=width/4;
	}
    

    bg_RectF.set(width / 2-r, height / 2-r, width / 2 + r, height
        / 2 + r);

    draw_RectF.set(width / 2 - r, height / 2 - r, width / 2 + r, height
        / 2 + r);

    progressValue = (float) (Math.PI * r * 2);


    removeCallbacks(myRunnable);
    post(myRunnable);

  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    removeCallbacks(myRunnable);
  }


  private Runnable myRunnable = new Runnable() {

    @Override
    public void run() {
      if (current_value < progressValue) {
        current_value += 30;
      } else {
        path.reset();
        current_value = 0;
      }
      postDelayed(myRunnable, 300);
      invalidate();
    }
  };

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    canvas.drawArc(bg_RectF, 0, 360, true, bg_Paint);

    // canvas.drawArc(draw_RectF, 90, -180, true,
    // draw_Paint);
    float degree = current_value / progressValue;
    path.addArc(draw_RectF, -90, degree * 360);
    canvas.drawPath(path, draw_Paint);
    String text = (int) (degree * 100) + " %";


    canvas.drawText(text, width / 2 - text_Paint.measureText(text) / 2, height / 2, text_Paint);
    canvas.restore();


  }
}
