package com.sbkinoko.sbkinokorpg.game_item.action_item.tool;

public class LastItemUseUpDate {

    static private boolean _isLastItemUseUp = true;

    static public boolean isLastItemUseUp() {
        return _isLastItemUseUp;
    }

    public static void setIsLastItemUseUp(boolean isLastItemUseUp) {
        _isLastItemUseUp = isLastItemUseUp;
    }
}
