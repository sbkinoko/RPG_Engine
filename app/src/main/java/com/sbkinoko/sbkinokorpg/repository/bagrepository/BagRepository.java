package com.sbkinoko.sbkinokorpg.repository.bagrepository;

public interface BagRepository {

    int[][] getToolArray();

    int getToolIdAt(int itemPosition);

    int getToolNumAt(int itemPosition);

    void addTool(int itemId, int quantity);

    /**
     * まとめて弄る
     */
    void decreaseTool(int itemId, int quantity);

    void decreaseBagTool(int usedItemPosition);

    boolean isMany(int itemId);
}
