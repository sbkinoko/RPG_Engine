package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.use_status;

import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.WindowDetail;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.StrategyForStatus;

public abstract class StrategyForNeedStatus extends StrategyForStatus {

    @Override
    public void useBtA_Player() {
        _useBtA_Player();
    }

    protected abstract void _useBtA_Player();

    @Override
    public void useBtB_Player() {
        _useBtB_Player();
    }

    protected abstract void _useBtB_Player();

    @Override
    public int[] getNowList() {
        return _getNowList();
    }

    public abstract int[] _getNowList();

    @Override
    public void openMenu_Player() {
        _openMenu_Player();
    }

    protected abstract void _openMenu_Player();

    /**
     * アイテムに空欄を入れる処理
     *
     * @param list 長さが足りないリスト
     * @return 空欄を埋めたリスト
     */
    protected int[] lengthenList(int[] list) {
        int[] tmpList = new int[WindowDetail.DETAIL_LIST_ITEM_NUM];
        System.arraycopy(list, 0, tmpList, 0, list.length);

        for (int i = list.length; i < tmpList.length; i++) {
            tmpList[i] = 1;
        }
        return tmpList;
    }
}
