package com.sbkinoko.sbkinokorpg.mapframe.map.bgcell;

import java.util.Random;

public class MonsAppRnd {
    static Random rnd = new Random();

    public static int getMidMonsApp() {
        return rnd.nextInt(110);
    }

    public static int getHighMonsApp() {
        return rnd.nextInt(200);
    }
}
