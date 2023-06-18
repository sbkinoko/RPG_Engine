package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy;

public class StrategyForTool_Give extends StrategyForTool_Menu {


    @Override
    public void tapDetailItem(int i) {
        groupOfWindows.getWindowPlayer().goToUseOrGive();
    }

    @Override
    public void use_Detail() {
        throw new RuntimeException("受け渡しでdetailを使うことはありあません");
    }

    @Override
    public void useBtB_Player() {
        groupOfWindows.getWindowPlayer().goToUseOrGive();
    }

    @Override
    public void useBtA_Player() {
        groupOfWindows.getWindowPlayer().giveProcess();
    }

    @Override
    public void openMenu_Player() {
        groupOfWindows.getWindowPlayer().openWithComment("渡す");
    }

}
