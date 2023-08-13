package com.sbkinoko.sbkinokorpg.battleframe;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.battleframe.status.MonsterStatus;
import com.sbkinoko.sbkinokorpg.battleframe.window.BattleWindow_ChooseItem;
import com.sbkinoko.sbkinokorpg.battleframe.window.StatusConditionWindow;
import com.sbkinoko.sbkinokorpg.battleframe.window.menu.BattleWindow_Menu;
import com.sbkinoko.sbkinokorpg.battleframe.window.window_action_type.BattleWindow_ChooseAction;
import com.sbkinoko.sbkinokorpg.battleframe.window.window_chose_target.BattleWindow_TOP;
import com.sbkinoko.sbkinokorpg.battleframe.window.window_chose_target.ChooseTarget;
import com.sbkinoko.sbkinokorpg.battleframe.window.window_text.BattleWindow_Attack;
import com.sbkinoko.sbkinokorpg.battleframe.window.window_text.BattleWindow_End;
import com.sbkinoko.sbkinokorpg.battleframe.window.window_text.BattleWindow_Escape;
import com.sbkinoko.sbkinokorpg.controller.ControllerFrame;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;
import com.sbkinoko.sbkinokorpg.window.GameWindow;

public class BattleFrame {
    private final FrameLayout frame;
    private final Context context;
    static Resources res;
    ImageView[] iv;

    public BattleWindow_Attack battleAttackWindow;
    public BattleWindow_Escape battleEscapeWindow;
    public BattleWindow_End battleEndWindow;
    public BattleWindow_Menu battleMenuWindow;
    public BattleWindow_ChooseAction battleWindow_chooseAction;
    public BattleWindow_ChooseItem battleWindow_chooseItem;
    public ChooseTarget battleChooseEnmWindow;
    public BattleWindow_TOP battleWindow_Top;
    public StatusConditionWindow battleWindowStatus;

    //todo　バトル開始のタイミングで作成する
    public BattleFrame(Context context, Configuration config, int frameWidth, int frameHeight) {
        this.context = context;
        res = context.getResources();
        frame = new FrameLayout(context);
        if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            frame.setX(0);
            Toast.makeText(context, "TATE", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "yoko", Toast.LENGTH_SHORT).show();
            frame.setX((float) (frameWidth - frameHeight) / 2);
        }

        setFrame();

