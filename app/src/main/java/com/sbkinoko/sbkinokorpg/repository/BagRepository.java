package com.sbkinoko.sbkinokorpg.repository;

import com.sbkinoko.sbkinokorpg.dataList.item.List_Tool;

public class BagRepository {
    private static BagRepository bagRepository;

    private final int[][] toolArray;

    private BagRepository() {
        toolArray = new int[new List_Tool().getItemNum()][2];
    }

    private final int INDEX_ITEM_ID = 0;
    private final int INDEX_ITEM_NUM = 1;

    public static void setBagRepository() {
        bagRepository = new BagRepository();
    }

    public static BagRepository getBagRepository() {
        return bagRepository;
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
}
