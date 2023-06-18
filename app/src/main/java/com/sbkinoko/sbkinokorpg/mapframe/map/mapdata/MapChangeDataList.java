package com.sbkinoko.sbkinokorpg.mapframe.map.mapdata;

public class MapChangeDataList {
    public static class MapChangeData {
        int mapID, mapX, mapY;
        String mapName;

        MapChangeData(int mapID, int mapX, int mapY, String mapName) {
            this.mapID = mapID;
            this.mapX = mapX;
            this.mapY = mapY;
            this.mapName = mapName;
        }

        public int[] getDataForRoad() {
            return new int[]{
                    mapY, mapX, mapID
            };
        }

        public String getName() {
            return mapName;
        }
    }

    public static MapChangeData getMapChangeData(int id) {
        switch (id) {
            case 20:
                return new MapChangeData(1, 0, 2, "町の中");
            case 21:
                return new MapChangeData(0, 9, 5, "町の外");
            case 22:
                return new MapChangeData(0, 4, 5, "敵の前");
        }
        throw new RuntimeException();
    }
}