        makeMonsIV();
    }

    public void setFrame() {
        frame.setLayoutParams(new ViewGroup.LayoutParams(
                MainGame.playWindowSize,
                MainGame.playWindowSize
        ));
        frame.setBackground(ResourcesCompat.getDrawable(res, R.drawable.bg_bt_01, null));
        frame.setVisibility(View.GONE);
        frame.setOnTouchListener(new BattleFrameTouchListener());
    }

    public void makeMonsIV() {
        iv = new ImageView[BattleConst.maxMonsNum];
        for (int i = 0; i < iv.length; i++) {
            iv[i] = new ImageView(context);
            iv[i].setScaleType(ImageView.ScaleType.FIT_XY);
            frame.addView(iv[i]);
            iv[i].setVisibility(View.GONE);
            iv[i].setOnTouchListener(new MonsterImageTouchListener(i));
        }
    }

    public FrameLayout getBattleFrame() {
        return frame;
    }

    private ControllerFrame controllerFrame;

    public void setButtonsFrame(ControllerFrame controllerFrame1) {
        this.controllerFrame = controllerFrame1;
    }

    private MapFrame mapFrame;

    public void setMapFrame(MapFrame mapFrame1) {
        this.mapFrame = mapFrame1;
    }

    private Player player;

    public void setPlayer(Player player1) {
        this.player = player1;
    }

    static public GameWindow[] battleWindows;

    public void setBattleWindows(BattleSystem battleSystem) {
        battleWindows = new GameWindow[]{
                battleAttackWindow
                        = new BattleWindow_Attack(context, battleSystem),
                battleEscapeWindow
                        = new BattleWindow_Escape(context, battleSystem),
                battleEndWindow
                        = new BattleWindow_End(context, mapFrame, player, battleSystem),
                battleChooseEnmWindow
                        = new ChooseTarget(context, battleSystem),
                battleWindow_chooseAction
                        = new BattleWindow_ChooseAction(context, battleSystem),
                battleWindow_chooseItem
                        = new BattleWindow_ChooseItem(context, battleSystem),
                battleMenuWindow
                        = new BattleWindow_Menu(context, battleSystem),
                battleWindowStatus
                        = new StatusConditionWindow(context, battleSystem)
        };

        battleWindow_Top = new BattleWindow_TOP(context, battleSystem);
    }

    /**
     * 背景をセットする関数
     *
     * @param bgImage 背景
     */
    public void setBackGroundImage(int bgImage) {
        frame.setBackground(ResourcesCompat.getDrawable(res, bgImage, null));
    }


    /**
     * モンスターの画像サイズを設定する
     */
    private void setMonsterSize(MonsterStatus[] statusMonsters) {
        int monsterSize;
        //割る数を変更する
        if (statusMonsters.length <= 3) {
            monsterSize = MainGame.playWindowSize / 3;
        } else {
            monsterSize = MainGame.playWindowSize / statusMonsters.length;
        }

        for (int i = 0; i < statusMonsters.length; i++) {
            frame.removeView(iv[i]);
            iv[i].setLayoutParams(new ViewGroup.LayoutParams(
                    monsterSize,
                    monsterSize
            ));
            frame.addView(iv[i]);
        }
    }

    private void setMonsterPosition(MonsterStatus[] statusMonsters) {
        switch (statusMonsters.length) {
            case 1:
                iv[0].setX((float) MainGame.playWindowSize / 3);
                iv[0].setY((float) MainGame.playWindowSize * 1 / 3);
                break;
            case 2:
                for (int i = 0; i < 2; i++) {
                    iv[i].setX((float) MainGame.playWindowSize * (2 * (i + 1) - 1) / 6);
                    iv[i].setY((float) MainGame.playWindowSize / 3);
                }
                break;
            default:
                for (int i = 0; i < statusMonsters.length; i++) {
                    iv[i].setX((float) MainGame.playWindowSize / statusMonsters.length * i);
                    iv[i].setY((float) MainGame.playWindowSize * 2 / 3 - (float) MainGame.playWindowSize / statusMonsters.length);
                }
                break;
        }
    }

    /**
     * @param statusMonsters 　セットしたいモンスター
     */
    protected void setMonstersImage(MonsterStatus[] statusMonsters) {
        setMonsterSize(statusMonsters);
        setMonsterPosition(statusMonsters);

        for (int i = 0; i < statusMonsters.length; i++) {
            statusMonsters[i].setIV(iv[i], frame);
            statusMonsters[i].setBattleFrame(this);
        }
    }


    /**
     * battleFrameからMapFrameへの移動
     */
    public void closeBattleFrame() {
        for (ImageView imageView : iv) {
            imageView.setVisibility(View.GONE);
        }

        player.setInMap();
        setVisibility(View.GONE);
        mapFrame.getFrameLayout().setVisibility(View.VISIBLE);

    }

    boolean Shaving;

    public boolean isImageShaving() {
        return Shaving;
    }

    public void setShaving(boolean isShaving1) {
        Shaving = isShaving1;
    }

    public void setVisibility(int visibility) {
        frame.setVisibility(visibility);
    }

    public void onClick(View view, MotionEvent event) {
        for (GameWindow battleWindow : battleWindows) {
            if (battleWindow.isOpen()) {
                battleWindow.OnBtClicked(view, event);
                break;
            }
        }
    }

    private class BattleFrameTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent e) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    controllerFrame.useBtB(e);
                    break;
            }
            return true;
        }
    }

    private class MonsterImageTouchListener implements View.OnTouchListener {
        int enmNumber;

        MonsterImageTouchListener(int i) {
            super();
            this.enmNumber = i;
        }

        @Override
        public boolean onTouch(View v, MotionEvent e) {
            if (!battleChooseEnmWindow.isOpen()) {
                return false;
            }

            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (v.getVisibility() == View.VISIBLE
                            && battleChooseEnmWindow.getCanSelectEnm()) {
                        battleChooseEnmWindow.setSelectedEnm(enmNumber);
                        return true;
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}
