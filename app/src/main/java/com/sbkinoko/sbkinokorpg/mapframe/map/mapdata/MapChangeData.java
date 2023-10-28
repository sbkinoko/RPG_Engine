package com.sbkinoko.sbkinokorpg.mapframe.map.mapdata;

public class MapChangeData {
    public final int mapID, mapX, mapY;
    public final String mapName;

    public MapChangeData(int mapID, int mapX, int mapY) {
        this.mapID = mapID;
        this.mapX = mapX;
        this.mapY = mapY;
        this.mapName = "";
    }

    public MapChangeData(int mapID, int mapX, int mapY, String mapName) {
        this.mapID = mapID;
        this.mapX = mapX;
        this.mapY = mapY;
        this.mapName = mapName;
    }

    public String getName() {
        return mapName;
    }
}
