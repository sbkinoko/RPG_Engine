package com.sbkinoko.sbkinokorpg.mapframe.map.mapdata;

public class Town2 extends MapData {

    @Override
    public MapId getMapId() {
        return MapId.Town2;
    }

    public Town2() {
        map = new int[][][]{
                {{05, 00}, {05, 00}, {05, 00}, {05, 00}, {05, 00}, {05, 00}},
                {{05, 00}, {00, 00}, {07, 00}, {07, 00}, {00, 00}, {05, 00}},
                {{23, 00}, {07, 00}, {07, 00}, {07, 00}, {00, 00}, {05, 00}},
                {{05, 00}, {07, 00}, {07, 00}, {07, 00}, {00, 00}, {00, 00}},
                {{05, 00}, {00, 00}, {07, 00}, {07, 00}, {00, 00}, {05, 00}},
                {{05, 00}, {05, 00}, {05, 00}, {05, 00}, {05, 00}, {05, 00}}
        };

        treasureBoxes = new int[]{0};
    }
}





