package com.sbkinoko.sbkinokorpg.mapframe.window.window_option;

class Back extends Option {
    private final Window_Option window_option;

    public Back(Window_Option window_option) {
        this.window_option = window_option;
    }

    @Override
    void useBtA() {
        window_option.useBtB();
    }

    @Override
    String getTxt() {
        return "戻る";
    }
}
