package com.sbkinoko.sbkinokorpg.mapframe.map.mapdata;

import com.sbkinoko.sbkinokorpg.gameparams.Axis;
import com.sbkinoko.sbkinokorpg.mapframe.MapPoint;
import com.sbkinoko.sbkinokorpg.mapframe.map.appmonsterlist.AppMonster;
import com.sbkinoko.sbkinokorpg.mapframe.npc.NPCData;

public abstract class MapData {

    public abstract MapId getMapId();

    public static final int MAP_NUM = MapId.values().length;
    public static final int SKY_MONS = 50;
    protected int[][][] map = new int[][][]{};

    public int getWidth() {
        return map[0].length;
    }

    public int getHeight() {
        return map.length;
    }

    public int getTreasureBoxId(MapPoint mapPoint) {
        return map[mapPoint.getY()][mapPoint.getX()][1];
    }

    public int[][][] getMap() {
        return map;
    }

    public int getCellType(int y, int x) {
        if (isOutOfMap(new int[]{y, x})) {
            return getOutSideCell();
        }
        return map[y][x][0];
    }

    public int getCellType(MapPoint mapPoint) {
        if (isOutOfMap(mapPoint)) {
            return getOutSideCell();
        }
        return map[mapPoint.getY()][mapPoint.getX()][0];
    }

    public int getMonsType(int y, int x) {
        return map[y][x][1];
    }

    protected int[] treasureBoxes = new int[]{};
    public static final int MAX_BOX_NUM = 10;
    public static final int eventFlagNum = 10;

    protected NPCData[] npcData;

    public NPCData[] getNpcData() {
        return npcData;
    }

    protected AppMonster[] appGroundMonster;

    public AppMonster getAppGroundMonster(int id) {
        return appGroundMonster[id];
    }

    public boolean canBeSkyMonster(int id) {
        return SKY_MONS <= id;
    }

    protected AppMonster[][] appSkyMonster;

    public AppMonster getAppSkyMonster(int id) {
        return appSkyMonster[id][0];
    }

    public AppMonster getAppGroundMonsterFromSky(int id) {
        return appSkyMonster[id][1];
    }

    public int[] getTreasureBoxes() {
        return treasureBoxes;
    }

    public static MapData getMapData(int mapId_int) {
        return MapId.convertIntToMapId(mapId_int).getMapData();
    }

    public boolean isOutOfMap(int[] mapPoint) {
        int y = mapPoint[Axis.Y.id];
        int x = mapPoint[Axis.X.id];

        return x < 0 || getWidth() <= x ||
                y < 0 || getHeight() <= y;
    }

    public boolean isOutOfMap(MapPoint mapPoint) {
        int y = mapPoint.getY();
        int x = mapPoint.getX();

        return x < 0 || getWidth() <= x ||
                y < 0 || getHeight() <= y;
    }

    public int getOutSideCell() {
        return 0;
    }

}
