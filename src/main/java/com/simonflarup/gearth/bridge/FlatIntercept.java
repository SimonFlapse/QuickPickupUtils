package com.simonflarup.gearth.bridge;

import com.simonflarup.gearth.events.EventSystem;
import com.simonflarup.gearth.events.type.flat.OnFlatInfoEvent;
import com.simonflarup.gearth.parsers.OHFlatInfo;
import gearth.protocol.packethandler.shockwave.packets.ShockPacketIncoming;

public class FlatIntercept extends AbstractIntercept {
    public static void onFlatInfo(ShockPacketIncoming packet) {
        OHFlatInfo flatInfo = new OHFlatInfo(packet);
        EventSystem.post((OnFlatInfoEvent) () -> flatInfo);
    }
}
