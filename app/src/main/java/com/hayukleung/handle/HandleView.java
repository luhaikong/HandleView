package com.hayukleung.handle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Handle
 * com.hayukleung.handle
 * HandleView.java
 *
 * by hayukleung
 * at 2016-10-27 15:09
 */

/**
 * 贪吃蛇大作战方向控制按钮效果
 */
public class HandleView extends View {

  private Paint mPaintForCircle;
  private HandleReaction mHandleReaction;

  public HandleView(Context context) {
    this(context, null);
  }

  public HandleView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public HandleView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  /**
   * Compare to: {@link android.view.View#getDefaultSize(int, int)}
   * If mode is AT_MOST, return the child size instead of the parent size
   * (unless it is too big).
   */
  private int getDefaultSize2(int size, int measureSpec) {
    int result = size;
    int specMode = MeasureSpec.getMode(measureSpec);
    int specSize = MeasureSpec.getSize(measureSpec);

    switch (specMode) {
      case MeasureSpec.UNSPECIFIED://在尺寸上设置限制条件
        result = size;
        break;
      case MeasureSpec.AT_MOST://最大可获得的空间
        result = Math.min(size, specSize);
        break;
      case MeasureSpec.EXACTLY://精确的尺寸
        result = specSize;
        break;
    }
    return result;
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    setMeasuredDimension(getDefaultSize2(getSuggestedMinimumWidth(), widthMeasureSpec),
            getDefaultSize2(getSuggestedMinimumHeight(), heightMeasureSpec));
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    canvas.drawColor(Color.TRANSPARENT);

    int radiusOuter = getWidth() / 2;
    int radiusInner = getWidth() / 5;
    float cx = getWidth() / 2;
    float cy = getHeight() / 2;

    if (null == mPaintForCircle) {
      mPaintForCircle = new Paint();
    }

    mPaintForCircle.setAntiAlias(true);
    mPaintForCircle.setStyle(Paint.Style.FILL);

    mPaintForCircle.setColor(Color.argb(0x7f, 0x11, 0x11, 0x11));
    canvas.drawCircle(cx, cy, radiusOuter, mPaintForCircle);

    if (null == mHandleReaction || null == mHandleReaction.getTouchPosition()) {
      mPaintForCircle.setColor(Color.argb(0xff, 0x11, 0x11, 0x11));
      canvas.drawCircle(cx, cy, radiusInner, mPaintForCircle);
      canvas.save();
      return;
    }

    float[] touchPosition = mHandleReaction.getTouchPosition();

    double ratio = (radiusOuter - radiusInner) / Math.sqrt(
        Math.pow(touchPosition[0] - cx - getLeft(), 2) + Math.pow(touchPosition[1] - cy - getTop(),
            2));
    float cx2 = (float) (ratio * (touchPosition[0] - cx - getLeft()) + cx);
    float cy2 = (float) (ratio * (touchPosition[1] - cy - getTop()) + cy);

    mPaintForCircle.setColor(Color.argb(0xff, 0x11, 0x11, 0x11));
    canvas.drawCircle(cx2, cy2, radiusInner, mPaintForCircle);

    canvas.save();
  }

  /**
   * 设置触摸监听器
   * @param handleReaction
   */
  public void setHandleReaction(HandleReaction handleReaction) {
    mHandleReaction = handleReaction;
  }

  /**
   * 触摸监听接口
   */
  public interface HandleReaction {
    float[] getTouchPosition();
  }
}
