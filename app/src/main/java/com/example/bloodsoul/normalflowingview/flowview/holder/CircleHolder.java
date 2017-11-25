package com.example.bloodsoul.normalflowingview.flowview.holder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.animation.AccelerateInterpolator;

import com.example.bloodsoul.normalflowingview.flowview.base.BaseDrawer;
import com.example.bloodsoul.normalflowingview.flowview.base.IBaseHolder;

public class CircleHolder implements IBaseHolder {

    private float cx, cy;

    private float dx, dy;

    public float curCX, curCY;

    public float radius;

    public float animatorRadius = 0;

    private float curPercent = 0f;

    private float percentSpeed;

    private int textSize = 40;

    private int color;

    private int selectColor;

    private boolean isGrowing = true;

    private boolean isNormal = true;

    private boolean isAnim = false;

    private boolean isStop = true;

    private String name = "";

    private Rect rect;

    private CircleHolder circleHolder;

    private CircleHolder(Builder builder){
        this.cx = builder.cx;
        this.cy = builder.cy;
        this.dx = builder.dx;
        this.dy = builder.dy;
        this.radius = builder.radius;
        this.percentSpeed = builder.percentSpeed;
        this.color = builder.color;
        this.name = builder.name;
        this.rect = new Rect();
        this.selectColor = builder.selectColor;
        circleHolder = this;
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

        paint.setColor(selectColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{2, 2}, 0));
        paint.setStrokeWidth(1);
        canvas.drawCircle(curCX, curCY, radius+1, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(null);
        paint.setColor(color);
        canvas.drawCircle(curCX, curCY, radius, paint);

        if (isNormal) {
            paint.setTextSize(textSize);
            paint.getTextBounds(name, 0, name.length(), rect);
            paint.setColor(selectColor);
            canvas.drawText(name, curCX - rect.width() / 2, curCY + rect.height() / 2, paint);
        } else {
            paint.setColor(selectColor);
            paint.setStyle(Paint.Style.FILL);

            paint.setColor(selectColor);
            canvas.drawCircle(curCX, curCY, radius, paint);

            paint.setColor(Color.WHITE);
            canvas.drawText(name, curCX - rect.width() / 2, curCY + rect.height() / 2, paint);
        }
    }

    @Override
    public void stop(boolean flag) {
        isStop = flag;
    }

    public void circleClick(boolean flag){
        if (isAnim) {
            return;
        }
        isNormal = flag;
        animClick();
    }

    public boolean isNormal(){
        return isNormal;
    }

    private void animClick(){
        final float         ra         = radius;
        final int           startColor = circleHolder.color;
        final int           endColor   = selectColor;
        ValueAnimator       animator   = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float rate = (float) animation.getAnimatedValue();
                if (isNormal) {
                    circleHolder.radius = rate * ra;
                    circleHolder.color = startColor;
                } else {
                    circleHolder.radius = rate * ra;
                    circleHolder.selectColor = endColor;
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
        private int color;
        private int selectColor;
        private float percentSpeed;
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

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        public Builder setSelectColor(int color) {
            this.selectColor = color;
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