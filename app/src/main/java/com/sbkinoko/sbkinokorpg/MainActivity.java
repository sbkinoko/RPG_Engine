package com.sbkinoko.sbkinokorpg;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    public static final String START_TYPE = "START_TYPE";
    public static final int
            start_autoSave = 0,
            start_Save = 1,
            start_reset = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpdateTime();
    }

    private void setUpdateTime() {
        Button button;
        Cursor cursor;
        long savedTime;
        String btText = "";
        MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(this);
        SQLiteDatabase db = myDataBaseHelper.getReadableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        button = findViewById(R.id.startButton);
        btText = getString(R.string.start_reset);

        cursor = db.query(
                MyDataBaseHelper.PLAYER_INFO_TABLE,
                new String[]{MyDataBaseHelper.UPDATE_TIME},
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        if (cursor.getCount() == 1) {
            savedTime = cursor.getLong(0);
            btText = getString(R.string.saveFile) + "\n" + sdf.format(new Date(savedTime));
        }
        cursor.close();
        button.setText(btText);


        button = findViewById(R.id.startButton2);
        btText = getString(R.string.start_reset);

        cursor = db.query(
                MyDataBaseHelper.AUTO_PLAYER_INFO_TABLE,
                new String[]{MyDataBaseHelper.UPDATE_TIME},
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        if (cursor.getCount() == 1) {
            savedTime = cursor.getLong(0);
            btText = getString(R.string.autoSaveFile) + "\n" + sdf.format(new Date(savedTime));
        }
        cursor.close();

        button.setText(btText);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setUpdateTime();
    }

    public void onStartButtonClick(View view) {
        intent = new Intent(MainActivity.this, MainGame.class);
        if (view.getId() == R.id.startButton) {
            intent.putExtra(START_TYPE, start_Save);
        } else if (view.getId() == R.id.startButton2) {
            intent.putExtra(START_TYPE, start_autoSave);
        } else if (view.getId() == R.id.startButton3) {
            intent.putExtra(START_TYPE, start_reset);
        }
        startActivity(intent);
    }
}
