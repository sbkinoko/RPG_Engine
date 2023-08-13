package com.sbkinoko.sbkinokorpg.game_item.action_item.tool_give.tool_receive_helper;

import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public class ToolReceiveHelperBag implements IToolReceiveHelper {
    private final Player player;

    public ToolReceiveHelperBag(Player player) {
        this.player = player;
    }

    @Override
    public boolean canReceiveTool() {
        return true;
    }

    @Override
    public void receive(int itemId) {
        player.addItem(itemId, 1);
    }

    @Override
    public String getReceiveText() {
        return "袋に入れた";
    }
}
