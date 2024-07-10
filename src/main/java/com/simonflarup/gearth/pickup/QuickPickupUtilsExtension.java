package com.simonflarup.gearth.pickup;

import com.google.common.eventbus.Subscribe;
import com.simonflarup.gearth.origins.OHExtension;
import com.simonflarup.gearth.origins.events.activeobject.OnStuffDataUpdatedEvent;
import com.simonflarup.gearth.origins.events.chat.OnChatEvent;
import com.simonflarup.gearth.origins.models.incoming.room.OHActiveObject;
import com.simonflarup.gearth.origins.models.incoming.room.OHItem;
import com.simonflarup.gearth.origins.services.OHFlatManager;
import gearth.extensions.ExtensionInfo;
import gearth.protocol.packethandler.shockwave.packets.ShockPacketOutgoing;

import java.util.Arrays;
import java.util.Map;


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
    void onStuffDataUpdate(OnStuffDataUpdatedEvent event) {
        System.out.println("Stuff data updated: " + event.getTargetId() + " - " + event.getNewData());
    }

    @Subscribe
    void onChat(OnChatEvent event) {
        String message = event.getMessage();

        if (!message.startsWith(":pickup")) {
            return;
        }

        if (message.startsWith(":pickup") && message.split(" ").length == 1) {
            event.silenceMessage();
            new Thread(() -> pickupItems(new String[]{":pickup", "all", "-1"})).start();
        }
        if (message.startsWith(":pickup") && message.split(" ").length > 1) {
            String[] argument = message.split(" ");
            event.silenceMessage();
            new Thread(() -> pickupItems(argument)).start();
        }
    }

    public void pickupItems(String[] args) {
        OHFlatManager flatManager = getServiceProvider().getFlatManager();
        Map<Integer, OHActiveObject> activeObjects = flatManager.getActiveObjectsInFlat();
        Map<Integer, OHItem> items = flatManager.getItemsInFlat();

        if (activeObjects.isEmpty() && items.isEmpty()) {
            String message = String.format("{out:WHISPER}{s:\" %s - %s\"}", "INFO", "There are no items in the room");
            sendToServer(new ShockPacketOutgoing(message));
            throttlePackets(500);
            message = String.format("{out:WHISPER}{s:\" %s - %s\"}", "INFO", "Try re-entering the room");
            sendToServer(new ShockPacketOutgoing(message));
            throttlePackets(500);
            message = String.format("{out:WHISPER}{s:\" %s - %s\"}", "DEBUG", Arrays.toString(args));
            sendToServer(new ShockPacketOutgoing(message));
            return;
        }

        String argument = args[1];

        int maxAmount = -1;

        if (args.length > 2) {
            maxAmount = canParseInt(args[2]);
        } else if (!argument.equals("floor") && !argument.equals("wall")) {
            argument = "all";
            maxAmount = canParseInt(args[1]);
        }

        if (argument.equals("wall")) {
            pickupItems(items, maxAmount);
            return;
        }

        if (argument.equals("floor")) {
            pickupActiveObject(activeObjects, maxAmount);
            return;
        }

        if (!argument.equals("all")) {
            pickupSpecific(activeObjects, items, argument, maxAmount);
            return;
        }

        maxAmount -= pickupItems(items, maxAmount);
        pickupActiveObject(activeObjects, maxAmount);
    }

    private int pickupSpecific(Map<Integer, OHActiveObject> activeObjects, Map<Integer, OHItem> items, String argument, int maxAmount) {
        int count = 0;
        for (OHActiveObject activeObject : activeObjects.values()) {
            if (maxAmount > -1 && count >= maxAmount) {
                return count;
            }
            if (activeObject.getClassName().equals(argument)) {
                pickup(activeObject);
                count++;
            }
        }

        for (OHItem item : items.values()) {
            if (maxAmount > -1 && count >= maxAmount) {
                return count;
            }
            if (item.getFullClassName().equals(argument)) {
                pickup(item);
                count++;
            }
        }
        return count;
    }

    private int pickupItems(Map<Integer, OHItem> items, int maxAmount) {
        int count = 0;
        for (OHItem item : items.values()) {
            if (maxAmount > -1 && count >= maxAmount) {
                break;
            }
            pickup(item);
            count++;
        }
        return count;
    }

    private int pickupActiveObject(Map<Integer, OHActiveObject> activeObjects, int maxAmount) {
        int count = 0;
        for (OHActiveObject activeObject : activeObjects.values()) {
            if (maxAmount > -1 && count >= maxAmount) {
                break;
            }
            pickup(activeObject);
            count++;
        }
        return count;
    }

    public void pickup(OHItem item) {
        sendToServer(new ShockPacketOutgoing(String.format("ACnew item %s", item.getId())));
        throttlePackets(500);
    }

    public void pickup(OHActiveObject activeObject) {
        sendToServer(new ShockPacketOutgoing(String.format("ACnew stuff %s", activeObject.getId())));
        throttlePackets(500);
    }

    public static void throttlePackets(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

    public static int canParseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
