package com.simonflarup.gearth.util;

import gearth.protocol.HMessage;
import gearth.protocol.HPacket;
import gearth.protocol.packethandler.shockwave.packets.ShockPacketIncoming;
import gearth.protocol.packethandler.shockwave.packets.ShockPacketOutgoing;

public final class ShockPacketUtils {
    private ShockPacketUtils() {}

    public static ShockPacketIncoming getShockPacketIncomingFromMessage(HMessage hMessage) {
        HPacket hPacket = hMessage.getPacket();
        if (!(hPacket instanceof ShockPacketIncoming)) {
            return null;
        }
        return (ShockPacketIncoming) hPacket;
    }

    public static ShockPacketOutgoing getShockPacketOutgoingFromMessage(HMessage hMessage) {
        HPacket hPacket = hMessage.getPacket();
        if (!(hPacket instanceof ShockPacketOutgoing)) {
            return null;
        }
        return (ShockPacketOutgoing) hPacket;
    }
}
