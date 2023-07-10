package com.sbkinoko.sbkinokorpg.game_item.action_item.tool_give_helper;

import com.sbkinoko.sbkinokorpg.game_item.action_item.tool.Tool;
import com.sbkinoko.sbkinokorpg.mapframe.Player;

public class ToolGiveHelperBag implements IToolGiveHelper {
    private final Player player;

    public ToolGiveHelperBag(Player player){
        this.player = player;
    }

    @Override
    public int getToolId(int selectedItemPosition) {
        return player.getHaveItem()[selectedItemPosition][0];
    }

    @Override
    public void decreaseTool(int selectedItemPosition) {
        Tool.decreaseBagTool(player.getHaveItem(), selectedItemPosition);
    }
}
