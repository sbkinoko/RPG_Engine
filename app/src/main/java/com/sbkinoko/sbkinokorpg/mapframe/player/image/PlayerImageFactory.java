package com.sbkinoko.sbkinokorpg.mapframe.player.image;

import com.sbkinoko.sbkinokorpg.gameparams.GameParams;

public class PlayerImageFactory {

    private final IPlayerImage playerImage = new PlayerImage1();

    public int getPlayerImageResourceId(
            int dir,
            int imageType) {

        switch (dir) {
            case GameParams.dir_right:
                switch (imageType) {
                    case 0:
                        return playerImage.getImageRight1();
                    case 1:
                        return playerImage.getImageRight2();
                }
                break;
            case GameParams.dir_down:
                switch (imageType) {
                    case 0:
                        return playerImage.getImageDown1();
                    case 1:
                        return playerImage.getImageDown2();
                }
                break;
            case GameParams.dir_left:
                switch (imageType) {
                    case 0:
                        return playerImage.getImageLeft1();
                    case 1:
                        return playerImage.getImageLeft2();
                }
                break;
            case GameParams.dir_up:
                switch (imageType) {
                    case 0:
                        return playerImage.getImageUp1();
                    case 1:
                        return playerImage.getImageUp2();
                }
                break;
        }
        throw new RuntimeException("dir{" + dir + "} imageType{" + imageType + "}が正しくありません");
    }
}
