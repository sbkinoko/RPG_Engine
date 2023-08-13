package com.sbkinoko.sbkinokorpg.controller;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

class Stick extends View {
    int areaSize;
    int areaCenter;
    Player player;
    Paint paint;
    float centerX, centerY;
    int stickSize;
    ControllerFrame controllerFrame;


    float r, rMax;
    float cos, sin;

    int controllerFrameWidth, controllerFrameHeight;
    Configuration config;

    Stick(Context context, Player player1,
          Configuration config, ControllerFrame controllerFrame,
          int controllerFrameHeight, int controllerFrameWidth) {
        super(context);
        this.player = player1;
        this.config = config;
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.black, null));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        this.setOnTouchListener(new StickTouchListener());
        this.controllerFrameHeight = controllerFrameHeight;
        this.controllerFrameWidth = controllerFrameWidth;

        if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setLayoutParams(controllerFrameHeight);
        } else {
            setLayoutParams((controllerFrameWidth - controllerFrameHeight) * 9 / 20);
        }
        setButtonsFrame(controllerFrame);
    }

    void setPosition() {
        float x;
        if (OptionConst.isCommandButtonLeft()) {
            if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
                x = controllerFrameWidth - areaSize;
            } else {
                x = controllerFrameWidth
                        - (float) (controllerFrameWidth - controllerFrameHeight) / 4
                        - (float) areaSize / 2;
            }
        } else {
            if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
                x = (float) (controllerFrameHeight - areaSize) / 2;
            } else {
                x = (float) (controllerFrameWidth - controllerFrameHeight) / 4
                        - (float) areaSize / 2;
            }
        }
        setX(x);
        setY((float) (controllerFrameHeight - areaSize) / 2);

        setVisibility(View.VISIBLE);
    }


    public void setLayoutParams(int AreaSize) {
        super.setLayoutParams(new ViewGroup.LayoutParams(
                AreaSize,
                AreaSize
        ));
        this.areaSize = AreaSize;
        areaCenter = areaSize / 2;
        stickSize = AreaSize / 6;
        rMax = areaCenter - stickSize;
        centerX = areaCenter;
        centerY = areaCenter;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(areaCenter, areaCenter, areaCenter, paint);
        canvas.drawCircle(centerX, centerY, stickSize, paint);
    }

    public void reDraw() {
        invalidate();
    }

    public void setButtonsFrame(ControllerFrame controllerFrame) {
        this.controllerFrame = controllerFrame;
    }

    private class StickTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent e) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    centerX = e.getX();
                    centerY = e.getY();

                    float distance = (float) Math.sqrt(Math.pow((centerX - areaCenter), 2)
                            + Math.pow((centerY - areaCenter), 2));

                    cos = (centerX - areaCenter) / distance;
                    sin = (centerY - areaCenter) / distance;
                    if (distance > rMax) {
                        r = rMax;
                        centerX = cos * rMax + areaCenter;
                        centerY = sin * rMax + areaCenter;
                    } else {
                        r = distance;
                    }

                    float moveRatio = r / (float) rMax;

                    controllerFrame.setSlant(sin, cos, moveRatio);

                    break;
                case MotionEvent.ACTION_UP:
                    player.setCanMove(false);
                    centerX = areaCenter;
                    centerY = areaCenter;
                    controllerFrame.setSlant(0, 0, 0);
                    break;
            }
            return true;
        }
    }
}
