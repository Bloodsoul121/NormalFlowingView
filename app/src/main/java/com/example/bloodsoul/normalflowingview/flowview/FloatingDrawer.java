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
                    .setCx(0.35f * width)
                    .setCy(0.3f * width)
                    .setDx(0.06f * width)
                    .setDy(0.022f * width)
                    .setRadius(0.11f * width)
                    .setPercentSpeed(0.0019f)
                    .setColor(Color.parseColor("#FFE2FFF8"))
                    .setName("情感星座")
                    .setSelectColor(Color.parseColor("#FF59DABC"))
                    .build());
            addHolder(new CircleHolder.Builder()
                    .setCx(0.75f * width)
                    .setCy(0.32f * width)
                    .setDx(0.06f * width)
                    .setDy(0.022f * width)
                    .setRadius(0.105f * width)
                    .setPercentSpeed(0.0017f)
                    .setColor(Color.parseColor("#FFECFCF3"))
                    .setName("科技数码")
                    .setSelectColor(Color.parseColor("#FF8BEAB4"))
                    .build());
            addHolder(new CircleHolder.Builder()
                    .setCx(0.25f * width)
                    .setCy(0.57f * width)
                    .setDx(-0.05f * width)
                    .setDy(0.05f * width)
                    .setRadius(0.14f * width)
                    .setPercentSpeed(0.002f)
                    .setColor(Color.parseColor("#FFFAF6EE"))
                    .setName("体育赛事")
                    .setSelectColor(Color.parseColor("#FFFACD74"))
                    .build());
            addHolder(new CircleHolder.Builder()
                    .setCx(0.68f * width)
                    .setCy(0.75f * width)
                    .setDx(0.08f * width)
                    .setDy(-0.035f * width)
                    .setRadius(0.12f * width)
                    .setPercentSpeed(0.0025f)
                    .setColor(Color.parseColor("#FFFDF8F8"))
                    .setName("生活休闲")
                    .setSelectColor(Color.parseColor("#FFFFB0B0"))
                    .build());
            addHolder(new CircleHolder.Builder()
                    .setCx(0.42f * width)
                    .setCy(0.8f * width)
                    .setDx(0.06f * width)
                    .setDy(-0.025f * width)
                    .setRadius(0.1f * width)
                    .setPercentSpeed(0.0020f)
                    .setColor(Color.parseColor("#FFE7F7FA"))
                    .setName("育儿护理")
                    .setSelectColor(Color.parseColor("#FF6BDEF5"))
                    .build());
            addHolder(new CircleHolder.Builder()
                    .setCx(0.57f * width)
                    .setCy(0.5f * width)
                    .setDx(-0.05f * width)
                    .setDy(0.05f * width)
                    .setRadius(0.13f * width)
                    .setPercentSpeed(0.00185f)
                    .setColor(Color.parseColor("#FFF3E2F7"))
                    .setName("时政资讯")
                    .setSelectColor(Color.parseColor("#FFCA61E4"))
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
                                holder.circleClick(!holder.isNormal());
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
