package com.simonflarup.gearth.pickup;

import gearth.protocol.packethandler.shockwave.packets.ShockPacket;

public interface SendToServer {
    boolean sendToServer(ShockPacket message);
}
