package com.sbkinoko.sbkinokorpg.game_item.action_item.tool_give_helper;

import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;

public class ToolGiveHelperPlayer implements IToolGiveHelper {
    private final PlayerStatus fromPlayer;

    public ToolGiveHelperPlayer(PlayerStatus fromPlayer) {
        this.fromPlayer = fromPlayer;
    }

    @Override
    public int getToolId(int selectedItemPosition) {
        return fromPlayer.getToolId(selectedItemPosition);
    }

    @Override
    public void decreaseTool(int selectedItemPosition) {
        fromPlayer.decreaseItem(selectedItemPosition);
    }
}