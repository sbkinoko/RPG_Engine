package com.sbkinoko.sbkinokorpg.dataList.player_status;

import com.sbkinoko.sbkinokorpg.battleframe.status.PlayerStatus;

public class Status_3 extends List_JobStatus {
    public Status_3() {
        name = "魔法使い3";

        STATUS_LIST = new int[][][]{
                {{0}, {10, 100, 8, 100, 10, 10},
                        {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,}},
                {{100}, {20, 100, 10, 100, 10, 10}, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                        11, 12, 13, 14,}},
                {{200}, {30, 100, 12, 100, 10, 10}, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                        11, 12, 13, 14, 15, 16,}},
                {{400}, {40, 100, 14, 100, 10, 10}, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                        11, 12, 13, 14, 15, 16, 17, 18,}},
                {{800}, {50, 100, 8, 100, 10, 10}, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                        11, 12, 13, 14, 15, 16, 17, 18, 19, 20,}},
                {{1000}, {60, 100, 16, 100, 10, 10}, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                        11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22}},
                {{1200}, {60, 100, 16, 100, 10, 10}, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                        11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22}},
                {{1400}, {60, 100, 16, 100, 10, 10}, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                        11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22}},
        };
    }

    @Override
    public boolean canChangeJob(PlayerStatus playerStatus) {
        return playerStatus.getPlayerID() == 1;
    }
}
