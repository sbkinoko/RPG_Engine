package com.sbkinoko.sbkinokorpg.game_item.action_item.tool_give.tool_give_helper;

import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public class ToolGiveHelperBag implements IToolGiveHelper {
    private final Player player;

    public ToolGiveHelperBag(Player player) {
        this.player = player;
    }

    @Override
    public int getToolId(int selectedItemPosition) {
        return player.getToolIdAt(selectedItemPosition);
    }

    @Override
    public void decreaseTool(int selectedItemPosition) {
        player.decItem(selectedItemPosition);
    }
}
