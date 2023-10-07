package com.sbkinoko.sbkinokorpg.mapframe.map.mapdata;

import com.sbkinoko.sbkinokorpg.mapframe.map.appmonsterlist.AppMonster;
import com.sbkinoko.sbkinokorpg.mapframe.map.appmonsterlist.FixMons;
import com.sbkinoko.sbkinokorpg.mapframe.map.appmonsterlist.RandomMons;
import com.sbkinoko.sbkinokorpg.mylibrary.ArrayToProb;

public class TestField extends MapData {

    @Override
    public MapId getMapId() {
        return MapId.Test;
    }

    public TestField() {
        map = new int[][][]{//マスの見た目とそれ以外の情報(立ち入り(見た目)、モンスターの生息)
                {{00, 01}, {00, 01}, {02, 01}, {01, 01}, {02, 01},/**/{02, 01}, {00, 01}, {00, 01}, {01, 01}, {00, 01}/**/},
                {{00, 01}, {00, 01}, {01, 01}, {01, 01}, {00, 01},/**/{03, 04}, {03, 04}, {03, 04}, {01, 01}, {00, 01}/**/},
                {{00, 01}, {00, 01}, {01, 01}, {99, 01}, {00, 01},/**/{03, 04}, {03, 04}, {03, 04}, {01, 01}, {00, 01}/**/},
                {{00, 01}, {00, 01}, {01, 01}, {00, 01}, {00, 01},/**/{03, 04}, {98, 00}, {03, 04}, {01, 01}, {00, 01}/**/},
                {{02, 01}, {00, 01}, {01, 01}, {98, 01}, {99, 01},/**/{03, 04}, {03, 04}, {03, 04}, {01, 01}, {02, 01}/**/},

                {{02, 01}, {00, 01}, {01, 01}, {00, 01}, {00, 01},/**/{00, 01}, {00, 01}, {01, 01}, {01, 01}, {20, 01}/**/},
                {{02, 01}, {00, 01}, {01, 01}, {00, 01}, {05, 01},/**/{00, 01}, {03, 01}, {01, 01}, {50, 51}, {50, 51}/**/},
                {{02, 01}, {00, 01}, {01, 01}, {01, 01}, {01, 01},/**/{01, 01}, {01, 01}, {01, 01}, {50, 51}, {50, 51}/**/},
                {{02, 01}, {40, 00}, {41, 00}, {06, 02}, {98, 02},/**/{06, 02}, {10, 02}, {01, 01}, {00, 01}, {00, 01}/**/},
                {{12, 01}, {01, 01}, {01, 01}, {06, 02}, {06, 02},/**/{06, 02}, {11, 02}, {01, 01}, {01, 01}, {01, 01}/**/},

                {{02, 01}, {00, 01}, {01, 01}, {01, 01}, {30, 01},/**/{31, 01}, {31, 00}, {01, 00}, {00, 00}, {00, 00}/**/},
                {{02, 01}, {02, 01}, {02, 01}, {01, 01}, {00, 01},/**/{00, 01}, {00, 00}, {01, 00}, {01, 00}, {00, 00}/**/},
        };
        treasureBoxes = new int[]{0, 3, 3};

        appGroundMonster = new AppMonster[]{
                new RandomMons(new ArrayToProb(new int[][]{{20, 1}, {20, 2}, {20, 3}, {20, 4}, {20, 5}}),
                        new ArrayToProb(new int[][]{{100, 1}})),
                new RandomMons(new ArrayToProb(new int[][]{{40, 2}, {60, 3}}),
                        new ArrayToProb(new int[][]{{100, 0}})),
                new FixMons(new int[]{0, 2, 0}),
                new RandomMons(new ArrayToProb(new int[][]{{50, 3}, {50, 4}}),
                        new ArrayToProb(new int[][]{{40, 3}, {40, 4},}))
        };

        appSkyMonster = new AppMonster[][]{
                {new RandomMons(new ArrayToProb(new int[][]{{50, 3}, {50, 4}}),
                        new ArrayToProb(new int[][]{{50, 2}, {50, 3},})),
                        appGroundMonster[0]}
        };
    }
}
