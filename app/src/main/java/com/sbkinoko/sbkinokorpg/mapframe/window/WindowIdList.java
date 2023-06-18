package com.sbkinoko.sbkinokorpg.mapframe.window;

public class WindowIdList {

    final public static int
            NUM_MapMenu_TO = 100;
    final public static int//ここからメインメニュー
            NUM_MapMenu_ITEM_SEE = 0;
    final public static int NUM_MapMenu_EQP_LIST = NUM_MapMenu_ITEM_SEE + 1;
    final public static int NUM_MapMenu_SKILL_SEE = NUM_MapMenu_EQP_LIST + 1;
    final public static int NUM_MapMenu_BOOK = NUM_MapMenu_SKILL_SEE + 1;
    final public static int NUM_MapMenu_STATUS = NUM_MapMenu_BOOK + 1;
    final public static int NUM_MapMenu_SAVE = NUM_MapMenu_STATUS + 1;
    final public static int NUM_MapMenu_OPTION = NUM_MapMenu_SAVE + 1;
    final public static int NUM_MapMenu_BACK = NUM_MapMenu_OPTION + 1;
    final public static int NUM_MapMenu_SKILL_USE = NUM_MapMenu_SKILL_SEE + NUM_MapMenu_TO;
    final public static int EQP_LIST_TO = NUM_MapMenu_EQP_LIST + NUM_MapMenu_TO;
    final public static int NUM_MapMenu_ITEM_GIVE = NUM_MapMenu_ITEM_SEE + 2 * NUM_MapMenu_TO;
    final public static int NUM_MapMenu_ITEM_USE = NUM_MapMenu_ITEM_SEE + NUM_MapMenu_TO;
    final public static int//ここまでメインメニュー
            NUM_MapMenu_BUY = 30;
    final public static int EQP_FROM_STATUS = 31;
    final public static int EQP_FROM_EQP_LIST = 32;
    final public static int NUM_MapMenu_WARP = 40;
    final public static int SELL_TOOL = 41;
    final public static int SELL_EQP = 42;
    final public static int JOB = 43;

    public static boolean isNeedTarget(int windowType) {
        switch (windowType) {
            case NUM_MapMenu_ITEM_USE:
            case NUM_MapMenu_SKILL_USE:
                return true;
        }
        return false;
    }

    public static boolean isToolWindow(int windowType) {
        return windowType == NUM_MapMenu_ITEM_SEE
                || windowType == NUM_MapMenu_ITEM_GIVE
                || windowType == NUM_MapMenu_ITEM_USE;
    }

    public static boolean isSkillWindow(int windowType) {
        return windowType == NUM_MapMenu_SKILL_SEE
                || windowType == NUM_MapMenu_SKILL_USE
                || windowType == NUM_MapMenu_WARP;
    }

    public static boolean isWindowTypeFrom(int windowType) {
        return windowType == NUM_MapMenu_SKILL_SEE
                || windowType == NUM_MapMenu_ITEM_SEE
                || windowType == NUM_MapMenu_STATUS
                || windowType == NUM_MapMenu_EQP_LIST;
    }

    public static boolean isShoppingWindow(int windowType) {
        return windowType == WindowIdList.NUM_MapMenu_BUY ||
                windowType == SELL_TOOL ||
                windowType == SELL_EQP;
    }

    public static boolean isWindowTypeWarp(int windowType) {
        return windowType == NUM_MapMenu_WARP;
    }

    public static boolean canSeeBag(int windowType) {
        return windowType == NUM_MapMenu_ITEM_SEE ||
                windowType == NUM_MapMenu_ITEM_GIVE ||
                windowType == NUM_MapMenu_EQP_LIST;
    }
}
