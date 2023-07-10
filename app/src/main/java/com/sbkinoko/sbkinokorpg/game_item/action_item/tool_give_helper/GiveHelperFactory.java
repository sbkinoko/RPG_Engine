package com.sbkinoko.sbkinokorpg.game_item.action_item.tool_give_helper;

import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.mapframe.Player;

public class GiveHelperFactory {
    static public IToolGiveHelper createGiveHelper(PlayerStatus fromPlayer,
                                                   Player player) {
        if (fromPlayer == null) {
            return new ToolGiveHelperBag(player);
        }

        return new ToolGiveHelperPlayer(fromPlayer);
    }
}
