package com.simonflapse.gearth.events.type.item;

import com.simonflapse.gearth.parsers.OHItem;

public interface OnItemsLoadedEvent {
    OHItem[] getItems();
}
