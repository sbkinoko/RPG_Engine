package com.sbkinoko.sbkinokorpg.dataList.item;

import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItem;
import com.sbkinoko.sbkinokorpg.game_item.action_item.item.ActionItemData;
import com.sbkinoko.sbkinokorpg.gameparams.GameParams;

public abstract class List_Item {
    ActionItemData[] dataList;

    public List_Item(ActionItemData[] dataList) {
        this.dataList = dataList;
    }

    private boolean isDataNull() {
        return dataList == null;
    }

    public int getItemNum() {
        if (isDataNull()) {
            return 0;
        }

        return dataList.length;
    }

    abstract public ActionItem getItemAt(int index);

    public int getWhereCanUse(int itemID) {
        if (isDataNull()) {
            return 0;
        }

        return dataList[itemID].getWhereCanUse();
    }

    public boolean canUseInBattle(int itemID) {
        int whereCanUse = getWhereCanUse(itemID);
        return (whereCanUse == GameParams.canInBoth
                || whereCanUse == GameParams.canInBattle);
    }

    public int getEffect(int itemID) {
        if (isDataNull()) {
            return 0;
        }
        return dataList[itemID].getEffect();
    }

    public String getName(int itemID) {
        if (isDataNull()) {
            return "";
        }
        return dataList[itemID].getName();
    }

    public int getTargetNum(int itemID) {
        if (isDataNull()) {
            return 0;
        }
        return dataList[itemID].getTargetNum();
    }

    public abstract int getAction();

}

