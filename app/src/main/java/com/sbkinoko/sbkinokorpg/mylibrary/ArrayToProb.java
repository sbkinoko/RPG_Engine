package com.sbkinoko.sbkinokorpg.mylibrary;

import java.util.Random;

public class ArrayToProb {
    int[][] _data;
    int sum;

    public ArrayToProb(int[][] data) {
        _data = new int[data.length][2];
        sum = 0;
        for (int i = 0; i < _data.length; i++) {
            sum += data[i][0];
            _data[i][0] = sum;
            _data[i][1] = data[i][1];
        }
    }

    public int getDatum() {
        int rnd = new Random().nextInt(sum);
        for (int[] datum : _data) {
            if (rnd < datum[0]) {
                return datum[1];
            }
        }
        throw new RuntimeException();
    }


}
