package com.sbkinoko.sbkinokorpg.game_item.action_item.tool_give_helper;

import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;
import com.sbkinoko.sbkinokorpg.repository.PlayerToolRepository;

public class ToolGiveHelperPlayer implements IToolGiveHelper {
    private final int fromPlayerId;
    private final PlayerToolRepository playerToolRepository =
            PlayerToolRepository.getPlayerToolRepository();

    public ToolGiveHelperPlayer(PlayerStatus fromPlayer) {
        fromPlayerId = fromPlayer.getPlayerID();
    }

    @Override
    public int getToolId(int selectedItemPosition) {
        return playerToolRepository.getItem(
                fromPlayerId,
                selectedItemPosition
        );
    }

    @Override
    public void decreaseTool(int selectedItemPosition) {
        PlayerToolRepository.getPlayerToolRepository().decreasePlayerTool(
                fromPlayerId,
                selectedItemPosition);
    }
}