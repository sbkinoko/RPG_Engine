package com.sbkinoko.sbkinokorpg.controller;

import static com.sbkinoko.sbkinokorpg.gameparams.GameParams.X_axis;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.core.content.res.ResourcesCompat;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.battleframe.BattleFrame;
import com.sbkinoko.sbkinokorpg.gameparams.Axis;
import com.sbkinoko.sbkinokorpg.gameparams.Dir;
import com.sbkinoko.sbkinokorpg.gameparams.GameParams;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public class ControllerFrame {
    private final Button btB, btA, btM;
    Stick stick;
    ArrowButton arrowButton;
    FrameLayout controllerFL;
    private final int controllerFrameHeight;
    private final int controllerFrameWidth;
    private int btSize;
    private final int btH_3;
    private final Player player;
    Resources res;
    Configuration config;
    MapFrame mapFrame;
    BattleFrame battleFrame;
    boolean btsCollision = false;

    Handler handler = new Handler();
    Runnable runnable;

    public ControllerFrame(Player player, Context context,
                           Configuration config1, int frameWidth, int frameHeight) {
        this.player = player;
        this.config = config1;
        this.controllerFrameWidth = frameWidth;

        controllerFL = new FrameLayout(context);
        if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            controllerFrameHeight = (int) ((frameHeight - frameWidth) * 0.6);//後々修正
            controllerFL.setY(MainGame.playWindowSize);
        } else {
            controllerFrameHeight = (MainGame.playWindowSize);//後々修正
            btSize = (frameWidth - frameHeight) / 2;
            controllerFL.setY(0);
        }
        btSize = (int) (controllerFrameHeight / 3.1);
        btH_3 = controllerFrameHeight / 3;
        controllerFL.setLayoutParams(new ViewGroup.LayoutParams(
                frameWidth,
                controllerFrameHeight
        ));

        res = context.getResources();
        controllerFL.setBackground(ResourcesCompat.getDrawable(res, R.drawable.bt_frame, null));

        btA = new Button(context);
        btB = new Button(context);
        btM = new Button(context);
        setCommandButton();

        arrowButton = new ArrowButton(context);

        arrowButton.setArrowButton(config, btSize, controllerFL,
                new ButtonTouchListener());

        stick = new Stick(context, player,
                config, this,
                controllerFrameHeight, controllerFrameWidth);

        controllerFL.addView(stick);

        if (controllerFrameWidth - controllerFrameHeight < controllerFrameHeight) {
            btsCollision = true;
        }

        resetButtonPlace();
    }

    public FrameLayout getControllerFL() {
        return controllerFL;
    }

    class ButtonTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            useBt(view, event);
            return false;
        }
    }

    void setCommandButton() {
        ButtonTouchListener buttonTouchListener = new ButtonTouchListener();
        btA.setText("A");
        btA.setId(GameParams.ID_btA);
        btA.setOnTouchListener(buttonTouchListener);
        ViewGroup.LayoutParams btLayout = new ViewGroup.LayoutParams(
                btSize,
                btSize
        );
        btA.setLayoutParams(btLayout);
        controllerFL.addView(btA);

        btB.setText("B");
        btB.setId(GameParams.ID_btB);
        btB.setOnTouchListener(buttonTouchListener);
        btB.setLayoutParams(btLayout);
        controllerFL.addView(btB);

        btM.setText("M");
        btM.setId(GameParams.ID_btMenu);
        btM.setOnTouchListener(buttonTouchListener);
        btM.setLayoutParams(btLayout);
        controllerFL.addView(btM);
    }

    public void setMapFrame(MapFrame mapFrame1) {
        this.mapFrame = mapFrame1;
    }

    public void setBattleFrame(BattleFrame battleFrame1) {
        battleFrame = battleFrame1;
    }

    private void useMoveButton(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            player.setCanMove(false);
            removeRunnable();
            return;
        }

        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return;
        }

        player.setCanMove(false);

        if (player.isInMap()) {
            if (mapFrame.isAllMenuClosed()) {
                player.setCanMove(true);
                mapFrame.onClick(view, event);
                return;
            }
            runnable = () -> {
                mapFrame.clickActiveWindow(view, event_down);
                handler.postDelayed(runnable, 500);
            };
        } else {
            runnable = () -> {
                battleFrame.onClick(view, event_down);
                handler.postDelayed(runnable, 500);
            };
        }
        handler.post(runnable);
    }

    private void useBt(View view, MotionEvent event) {
        Log.d("bugTest", "---useBt---");
        if (arrowButton.isArrowButton(view)) {
            useMoveButton(view, event);
            return;
        }

        if (player.isInBattle()) {
            battleFrame.onClick(view, event);
            return;
        }

        if (mapFrame.isAllMenuClosed()) {
            mapFrame.onClick(view, event);
            return;
        }

        mapFrame.clickActiveWindow(view, event);
    }

    public void removeRunnable() {
        handler.removeCallbacks(runnable);
    }

    public void useBtB(MotionEvent e) {
        useBt(btB, e);
    }

    public void useBtA(MotionEvent e) {
        useBt(btA, e);
    }

    public void useBtM(MotionEvent e) {
        useBt(btM, e);
    }

    public void useBtRight(MotionEvent e) {
        useBt(arrowButton.getBtRight(), e);
    }

    public void useBtDown(MotionEvent e) {
        useBt(arrowButton.getBtDown(), e);
    }

    public void useBtLeft(MotionEvent e) {
        useBt(arrowButton.getBtLeft(), e);
    }

    public void useBtUp(MotionEvent e) {
        useBt(arrowButton.getBtUp(), e);
    }

    public void resetButtonPlace() {
        setCommandButtonPosition();

        setMoveButtonPosition();
    }

    private void setCommandButtonPosition() {
        float baseX;
        float dX;
        if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (!btsCollision) {
                baseX = (float) (btH_3 - btSize) / 2;
                dX = btH_3;
            } else {
                baseX = (float) (btH_3 - btSize) / 2;
                dX = (float) (controllerFrameWidth - controllerFrameHeight) / 3 / 2;
            }
        } else {
            baseX = (float) ((controllerFrameWidth - controllerFrameHeight) / 4 - btSize / 2);
            dX = 0;
        }

        if (!OptionConst.isCommandButtonLeft()) {
            baseX = controllerFrameWidth - baseX - btSize;
            dX = -dX;
        }

        btA.setX(baseX);
        btB.setX(baseX + dX);
        btM.setX(baseX + 2 * dX);

        btA.setY((float) (btH_3 / 2 - btSize / 2));
        btB.setY((float) (btH_3 * 3 / 2 - btSize / 2));
        btM.setY((float) (btH_3 * 5 / 2 - btSize / 2));
    }

    private void setMoveButtonPosition() {
        int
                btVisibility = View.GONE,
                stickVisibility = View.GONE;
        switch (OptionConst.moveButtonType) {
            case OptionConst.moveType_Button:
                arrowButton.setArrowButtonPosition(config,
                        controllerFrameWidth, controllerFrameHeight,
                        btSize, btH_3);
                btVisibility = View.VISIBLE;
                break;

            case OptionConst.moveType_Stick:
                stick.setPosition();
                stickVisibility = View.VISIBLE;
                break;
        }

        arrowButton.setBtVisibility(btVisibility);
        stick.setVisibility(stickVisibility);
    }


    public void reDrawStick() {
        if (OptionConst.moveButtonType == OptionConst.moveType_Stick) {
            stick.reDraw();
        }
    }


    Dir dir = Dir.None;

    MotionEvent event_down = MotionEvent.obtain(
            0, 0, MotionEvent.ACTION_DOWN, 0, 0, 0);


    public void setSlant(float sin, float cos, float moveRatio) {
        if (player.isInMap()
                && mapFrame.isAllMenuClosed()) {
            int vParam = OptionConst.getActualV();
            float Vx = cos * vParam * moveRatio;
            float Vy = sin * vParam * moveRatio;

            int[] V = new int[2];
            V[X_axis] = (int) Vx;
            V[Axis.Y.id] = (int) Vy;

            player.setCanMove(true);
            player.setV(V);
            return;
        }

        switch (checkDir(sin, cos, moveRatio)) {
            case Up:
                if (dir != Dir.Up) {
                    dir = Dir.Up;
                    removeRunnable();
                    useBtUp(event_down);
                }
                break;
            case Down:
                if (dir != Dir.Down) {
                    dir = Dir.Down;
                    removeRunnable();
                    useBtDown(event_down);
                }
                break;
            case Left:
                if (dir != Dir.Left) {
                    dir = Dir.Left;
                    removeRunnable();
                    useBtLeft(event_down);
                }
                break;
            case Right:
                if (dir != Dir.Right) {
                    dir = Dir.Right;
                    removeRunnable();
                    useBtRight(event_down);
                }
                break;
            case None:
                removeRunnable();
                dir = Dir.None;
                break;
        }

    }

    private Dir checkDir(float sin, float cos, float moveRatio) {
        float bound = (float) 0.7;

        if (moveRatio <= 0.5) {
            return Dir.None;
        }

        if (cos > bound) {
            return Dir.Right;
        }
        if (cos < -bound) {
            return Dir.Left;
        }
        if (bound < sin) {
            return Dir.Down;
        }
        if (sin < -bound) {
            return Dir.Up;
        }

        return Dir.None;
    }
}
