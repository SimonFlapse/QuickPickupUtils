package com.simonflarup.gearth.parsers;

import com.simonflarup.gearth.parsers.enums.room.OHObjectDirection;
import gearth.protocol.packethandler.shockwave.packets.ShockPacket;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Getter
public class OHActiveObject {
    private final int id;
    private final String className;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final OHObjectDirection direction;
    private final double z;
    private final String colors;
    private final String runtimeData;
    private final int extra;
    @Setter
    private String stuffData;

    public OHActiveObject(ShockPacket packet) {
        this.id = Integer.parseInt(packet.readString());
        this.className = packet.readString();
        this.x = packet.readInteger();
        this.y = packet.readInteger();
        this.width = packet.readInteger();
        this.height = packet.readInteger();
        this.direction = OHObjectDirection.fromValue(packet.readInteger());
        this.z = Double.parseDouble(packet.readString());
        this.colors = packet.readString();
        this.runtimeData = packet.readString();
        this.extra = packet.readInteger();
        this.stuffData = packet.readString();
        log.debug(this.toString());
    }

    public static OHActiveObject[] parse(ShockPacket packet) {
        int size = packet.readInteger();

        if(size == 0){
            size = 1;
        }

        OHActiveObject[] entities = new OHActiveObject[size];

        for(int i = 0; i < entities.length; ++i) {
            entities[i] = new OHActiveObject(packet);
        }

        return entities;
    }
}
