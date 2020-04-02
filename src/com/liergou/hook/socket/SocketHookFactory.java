package com.liergou.hook.socket;


import java.io.IOException;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.SocketImplFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketHookFactory implements SocketImplFactory
    {
        private static SocketImpl   clazz;
        private static Boolean startHook = false;

        public static void setStart(Boolean set){
            startHook = set;
        }

        public static synchronized void initSocket() throws NoSuchFieldException {
            if ( clazz != null ) { return; }

            Socket  socket = new Socket();
            try{
                Field implField = Socket.class.getDeclaredField("impl");
                implField.setAccessible( true );
                clazz = (SocketImpl) implField.get(socket);

            }catch (NoSuchFieldException | IllegalAccessException ignored){

            }

            try {
                socket.close();
            }
            catch ( IOException ignored)
            {

            }
        }

        public SocketImpl createSocketImpl() {

            if(startHook) {
                    try {
                        return new SocketHookImpl(clazz);
                    } catch (Exception e) {
                        Logger.getLogger(SocketHookFactory.class.getName()).log(Level.WARNING, "hook 失败  请检查" );
                        return clazz;
                    }
            }else{
                return clazz;
            }
        }
    }
