package com.simonflapse.gearth.bridge;

import com.simonflapse.gearth.events.EventSystem;
import com.simonflapse.gearth.events.type.item.OnItemAddedEvent;
import com.simonflapse.gearth.events.type.item.OnItemRemovedEvent;
import com.simonflapse.gearth.events.type.item.OnItemUpdatedEvent;
import com.simonflapse.gearth.events.type.item.OnItemsLoadedEvent;
import com.simonflapse.gearth.parsers.OHItem;
import gearth.protocol.packethandler.shockwave.packets.ShockPacketIncoming;

import java.nio.charset.StandardCharsets;

public class ItemsIntercept extends AbstractIntercept {
    public static void onItemsAdd(ShockPacketIncoming packet) {
        OHItem item = new OHItem(packet);
        EventSystem.post((OnItemAddedEvent) () -> item);
    }

    public static void onItemsUpdate(ShockPacketIncoming packet) {
        OHItem item = new OHItem(packet);
        EventSystem.post((OnItemUpdatedEvent) () -> item);
    }

    public static void onItemsRemove(ShockPacketIncoming packet) {
        int headerBytes = 2;
        final byte[] dataRemainder = packet.readBytes(packet.getBytesLength() - headerBytes, headerBytes);
        String data = new String(dataRemainder, StandardCharsets.ISO_8859_1);
        int id = Integer.parseInt(data);
        EventSystem.post((OnItemRemovedEvent) () -> id);
    }

    public static void onItems(ShockPacketIncoming packet) {
        if (packet.length() > 2) {
            OHItem[] items = OHItem.parse(packet);
            EventSystem.post((OnItemsLoadedEvent) () -> items);
        }
    }
}
