package com.altimit_server;

import com.altimit_server.types.User;
import com.altimit_server.util.AltimitConverter;

import java.util.*;

/**
 * Post Man is used to send messages to clients.
 */

class PostMan {
    /**
     * This class is used to send a message to a specific client.
     * @param clientUUID The client UUID used to get the client socket.
     * @param methodName The name of the method to be invoked on the client.
     * @param args The parameters that will be passed to the method invoked on the client.
     */
    static void SendPost(UUID clientUUID, String methodName, Object... args){
        if(main.localClientMap.containsKey(clientUUID)) {
            byte[] postToSend = AltimitConverter.SendConversion(methodName, args);
            try {
                main.localClientMap.get(clientUUID).outputStream.write(postToSend);
            } catch (Exception e) {
                AltimitHeatBeat.badUUIDs.add(clientUUID);
            }
        }
    }

    /**
     * This class is used to send a message to every connected client.
     * @param methodName The name of the method to be invoked on the client.
     * @param args The parameters that will be passed to the method invoked on the client.
     */
    static void SendPost(String methodName, Object... args){
        UUID[] temp = {};
        SendPost(methodName, temp, args);
    }

    /**
     * This class is used to send a message to every connected client.
     * @param methodName The name of the method to be invoked on the client.
     * @param args The parameters that will be passed to the method invoked on the client.
     */
    static void SendPost(String methodName, UUID[] ignoreList, Object... args){
        if(main.localClientMap.size() > 0) {
            byte[] postToSend = AltimitConverter.SendConversion(methodName, args);

            UUID currentUUID;

            Iterator it = main.localClientMap.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<UUID, ClientInfo> pair = (Map.Entry)it.next();
                currentUUID = pair.getKey();
                if(!Arrays.asList(ignoreList).contains(currentUUID)) {
                    try {
                        pair.getValue().outputStream.write(postToSend);
                    } catch (Exception e) {
                        AltimitHeatBeat.badUUIDs.add(currentUUID);
                    }
                }
            }
        }
    }

    /**
     * This class is used to message all clients within a room.
     * @param roomName The name of the room to message in.
     * @param methodName The name of the method to be invoked on the client.
     * @param args The parameters that will be passed to the method invoked on the client.
     */
    static void SendPost(String roomName, String methodName, Object... args) {
        UUID[] temp = {};
        SendPost(roomName, temp, methodName, args);
    }

    /**
     * This class is used to message all clients within a room.
     * @param roomName The name of the room to message in.
     * @param ignoreList All clients that will not receive the message.
     * @param methodName The name of the method to be invoked on the client.
     * @param args The parameters that will be passed to the method invoked on the client.
     */
    static void SendPost(String roomName, UUID[] ignoreList, String methodName, Object... args){
        byte[] postToSend = AltimitConverter.SendConversion(methodName, args);

        UUID currentUUID;

        if(Rooms.GetRoomUsers(roomName).size() > 0) {
            HashMap<UUID, User> selects = Rooms.GetRoomUsers(roomName);
            for(Map.Entry<UUID, User> entry : selects.entrySet()) {
                //TODO: Find better way to get the users id. crappy way to do it right now.
                currentUUID = entry.getKey();
                if(!Arrays.asList(ignoreList).contains(currentUUID)) {
                    try {
                        main.localClientMap.get(currentUUID).outputStream.write(postToSend);
                    } catch (Exception e) {
                        AltimitHeatBeat.badUUIDs.add(currentUUID);
                    }
                }
            }
        }
    }
}
