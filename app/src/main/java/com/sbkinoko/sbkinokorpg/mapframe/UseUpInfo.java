package com.sbkinoko.sbkinokorpg.mapframe;

public class UseUpInfo {
    private final String txt;
    private final boolean useUp;

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
