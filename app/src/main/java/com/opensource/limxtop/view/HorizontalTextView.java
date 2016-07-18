package com.opensource.limxtop.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.opensource.limxtop.form.R;

/**
 * Created by limxtop on 7/18/16.
 */
public class HorizontalTextView extends TextView {

    private Rect mTextBounds;
    Paint mTextPaint;
    private Context mContext;

    public HorizontalTextView(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    public HorizontalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalTextView(Context context) {
        super(context);
        mTextBounds = new Rect();
        mContext = context;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int widthOfDrawable = 0;;
        int heightOfDrawable = 0;
        int widthOfText = 0;
        // int heightOfText;
        Drawable drawableLeft = null;
        int translateX = 0;

        Drawable[] drawables =  getCompoundDrawables();
        if (drawables != null) {
            drawableLeft = drawables[0];
            if (drawableLeft != null ) {
                widthOfDrawable = drawableLeft.getIntrinsicWidth();
                heightOfDrawable = drawableLeft.getIntrinsicHeight();
            }
        }

        String text = getText().toString();
        if(text!=null){
            mTextPaint = getPaint();
            mTextPaint.setTextAlign(Paint.Align.LEFT);
            mTextPaint.setTextSize(getTextSize());
            mTextPaint.getTextBounds(text, 0, text.length(), mTextBounds);
            widthOfText = mTextBounds.width();
        }

        if(drawableLeft!=null){
            translateX = (getWidth() - widthOfDrawable - widthOfText )/2 ;
            int translateY = (getHeight() - heightOfDrawable)/2;
            canvas.save();
            canvas.translate(translateX, translateY);
            drawableLeft.draw(canvas);
            canvas.restore();
        }

        if(text!=null){
            Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
            int left = translateX + widthOfDrawable;
            int top  = getHeight()/2 - (fontMetrics.top+fontMetrics.bottom)/2;
            mTextPaint.setColor(mContext.getResources().getColor(R.color.black));
            canvas.drawText(text, left, top, mTextPaint);
        }
    }
}