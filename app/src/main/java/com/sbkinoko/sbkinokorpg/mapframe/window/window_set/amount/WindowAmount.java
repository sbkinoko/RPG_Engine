package com.sbkinoko.sbkinokorpg.mapframe.window.window_set.amount;

import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sbkinoko.sbkinokorpg.MainGame;
import com.sbkinoko.sbkinokorpg.mapframe.window.MapGameWindow;
import com.sbkinoko.sbkinokorpg.mapframe.window.window_set.GroupOfWindows;

public abstract class WindowAmount extends MapGameWindow {
    private int frameWidth;
    private int frameHeight;
    protected int itemID;
    protected final int
            amount_10 = 1,
            amount_1 = 2;
    private final int digitNum = 2;
    private final int maxDigitPosition = amount_10;

    GroupOfWindows groupOfWindows;

    public WindowAmount(GroupOfWindows groupOfWindows) {
        super(groupOfWindows.getMapFrame().getFrameLayout().getContext(),
                groupOfWindows.getMapFrame(), "info");

        this.menuTV = new TextView[4];
        this.groupOfWindows = groupOfWindows;
        setMenuTvs();
        setSelectedTv(selectedTV);

        setDials();
    }

    private void setDials() {
        for (int i = 0; i < digitNum * 2; i++) {
            frameLayout.addView(getDial(i));
        }
    }

    public TextView getDial(int i) {
        TextView dial = new TextView(context);
        dial.setLayoutParams(new ViewGroup.LayoutParams(
                frameWidth / 6,
                frameHeight / 2
        ));
        if (i < 2) {
            dial.setX((float) (frameWidth / 6));
        } else {
            dial.setX((float) (frameWidth / 3));
        }

        if (i % 2 == 0) {
            dial.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() != MotionEvent.ACTION_DOWN) {
                        return true;
                    }
                    setSelectedTv((int) (i / 2) + maxDigitPosition);
                    changeCount(1);
                    return true;
                }
            });
            dial.setText(" ∧");
        } else {
            dial.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() != MotionEvent.ACTION_DOWN) {
                        return true;
                    }
                    setSelectedTv((int) (i / 2) + maxDigitPosition);
                    changeCount(-1);
                    return true;
                }
            });
            dial.setY(3 * frameHeight >> 1);
            dial.setText(" ∨");
        }

        return dial;
    }


    @Override
    public void setFramePosition() {
        frameWidth = MainGame.playWindowSize / 2;
        frameHeight = MainGame.playWindowSize / 10;
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(
                frameWidth,
                frameHeight * 2
        ));
        frameLayout.setY(MainGame.playWindowSize * 3 / 10);
    }

    @Override
    public void setMenuTv(int i) {
        if (i < 3) {
            menuTV[i].setX(frameWidth / 6 * i);
            menuTV[i].setLayoutParams(new ViewGroup.LayoutParams(
                            frameWidth / 6,
                            frameHeight
                    )
            );
        } else {
            menuTV[i].setX(frameWidth * 5 / 8);
            menuTV[i].setLayoutParams(new ViewGroup.LayoutParams(
                    frameWidth / 4,
                    frameHeight
            ));
        }
        menuTV[i].setY(frameHeight / 2);

        menuTV[i].setText(getText(i));
        menuTV[i].setGravity(Gravity.CENTER_HORIZONTAL);
    }

    private String getText(int i) {
        switch (i) {
            case 0:
                return "×";
            case amount_10:
                return "0";
            case amount_1:
                return "1";
            case 3:
                return "決定";
        }
        return "";
    }

    @Override
    protected void correctSelectedTV() {
        if (selectedTV <= 0) {
            setSelectedTv(menuTV.length - 1);
        } else if (menuTV.length <= selectedTV) {
            setSelectedTv(1);
        }
    }

    public abstract void openMenu(int itemID);

    protected void resetAmount() {
        menuTV[amount_10].setText(getText(amount_10));
        menuTV[amount_1].setText(getText(amount_1));
    }

    @Override
    public void useBtLeft() {
        selectedTV--;
        correctSelectedTV();
        setSelectedTv(selectedTV);
    }

    @Override
    public void useBtRight() {
        selectedTV++;
        correctSelectedTV();
        setSelectedTv(selectedTV);
    }

    @Override
    public void useBtUp() {
        switch (selectedTV) {
            case amount_10:
            case amount_1:
                changeCount(1);
                break;
        }
    }

    @Override
    public void useBtDown() {
        switch (selectedTV) {
            case amount_1:
            case amount_10:
                changeCount(-1);
                break;
        }
    }

    private void changeCount(int dir) {
        final int beforeAmount = getAmount();
        int carry = 0;
        int amount = Integer.parseInt((String) menuTV[selectedTV].getText());
        amount += dir;

        if (amount < 0) {
            amount = 9;
            carry = -1;
        } else if (9 < amount) {
            amount = 0;
            carry = 1;
        }

        String num = amount + "";
        menuTV[selectedTV].setText(num);
        if (carry != 0 && selectedTV == amount_1) {
            calCarry(carry);
        }

        correctAmount();
        if (beforeAmount == getAmount()) {
            resetAmount();
        }
    }

    private void calCarry(int carry) {
        int amount = Integer.parseInt((String) menuTV[amount_10].getText());
        int tmpNum = amount + carry;
        if (tmpNum < 0) {
            tmpNum = 9;
        } else if (9 < tmpNum) {
            tmpNum = 0;
        }
        String num = tmpNum + "";
        menuTV[amount_10].setText(num);
    }

    protected abstract void correctAmount();

    protected int getAmount() {
        return Integer.parseInt((String) menuTV[amount_10].getText()) * 10
                + Integer.parseInt((String) menuTV[amount_1].getText());
    }

    protected abstract int getTotalPrice();


    @Override
    public void useBtB() {
        closeMenu();
    }

    @Override
    public void useBtM() {
        useBtB();
    }

    @Override
    public void closeMenu() {
        if (isOpen) {
            super.closeMenu();
            groupOfWindows.getWindowDetail().setOpen();
        }
    }
}
