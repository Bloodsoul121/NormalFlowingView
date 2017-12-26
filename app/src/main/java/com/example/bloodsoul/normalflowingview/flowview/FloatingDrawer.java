package com.example.bloodsoul.normalflowingview.flowview;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.example.bloodsoul.normalflowingview.flowview.base.BaseDrawer;
import com.example.bloodsoul.normalflowingview.flowview.base.IBaseHolder;
import com.example.bloodsoul.normalflowingview.flowview.holder.CircleHolder;

public class FloatingDrawer extends BaseDrawer {

    private long currentMS;
    private float downX;
    private float downY;
    private float moveX;
    private float moveY;

    public FloatingDrawer() {
        super();
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        if (holders.size() == 0) {
            addHolder(new CircleHolder.Builder()
                    .setCx(156f)
                    .setCy(222f)
                    .setDx(0f)
                    .setDy(50f)
                    .setRadius(129f)
                    .setPercentSpeed(0.0075f)
                    .setColorStart(Color.parseColor("#2600baff"))
                    .setColorEnd(Color.parseColor("#2616d5ff"))
                    .setSelectColorStart(Color.parseColor("#b300baff"))
                    .setSelectColorEnd(Color.parseColor("#b316d5ff"))
                    .setName("二次元")
                    .setTextColor(Color.parseColor("#06c0ff"))
                    .build());
            addHolder(new CircleHolder.Builder()
                    .setCx(432f)
                    .setCy(147f)
                    .setDx(0f)
                    .setDy(45f)
                    .setRadius(129f)
                    .setPercentSpeed(0.0076f)
                    .setColorStart(Color.parseColor("#26ff2e6e"))
                    .setColorEnd(Color.parseColor("#26ff5799"))
                    .setSelectColorStart(Color.parseColor("#b3ff2e6e"))
                    .setSelectColorEnd(Color.parseColor("#b3ff5799"))
                    .setName("数码运动")
                    .setTextColor(Color.parseColor("#ff3a7b"))
                    .build());
            addHolder(new CircleHolder.Builder()
                    .setCx(936)
                    .setCy(201f)
                    .setDx(0f)
                    .setDy(60f)
                    .setRadius(129f)
                    .setPercentSpeed(0.0083f)
                    .setColorStart(Color.parseColor("#26ffbc00"))
                    .setColorEnd(Color.parseColor("#26ffdd00"))
                    .setSelectColorStart(Color.parseColor("#b3ffbc00"))
                    .setSelectColorEnd(Color.parseColor("#b3ffdd00"))
                    .setName("文艺范")
                    .setTextColor(Color.parseColor("#ffc000"))
                    .build());
            addHolder(new CircleHolder.Builder()
                    .setCx(672f)
                    .setCy(351f)
                    .setDx(0f)
                    .setDy(40f)
                    .setRadius(129f)
                    .setPercentSpeed(0.0082f)
                    .setColorStart(Color.parseColor("#2600df50"))
                    .setColorEnd(Color.parseColor("#2676e900"))
                    .setSelectColorStart(Color.parseColor("#b300df50"))
                    .setSelectColorEnd(Color.parseColor("#b376e900"))
                    .setName("成熟中年")
                    .setTextColor(Color.parseColor("#23e238"))
                    .build());
            addHolder(new CircleHolder.Builder()
                    .setCx(180f)
                    .setCy(699f)
                    .setDx(0f)
                    .setDy(50f)
                    .setRadius(129f)
                    .setPercentSpeed(0.0071f)
                    .setColorStart(Color.parseColor("#2600baff"))
                    .setColorEnd(Color.parseColor("#2616d5ff"))
                    .setSelectColorStart(Color.parseColor("#b300baff"))
                    .setSelectColorEnd(Color.parseColor("#b316d5ff"))
                    .setName("娱乐八卦")
                    .setTextColor(Color.parseColor("#06c0ff"))
                    .build());
            addHolder(new CircleHolder.Builder()
                    .setCx(384f)
                    .setCy(489f)
                    .setDx(0f)
                    .setDy(-50f)
                    .setRadius(129f)
                    .setPercentSpeed(0.0082f)
                    .setColorStart(Color.parseColor("#26ffbc00"))
                    .setColorEnd(Color.parseColor("#26ffdd00"))
                    .setSelectColorStart(Color.parseColor("#b3ffbc00"))
                    .setSelectColorEnd(Color.parseColor("#b3ffdd00"))
                    .setName("财经新闻")
                    .setTextColor(Color.parseColor("#ffc000"))
                    .build());
            addHolder(new CircleHolder.Builder()
                    .setCx(630f)
                    .setCy(650f)
                    .setDx(0f)
                    .setDy(20f)
                    .setRadius(129f)
                    .setPercentSpeed(0.0077f)
                    .setColorStart(Color.parseColor("#26ff2e6e"))
                    .setColorEnd(Color.parseColor("#26ff5799"))
                    .setSelectColorStart(Color.parseColor("#b3ff2e6e"))
                    .setSelectColorEnd(Color.parseColor("#b3ff5799"))
                    .setName("打工族")
                    .setTextColor(Color.parseColor("#ff3a7b"))
                    .build());
            addHolder(new CircleHolder.Builder()
                    .setCx(894f)
                    .setCy(537f)
                    .setDx(0f)
                    .setDy(20f)
                    .setRadius(129f)
                    .setPercentSpeed(0.0069f)
                    .setColorStart(Color.parseColor("#2600baff"))
                    .setColorEnd(Color.parseColor("#2616d5ff"))
                    .setSelectColorStart(Color.parseColor("#b300baff"))
                    .setSelectColorEnd(Color.parseColor("#b316d5ff"))
                    .setName("宝妈奶爸")
                    .setTextColor(Color.parseColor("#06c0ff"))
                    .build());
            addHolder(new CircleHolder.Builder()
                    .setCx(423f)
                    .setCy(840f)
                    .setDx(0f)
                    .setDy(20f)
                    .setRadius(129f)
                    .setPercentSpeed(0.0081f)
                    .setColorStart(Color.parseColor("#2600df50"))
                    .setColorEnd(Color.parseColor("#2676e900"))
                    .setSelectColorStart(Color.parseColor("#b300df50"))
                    .setSelectColorEnd(Color.parseColor("#b376e900"))
                    .setName("历史军事")
                    .setTextColor(Color.parseColor("#23e238"))
                    .build());
            addHolder(new CircleHolder.Builder()
                    .setCx(906f)
                    .setCy(864f)
                    .setDx(0f)
                    .setDy(-20f)
                    .setRadius(129f)
                    .setPercentSpeed(0.0065f)
                    .setColorStart(Color.parseColor("#2600df50"))
                    .setColorEnd(Color.parseColor("#2676e900"))
                    .setSelectColorStart(Color.parseColor("#b300df50"))
                    .setSelectColorEnd(Color.parseColor("#b376e900"))
                    .setName("涨知识")
                    .setTextColor(Color.parseColor("#23e238"))
                    .build());
        }
    }

