package com.sbkinoko.sbkinokorpg.battleframe.window.window_text;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sbkinoko.sbkinokorpg.battleframe.BattleSystem;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Tool;
import com.sbkinoko.sbkinokorpg.gameparams.BattleResult;
import com.sbkinoko.sbkinokorpg.gameparams.EventBattleFlag;
import com.sbkinoko.sbkinokorpg.gameparams.GameParams;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public class BattleWindow_End extends BattleTxtWindow {
    MapFrame mapFrame;
    Player player;
    int pageNum;
    int maxPageNum;
    int lvNowCheckNum, itemIndex;
    int exp, money;
    int maxLines = 2;

    private BattleResult battleResult;

    public BattleWindow_End(Context context, MapFrame mapFrame1, Player player1, BattleSystem battleSystem) {
        super(context, battleSystem);

        this.mapFrame = mapFrame1;
        this.player = player1;

        this.menuTV = new TextView[maxLines];
        setMenuTvs();
    }

    @Override
    public void setMenuTv(int i) {
        this.menuTV[i].setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        menuTV[i].setGravity(Gravity.CENTER);
        menuTV[i].setMaxLines(2);
    }


    int lvUpNum = 0;
    int dropItemNum = 0;

    //todo　表示だけにして処理を別にする
    @Override
    public void useBtA() {
        if (maxPageNum <= pageNum) {
            battleFrame.closeBattleFrame(eventBattleFlag, battleResult);
            this.closeMenu();
            return;
        }

        String txt;
        if (pageNum == 0) {
            //関数化
            exp = battleSystem.getEXP();
            money = battleSystem.getPrize();
            txt = exp + "の経験値と\n"
                    + money + "Mを手に入れた";
            menuTV[0].setText(txt);
            player.addMoney(money);

            lvUpNum = 0;
            dropItemNum = 0;
            lvNowCheckNum = 0;

            for (int i = 0; i < GameParams.PLAYER_NUM; i++) {
                if (battleSystem.getPlayer(i).isLevelUp()) {
                    maxPageNum++;
                    lvUpNum++;
                }
            }

            itemIndex = 0;
            for (int i = 0; i < dropItems.length; i++) {
                if (dropItems[i] != 0) {
                    maxPageNum++;
                    dropItemNum++;
                }
            }

        } else if (pageNum <= lvUpNum) {
            //関数化
            for (; lvNowCheckNum < GameParams.PLAYER_NUM; lvNowCheckNum++) {
                if (!battleSystem.getPlayer(lvNowCheckNum).isLevelUp()) {
                    continue;
                }

                txt = battleSystem.getPlayer(lvNowCheckNum).getName()
                        + "\nはレベルが上がった";
                menuTV[0].setText(txt);
                lvNowCheckNum++;
                break;
            }
        } else {
            for (; itemIndex < dropItems.length; itemIndex++) {
                int itemID = dropItems[itemIndex];
                if (itemID == 0) {
                    continue;
                }

                txt = List_Tool.getToolAt(itemID).getName();
                txt += "\nを手に入れた";
                menuTV[0].setText(txt);
                itemIndex++;
                break;

            }
        }
        pageNum++;
    }

    @Override
    public void useBtB() {
        useBtA();
    }

    @Override
    public void useBtM() {
        useBtA();
    }

    private EventBattleFlag eventBattleFlag;

    public void openMenu(String name, BattleResult battleResult, EventBattleFlag eventBattleFlag) {
        super.openMenu();
        pageNum = 0;
        maxPageNum = 0;
        this.battleResult = battleResult;
        this.eventBattleFlag = eventBattleFlag;
        menuTV[0].setText(getText(this.battleResult, name));
        if (this.battleResult == BattleResult.Win) {
            maxPageNum = 1;
        }
    }

    private String getText(BattleResult battleResult, String name) {
        if (battleResult == BattleResult.Win) {
            return name + "との\n戦闘に勝った";
        } else {
            return name + "との\n戦闘に負けた";
        }
    }

    int[] dropItems;

    public void addItem(int[] dropItems) {
        this.dropItems = dropItems;
    }
}
