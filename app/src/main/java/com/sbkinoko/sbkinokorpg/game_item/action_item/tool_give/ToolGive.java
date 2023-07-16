package com.sbkinoko.sbkinokorpg.game_item.action_item.tool_give;

import com.sbkinoko.sbkinokorpg.GameParams;
import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.game_item.action_item.tool.LastItemUseUpDate;
import com.sbkinoko.sbkinokorpg.game_item.action_item.tool_give.tool_give_helper.GiveHelperFactory;
import com.sbkinoko.sbkinokorpg.game_item.action_item.tool_give.tool_give_helper.IToolGiveHelper;
import com.sbkinoko.sbkinokorpg.game_item.action_item.tool_give.tool_receive_helper.IToolReceiveHelper;
import com.sbkinoko.sbkinokorpg.game_item.action_item.tool_give.tool_receive_helper.ReceiveHelperFactory;
import com.sbkinoko.sbkinokorpg.mapframe.Player;
import com.sbkinoko.sbkinokorpg.mapframe.UseUpInfo;

public class ToolGive {

    public static UseUpInfo giveProcess(int from_player,
                                        int to_player,
                                        int usedItemPosition,
                                        Player player,
                                        PlayerStatus[] status) {
        if (from_player == to_player) {
            return new UseUpInfo("移動しないよ", false);
        }

        IToolGiveHelper toolGiveHelper = GiveHelperFactory.createGiveHelper(
                getPlayerStatus(from_player, status),
                player);
        IToolReceiveHelper toolReceiveHelper = ReceiveHelperFactory.createReceiveHelper(
                getPlayerStatus(to_player, status),
                player);

        if (!toolReceiveHelper.canReceiveTool()) {
            return new UseUpInfo("アイテムがいっぱいです", false);
        }

        int itemID = toolGiveHelper.getToolId(usedItemPosition);
        toolReceiveHelper.receive(itemID);

        toolGiveHelper.decreaseTool(usedItemPosition);

        return new UseUpInfo(
                toolReceiveHelper.getReceiveText(),
                LastItemUseUpDate.isLastItemUseUp());
    }

    static private PlayerStatus getPlayerStatus(int playerId,
                                                PlayerStatus[] playerStatus) {
        if (playerId < GameParams.PLAYER_NUM) {
            return playerStatus[playerId];
        } else {
            return null;
        }
    }
}
