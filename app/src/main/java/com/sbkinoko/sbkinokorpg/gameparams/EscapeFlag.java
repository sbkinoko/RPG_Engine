package com.sbkinoko.sbkinokorpg.gameparams;

public enum EscapeFlag {
    Can(true),
    CanNot(false),
    ;

    //fixme　booleanを持たせるのをやめて比較にする
    //例)　escapeFlag == EscapeFlag.Win とか
    boolean escapeFlag;

    EscapeFlag(boolean escapeFlag) {
        this.escapeFlag = escapeFlag;
    }

    public boolean caeEscapeBattle() {
        return escapeFlag;
    }
}
