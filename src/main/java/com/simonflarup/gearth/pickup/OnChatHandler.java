package com.simonflarup.gearth.pickup;

import com.simonflarup.gearth.origins.events.chat.OnChatOutEvent;
import com.simonflarup.gearth.origins.services.OHFlatManager;
import com.simonflarup.gearth.origins.services.OHServiceProvider;

public final class OnChatHandler {
    private OnChatHandler() {
    }

    public static void onChat(OnChatOutEvent event, String message, OHServiceProvider serviceProvider) {
        PickupHandler pickupHandler = new PickupHandler(serviceProvider.getPacketSender());
        OHFlatManager flatManager = serviceProvider.getFlatManager();

        if (message.startsWith(":pickup") && message.split(" ").length == 1) {
            event.silenceMessage();
            new Thread(() -> pickupHandler.pickupItems(new String[]{":pickup", "all", "-1"}, flatManager)).start();
        }
        if (message.startsWith(":pickup") && message.split(" ").length > 1) {
            String[] argument = message.split(" ");
            event.silenceMessage();
            new Thread(() -> pickupHandler.pickupItems(argument, flatManager)).start();
        }
    }
}
