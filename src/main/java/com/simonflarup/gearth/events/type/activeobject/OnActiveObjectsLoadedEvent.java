package com.simonflarup.gearth.events.type.activeobject;

import com.simonflarup.gearth.parsers.OHActiveObject;

public interface OnActiveObjectsLoadedEvent {
    OHActiveObject[] getActiveObjects();
}
