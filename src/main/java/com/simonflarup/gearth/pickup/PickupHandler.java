package com.simonflarup.gearth.pickup;

import com.simonflarup.gearth.origins.models.incoming.room.OHActiveObject;
import com.simonflarup.gearth.origins.models.incoming.room.OHItem;
import com.simonflarup.gearth.origins.services.OHFlatManager;
import gearth.protocol.packethandler.shockwave.packets.ShockPacket;
import gearth.protocol.packethandler.shockwave.packets.ShockPacketOutgoing;

import java.util.Arrays;
import java.util.Map;

public class PickupHandler {
    private final SendToServer sendToServer;

    public PickupHandler(SendToServer sendToServer) {
        this.sendToServer = sendToServer;
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

    private boolean sendToServer(ShockPacket packet) {
        return this.sendToServer.sendToServer(packet);
    }

    public void pickupItems(String[] args, OHFlatManager flatManager) {
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
            maxAmount = canParseInt(args[1]);
            if (maxAmount != -1) {
                argument = "all";
            }
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
}
