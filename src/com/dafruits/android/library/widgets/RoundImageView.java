package com.dafruits.android.library.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 
 * @description 
 * @autor Ryze 2015-5-21 上午9:32:46
 * 
 */
public class RoundImageView extends ImageView {



  private int width = 0;

  private int height = 0;



  /**
   * @param context
   * @param attrs
   * @param defStyle
   */
  public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  /**
   * @param context
   * @param attrs
   */
  public RoundImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * @param context
   */
  public RoundImageView(Context context) {
    super(context);
  }


  @Override
  protected void onDraw(Canvas canvas) {
//    super.onDraw(canvas);

    Drawable drawable = getDrawable();
    this.measure(0, 0);
    if (drawable == null) {
      return;
    }
    if (width == 0) {
      width = getWidth();
    }
    if (height == 0) {
      height = getHeight();
    }
    Bitmap b = ((BitmapDrawable) drawable).getBitmap();
    Bitmap src = b.copy(Config.ARGB_8888, true);


    Bitmap out = getCilcleBitmap(src);

    canvas.drawBitmap(out, width / 2 - out.getWidth() / 2, height / 2 - out.getWidth() / 2, null);

  }


  private Bitmap getCilcleBitmap(Bitmap src) {


    Bitmap scale_bit = null;
    int squrewidth, squreheight;
    int x, y;
    int radius = 0;
    int bmpwidth = src.getWidth();
    int bmpheight = src.getHeight();
    if (bmpwidth > bmpheight) {
      squrewidth = squreheight = bmpheight;
      x = (bmpwidth - bmpheight) / 2;
      y = 0;
      scale_bit = Bitmap.createBitmap(src, x, y, squrewidth, squreheight);
    } else if (bmpheight > bmpwidth) {
      squrewidth = squreheight = bmpwidth;
      x = 0;
      y = (bmpheight - bmpwidth) / 2;
      scale_bit = Bitmap.createBitmap(src, x, y, squrewidth, squreheight);
    } else {
      scale_bit = src;
    }
    radius = scale_bit.getWidth() / 2;



    Bitmap out = Bitmap.createBitmap(scale_bit.getWidth(), scale_bit.getHeight(), Config.ARGB_8888);

    Rect rect = new Rect(0, 0, out.getWidth(), out.getHeight());
    Canvas canvas = new Canvas(out);
    Paint paint = new Paint();
    paint.setFilterBitmap(true);
    paint.setAntiAlias(true);
    paint.setDither(true);
    canvas.drawARGB(0, 0, 0, 0);
    canvas.drawCircle(out.getWidth() / 2, out.getHeight() / 2, radius, paint);
    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

    canvas.drawBitmap(scale_bit, rect, rect, paint);



    return out;
  }


}
