package com.simonflapse.gearth.bridge;

import com.simonflapse.gearth.events.EventSystem;
import com.simonflapse.gearth.events.type.flat.OnFlatInfoEvent;
import com.simonflapse.gearth.parsers.OHFlatInfo;
import gearth.protocol.packethandler.shockwave.packets.ShockPacketIncoming;

public class FlatIntercept extends AbstractIntercept {
    public static void onFlatInfo(ShockPacketIncoming packet) {
        OHFlatInfo flatInfo = new OHFlatInfo(packet);
        EventSystem.post((OnFlatInfoEvent) () -> flatInfo);
    }
}
