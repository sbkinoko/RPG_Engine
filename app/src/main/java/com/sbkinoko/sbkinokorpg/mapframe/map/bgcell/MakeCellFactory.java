package com.sbkinoko.sbkinokorpg.mapframe.map.bgcell;

import static com.sbkinoko.sbkinokorpg.mapframe.map.bgcell.CellIdList.Cell24;

import android.content.Context;

import com.sbkinoko.sbkinokorpg.mapframe.player.Player;

//todo 一個上のパッケージ
public class MakeCellFactory {
    static public MakeCell make(int cellType, Context context, Player player) {
        MakeCell makeCell;
        switch (cellType) {
            case 98:
                makeCell = new MakeCell_Type_TreasureBox();
                break;
            case 99:
                makeCell = new MakeCell_Type_SymbolMons();
                break;
            case 50:
                makeCell = new MakeCell_Cloud();
                break;
            case 21:
            case 20:
            case 22:
            case 23:
                makeCell = new MakeCell_Town();
                break;
            case Cell24:
                makeCell = new Cell24_Town1_out();
                break;
            case 30:
                makeCell = new MakeCell_LeftBridge();
                break;
            case 31:
                makeCell = new MakeCell_Tunnel();
                break;
            case 40:
                makeCell = new Cell40_switch();
                break;
            case 41:
                makeCell = new Cell41_RotateWall();
                break;
            case 5:
                makeCell = new MakeCell_Type_Fence();
                break;
            case 2:
            case 3:
                makeCell = new MakeCell_Type_Water();
                break;
            case 10:
                makeCell = new MakeCell_Type_10();
                break;
            case 11:
                makeCell = new MakeCell_Type_11();
                break;
            case 12:
                makeCell = new Cell12_Bridge();
                break;
            case 0:
                makeCell = new MakeCell_HighMons();
                break;
            default:
                makeCell = new MakeCell_Normal();
                break;
        }
        makeCell.setCellType(cellType, context);
        makeCell.setPlayer(player);
        return makeCell;
    }
}
