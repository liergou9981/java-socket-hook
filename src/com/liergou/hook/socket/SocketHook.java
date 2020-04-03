package com.liergou.hook.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class SocketHook {
    static void startHook() throws NoSuchFieldException, IOException {
        SocketHookFactory.initSocket();
        SocketHookFactory.setStart(true);
        try{
            Socket.setSocketImplFactory(new SocketHookFactory());
        }catch (SocketException ignored){
        }
    }

    static void stopHook(){
        SocketHookFactory.setStart(false);
    }
}
