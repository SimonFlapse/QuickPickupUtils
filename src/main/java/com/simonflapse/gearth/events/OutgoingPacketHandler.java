package com.simonflapse.gearth.events;

import gearth.protocol.packethandler.shockwave.packets.ShockPacketOutgoing;

public interface OutgoingPacketHandler {
    void handlePacket(ShockPacketOutgoing packet);
}
