package com.orangeboston.solarenergy.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * @author 黄家洋
 *         <p>
 *         2016年7月15日上午11:06:22
 *
 *         thanks,2019/7/4
 */
public class VivoView extends View {
    // 定义五个画笔
    private Paint mSmileRing, mBigRing, mInCrilePaint, mInLine, mTextPaint;
    // 控件的高宽
    private float mWidth, mHeight;
    // 矩形的空间
    private RectF mRectF;
    // 四个弧线的开始角度
    private float startAngle = 270, startAngle2 = 270, startAngle3 = 270, startAngle4 = 270, sweepAngle = 90;
    // 文字
    private String text = "70%";
    // 文字的大小
    private float tvSize = 80;
    // 刻度的进度
    private float progress;
    private boolean run = true;
    private int count = 0;
    private Handler handler;
    private final int WHAT = 0, DELETETIME = 100;



    /**
     * @param context
     */
    public VivoView(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public VivoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public VivoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        handler = new MyHandler(this);
        mSmileRing = new Paint();
        mSmileRing.setAntiAlias(true);
        mSmileRing.setStrokeWidth(5);
        mSmileRing.setStyle(Style.STROKE);
        mSmileRing.setColor(Color.parseColor("#12ADFF"));

        mBigRing = new Paint();
        mBigRing.setAntiAlias(true);
        mBigRing.setStrokeWidth(20);
        mBigRing.setStyle(Style.STROKE);
        mBigRing.setColor(Color.parseColor("#12ADFF"));

        mInCrilePaint = new Paint();
        mInCrilePaint.setAntiAlias(true);
        mInCrilePaint.setStrokeWidth((float) 0.5);
        mInCrilePaint.setStyle(Style.STROKE);
        mInCrilePaint.setColor(Color.parseColor("#eeeeee"));

        mInLine = new Paint();
        mInLine.setAntiAlias(true);
        mInLine.setStrokeWidth(3);
        mInLine.setColor(Color.parseColor("#00ff00"));

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStrokeWidth(3);
        mTextPaint.setTextSize(tvSize);
        mTextPaint.setColor(Color.parseColor("#ffffff"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasOutArc1(canvas, mRectF);
        canvasOutArc2(canvas, mRectF);
        canvasOutArc3(canvas, mRectF);
        canvasOutArc4(canvas, mRectF);
        drawCircle(canvas);
        drawCircleIn(canvas);
        canvasDrawText(canvas);

    }

    // 绘制文字
    private void canvasDrawText(Canvas canvas) {
//        text = (int) (progress / 72 * 100) + "%";
        text = "⚡";
        float textSize = mTextPaint.measureText(text);
        float x = mWidth / 2 - textSize / 2;
        // float y = mHeight / 2 + textSize / 5;
        float y = mWidth / 2 + textSize / 5;

        canvas.drawText(text, x, y, mTextPaint);
    }

    // 绘制最里面的圆
    // 绘制内切圆和锯齿
    private void drawCircleIn(Canvas canvas) {
        float radius = (float) (mHeight - (mHeight * 0.3) * 2 - (mWidth * 0.22));
        float yuanX = (float) (mWidth / 2);
        float yuanY = (float) (mWidth / 2);

        canvas.drawCircle(yuanX, yuanY, radius, mInCrilePaint);
        canvas.save();

    }

    // 绘制内切圆和锯齿
    private void drawCircle(Canvas canvas) {
        // float radius = (float) (mHeight - (mHeight * 0.3) * 2 - (mWidth *
        // 0.17));
        // float yuanX = (float) (mWidth / 2);
        // // float yuanY = (float) (mWidth / 2 + textSize / 5);
        // // float yuanY = (float) (mWidth / 2);
        // float yuanY = (float) (0);
        //
        // canvas.drawCircle(yuanX, yuanY, radius, mInCrilePaint);
        // canvas.save();

        //float nowWidth = (float) (getMeasuredWidth());
        //float nowHeight = getMeasuredHeight();
        for (int i = 0; i < 72; i++) {
            // canvas.drawLine(nowWidth / 2, nowHeight / 2 - nowWidth / 2,
            // nowWidth / 2, nowHeight / 2 - nowWidth / 2 + 30, mInLine);

            if (i >= progress) {
//                mInLine.setColor(Color.parseColor("#555555"));
                mInLine.setColor(Color.parseColor("#12ADFF"));
            } else {
                mInLine.setColor(Color.parseColor("#555555"));
//                mInLine.setColor(Color.parseColor("#00ff00"));
            }

            canvas.drawLine(mWidth / 2, (float) (mWidth / 2 - 0.23 * mWidth), mWidth / 2, (float) (mWidth / 2 - 0.18 * mWidth), mInLine);

            canvas.rotate(5, mWidth / 2, mWidth / 2);

        }

    }

    /**
     * 绘制最外面的弧线
     *
     * @param canvas
     */
    private void canvasOutArc1(Canvas canvas, RectF mRectF) {
        mRectF = new RectF((float) (mWidth * 0.1), (float) (mWidth * 0.1), (float) (mWidth * 0.9), (float) (mWidth * 0.9));
        canvas.drawArc(mRectF, startAngle, sweepAngle + 90, false, mSmileRing);
    }

    /**
     * 绘制外层的第二个
     *
     * @param canvas
     * @param mRectF
     */
    private void canvasOutArc2(Canvas canvas, RectF mRectF) {
        mRectF = new RectF((float) (mWidth * 0.14), (float) (mWidth * 0.14), (float) (mWidth * 0.85), (float) (mWidth * 0.85));
        canvas.drawArc(mRectF, startAngle2, sweepAngle + 30, false, mBigRing);
    }

    /**
     * 绘制里面第二个小的
     *
     * @param canvas
     */
    private void canvasOutArc3(Canvas canvas, RectF mRectF) {
        mRectF = new RectF((float) (mWidth * 0.22), (float) (mWidth * 0.22), (float) (mWidth * 0.795), (float) (mWidth * 0.795));
        canvas.drawArc(mRectF, startAngle3, sweepAngle, false, mSmileRing);
    }

    /**
     * 绘制里面第二个小的
     *
     * @param canvas
     */
    private void canvasOutArc4(Canvas canvas, RectF mRectF) {
        mRectF = new RectF((float) (mWidth * 0.255), (float) (mWidth * 0.255), (float) (mWidth * 0.75), (float) (mWidth * 0.75));
        canvas.drawArc(mRectF, startAngle4, sweepAngle, false, mBigRing);
    }

    public void setData(int startAngle) {
        this.startAngle = startAngle;
        this.startAngle2 = 360 - startAngle;
        this.startAngle3 = startAngle;
        this.startAngle4 = 360 - startAngle;
        if (count != 10) {
            count++;
        } else {
            count = 0;
            if (progress < 72) {
                progress++;
            } else {
                progress = 0;
            }
        }
        postInvalidateDelayed(500);
    }

    public void startAnimation() {
        handler.removeMessages(WHAT);
        handler.sendEmptyMessageDelayed(WHAT, DELETETIME);
    }
    public void stopAnimation() {
        run = false;
    }
    private static class MyHandler extends Handler {
        private int data = 0;
        private final WeakReference<VivoView> vivoViewWeakReference;

        public MyHandler(VivoView vivoView) {
            vivoViewWeakReference = new WeakReference<VivoView>(vivoView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            VivoView vivoView = vivoViewWeakReference.get();
            if (vivoView != null && vivoView.run) {

                switch (msg.what) {
                    case 0:
                        if(data < 349){
                            data +=10;
                        }else {
                            data = 0;
                        }
                        vivoView.setData(data);
                        vivoView.startAnimation();

                        break;
                    default:
                        break;

                }
                Log.d("TAG","working");
            }
        }
    }

}
