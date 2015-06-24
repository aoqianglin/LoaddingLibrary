package com.dafruits.android.library.widgets;

import java.lang.ref.WeakReference;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * 
 * @description 圆形进度加载
 * @autor Ryze 2015-5-27 下午3:05:05
 * 
 */
public class CircleLoaddingView extends ImageView {


  private int width;

  private int height;

  private int bg_color = Color.parseColor("#64000000");

  private Paint bg_Paint;

  private float radius;


  private int draw_color = Color.parseColor("#ffffff");

  private Paint draw_Paint;

  private RectF draw_RectF;
  private Path path;


  private int bg_cirlce_color = Color.parseColor("#05000000");
  private Paint bg_circle_Paint;


  private float progressValue = 0;

  private float current_value = 0;

  private float degree = 0;


  private ProgressHandler handler;

  /**
   * @param context
   * @param attrs
   * @param defStyleAttr
   * @param defStyleRes
   */
  @SuppressLint("NewApi")
  public CircleLoaddingView(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  /**
   * @param context
   * @param attrs
   * @param defStyleAttr
   */
  public CircleLoaddingView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  /**
   * @param context
   * @param attrs
   */
  public CircleLoaddingView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  /**
   * @param context
   */
  public CircleLoaddingView(Context context) {
    super(context);
    init();
  }


  private static class ProgressHandler extends Handler {

    private CircleLoaddingView loaddingImageView;

    /**
     * @param loaddingImageView
     */
    public ProgressHandler(CircleLoaddingView loaddingImageView) {
      super();
      this.loaddingImageView = new WeakReference<CircleLoaddingView>(loaddingImageView).get();
    }

    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (loaddingImageView == null) {
        return;
      }
      loaddingImageView.degree = (Float)msg.obj;
      loaddingImageView.postInvalidate();
    }
  }

  /**
   * 
   * 获取屏幕高宽
   * 
   * @param 第一个参数为宽 第二个为高
   * @return
   */
  public int[] getScreenSize(Context mContext) {
    DisplayMetrics dm = new DisplayMetrics();
    ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
    int nowWidth = dm.widthPixels; // 当前分辨率 宽度
    int nowHeigth = dm.heightPixels; // 当前分辨率高度

    return new int[] {nowWidth, nowHeigth};
  }

  private void init() {
    bg_Paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    bg_Paint.setColor(bg_color);
    bg_Paint.setStyle(Style.FILL);

    draw_Paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    draw_Paint.setColor(draw_color);
    draw_Paint.setAntiAlias(true);
    draw_Paint.setStrokeWidth(18);
    draw_Paint.setStyle(Style.STROKE);
    draw_RectF = new RectF();
    path = new Path();

    bg_circle_Paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    bg_circle_Paint.setColor(bg_cirlce_color);
    bg_circle_Paint.setStrokeWidth(18);
    bg_circle_Paint.setStyle(Style.STROKE);

    handler = new ProgressHandler(this);
  }

  public void updateProgress(int total, int progress) {
    if (handler == null || total == 0) {
      return;
    }
    this.progressValue = total;
    this.current_value = progress;
    handler.sendMessageDelayed(handler.obtainMessage(1, current_value / progressValue), 100);

  }

  /**
   * 进度清零,重新设进度
   */
  public void reSetProgress() {
    progressValue = 0f;
    current_value = 0f;
    handler.sendMessageDelayed(handler.obtainMessage(1, 0f), 200);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    this.width = getWidth();
    this.height = getHeight();

    radius = getScreenSize(getContext())[0] / 20.0f;

    draw_RectF
        .set(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius);
  }


  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (degree == 0) {
      // canvas.restore();
    } else {
      canvas.restore();

      canvas.drawCircle(width / 2, height / 2, radius + radius / 2, bg_Paint);
      canvas.save();

      canvas.drawArc(draw_RectF, 0, 360, true, bg_circle_Paint);
      canvas.restore();
      path.addArc(draw_RectF, -90, degree * 360);
      canvas.drawPath(path, draw_Paint);
    }
    canvas.restore();

  }

}