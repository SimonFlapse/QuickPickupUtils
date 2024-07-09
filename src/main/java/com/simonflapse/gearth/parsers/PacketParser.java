package com.simonflapse.gearth.parsers;

import gearth.protocol.packethandler.shockwave.packets.ShockPacket;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class PacketParser {
    private final int expectedHeaderId;

    PacketParser(int expectedHeaderId, ShockPacket packet) {
        this.expectedHeaderId = expectedHeaderId;
        verifyHeader(packet);
    }

    void verifyHeader(ShockPacket packet) {
        if (packet.headerId() != expectedHeaderId) {
            throw new IllegalArgumentException("Invalid packet header id: " + packet.headerId() + ". Expected: " + expectedHeaderId);
        }
    }

    void warnAboutUnparsedData(ShockPacket packet) {
        if (packet.getReadIndex() == packet.length()) {
            return;
        }

        int additionalDataLength = packet.length() - packet.getReadIndex();
        byte[] additionalData = packet.readBytes(additionalDataLength);
        log.warn("packet contains < {} > bytes of additional data: < {} >", additionalDataLength, new String(additionalData, StandardCharsets.UTF_8));
    }
}
