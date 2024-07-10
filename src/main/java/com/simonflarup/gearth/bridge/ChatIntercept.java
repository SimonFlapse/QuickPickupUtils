package com.simonflarup.gearth.bridge;

import com.simonflarup.gearth.events.EventSystem;
import com.simonflarup.gearth.events.type.chat.OnChatEvent;
import com.simonflarup.gearth.util.ShockPacketUtils;
import gearth.protocol.HMessage;
import gearth.protocol.packethandler.shockwave.packets.ShockPacketOutgoing;

import java.nio.charset.StandardCharsets;

public class ChatIntercept extends AbstractIntercept {

    public static void onChat(HMessage hMessage) {
        ShockPacketOutgoing packet = ShockPacketUtils.getShockPacketOutgoingFromMessage(hMessage);
        if (packet == null) {
            return;
        }
        String message = packet.readString(StandardCharsets.ISO_8859_1);
        EventSystem.post(new OnChatEvent() {
            @Override
            public String getMessage() {
                return message;
            }

            @Override
            public void silenceMessage() {
                hMessage.setBlocked(true);
            }
        });
    }
}
