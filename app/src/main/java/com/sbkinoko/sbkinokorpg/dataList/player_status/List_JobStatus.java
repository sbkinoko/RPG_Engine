package com.sbkinoko.sbkinokorpg.dataList.player_status;

public abstract class List_JobStatus {

    public static final int JOB_NUM = 5;

    public static JobStatus getStatusList(int i) {
        switch (i) {
            case 1:
                return new Status_1();
            case 2:
                return new Status_2();
            case 3:
                return new Status_3();
            case 4:
                return new Status_4();
            case 5:
                return new Status_5();
        }
        return new Status_1();
    }
}

