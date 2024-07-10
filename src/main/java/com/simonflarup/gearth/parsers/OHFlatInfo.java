package com.simonflarup.gearth.parsers;

import com.simonflarup.gearth.parsers.enums.navigator.OHFlatLock;
import com.simonflarup.gearth.parsers.enums.navigator.OHFlatModels;
import gearth.protocol.packethandler.shockwave.packets.ShockPacketIncoming;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString
@Slf4j
public class OHFlatInfo extends PacketParser {
    private final boolean othersCanMoveFurni;
    private final OHFlatLock flatLock;
    private final int flatId;
    private final String owner;
    private final OHFlatModels roomType;
    private final String roomName;
    private final String roomDescription;
    private final boolean showOwnerName;
    private final boolean trading;
    private final boolean alertForNoCategory;
    private final int maxVisitors;
    private final int absoluteMaxVisitors;
    //#nodeType seems to be missing from the packet but always defaults to 2

    public OHFlatInfo(ShockPacketIncoming packet) {
        super(54, packet);

        othersCanMoveFurni = packet.readBoolean();
        flatLock = OHFlatLock.fromValue(packet.readInteger());
        flatId = packet.readInteger();
        owner = packet.readString();

        roomType = OHFlatModels.valueOf(packet.readString().toUpperCase());
        roomName = packet.readString();
        roomDescription = packet.readString();
        showOwnerName = packet.readBoolean();
        trading = packet.readBoolean();
        alertForNoCategory = packet.readBoolean();
        maxVisitors = packet.readInteger();
        absoluteMaxVisitors = packet.readInteger();

        log.debug(this.toString());

        warnAboutUnparsedData(packet);
    }
}
