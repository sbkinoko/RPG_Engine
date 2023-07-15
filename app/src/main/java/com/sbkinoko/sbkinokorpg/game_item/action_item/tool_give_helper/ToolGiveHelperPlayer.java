package com.sbkinoko.sbkinokorpg.game_item.action_item.tool_give_helper;

import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.game_item.action_item.tool.Tool;
import com.sbkinoko.sbkinokorpg.game_item.action_item.tool_give_helper.IToolGiveHelper;

public class ToolGiveHelperPlayer implements IToolGiveHelper {
    private final  PlayerStatus fromPlayer;

    public ToolGiveHelperPlayer(PlayerStatus fromPlayer) {
        this.fromPlayer = fromPlayer;
    }

    @Override
    public int getToolId(int selectedItemPosition) {
        return fromPlayer.getHaveTool()[selectedItemPosition];
    }

    @Override
    public void decreaseTool(int selectedItemPosition) {
        Tool.decreasePlayerTool(fromPlayer.getHaveTool(), selectedItemPosition);
    }
}