package com.example.a19401.filetest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dongqiang on 2016/10/21.
 */
public class VerticalColorSeekBar extends View{

    private static final String TAG = VerticalColorSeekBar.class.getSimpleName();
    private int startColor= Color.BLACK;
    private int middleColor = Color.GRAY;
    private int endColor=Color.WHITE;
    private int thumbColor=Color.BLACK;
    private int thumbBorderColor=Color.WHITE;
    private int colorArray[]={startColor, middleColor, endColor};
    private float x,y;
    private float mRadius;
    private float progress;
    private float maxCount = 100f;
    private float sLeft, sTop, sRight, sBottom;
    private float sWidth,sHeight;
    private LinearGradient linearGradient;
    private Paint paint = new Paint();
    protected OnStateChangeListener onStateChangeListener;

    public VerticalColorSeekBar(Context context) {
        this(context, null);
    }

    public VerticalColorSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }

    public void setColor(int startColor,int middleColor, int endColor,int thumbColor,int thumbBorderColor){
        this.startColor= startColor;
        this.middleColor = middleColor;
        this.endColor= endColor;
        this.thumbColor= thumbColor;
        this.thumbBorderColor= thumbBorderColor;
        colorArray[0] = startColor;
        colorArray[1] = middleColor;
        colorArray[2] = endColor;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int h = getMeasuredHeight();
        int w = getMeasuredWidth();
        mRadius = (float) w/2;
        sLeft = w * 0.25f; // 背景左边缘坐标
        sRight = w * 0.75f;// 背景右边缘坐标
        sTop = 0;
        sBottom = h;
        sWidth = sRight - sLeft; // 背景宽度
        sHeight = sBottom - sTop; // 背景高度
        x = (float) w/2;//圆心的x坐标
        y = (float) (1-0.01*progress)*sHeight;//圆心y坐标
        drawBackground(canvas);
        drawCircle(canvas);
        paint.reset();
    }

    private void drawBackground(Canvas canvas){
        RectF rectBlackBg = new RectF(sLeft, sTop, sRight, sBottom);
        linearGradient=new LinearGradient(sLeft,sTop,sWidth,sHeight,colorArray,null, Shader.TileMode.MIRROR);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        //设置渲染器
        paint.setShader(linearGradient);
        canvas.drawRoundRect(rectBlackBg, sWidth/2, sWidth/2, paint);
    }

    private void drawCircle(Canvas canvas){
        Paint thumbPaint = new Paint();
        y = y < mRadius ? mRadius : y;//判断thumb边界
        y = y > sHeight-mRadius ? sHeight-mRadius : y;
        thumbPaint.setAntiAlias(true);
        thumbPaint.setStyle(Paint.Style.FILL);
        thumbPaint.setColor(thumbColor);
        canvas.drawCircle(x, y, mRadius, thumbPaint);
        thumbPaint.setStyle(Paint.Style.STROKE);
        thumbPaint.setColor(thumbBorderColor);
        thumbPaint.setStrokeWidth(2);
        canvas.drawCircle(x, y, mRadius, thumbPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.y = event.getY();
        progress= (sHeight-y)/sHeight*100;
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if (onStateChangeListener!=null){
                    onStateChangeListener.onStopTrackingTouch(this, progress);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (onStateChangeListener!=null){
                    Log.i("test","Not null");
                    onStateChangeListener.OnStateChangeListener(this, progress);
                }
                setProgress(progress);
                this.invalidate();
                break;
        }

        return true;
    }


    public interface OnStateChangeListener{
        void OnStateChangeListener(View view, float progress);
        void onStopTrackingTouch(View view, float progress);
    }

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener){
        this.onStateChangeListener=onStateChangeListener;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public float getProgress() {
        return this.progress;
    }
}
