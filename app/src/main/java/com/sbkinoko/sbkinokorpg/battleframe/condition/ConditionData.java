package com.sbkinoko.sbkinokorpg.battleframe.condition;

public enum ConditionData {
    CON_POISON(ConditionConst.POISON) {
        @Override
        public String getName() {
            return "毒";
        }

        @Override
        public DefaultCondition getCondition(int restTurn) {
            return new Poison(restTurn);
        }
    },

    CON_PARALYZE(ConditionConst.PARALYZE) {
        @Override
        public String getName() {
            return "麻痺";
        }

        @Override
        public DefaultCondition getCondition(int restTurn) {
            return new Paralyze(restTurn);
        }
    };

    int id;

    ConditionData(int id) {
        this.id = id;
    }

    public abstract String getName();

    public abstract DefaultCondition getCondition(int restTurn);

    public int getId() {
        return id;
    }
}
