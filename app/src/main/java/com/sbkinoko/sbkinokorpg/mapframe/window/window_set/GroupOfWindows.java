package com.sbkinoko.sbkinokorpg.mapframe.window.window_set;

import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.EQP_FROM_EQP_LIST;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.EQP_FROM_STATUS;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.EQP_LIST_TO;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.JOB;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.NUM_MapMenu_BUY;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.NUM_MapMenu_EQP_LIST;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.NUM_MapMenu_ITEM_GIVE;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.NUM_MapMenu_ITEM_SEE;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.NUM_MapMenu_ITEM_USE;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.NUM_MapMenu_SKILL_SEE;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.NUM_MapMenu_SKILL_USE;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.NUM_MapMenu_STATUS;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.NUM_MapMenu_TO;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.NUM_MapMenu_WARP;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.SELL_EQP;
import static com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList.SELL_TOOL;

import android.content.Context;

import com.sbkinoko.sbkinokorpg.GameParams;
import com.sbkinoko.sbkinokorpg.battleframe.BattleConst;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Item;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Skill;
import com.sbkinoko.sbkinokorpg.dataList.item.List_Tool;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.mapframe.MapFrame;
import com.sbkinoko.sbkinokorpg.mapframe.Player;
import com.sbkinoko.sbkinokorpg.mapframe.window.MapWindow_TextBox;
import com.sbkinoko.sbkinokorpg.mapframe.window.WindowIdList;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.amount.WindowAmount;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.StrategyForEQP;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.StrategyForEQPList;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.StrategyForJob;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.StrategyForList;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.StrategyForSkill;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.StrategyForStatus;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.StrategyForTool_Give;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.StrategyForTool_Menu;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.StrategyForWarp;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.StrategyForBuy;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.WindowMoney;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell.StrategyForSellEQP;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell.StrategyForSellTool;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping.for_sell.WindowSelectItemKind;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.use_status.StrategyForEQPTo;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.use_status.StrategyForUseItem;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.use_status.StrategyForUseSkill;
import com.sbkinoko.sbkinokorpg.repository.PlayerToolRepository;

public class GroupOfWindows {
    private WindowDetail windowDetail;

    public WindowDetail getWindowDetail() {
        return windowDetail;
    }

    private WindowPlayer windowPlayer;

    public WindowPlayer getWindowPlayer() {
        return windowPlayer;
    }

    private WindowSelectItemKind windowSelectItemKind;

    public WindowSelectItemKind getWindowSelectItemKind() {
        return windowSelectItemKind;
    }

    public void reOpenPlayerWindow() {
        windowPlayer.openMenu(
                getWindowType()
                        + NUM_MapMenu_TO);
    }

    public void openJob() {
        windowPlayer.openMenu(JOB);
    }


    private int EQPPosition;

    public int getEQPPosition() {
        return EQPPosition;
    }

    public void setEQPPosition(int EQPPosition) {
        this.EQPPosition = EQPPosition;
    }

    private final Context context;

    public GroupOfWindows(Context context) {
        this.context = context;
    }

    public boolean isIdBag() {
        return selectedPlayerID == GameParams.PLAYER_NUM;
    }

    public boolean isFromPlayerBag() {
        return fromPlayer == GameParams.PLAYER_NUM;
    }

    private WindowMoney windowMoney;

    public void showMoney() {
        if (windowMoney == null) {
            windowMoney = new WindowMoney(player, context);
            getMapFrame().getFrameLayout().addView(windowMoney);
        }
        windowMoney.showMoney();
    }

    public void hideMoney() {
        if (windowMoney == null) {
            return;
        }
        getMapFrame().getFrameLayout().removeView(windowMoney);
        windowMoney = null;
    }


    private WindowExplanation windowExplanation;

    public WindowExplanation getWindowExplanation() {
        return windowExplanation;
    }

    public MapFrame getMapFrame() {
        return windowDetail.getMapFrame();
    }

    private WindowAmount windowAmount;

    public WindowAmount getWindowAmount() {
        return windowAmount;
    }

