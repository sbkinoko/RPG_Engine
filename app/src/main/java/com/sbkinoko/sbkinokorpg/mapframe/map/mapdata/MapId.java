package com.sbkinoko.sbkinokorpg.mapframe.map.mapdata;

public enum MapId {
    Test(false){
        @Override
        public MapData getMapData() {
            return new TestField();
        }
    },
    Town(true){
        @Override
        public MapData getMapData() {
            return new Town();
        }
    };

    boolean canBeLastTown;

    public abstract MapData getMapData();

    MapId(boolean canBeLastTown) {
        this.canBeLastTown = canBeLastTown;
    }

    public boolean isCanBeLastTown() {
        return canBeLastTown;
    }

    public static MapId convertIntToMapId(int mapId_Int){
        for(MapId mapId:MapId.values()){
            if(mapId.ordinal() == mapId_Int){
                return mapId;
            }
        }
        throw new RuntimeException("mapIdが存在しません");
    }
}
