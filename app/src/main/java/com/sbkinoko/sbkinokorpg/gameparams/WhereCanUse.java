package com.sbkinoko.sbkinokorpg.gameparams;

public enum WhereCanUse {
    canInField {
        @Override
        public boolean canUseInField() {
            return true;
        }

        @Override
        public boolean canUseInBattle() {
            return false;
        }
    },
    canInBattle {
        @Override
        public boolean canUseInField() {
            return false;
        }

        @Override
        public boolean canUseInBattle() {
            return true;
        }
    },

    canInEither {
        @Override
        public boolean canUseInField() {
            return true;
        }

        @Override
        public boolean canUseInBattle() {
            return true;
        }
    },
    canNotInEither {
        @Override
        public boolean canUseInField() {
            return false;
        }

        @Override
        public boolean canUseInBattle() {
            return false;
        }
    };

    public abstract boolean canUseInField();

    public abstract boolean canUseInBattle();
}
