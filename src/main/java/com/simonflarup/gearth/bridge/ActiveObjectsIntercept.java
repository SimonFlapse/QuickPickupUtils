package com.simonflarup.gearth.bridge;

import com.simonflarup.gearth.events.EventSystem;
import com.simonflarup.gearth.events.type.activeobject.*;
import com.simonflarup.gearth.parsers.OHActiveObject;
import gearth.protocol.packethandler.shockwave.packets.ShockPacketIncoming;

public class ActiveObjectsIntercept extends AbstractIntercept {
    public static void onActiveObjectsAdd(ShockPacketIncoming packet) {
        OHActiveObject activeObject = new OHActiveObject(packet);
        EventSystem.post((OnActiveObjectAddedEvent) () -> activeObject);
    }


    public static void onActiveObjectsUpdate(ShockPacketIncoming packet) {
        OHActiveObject activeObject = new OHActiveObject(packet);
        EventSystem.post((OnActiveObjectUpdatedEvent) () -> activeObject);
    }

    public static void onActiveObjectsRemove(ShockPacketIncoming packet) {
        OHActiveObject activeObject = new OHActiveObject(packet);
        EventSystem.post((OnActiveObjectRemovedEvent) () -> activeObject);
    }

    public static void onActiveObjects(ShockPacketIncoming packet) {
        OHActiveObject[] activeObjects = OHActiveObject.parse(packet);
        EventSystem.post((OnActiveObjectsLoadedEvent) () -> activeObjects);
    }

    public static void onStuffDataUpdate(ShockPacketIncoming packet) {
        int furniId = Integer.parseInt(packet.readString());
        String stuffData = packet.readString();
        EventSystem.post(new OnStuffDataUpdatedEvent() {
            @Override
            public int getTargetId() {
                return furniId;
            }

            @Override
            public String getNewData() {
                return stuffData;
            }
        });
    }
}
