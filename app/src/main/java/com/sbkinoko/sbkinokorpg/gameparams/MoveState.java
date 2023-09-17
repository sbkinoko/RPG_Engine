package com.sbkinoko.sbkinokorpg.gameparams;

public enum MoveState {

    MoveState_Water(MoveStateInt.Water),
    MoveState_Ground(MoveStateInt.Ground),
    MoveState_Sky(MoveStateInt.Sky);


    private final int moveStateInt;

    MoveState(int moveStateInt) {
        this.moveStateInt = moveStateInt;
    }

    public int getMoveStateInt() {
        return moveStateInt;
    }

    public static MoveState convertIntToMoveState(int moveState) {
        switch (moveState) {
            case MoveStateInt.Water:
                return MoveState_Water;
            case MoveStateInt.Ground:
                return MoveState_Ground;
            case MoveStateInt.Sky:
                return MoveState_Sky;
            default:
                throw new RuntimeException();
        }
    }
}
