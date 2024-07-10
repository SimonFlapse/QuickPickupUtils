package com.simonflarup.gearth.events.type.item;

import com.simonflarup.gearth.parsers.OHItem;

public interface OnItemsLoadedEvent {
    OHItem[] getItems();
}
