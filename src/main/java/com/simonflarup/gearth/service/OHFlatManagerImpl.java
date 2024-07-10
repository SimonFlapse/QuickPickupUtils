package com.simonflarup.gearth.service;

import com.google.common.eventbus.Subscribe;
import com.simonflarup.gearth.events.EventSystem;
import com.simonflarup.gearth.events.type.activeobject.OnActiveObjectAddedEvent;
import com.simonflarup.gearth.events.type.activeobject.OnActiveObjectRemovedEvent;
import com.simonflarup.gearth.events.type.activeobject.OnActiveObjectsLoadedEvent;
import com.simonflarup.gearth.events.type.flat.OnFlatInfoEvent;
import com.simonflarup.gearth.events.type.item.OnItemAddedEvent;
import com.simonflarup.gearth.events.type.item.OnItemRemovedEvent;
import com.simonflarup.gearth.events.type.item.OnItemsLoadedEvent;
import com.simonflarup.gearth.parsers.OHActiveObject;
import com.simonflarup.gearth.parsers.OHFlatInfo;
import com.simonflarup.gearth.parsers.OHItem;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class OHFlatManagerImpl implements OHFlatManager {
    private static OHFlatManagerImpl INSTANCE;

    public ConcurrentMap<Integer, OHActiveObject> activeObjectsInFlat = new ConcurrentHashMap<>();
    public ConcurrentMap<Integer, OHItem> itemsInFlat = new ConcurrentHashMap<>();

    @Getter
    public OHFlatInfo currentFlatInfo;

    private OHFlatManagerImpl() {}

    public static OHFlatManagerImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OHFlatManagerImpl();
            EventSystem.registerPriority(INSTANCE);
        }
        return INSTANCE;
    }

    @Override
    public Map<Integer, OHActiveObject> getActiveObjectsInFlat() {
        return new ConcurrentHashMap<>(activeObjectsInFlat);
    }

    @Override
    public Map<Integer, OHItem> getItemsInFlat() {
        return new ConcurrentHashMap<>(itemsInFlat);
    }

    @Subscribe
    void onItemAddedEvent(OnItemAddedEvent event) {
        OHItem item = event.getItem();
        itemsInFlat.put(item.getId(), item);
    }


    @Subscribe
    void onItemRemovedEvent(OnItemRemovedEvent event) {
        itemsInFlat.remove(event.getItemId());
    }

    @Subscribe
    void onItemsLoadedEvent(OnItemsLoadedEvent event) {
        for (OHItem item : event.getItems()) {
            itemsInFlat.put(item.getId(), item);
        }
    }

    @Subscribe
    void onActiveObjectAddedEvent(OnActiveObjectAddedEvent event) {
        OHActiveObject activeObject = event.getActiveObject();

        activeObjectsInFlat.put(activeObject.getId(), activeObject);
    }

    @Subscribe
    void onActiveObjectRemoved(OnActiveObjectRemovedEvent event) {
        OHActiveObject activeObject = event.getActiveObject();
        activeObjectsInFlat.remove(activeObject.getId());
    }

    @Subscribe
    void onActiveObjectsLoaded(OnActiveObjectsLoadedEvent event) {
        for (OHActiveObject activeObject : event.getActiveObjects()) {
            activeObjectsInFlat.put(activeObject.getId(), activeObject);
        }
    }

    @Subscribe
    void updateFlatInfo(OnFlatInfoEvent event) {
        OHFlatInfo flatInfo = event.getFlatInfo();
        if (currentFlatInfo == null || currentFlatInfo.getFlatId() != flatInfo.getFlatId()) {
            activeObjectsInFlat.clear();
            itemsInFlat.clear();
        }
        currentFlatInfo = flatInfo;
    }
}
