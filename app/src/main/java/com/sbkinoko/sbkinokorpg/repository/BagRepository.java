package com.sbkinoko.sbkinokorpg.repository;

import static com.sbkinoko.sbkinokorpg.mapframe.Player.MAX_ITEM_NUM;

import com.sbkinoko.sbkinokorpg.dataList.item.List_Tool;
import com.sbkinoko.sbkinokorpg.game_item.action_item.tool.LastItemUseUpDate;

import javax.inject.Inject;

public class BagRepository {
    private static BagRepository bagRepository;

    private final int[][] toolArray;
    private final int arrayLength = new List_Tool().getItemNum();

    @Inject
    public BagRepository() {
        toolArray = new int[arrayLength][2];
    }

    private final int INDEX_ITEM_ID = 0;
    private final int INDEX_ITEM_NUM = 1;

    public static void setBagRepository() {
        bagRepository = new BagRepository();
    }

    public static BagRepository getBagRepository() {
        return bagRepository;
    }

    public int[][] getToolArray() {
        return toolArray;
    }

    public int getToolIdAt(int itemPosition) {
        return toolArray[itemPosition][INDEX_ITEM_ID];
    }

    public int getToolNumAt(int itemPosition) {
        return toolArray[itemPosition][INDEX_ITEM_NUM];
    }

    public void addTool(int itemId, int quantity) {
        for (int i = 0; i < new List_Tool().getItemNum(); i++) {
            if (toolArray[i][INDEX_ITEM_ID] == itemId) {
                toolArray[i][INDEX_ITEM_NUM] += quantity;
                break;
            } else if (toolArray[i][INDEX_ITEM_ID] == 0) {
                toolArray[i][INDEX_ITEM_ID] = itemId;
                toolArray[i][INDEX_ITEM_NUM] = quantity;
                break;
            }
        }
    }

    /**
     * まとめて弄る
     */
    public void decreaseTool(int itemId, int quantity) {
        int itemPosition = arrayLength - 1;
        for (int i = 0; i < arrayLength; i++) {
            if (toolArray[i][INDEX_ITEM_ID] == itemId) {
                toolArray[i][INDEX_ITEM_NUM] -= quantity;
                itemPosition = i;
                break;
            }
        }

        if (toolArray[itemPosition][0] != 0) {
            return;
        }

        siftTools(itemPosition);
    }

    public void decreaseBagTool(int usedItemPosition) {
        toolArray[usedItemPosition][INDEX_ITEM_NUM]--;//アイテムを一個減らす

        //アイテムがなくなっていないので使い切ってない状態にする
        if (toolArray[usedItemPosition][INDEX_ITEM_NUM] != 0) {
            LastItemUseUpDate.setIsLastItemUseUp(false);
            return;
        }

        siftTools(usedItemPosition);
        //Toolを使い切った状態にする
        LastItemUseUpDate.setIsLastItemUseUp(true);
    }

    /**
     * バッグのToolをシフト
     */
    private void siftTools(int usedItemPosition) {
        for (int i = usedItemPosition; i < arrayLength - 1; i++) {
            //todo bagItemクラスを作成する
            toolArray[i][INDEX_ITEM_NUM] = toolArray[i + 1][INDEX_ITEM_NUM];
            toolArray[i][INDEX_ITEM_ID] = toolArray[i + 1][INDEX_ITEM_ID];
            if (toolArray[i + 1][0] == 0) {
                break;
            }
        }

        //最後まで来たので一番下を空欄に
        toolArray[arrayLength - 1][INDEX_ITEM_NUM] = 0;
        toolArray[arrayLength - 1][INDEX_ITEM_ID] = 0;
    }

    public boolean isMany(int itemId) {
        for (int i = 0; i < new List_Tool().getItemNum(); i++) {
            if (toolArray[i][0] == itemId) {
                return MAX_ITEM_NUM <= toolArray[i][1];
            }

            if (toolArray[i][0] == 0) {
                return false;
            }
        }
        return false;
    }
}
