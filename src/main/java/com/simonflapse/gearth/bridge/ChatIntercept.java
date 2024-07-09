package com.simonflapse.gearth.bridge;

import com.simonflapse.gearth.events.EventSystem;
import com.simonflapse.gearth.events.type.chat.OnChatEvent;
import com.simonflapse.gearth.util.ShockPacketUtils;
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