    @Override
    public void onTouch(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = e.getX();
                downY = e.getY();
                moveX = 0;
                moveY = 0;
                currentMS = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX += Math.abs(e.getX() - downX);
                moveY += Math.abs(e.getY() - downY);
                downX = e.getX();
                downY = e.getY();
                break;
            case MotionEvent.ACTION_UP:
                long moveTime = System.currentTimeMillis() - currentMS;
                if (isClickMove(moveTime)) {
                    // 滑动
                    break;
                } else {
                    // 点击
                    for (IBaseHolder hold : getIHolders()) {
                        if (hold instanceof CircleHolder) {
                            CircleHolder holder = ((CircleHolder) hold);
                            //点击位置x坐标与圆心的x坐标的距离
                            int distanceX = (int) Math.abs(holder.curCX - downX);
                            //点击位置y坐标与圆心的y坐标的距离
                            int distanceY = (int) Math.abs(holder.curCY - downY);
                            //点击位置与圆心的直线距离
                            int distanceZ = (int) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
                            //如果点击位置与圆心的距离大于圆的半径，证明点击位置没有在圆内
                            if (distanceZ <= holder.radius) {
                                boolean isClick = holder.circleClick(!holder.isNormal());
                                if (isClick && mOnItemClickListener != null) {
                                    mOnItemClickListener.onChannelItemClick(holder.getName());
                                }
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean isClickMove(long moveTime) {
        return moveTime > ViewConfiguration.getTapTimeout() || (moveX > 20 || moveY > 20);
    }

}
