package com.sbkinoko.sbkinokorpg.dataList.player_status;

public class Status_1 extends List_JobStatus {

    public Status_1() {
        name = "戦士";

        STATUS_LIST = new int[][][]{
                {{0}, {1000, 100, 8, 100, 10, 10},
                        {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,}},
                {{10}, {2000, 100, 10, 100, 10, 10}, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                        11, 12, 13, 14,}},
                {{20}, {3000, 100, 12, 100, 10, 10}, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                        11, 12, 13, 14, 15, 16,}},
                {{40}, {4000, 100, 14, 100, 10, 10}, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                        11, 12, 13, 14, 15, 16, 17, 18,}},
                {{80}, {5000, 100, 8, 100, 10, 10}, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                        11, 12, 13, 14, 15, 16, 17, 18, 19, 20,}},
                {{120}, {6000, 100, 16, 100, 10, 10}, {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                        11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22}},
        };
    }
}
