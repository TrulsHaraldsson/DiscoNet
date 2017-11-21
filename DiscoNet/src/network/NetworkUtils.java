/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import com.jme3.network.serializing.Serializer;
import network.messages.DiskStateMessage;
import network.messages.GameStateMessage;
import network.messages.InitAckMessage;
import network.messages.InitMessage;
import network.messages.JoinAckMessage;
import network.messages.JoinMessage;
import network.messages.PlayerMoveMessage;
import network.messages.StartMessage;

/**
 *
 * @author hannes
 */
public class NetworkUtils {
    static final int SERVER_PORT = 7999;
    
    public static void initSerializables(){
        // TODO: register for all new message types
        Serializer.registerClass(DiskStateMessage.class);
        Serializer.registerClass(GameStateMessage.class);
        Serializer.registerClass(InitMessage.class);
        Serializer.registerClass(InitAckMessage.class);
        Serializer.registerClass(JoinMessage.class);
        Serializer.registerClass(JoinAckMessage.class);
        Serializer.registerClass(StartMessage.class);
        Serializer.registerClass(PlayerMoveMessage.class);
        Serializer.registerClass(DiskStateMessage.class);
    }
}
