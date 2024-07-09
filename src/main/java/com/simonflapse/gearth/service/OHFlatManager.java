package com.simonflapse.gearth.service;


import com.simonflapse.gearth.parsers.OHActiveObject;
import com.simonflapse.gearth.parsers.OHFlatInfo;
import com.simonflapse.gearth.parsers.OHItem;

import java.util.Map;

public interface OHFlatManager {
    OHFlatInfo getCurrentFlatInfo();
    Map<Integer, OHActiveObject> getActiveObjectsInFlat();
    Map<Integer, OHItem> getItemsInFlat();
}
