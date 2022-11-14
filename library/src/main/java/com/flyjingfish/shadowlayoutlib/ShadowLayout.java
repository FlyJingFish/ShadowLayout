package com.flyjingfish.shadowlayoutlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShadowLayout extends FrameLayout {

    private final Paint mBgPaint;
    private int[] gradientColors;
    private float[] gradientPositions;
    private float shadowMaxLength;
    private float shadowInscribedRadius;

    public ShadowLayout(Context context) {
        this(context,null);
    }

    public ShadowLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShadowLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShadowLayout);
        shadowMaxLength = a.getDimension(R.styleable.ShadowLayout_shadow_max_length, 0);
        shadowInscribedRadius = a.getDimension(R.styleable.ShadowLayout_shadow_inscribed_radius, shadowMaxLength);

        int startColor = a.getColor(R.styleable.ShadowLayout_shadow_start_color, Color.TRANSPARENT);
        int endColor = a.getColor(R.styleable.ShadowLayout_shadow_end_color, Color.TRANSPARENT);

        a.recycle();

        mBgPaint = new Paint();
        mBgPaint.setColor(Color.BLACK);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStrokeWidth(shadowMaxLength);
        mBgPaint.setStyle(Paint.Style.STROKE);


        gradientColors = new int[]{startColor,endColor};
        gradientPositions = null;
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();

        float shadowRadius = shadowInscribedRadius;
        mBgPaint.setStrokeWidth(shadowMaxLength);
        if (shadowRadius>=shadowMaxLength){
            shadowRadius = shadowInscribedRadius + shadowMaxLength/2;
            float radialGradientRadius = shadowMaxLength / 2f + shadowRadius;
            if (radialGradientRadius<=0){
                super.dispatchDraw(canvas);
                return;
            }

            RectF leftTopRectF = new RectF(shadowMaxLength / 2f, shadowMaxLength / 2f, shadowRadius * 2 + shadowMaxLength / 2f, shadowRadius * 2 + shadowMaxLength / 2f);
            RectF rightTopRectF = new RectF(width - shadowRadius * 2 - shadowMaxLength / 2f, shadowMaxLength / 2f, width - shadowMaxLength / 2f, shadowRadius * 2 + shadowMaxLength / 2f);
            RectF rightBottomRectF = new RectF(width - shadowRadius * 2 - shadowMaxLength / 2f, height - shadowRadius * 2 - shadowMaxLength / 2f, width - shadowMaxLength / 2f, height - shadowMaxLength / 2f);
            RectF leftBottomRectF = new RectF(shadowMaxLength / 2f, height - shadowRadius * 2 - shadowMaxLength / 2f, shadowRadius * 2 + shadowMaxLength / 2f, height - shadowMaxLength / 2f);



            RadialGradient radialGradient;
            radialGradient = new RadialGradient(shadowMaxLength / 2f + shadowRadius,shadowRadius + shadowMaxLength / 2f,radialGradientRadius, gradientColors,gradientPositions,Shader.TileMode.CLAMP);
            mBgPaint.setShader(radialGradient);

            canvas.drawArc(leftTopRectF, -90, -90, false, mBgPaint);

            radialGradient = new RadialGradient(width - shadowMaxLength / 2f - shadowRadius,shadowRadius + shadowMaxLength / 2f,radialGradientRadius, gradientColors,gradientPositions,Shader.TileMode.CLAMP);
            mBgPaint.setShader(radialGradient);
            canvas.drawArc(rightTopRectF, 0, -90, false, mBgPaint);

            radialGradient = new RadialGradient(width - shadowMaxLength / 2f - shadowRadius,height - shadowRadius - shadowMaxLength / 2f,radialGradientRadius, gradientColors,gradientPositions,Shader.TileMode.CLAMP);
            mBgPaint.setShader(radialGradient);
            canvas.drawArc(rightBottomRectF, 0, 90, false, mBgPaint);

            radialGradient = new RadialGradient(shadowMaxLength / 2f + shadowRadius,height - shadowRadius - shadowMaxLength / 2f,radialGradientRadius, gradientColors,gradientPositions,Shader.TileMode.CLAMP);
            mBgPaint.setShader(radialGradient);
            canvas.drawArc(leftBottomRectF, 90, 90, false, mBgPaint);

            LinearGradient linearGradient;
            float[] ptsLeft = new float[4];
            float[] ptsTop = new float[4];
            float[] ptsRight = new float[4];
            float[] ptsBottom = new float[4];
            ptsLeft[0] = shadowMaxLength /2f;
            ptsLeft[1] = shadowRadius + shadowMaxLength / 2f;
            ptsLeft[2] = shadowMaxLength /2f;
            ptsLeft[3] = height - shadowRadius - shadowMaxLength / 2f;
            linearGradient = new LinearGradient(shadowMaxLength / 2f + shadowRadius, 0, 0, 0, gradientColors, gradientPositions, Shader.TileMode.CLAMP);
            mBgPaint.setShader(linearGradient);
            canvas.drawLines(ptsLeft, mBgPaint);



            ptsTop[0] = shadowMaxLength / 2f + shadowRadius;
            ptsTop[1] = shadowMaxLength / 2f;
            ptsTop[2] = width - shadowMaxLength / 2f - shadowRadius;
            ptsTop[3] = shadowMaxLength / 2f;
            linearGradient = new LinearGradient(0, shadowMaxLength / 2f + shadowRadius, 0, 0, gradientColors, gradientPositions, Shader.TileMode.CLAMP);
            mBgPaint.setShader(linearGradient);
            canvas.drawLines(ptsTop, mBgPaint);



            ptsRight[0] = width - shadowMaxLength / 2f;
            ptsRight[1] = shadowMaxLength / 2f + shadowRadius;
            ptsRight[2] = width - shadowMaxLength / 2f;
            ptsRight[3] = height - shadowRadius - shadowMaxLength / 2f;
            linearGradient = new LinearGradient(width - shadowRadius  - shadowMaxLength / 2f, 0, width, 0, gradientColors, gradientPositions, Shader.TileMode.CLAMP);
            mBgPaint.setShader(linearGradient);
            canvas.drawLines(ptsRight, mBgPaint);



            ptsBottom[0] = shadowMaxLength / 2f + shadowRadius;
            ptsBottom[1] = height - shadowMaxLength / 2f;
            ptsBottom[2] = width - shadowMaxLength / 2f - shadowRadius;
            ptsBottom[3] = height - shadowMaxLength / 2f;
            linearGradient = new LinearGradient(0, height - shadowRadius - shadowMaxLength / 2f, 0, height, gradientColors, gradientPositions, Shader.TileMode.CLAMP);
            mBgPaint.setShader(linearGradient);
            canvas.drawLines(ptsBottom, mBgPaint);
        }else {
            float radialGradientRadius = shadowMaxLength;
            if (radialGradientRadius<=0){
                super.dispatchDraw(canvas);
                return;
            }

            RectF leftTopRectF = new RectF(0, 0, shadowMaxLength, shadowMaxLength);
            RectF rightTopRectF = new RectF(width - shadowMaxLength, 0, width , shadowMaxLength);
            RectF rightBottomRectF = new RectF(width - shadowMaxLength, height - shadowMaxLength, width, height);
            RectF leftBottomRectF = new RectF(0, height - shadowMaxLength, shadowMaxLength, height);



            RadialGradient radialGradient;
            radialGradient = new RadialGradient(shadowMaxLength,shadowMaxLength,radialGradientRadius, gradientColors,gradientPositions,Shader.TileMode.CLAMP);
            mBgPaint.setShader(radialGradient);

            canvas.drawArc(leftTopRectF, -90, -90, true, mBgPaint);

            radialGradient = new RadialGradient(width - shadowMaxLength,shadowMaxLength,radialGradientRadius, gradientColors,gradientPositions,Shader.TileMode.CLAMP);
            mBgPaint.setShader(radialGradient);
            canvas.drawArc(rightTopRectF, 0, -90, true, mBgPaint);

            radialGradient = new RadialGradient(width - shadowMaxLength,height - shadowMaxLength,radialGradientRadius, gradientColors,gradientPositions,Shader.TileMode.CLAMP);
            mBgPaint.setShader(radialGradient);
            canvas.drawArc(rightBottomRectF, 0, 90, true, mBgPaint);

            radialGradient = new RadialGradient(shadowMaxLength,height - shadowMaxLength,radialGradientRadius, gradientColors,gradientPositions,Shader.TileMode.CLAMP);
            mBgPaint.setShader(radialGradient);
            canvas.drawArc(leftBottomRectF, 90, 90, true, mBgPaint);

            LinearGradient linearGradient;
            float[] ptsLeft = new float[4];
            float[] ptsTop = new float[4];
            float[] ptsRight = new float[4];
            float[] ptsBottom = new float[4];
            ptsLeft[0] = shadowMaxLength /2f;
            ptsLeft[1] = shadowMaxLength;
            ptsLeft[2] = shadowMaxLength /2f;
            ptsLeft[3] = height - shadowMaxLength;
            linearGradient = new LinearGradient(shadowMaxLength , 0, 0, 0, gradientColors, gradientPositions, Shader.TileMode.CLAMP);
            mBgPaint.setShader(linearGradient);
            canvas.drawLines(ptsLeft, mBgPaint);



            ptsTop[0] = shadowMaxLength;
            ptsTop[1] = shadowMaxLength / 2f;
            ptsTop[2] = width - shadowMaxLength;
            ptsTop[3] = shadowMaxLength / 2f;
            linearGradient = new LinearGradient(0, shadowMaxLength, 0, 0, gradientColors, gradientPositions, Shader.TileMode.CLAMP);
            mBgPaint.setShader(linearGradient);
            canvas.drawLines(ptsTop, mBgPaint);



            ptsRight[0] = width - shadowMaxLength/2f;
            ptsRight[1] = shadowMaxLength;
            ptsRight[2] = width - shadowMaxLength/2f;
            ptsRight[3] = height - shadowMaxLength;
            linearGradient = new LinearGradient(width - shadowMaxLength, 0, width, 0, gradientColors, gradientPositions, Shader.TileMode.CLAMP);
            mBgPaint.setShader(linearGradient);
            canvas.drawLines(ptsRight, mBgPaint);



            ptsBottom[0] = shadowMaxLength;
            ptsBottom[1] = height - shadowMaxLength / 2f;
            ptsBottom[2] = width - shadowMaxLength;
            ptsBottom[3] = height - shadowMaxLength / 2f;
            linearGradient = new LinearGradient(0, height - shadowMaxLength, 0, height, gradientColors, gradientPositions, Shader.TileMode.CLAMP);
            mBgPaint.setShader(linearGradient);
            canvas.drawLines(ptsBottom, mBgPaint);
        }

        super.dispatchDraw(canvas);
    }

    public int[] getGradientColors() {
        return gradientColors;
    }

    public void setGradientColors(@NonNull int[] gradientColors) {
        this.gradientColors = gradientColors;
        if (gradientPositions != null && gradientColors.length != gradientPositions.length){
            this.gradientPositions = null;
        }
        invalidate();
    }

    public float[] getGradientPositions() {
        return gradientPositions;
    }

    public void setGradientPositions(float[] gradientPositions) {
        this.gradientPositions = gradientPositions;
        invalidate();
    }

    public float getShadowMaxLength() {
        return shadowMaxLength;
    }

    public void setShadowMaxLength(float shadowMaxLength) {
        this.shadowMaxLength = shadowMaxLength;
        invalidate();
    }

    public float getShadowInscribedRadius() {
        return shadowInscribedRadius;
    }

    public void setShadowInscribedRadius(float shadowInscribedRadius) {
        this.shadowInscribedRadius = shadowInscribedRadius;
        invalidate();
    }
}
