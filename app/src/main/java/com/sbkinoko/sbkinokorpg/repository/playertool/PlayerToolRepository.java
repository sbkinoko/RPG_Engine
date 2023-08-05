package com.sbkinoko.sbkinokorpg.repository.playertool;


public interface PlayerToolRepository {

    int[] getAllItem(int playerId);

    int getItem(int playerId, int itemPosition);

    void addItem(int playerId, int itemId);

    void decreasePlayerTool(int playerId, int usedItemPos);

    boolean canReceiveTool(int playerId);
}