    public void setWindowAmount(WindowAmount windowAmount) {
        this.windowAmount = windowAmount;
    }

    public void setWindows(WindowDetail detail,
                           WindowPlayer player,
                           WindowExplanation info,
                           MapWindow_TextBox textBox,
                           WindowSelectItemKind windowSelectItemKind) {
        this.windowDetail = detail;
        this.windowPlayer = player;
        this.windowExplanation = info;
        this.windowText = textBox;
        this.windowSelectItemKind = windowSelectItemKind;
    }

    private MapWindow_TextBox windowText;

    public MapWindow_TextBox getWindowText() {
        return windowText;
    }

    public void setDetailOpen() {
        windowDetail.setOpen();
        windowPlayer.setIsClosed();
        windowExplanation.setIsClosed();
    }

    public void setPlayerOpen() {
        windowPlayer.setOpen();
        windowDetail.setIsClosed();
        windowExplanation.setIsClosed();
    }

    void setExplanationOpen() {
        windowPlayer.setIsClosed();
        windowDetail.setIsClosed();
        windowExplanation.setOpen();
    }

    Player player;

    public Player getPlayer() {
        return player;
    }

    PlayerStatus[] playerStatuses = new PlayerStatus[1];

    public PlayerStatus[] getPlayerStatuses() {
        return playerStatuses;
    }

    public void setPlayerInfo(Player player, PlayerStatus[] playerStatuses) {
        this.player = player;
        this.playerStatuses = playerStatuses;
    }

    private int fromPlayer;

    public int getFromPlayer() {
        return fromPlayer;
    }

    public void setFromPlayer() {
        this.fromPlayer = windowPlayer.getSelectedTV();
    }

    private int toPlayer;

    public void setToPlayer() {
        this.toPlayer = windowPlayer.getSelectedTV();
    }

    public int getToPlayer() {
        return toPlayer;
    }

    private int selectedPlayerID;

    public int getSelectedPlayerID() {
        return selectedPlayerID;
    }

    public void setSelectedPlayerID(int playerID) {
        this.selectedPlayerID = playerID;
    }

    public PlayerStatus getSelectedIdPlayerStatus() {
        return playerStatuses[selectedPlayerID];
    }

    public PlayerStatus getFromPlayerStatus() {
        if (0 <= getFromPlayer()
                && getFromPlayer() < GameParams.PLAYER_NUM) {
            return playerStatuses[getFromPlayer()];
        }
        return null;
    }

    public List_Item getList_item() {
        if (WindowIdList.isToolWindow(windowType)) {
            return new List_Tool();
        } else if (WindowIdList.isSkillWindow(windowType)) {
            return new List_Skill();
        }
        return null;
    }

    private int windowType;

    public int getWindowType() {
        return windowType;
    }

    public void setWindowType(int windowType) {
        this.windowType = windowType;
        setStrategyForList();
    }

    public boolean canSeeBag() {
        return WindowIdList.canSeeBag(windowType);
    }

    public boolean isSelectableAll(int itemId) {
        return (isToPlayer()
                && windowType != NUM_MapMenu_ITEM_GIVE
                && getList_item() != null
                && 1 != getList_item().getItemAt(itemId).getTargetNum()
        );
    }

    public boolean canUse() {
        return getWindowDetail().canSelect(
                getWindowDetail().getSelectedTV());
    }

    public boolean isToPlayer() {
        return NUM_MapMenu_TO <= windowType;
    }

    public boolean needTarget() {
        return WindowIdList.isNeedTarget(windowType);
    }

    public boolean isWindowTypeFrom() {
        return WindowIdList.isWindowTypeFrom(windowType);
    }

    PlayerToolRepository playerToolRepository = PlayerToolRepository.getPlayerToolRepository();

