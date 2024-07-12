package com.simonflarup.gearth.pickup;

import com.google.common.eventbus.Subscribe;
import com.simonflarup.gearth.origins.OHExtension;
import com.simonflarup.gearth.origins.events.chat.OnChatOutEvent;
import gearth.extensions.ExtensionInfo;

@ExtensionInfo(
        Title = "Quick Pickup Utils",
        Description = "Chat commands to quickly pickup items in your habbo flat",
        Version = "1.0.1",
        Author = "SimonFlapse"
)

public class QuickPickupUtilsExtension extends OHExtension {

    public QuickPickupUtilsExtension(String[] args) {
        super(args);
    }

    @Subscribe
    void onChat(OnChatOutEvent event) {
        String message = event.get().getMessage();

        if (!message.startsWith(":pickup")) {
            return;
        }
        OnChatHandler.onChat(event, message, getServiceProvider());
    }
}
