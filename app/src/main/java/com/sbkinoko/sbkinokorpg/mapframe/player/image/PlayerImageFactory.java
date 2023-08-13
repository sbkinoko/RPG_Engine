package com.sbkinoko.sbkinokorpg.mapframe.player.image;

import com.sbkinoko.sbkinokorpg.gameparams.Dir;

public class PlayerImageFactory {

    private final IPlayerImage playerImage = new PlayerImage1();

    public int getPlayerImageResourceId(
            Dir dir,
            int imageType) {

        switch (dir) {
            case Right:
                switch (imageType) {
                    case 0:
                        return playerImage.getImageRight1();
                    case 1:
                        return playerImage.getImageRight2();
                }
                break;
            case Down:
                switch (imageType) {
                    case 0:
                        return playerImage.getImageDown1();
                    case 1:
                        return playerImage.getImageDown2();
                }
                break;
            case Left:
                switch (imageType) {
                    case 0:
                        return playerImage.getImageLeft1();
                    case 1:
                        return playerImage.getImageLeft2();
                }
                break;
            case Up:
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
