package com.sbkinoko.sbkinokorpg.game_item.action_item.tool_give.tool_receive_helper;

import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;

public class ToolReceiveHelperPlayer implements IToolReceiveHelper {
    private final PlayerStatus toPlayer;

    public ToolReceiveHelperPlayer(PlayerStatus toPlayer) {
        this.toPlayer = toPlayer;
    }


    @Override
    public boolean canReceiveTool() {
        return toPlayer.canReceiveTool();
    }

    @Override
    public void receive(int itemId) {
        toPlayer.addHaveItem(itemId);
    }

    @Override
    public String getReceiveText() {
        return toPlayer.getName() + "に渡した";
    }
}