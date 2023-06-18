package com.sbkinoko.sbkinokorpg.mapframe;

public class UseUpInfo {
    String txt;
    boolean useUp;

    public UseUpInfo(String txt, boolean useUp) {
        this.txt = txt;
        this.useUp = useUp;
    }

    public String getTxt() {
        return txt;
    }

    public boolean isUseUp() {
        return useUp;
    }
}
