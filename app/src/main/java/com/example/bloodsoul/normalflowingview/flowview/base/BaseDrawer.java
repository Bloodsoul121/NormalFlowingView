package com.example.bloodsoul.normalflowingview.flowview.base;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * 抽象的基础绘制类 需要定制浮动的图像
 */
public abstract class BaseDrawer {

	private int width;

	private int height;

	private Paint paint;

	protected ArrayList<IBaseHolder> holders;

	private GradientDrawable gradientDrawable;

	public BaseDrawer() {
		this.holders = new ArrayList<>();
		this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}

	public void draw(Canvas canvas, float alpha) {
		drawFloatingBackground(canvas, alpha);
		drawGraphics(canvas);
	}

	private void drawFloatingBackground(Canvas canvas, float alpha) {
		if (gradientDrawable == null) {
			gradientDrawable = makeFloatingBackground();
			gradientDrawable.setBounds(0, 0, width, height);
		}
		gradientDrawable.setAlpha(Math.round(alpha * 255f));
		gradientDrawable.draw(canvas);
	}

	private GradientDrawable makeFloatingBackground(){
		return new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, getFloatingBackgroundGradient());
	}

	private int[] getFloatingBackgroundGradient() {
		return new int[] { 0x00000000, 0x00000000 };
	}

	private void drawGraphics(Canvas canvas){
		for (IBaseHolder holder : this.holders) {
			holder.updateAndDraw(canvas, paint);
		}
	}

	protected void addHolder(IBaseHolder holder){
		if (holders!=null) {
			holders.add(holder);
		}
	}

	protected ArrayList<IBaseHolder> getIHolders(){
		return holders;
	}

	public void setSize(int width, int height) {
		if(this.width != width && this.height != height){
			this.width = width;
			this.height = height;
			if (this.gradientDrawable != null) {
				gradientDrawable.setBounds(0, 0, width, height);
			}
		}
	}

	public static float getRandom(float min, float max) {
		if (max < min) {
			throw new IllegalArgumentException("max should bigger than min!!!!");
		}
		return (float) (min + Math.random() * (max - min));
	}

	public boolean setStop(boolean stop) {
		for (IBaseHolder holder : holders) {
			holder.stop(stop);
		}
		return stop;
	}

	public abstract void onTouch(MotionEvent event);

}
