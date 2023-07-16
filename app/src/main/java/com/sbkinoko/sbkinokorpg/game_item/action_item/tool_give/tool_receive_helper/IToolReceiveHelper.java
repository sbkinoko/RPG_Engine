package com.sbkinoko.sbkinokorpg.game_item.action_item.tool_give.tool_receive_helper;

public interface IToolReceiveHelper {
    boolean canReceiveTool();

    void receive(int itemId);

    String getReceiveText();
}