    public ActionItem getActionItem() {
        if (WindowIdList.isWindowTypeWarp(windowType)) {
            return ((StrategyForWarp) strategyForList).getWarpItem();
        }

        //todo          isToolWindow(windowType)でよいのでは？
        if (getList_item().getAction() == BattleConst.Action_Tool) {
            int itemID;
            if (getFromPlayerStatus() == null) {
                itemID = getPlayer().getToolIdAt(getSelectedItemPosition());
                ;
            } else {
                //fixme toolrepoを使わない
                itemID = playerToolRepository.getItem(
                        getFromPlayerStatus().getPlayerID(),
                        getSelectedItemPosition());
            }
            return List_Tool.getToolAt(itemID);
        } else {//スキル使用時の分岐
            int itemID = getFromPlayerStatus().getSkills()[getSelectedItemPosition()];
            return List_Skill.getSkillAt(itemID);
        }
    }

    public int getItemId() {
        PlayerStatus playerStatus = getFromPlayerStatus();
        switch (windowType) {
            case NUM_MapMenu_ITEM_GIVE:
            case NUM_MapMenu_ITEM_USE:
                if (playerStatus != null) {
                    return playerToolRepository.getItem(
                            playerStatus.getPlayerID(),
                            getSelectedItemPosition());
                }
                return player.getToolIdAt(getSelectedItemPosition());

            case NUM_MapMenu_SKILL_USE:
                if (playerStatus != null) {
                    return playerStatus.getSkills()[getSelectedItemPosition()];
                }
        }
        throw new RuntimeException("ここには来ないはず");
    }

    private StrategyForList strategyForList;


    public void setStrategyForList() {
        switch (windowType) {
            case NUM_MapMenu_ITEM_SEE:
                strategyForList = new StrategyForTool_Menu();
                break;
            case NUM_MapMenu_SKILL_SEE:
                strategyForList = new StrategyForSkill(this);
                break;
            case NUM_MapMenu_STATUS:
                strategyForList = new StrategyForStatus();
                break;
            case EQP_FROM_STATUS:
            case EQP_FROM_EQP_LIST:
                strategyForList = new StrategyForEQP();
                break;
            case NUM_MapMenu_BUY:
                strategyForList = new StrategyForBuy();
                break;
            case SELL_TOOL:
                strategyForList = new StrategyForSellTool();
                break;
            case SELL_EQP:
                strategyForList = new StrategyForSellEQP();
                break;
            case NUM_MapMenu_ITEM_USE:
                strategyForList = new StrategyForUseItem();
                break;
            case NUM_MapMenu_ITEM_GIVE:
                strategyForList = new StrategyForTool_Give();
                break;
            case NUM_MapMenu_SKILL_USE:
                strategyForList = new StrategyForUseSkill();
                break;
            case NUM_MapMenu_WARP:
                strategyForList = new StrategyForWarp();
                break;

            case NUM_MapMenu_EQP_LIST:
                strategyForList = new StrategyForEQPList();
                break;

            case EQP_LIST_TO:
                strategyForList = new StrategyForEQPTo();
                break;

            case JOB:
                strategyForList = new StrategyForJob();
                break;

            default:
                throw new RuntimeException("windowTypeが不正です");
        }
        strategyForList.setGroupOfWindows(this);
    }

    public StrategyForList getStrategyForList() {
        return strategyForList;
    }

    private int selectedItemPosition;

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    public void setSelectedItemPosition(int selectedItemPosition) {
        this.selectedItemPosition = selectedItemPosition;
    }

    public void closeShoppingWindow() {
        getWindowDetail().closeMenu();
        getWindowDetail().resetSelectedTV();
        hideMoney();
        getPlayer().setNextEventFlag(true);
        setWindowAmount(null);
        getMapFrame().checkNextEvent();
    }

    public void goToSelectSellItem() {
        getWindowDetail().closeMenu();
        windowSelectItemKind.reOpenMenu();
    }

    public void tapMerchandiseWindow(int i) {
        getWindowAmount().closeMenu();
        getMapFrame().getMapTextBoxWindow().closeMenu();
        windowSelectItemKind.setIsClosed();
        setDetailOpen();
        getWindowDetail().setSelectedTv(i);
    }

    public boolean isShopping() {
        return WindowIdList.isShoppingWindow(windowType);
    }
}
