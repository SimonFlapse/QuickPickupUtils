package com.simonflapse.gearth.events;

import com.simonflapse.gearth.bridge.ActiveObjectsIntercept;
import com.simonflapse.gearth.bridge.ChatIntercept;
import com.simonflapse.gearth.bridge.FlatIntercept;
import com.simonflapse.gearth.bridge.ItemsIntercept;
import com.simonflapse.gearth.service.OHFlatManager;
import com.simonflapse.gearth.service.OHFlatManagerImpl;
import com.simonflapse.gearth.util.ShockPacketUtils;
import gearth.extensions.Extension;
import gearth.protocol.HMessage;
import gearth.protocol.packethandler.shockwave.packets.ShockPacketIncoming;
import gearth.protocol.packethandler.shockwave.packets.ShockPacketOutgoing;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public abstract class OHExtension extends Extension {
    protected OHExtension(String[] args) {
        super(args);
        EventSystem.register(this);
    }

    public OHFlatManager getFlatManager() {
        return OHFlatManagerImpl.getInstance();
    }

    @Override
    protected void initExtension() {
        onConnect((host, port, APIVersion, versionClient, client) -> {
            if (!Objects.equals(versionClient, "SHOCKWAVE")) {
                log.error("This extension only works with Habbo Hotel: Origins");
                System.exit(0);
            }
        });

        setupIntercepts();
    }

    private void setupIntercepts() {
        interceptToClient("ITEMS", ItemsIntercept::onItems);
        interceptToClient("ITEMS_2", ItemsIntercept::onItemsAdd);
        interceptToClient("UPDATEITEM", ItemsIntercept::onItemsUpdate);
        interceptToClient("REMOVEITEM", ItemsIntercept::onItemsRemove);
        interceptToClient("ACTIVEOBJECTS", ActiveObjectsIntercept::onActiveObjects);
        interceptToClient("ACTIVEOBJECT_ADD", ActiveObjectsIntercept::onActiveObjectsAdd);
        interceptToClient("ACTIVEOBJECT_UPDATE", ActiveObjectsIntercept::onActiveObjectsUpdate);
        interceptToClient("ACTIVEOBJECT_REMOVE", ActiveObjectsIntercept::onActiveObjectsRemove);
        interceptToClient("STUFFDATAUPDATE", ActiveObjectsIntercept::onStuffDataUpdate);
        interceptToClient("FLATINFO", FlatIntercept::onFlatInfo);
        intercept(HMessage.Direction.TOSERVER, "CHAT", ChatIntercept::onChat);
    }

    private void interceptToClient(String header, IncomingPacketHandler incomingPacketHandler) {
        intercept(HMessage.Direction.TOCLIENT, header, (hMessage) -> handleEvent(hMessage, incomingPacketHandler));
    }

    private void handleEvent(HMessage hMessage, IncomingPacketHandler incomingPacketHandler) {
        ShockPacketIncoming packet = ShockPacketUtils.getShockPacketIncomingFromMessage(hMessage);
        if (packet == null) {
            return;
        }
        incomingPacketHandler.handlePacket(packet);
    }

    private void handleEvent(HMessage hMessage, OutgoingPacketHandler outgoingPacketHandler) {
        ShockPacketOutgoing packet = ShockPacketUtils.getShockPacketOutgoingFromMessage(hMessage);
        if (packet == null) {
            return;
        }
        outgoingPacketHandler.handlePacket(packet);
    }
}
