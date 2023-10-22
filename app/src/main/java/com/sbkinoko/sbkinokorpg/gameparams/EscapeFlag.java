package com.sbkinoko.sbkinokorpg.gameparams;

public enum EscapeFlag {
    Can(true),
    CanNot(false),
    ;

    boolean escapeFlag;

    EscapeFlag(boolean escapeFlag) {
        this.escapeFlag = escapeFlag;
    }

    public boolean caeEscapeBattle() {
        return escapeFlag;
    }
}
