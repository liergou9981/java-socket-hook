package com.liergou.hook.socket;

import java.io.IOException;
import java.net.Socket;

public class SocketHook {
    static void startHook() throws NoSuchFieldException, IOException {
        SocketHookFactory.initSocket();
        SocketHookFactory.setStart(true);
        Socket.setSocketImplFactory(new SocketHookFactory());
    }

    static void stopHook(){
        SocketHookFactory.setStart(false);
    }
}
