package com.simonflapse.gearth.events.type.activeobject;

import com.simonflapse.gearth.parsers.OHActiveObject;

public interface OnActiveObjectsLoadedEvent {
    OHActiveObject[] getActiveObjects();
}
