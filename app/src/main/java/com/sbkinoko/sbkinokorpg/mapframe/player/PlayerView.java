package com.sbkinoko.sbkinokorpg.mapframe.player;

import static com.sbkinoko.sbkinokorpg.GameParams.X_axis;
import static com.sbkinoko.sbkinokorpg.GameParams.Y_axis;

import android.content.Context;
import android.content.res.Resources;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.sbkinoko.sbkinokorpg.GameParams;
import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.R;

public class PlayerView {

    private final ImageView imageView;

    ImageView getImageView() {
        return imageView;
    }

    void setImageViewPosition(int[] position) {
        imageView.setX(position[X_axis]);
        imageView.setY(position[Y_axis]);
    }

    void setImageResourceId(
            int dir,
            int imageType
    ) {
        imageView.setImageResource(
                getPlayerImageResourceId(dir, imageType)
        );
    }

    private final TextView touchActionView;

    public TextView getTouchActionView() {
        return touchActionView;
    }

    void setCanAction(boolean canAction) {
        if (canAction) {
            this.touchActionView.setBackgroundResource(R.drawable.character_frame);
        } else {
            this.touchActionView.setBackgroundResource(0);
        }
    }

    void setActionViewPosition(int[] position) {
        touchActionView.setX(position[X_axis]);
        touchActionView.setY(position[Y_axis]);
    }

    private final Resources res;

    PlayerView(Context context,
               int playerSize,
               Player.PlayerImageTouchListener playerImageTouchListener,
               int firstImage) {
        imageView = new ImageView(context);
        imageView.setImageResource(firstImage);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                playerSize,
                playerSize
        ));

        imageView.setOnTouchListener(playerImageTouchListener);

        touchActionView = new TextView(context);
        touchActionView.setLayoutParams(new ViewGroup.LayoutParams(
                playerSize,
                playerSize
        ));
        touchActionView.setOnTouchListener(playerImageTouchListener);

        res = context.getResources();
    }

    void reDraw() {
        if (OptionConst.collisionDrawFlag) {
            imageView.setBackground(ResourcesCompat.getDrawable
                    (res, R.drawable.character_frame, null));
        } else {
            imageView.setBackground(null);
        }
    }

    private int getPlayerImageResourceId(
            int dir,
            int imageType) {
        int
                imageDown1 = R.drawable.player_0100,
                imageDown2 = R.drawable.player_0101,
                imageUp1 = R.drawable.player_0102,
                imageUp2 = R.drawable.player_0103,
                imageLeft1 = R.drawable.player_0104,
                imageLeft2 = R.drawable.player_0105,
                imageRight1 = R.drawable.player_0106,
                imageRight2 = R.drawable.player_0107;

        switch (dir) {
            case GameParams.dir_right:
                switch (imageType) {
                    case 0:
                        return imageRight1;
                    case 1:
                        return imageRight2;
                }
                break;
            case GameParams.dir_down:
                switch (imageType) {
                    case 0:
                        return imageDown1;
                    case 1:
                        return imageDown2;
                }
                break;
            case GameParams.dir_left:
                switch (imageType) {
                    case 0:
                        return imageLeft1;
                    case 1:
                        return imageLeft2;
                }
                break;
            case GameParams.dir_up:
                switch (imageType) {
                    case 0:
                        return imageUp1;
                    case 1:
                        return imageUp2;
                }
                break;
        }
        throw new RuntimeException("dir{" + dir + "} imageType{" + imageType + "}が正しくありません");
    }
}
