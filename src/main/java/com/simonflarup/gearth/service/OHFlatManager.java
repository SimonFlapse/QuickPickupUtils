package com.simonflarup.gearth.service;


import com.simonflarup.gearth.parsers.OHActiveObject;
import com.simonflarup.gearth.parsers.OHFlatInfo;
import com.simonflarup.gearth.parsers.OHItem;

import java.util.Map;

public interface OHFlatManager {
    OHFlatInfo getCurrentFlatInfo();
    Map<Integer, OHActiveObject> getActiveObjectsInFlat();
    Map<Integer, OHItem> getItemsInFlat();
}
