package com.altimit_server;

import com.altimit_server.types.*;

import java.util.UUID;

public class AltimitUnity {

    @AltimitCmd
    public static void Instantiate(String prefabName, Vector3 position, Quaternion rotation, Integer viewId, UUID clientUUID){
        System.out.println("INSTALL " + viewId);
        UnityObject temp = new UnityObject(viewId, Users.GetId(clientUUID), position, rotation, prefabName);
        String room = Users.GetRoomName(clientUUID);
        Rooms.AddUnityObject(room, temp, clientUUID);
    }

    @AltimitCmd
    public static void UpdateUnityObject(Vector3 position, Quaternion rotation, long timeStamp, Integer viewId, UUID clientUUID){
        System.out.println("POS + ROT");
        Rooms.UpdateRoomUnityObject(Users.GetRoomName(clientUUID), viewId, position, rotation, timeStamp, clientUUID);
        System.out.println("updating object");
    }

    @AltimitCmd
    public static void UpdateUnityObject(Vector3 position, long timeStamp, Integer viewId, UUID clientUUID){
        System.out.println("POS");
        Rooms.UpdateRoomUnityObject(Users.GetRoomName(clientUUID), viewId, position, timeStamp, clientUUID);
    }

    @AltimitCmd
    public static void UpdateUnityObject(Quaternion rotation, long timeStamp, Integer viewId, UUID clientUUID){
        System.out.println("ROT");
        Rooms.UpdateRoomUnityObject(Users.GetRoomName(clientUUID), viewId, rotation, timeStamp, clientUUID);
    }

    @AltimitCmd
    public static void RegisterSceneObject(int viewId, Vector3 position, Quaternion rotation, UUID clientUUID){

    }
}
