package com.flyjingfish.shadowlayoutlib;

import android.content.Context;
import android.content.res.ColorStateList;
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

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShadowLayout extends FrameLayout {

    private final Paint mBgPaint;
    private int[] gradientColors;
    private float[] gradientPositions;
    private float shadowMaxLength;
    private float shadowInscribedRadius;
    private final List<ColorStateList> gradientColorStates = new ArrayList<>();

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

        ColorStateList startColor = a.getColorStateList(R.styleable.ShadowLayout_shadow_start_color);
        ColorStateList endColor = a.getColorStateList(R.styleable.ShadowLayout_shadow_end_color);

        a.recycle();

        mBgPaint = new Paint();
        mBgPaint.setColor(Color.BLACK);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStrokeWidth(shadowMaxLength);
        mBgPaint.setStyle(Paint.Style.STROKE);

        if (startColor == null){
            startColor = ColorStateList.valueOf(Color.TRANSPARENT);
        }
        if (endColor == null){
            endColor = ColorStateList.valueOf(Color.TRANSPARENT);
        }

        gradientColorStates.add(startColor);
        gradientColorStates.add(endColor);
        updateColors();
        gradientPositions = null;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateColors();
    }

    private boolean updateColors(){
        boolean inval = false;
        final int[] drawableState = getDrawableState();
        if (gradientColorStates != null && gradientColorStates.size() > 0){
            int[] gradientCls = new int[gradientColorStates.size()];
            for (int i = 0; i < gradientColorStates.size(); i++) {
                int gradientColor = gradientColorStates.get(i).getColorForState(drawableState, 0);
                gradientCls[i] = gradientColor;
            }
            if (gradientColors == null) {
                gradientColors = gradientCls;
                inval = true;
            } else if (gradientColors.length != gradientCls.length){
                gradientColors = gradientCls;
                inval = true;
            } else {
                boolean equals = true;
                for (int i = 0; i < gradientColors.length; i++) {
                    if (gradientColors[i] != gradientCls[i]){
                        equals = false;
                        break;
                    }
                }
                if (!equals){
                    gradientColors = gradientCls;
                    inval = true;
                }
            }
        }

        if (inval){
            invalidate();
        }
        return inval;
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

    public List<ColorStateList> getGradientColorStates() {
        return gradientColorStates;
    }
    public void setGradientColors(@NonNull @ColorInt int[] gradientColors) {
        ColorStateList[] colorStateLists = new ColorStateList[gradientColors.length];
        for (int i = 0; i < gradientColors.length; i++) {
            colorStateLists[i] = ColorStateList.valueOf(gradientColors[i]);
        }
        setGradientColors(colorStateLists);
    }

    public void setGradientColors(@NonNull ColorStateList[] colorStateLists) {
        gradientColorStates.clear();
        gradientColorStates.addAll(Arrays.asList(colorStateLists));
        if (gradientColorStates.size() == 1){
            gradientColorStates.add(ColorStateList.valueOf(Color.TRANSPARENT));
        }
        if (gradientPositions != null && gradientColorStates.size() != gradientPositions.length){
            this.gradientPositions = null;
        }
        updateColors();
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
