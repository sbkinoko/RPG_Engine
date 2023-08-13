package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.list_strategy.shopping;

import android.content.Context;
import android.content.res.Resources;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.R;
import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

public class WindowMoney extends androidx.appcompat.widget.AppCompatTextView {
    private final Player player;

    public WindowMoney(Player player, Context context) {
        super(context);
        this.player = player;
        Resources res = context.getResources();
        setBackground(ResourcesCompat.getDrawable(res, R.drawable.character_frame, null));
        setLayoutParams(new ViewGroup.LayoutParams(
                MainGame.playWindowSize / 2,
                MainGame.playWindowSize / 10
        ));
        setAutoSizeTextTypeUniformWithConfiguration(10, 200, 10, 0);
        setPadding(5, 5, 5, 5);
    }

    public void showMoney() {
        String txt = "所持金:" + player.getMoney();
        setText(txt);
    }
}
