package com.sbkinoko.sbkinokorpg.mapframe.player;

import static com.sbkinoko.sbkinokorpg.gameparams.GameParams.X_axis;
import static com.sbkinoko.sbkinokorpg.gameparams.GameParams.Y_axis;

import android.content.Context;
import android.content.res.Resources;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.sbkinoko.sbkinokorpg.OptionConst;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.gameparams.Dir;
import com.sbkinoko.sbkinokorpg.gameparams.GameParams;
import com.sbkinoko.sbkinokorpg.mapframe.player.image.PlayerImageFactory;

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
            Dir dir,
            int imageType
    ) {
        imageView.setImageResource(
                playerImageFactory.getPlayerImageResourceId(
                        dir,
                        imageType
                )
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


    private final PlayerImageFactory playerImageFactory = new PlayerImageFactory();

    PlayerView(Context context,
               int playerSize,
               Player.PlayerImageTouchListener playerImageTouchListener) {
        imageView = new ImageView(context);
        int firstImage =
                playerImageFactory.getPlayerImageResourceId(Dir.Down, 0);
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

    void setMoveStateImage(int moveState) {
        getImageView().setBackground(
                ResourcesCompat.getDrawable(
                        res,
                        getImageForMoveState(moveState),
                        null
                )
        );
    }

    private int getImageForMoveState(int moveState) {
        switch (moveState) {
            case GameParams.MoveState_Ground:
                return R.drawable.character_frame_1;
            case GameParams.MoveState_Water:
                return R.drawable.character_frame_2;
            case GameParams.MoveState_Sky:
                return R.drawable.character_frame_3;
        }
        throw new RuntimeException("moveStateが不適{" + moveState + "}");
    }

}
