package com.sbkinoko.sbkinokorpg.mapframe.map.mapdata;

import com.sbkinoko.sbkinokorpg.mapframe.map.appmonsterlist.AppMonster;
import com.sbkinoko.sbkinokorpg.mapframe.map.appmonsterlist.FixMons;
import com.sbkinoko.sbkinokorpg.mapframe.npc.NPCData;
import com.sbkinoko.sbkinokorpg.mapframe.npc.NPC_Move;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.ChoiceData;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventBattle;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventChoice;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventData;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventItemGet;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventJob;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventSell;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventShopping;
import com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata.EventTalk;
import com.sbkinoko.sbkinokorpg.mapframe.npc.movedata.MoveData;
import com.sbkinoko.sbkinokorpg.mapframe.npc.movedata.MoveDown;
import com.sbkinoko.sbkinokorpg.mapframe.npc.movedata.MoveLeft;
import com.sbkinoko.sbkinokorpg.mapframe.npc.movedata.MoveRight;
import com.sbkinoko.sbkinokorpg.mapframe.npc.movedata.MoveUp;

public class Town extends MapData {

    @Override
    public MapId getMapId() {
        return MapId.Town;
    }

    public Town() {
        map = new int[][][]{
                {{05, 00}, {05, 00}, {05, 00}, {05, 00}, {05, 00}, {05, 00}},
                {{05, 00}, {00, 00}, {07, 00}, {07, 00}, {00, 00}, {05, 00}},
                {{21, 00}, {07, 00}, {07, 00}, {07, 00}, {00, 00}, {05, 00}},
                {{05, 00}, {07, 00}, {07, 00}, {07, 00}, {00, 00}, {00, 00}},
                {{05, 00}, {00, 00}, {07, 00}, {07, 00}, {00, 00}, {05, 00}},
                {{05, 00}, {05, 00}, {05, 00}, {05, 00}, {05, 00}, {05, 00}}
        };

        treasureBoxes = new int[]{0};

        npcData = new NPCData[]{
                new NPCData(2.0, 1.0, 1.0, 1,
                        new NPC_Move(new MoveData[]{
                                new MoveDown(30),
                                new MoveRight(30),
                                new MoveUp(30),
                                new MoveLeft(30),
                        }),
                        new EventData[]{
                                new EventTalk(0, 0, new String[]{"ようこそ"}, false),
                                new EventTalk(2, 2, new String[]{"鍵をもらったのですね"}, false),
                        }),
                new NPCData(1.0, 2.0, 1.0, 1,
                        new EventData[]{
                                new EventItemGet(1, 2,
                                        new String[]{"町の外に行くのですね", "こちらをどうぞ"}, 8, 1, true),
                                new EventTalk(2, 5, new String[]{"行ってらっしゃい"}, false),
                                new EventTalk(4, 4, new String[]{"おかえりなさい"}, false)
                        }),
                new NPCData(1.0, 3.0, 1.0, 2,
                        new EventData[]{
                                new EventChoice(1, 1, new String[]{"gao1"},
                                        new ChoiceData[]{
                                                new ChoiceData(2, "たたかう"),
                                                new ChoiceData(6, "たたかわない"),
                                        }),
                                new EventTalk(2, 3, new String[]{"gao2"}, true),
                                new EventBattle(3,  1),
                                new EventTalk(4, 1, new String[]{"gao!(負けたが戦えてうれしいようだ)"}, false),
                                new EventTalk(5, 1, new String[]{"gao!(勝ててうれしいようだ)"}, false),
                                new EventTalk(6, 1, new String[]{"gao……"}, false)
                        }
                ),
                new NPCData(3.0, 4.0, 1.0, 2,
                        new EventData[]{
                                new EventChoice(1, 1, new String[]{"わたしは商人"},
                                        new ChoiceData[]{
                                                new ChoiceData(2, "買う"),
                                                new ChoiceData(3, "売る"),
                                                new ChoiceData(4, "何もしない"),
                                        }),
                                new EventTalk(2, 5, new String[]{"かってくんだね"}, true),
                                new EventTalk(3, 10, new String[]{"売ってくんだね"}, true),
                                new EventTalk(4, 1, new String[]{"さようならー"}, false),
                                new EventShopping(5, 7, new String[]{"まいどあり"},
                                        new int[]{1, 2, 3, 4, 5, 1002}),
                                new EventTalk(7, 8, new String[]{"他に用はあるかい?"}, true),
                                new EventChoice(8, 1, new String[]{"他に用はあるかい?"},
                                        new ChoiceData[]{
                                                new ChoiceData(2, "買う"),
                                                new ChoiceData(3, "売る"),
                                                new ChoiceData(4, "もう十分"),
                                        }),
                                new EventSell(10, 7, new String[]{"まいどあり"}),
                        }),
                new NPCData(4.0, 3.0, 1.0, 2,
                        new EventData[]{
                                new EventChoice(1, 1, new String[]{"転職しますか?"},
                                        new ChoiceData[]{
                                                new ChoiceData(3, "はい"),
                                                new ChoiceData(2, "いいえ"),
                                        }),
                                new EventTalk(2, 1, new String[]{"さようなら"}, false),
                                new EventTalk(3, 4, new String[]{"転職ですね"}, true),
                                new EventJob(4, 1),
                                new EventTalk(5, 1, new String[]{"お疲れさまでした"}, false),
                        })
        };

        appGroundMonster = new AppMonster[]{
                new FixMons(new int[]{0, 2, 0})
        };
    }
}





