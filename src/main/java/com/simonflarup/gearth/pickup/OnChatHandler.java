package com.simonflarup.gearth.pickup;

import com.simonflarup.gearth.origins.events.chat.OnChatEvent;
import com.simonflarup.gearth.origins.services.OHFlatManager;

public final class OnChatHandler {
    private OnChatHandler() {
    }

    public static void onChat(OnChatEvent event, String message, SendToServer sendToServer, OHFlatManager flatManager) {
        PickupHandler pickupHandler = new PickupHandler(sendToServer);

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
