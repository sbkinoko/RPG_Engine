package com.sbkinoko.sbkinokorpg.gameparams;

public enum BattleResult {
    Win{
        @Override
        public BattleResult not() {
            return Lose;
        }
    },
    Lose{
        @Override
        public BattleResult not() {
            return  Win;
        }
    },
    Escape{
        @Override
        public BattleResult not() {
            throw new RuntimeException();
        }
    };

    public abstract BattleResult not();
}
