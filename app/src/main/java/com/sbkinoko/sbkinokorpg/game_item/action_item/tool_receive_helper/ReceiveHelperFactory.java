package com.sbkinoko.sbkinokorpg.game_item.action_item.tool_receive_helper;

import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.mapframe.Player;

public class ReceiveHelperFactory {
    static public IToolReceiveHelper createReceiveHelper(PlayerStatus fromPlayer,
                                                         Player player) {
        if (fromPlayer == null) {
            return new ToolReceiveHelperBag(player);
        }

        return new ToolReceiveHelperPlayer(fromPlayer);
    }
}
