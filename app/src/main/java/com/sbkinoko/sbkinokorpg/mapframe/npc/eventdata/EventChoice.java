package com.sbkinoko.sbkinokorpg.mapframe.npc.eventdata;

import com.sbkinoko.sbkinokorpg.mapframe.event.EventIDList;

public class EventChoice extends EventData {
    private final ChoiceData[] choiceData;

    public EventChoice(int keyStep, int afterStep, String[] txt,
                       ChoiceData[] choiceData) {
        super(keyStep, afterStep, txt);
        this.choiceData = choiceData;
    }

    public ChoiceData[] getChoiceData() {
        return choiceData;
    }

    @Override
    public EventIDList getEventType() {
        return EventIDList.OPEN_CHOICE;
    }
}
