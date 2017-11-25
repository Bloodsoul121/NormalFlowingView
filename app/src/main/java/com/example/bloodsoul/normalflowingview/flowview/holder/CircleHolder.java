package com.example.bloodsoul.normalflowingview.flowview.holder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
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

    private float animatorRadius = 0;

    private float curPercent = 0f;

    private float percentSpeed;

    private int normalColor;

    private int selectColor;

    private boolean isGrowing = true;

    private boolean isNormal = true;

    private boolean isAnim = false;

    private boolean isStop = true;

    private String name = "";

    private Rect rect;

    private float rectWidth, rectHeight;

    private float animatorRectWidth, animatorRectHeight;

    private CircleHolder circleHolder;

    private LinearGradient mLinearGradient;

    private CircleHolder(Builder builder){
        this.cx = builder.cx;
        this.cy = builder.cy;
        this.dx = builder.dx;
        this.dy = builder.dy;
        this.radius = builder.radius;
        this.percentSpeed = builder.percentSpeed;
        this.normalColor = builder.color;
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
        paint.setColor(normalColor);

//        mLinearGradient = new LinearGradient(curCX - radius, curCY - radius, curCX + radius,
//                curCY + radius, new int[]{Color.BLUE, Color.RED}, null,
//                Shader.TileMode.CLAMP);
//        paint.setShader(mLinearGradient);

        canvas.drawCircle(curCX, curCY, radius, paint);

        paint.setTextSize(40);
        paint.getTextBounds(name, 0, name.length(), rect);
        rectWidth = rect.width() / 2;
        rectHeight = rect.height() / 2;
        animatorRectWidth = rectWidth;
        animatorRectHeight = rectHeight;

        if (isNormal) {
            paint.setColor(selectColor);
            canvas.drawCircle(curCX, curCY, animatorRadius, paint);

            paint.setColor(selectColor);
            canvas.drawText(name, curCX - animatorRectWidth, curCY + animatorRectHeight, paint);
        } else {
            paint.setColor(selectColor);
            canvas.drawCircle(curCX, curCY, animatorRadius, paint);

            paint.setColor(Color.WHITE);
            canvas.drawText(name, curCX - animatorRectWidth, curCY + animatorRectHeight, paint);
        }
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
                    circleHolder.animatorRadius = (1 - rate) * radius;
                    animatorRectWidth = (1 - rate) * rectWidth;
                    animatorRectHeight = (1 - rate) * rectHeight;
                } else {
                    circleHolder.animatorRadius = rate * radius;
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