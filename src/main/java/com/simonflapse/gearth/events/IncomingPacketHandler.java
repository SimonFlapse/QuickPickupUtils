package com.simonflapse.gearth.events;

import gearth.protocol.packethandler.shockwave.packets.ShockPacketIncoming;

public interface IncomingPacketHandler {
    void handlePacket(ShockPacketIncoming packet);
}
