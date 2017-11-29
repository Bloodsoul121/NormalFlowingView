package com.example.bloodsoul.normalflowingview.flowview.holder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.view.animation.AccelerateInterpolator;

import com.example.bloodsoul.normalflowingview.flowview.base.BaseDrawer;
import com.example.bloodsoul.normalflowingview.flowview.base.IBaseHolder;

public class CircleHolder implements IBaseHolder {

    private float cx, cy;

    private float dx, dy;

    public float curCX, curCY;

    public float radius;

    private float animatorRadius = 0;

    private float curPercent = 0f;

    private float percentSpeed;

    private int normalColorStart, normalColorEnd;

    private int selectColorStart, selectColorEnd;

    private int textColor;

    private boolean isGrowing = true;

    private boolean isNormal = true;

    private boolean isAnim = false;

    private boolean isStop = true;

    private String name = "";

    private Rect rect;

    private float rectWidth, rectHeight;

    private float animatorRectWidth, animatorRectHeight;

    private LinearGradient mLinearGradientNormal;

    private LinearGradient mLinearGradientSelect;

    private CircleHolder(Builder builder){
        this.cx = builder.cx;
        this.cy = builder.cy;
        this.dx = builder.dx;
        this.dy = builder.dy;
        this.radius = builder.radius;
        this.percentSpeed = builder.percentSpeed;
        this.normalColorStart = builder.colorStart;
        this.normalColorEnd = builder.colorEnd;
        this.selectColorStart = builder.selectColorStart;
        this.selectColorEnd = builder.selectColorEnd;
        this.textColor = builder.textColor;
        this.name = builder.name;
        this.rect = new Rect();
    }

    @Override
    public void updateAndDraw(Canvas canvas, Paint paint) {
        if (isStop) {
            curCX = cx;
            curCY = cy;
            return;
        }else {
            float randomPercentSpeed = BaseDrawer.getRandom(percentSpeed * 0.7f, percentSpeed * 1.3f);
            if (isGrowing) {
                curPercent += randomPercentSpeed;
                if (curPercent > 1f) {
                    curPercent = 1f;
                    isGrowing = false;
                }
            } else {
                curPercent -= randomPercentSpeed;
                if (curPercent < 0f) {
                    curPercent = 0f;
                    isGrowing = true;
                }
            }
            curCX = cx + dx * curPercent;
            curCY = cy + dy * curPercent;
        }

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        mLinearGradientNormal = new LinearGradient(curCX - radius, curCY - radius, curCX + radius,
                curCY + radius, new int[]{normalColorStart, normalColorEnd}, null,
                Shader.TileMode.CLAMP);
        paint.setShader(mLinearGradientNormal);
        canvas.drawCircle(curCX, curCY, radius, paint);

        mLinearGradientSelect = new LinearGradient(curCX - radius, curCY - radius, curCX + radius,
                curCY + radius, new int[]{selectColorStart, selectColorEnd}, null,
                Shader.TileMode.CLAMP);
        paint.setShader(mLinearGradientSelect);
        canvas.drawCircle(curCX, curCY, animatorRadius, paint);

        paint.setTextSize(40);
        paint.getTextBounds(name, 0, name.length(), rect);
        rectWidth = rect.width() / 2;
        rectHeight = rect.height() / 2;
        animatorRectWidth = rectWidth;
        animatorRectHeight = rectHeight;
        paint.setShader(null);
        paint.setColor(isNormal ? textColor : Color.WHITE);
        canvas.drawText(name, curCX - animatorRectWidth, curCY + animatorRectHeight, paint);
    }

    @Override
    public void stop(boolean flag) {
        isStop = flag;
    }

    public boolean circleClick(boolean flag){
        if (isAnim) {
            return false;
        }
        isNormal = flag;
        animClick();
        return true;
    }

    public boolean isNormal(){
        return this.isNormal;
    }

    public String getName() {
        return this.name;
    }

    private void animClick(){
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float rate = (float) animation.getAnimatedValue();
                if (isNormal) {
                    animatorRadius = (1 - rate) * radius;
                    animatorRectWidth = (1 - rate) * rectWidth;
                    animatorRectHeight = (1 - rate) * rectHeight;
                } else {
                    animatorRadius = rate * radius;
                    animatorRectWidth = rate * rectWidth;
                    animatorRectHeight = rate * rectHeight;
                }
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnim = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnim = false;
            }
        });
        animator.start();
    }

    public static class Builder{

        private float cx, cy;
        private float dx, dy;
        private float radius;
        private float percentSpeed;
        private int textColor;
        private int colorStart, colorEnd;
        private int selectColorStart, selectColorEnd;
        private String name;

        public Builder setCx(float cx) {
            this.cx = cx;
            return this;
        }

        public Builder setCy(float cy) {
            this.cy = cy;
            return this;
        }

        public Builder setDx(float dx) {
            this.dx = dx;
            return this;
        }

        public Builder setDy(float dy) {
            this.dy = dy;
            return this;
        }

        public Builder setRadius(float radius) {
            this.radius = radius;
            return this;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setColorStart(int colorStart) {
            this.colorStart = colorStart;
            return this;
        }

        public Builder setSelectColorStart(int color) {
            this.selectColorStart = color;
            return this;
        }

        public Builder setColorEnd(int colorEnd) {
            this.colorEnd = colorEnd;
            return this;
        }

        public Builder setSelectColorEnd(int selectColorEnd) {
            this.selectColorEnd = selectColorEnd;
            return this;
        }

        public Builder setPercentSpeed(float percentSpeed) {
            this.percentSpeed = percentSpeed;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public CircleHolder build(){
            return new CircleHolder(this);
        }
    }

}