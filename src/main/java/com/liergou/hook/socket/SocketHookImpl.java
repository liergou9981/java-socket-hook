package com.liergou.hook.socket;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author liergou
 * @Description socket impl
 * @Date 23:39 2020/4/2
 * @Param
 * @return
 **/
public class SocketHookImpl extends SocketImpl implements SocketOptions
{
    private static Logger logger = LoggerFactory.getLogger(SocketHookImpl.class);

    static Boolean isInit = false;
    static private SocketImpl socketImpl = null;
    static private Method createImpl;
    static private Method connectHostImpl;
    static private Method connectInetAddressImpl;
    static private Method connectSocketAddressImpl;
    static private Method bindImpl;
    static private Method listenImpl;
    static private Method acceptImpl;
    static private Method getInputStreamImpl;
    static private Method getOutputStreamImpl;
    static private Method availableImpl;
    static private Method closeImpl;
    static private Method shutdownInputImpl;
    static private Method shutdownOutputImpl;
    static private Method sendUrgentDataImpl;


    SocketHookImpl(Constructor socketConstructor) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        socketImpl = (SocketImpl) socketConstructor.newInstance();
    }

    /**
     * @Author liergou
     * @Description 初始化反射方法
     * @Date 23:40 2020/4/2
     * @Param [initSocketImpl]
     * @return
     **/
    public static void initSocketImpl(Class<?> initSocketImpl) {

        if ( initSocketImpl == null){
            SocketHookFactory.setHook(false);
            throw new RuntimeException("initSocketImpl failed; hook close!");
        }

        if(!isInit){
        //need optimize performance
            createImpl = SocketHookUtils.findMethod(initSocketImpl,"create", new Class<?>[]{ boolean.class } );
            connectHostImpl = SocketHookUtils.findMethod(initSocketImpl, "connect", new Class<?>[]{ String.class, int.class } );
            connectInetAddressImpl = SocketHookUtils.findMethod(initSocketImpl, "connect", new Class<?>[]{ InetAddress.class, int.class } );
            connectSocketAddressImpl = SocketHookUtils.findMethod(initSocketImpl, "connect", new Class<?>[]{ SocketAddress.class, int.class } );
            bindImpl = SocketHookUtils.findMethod(initSocketImpl, "bind", new Class<?>[]{ InetAddress.class, int.class } );
            listenImpl = SocketHookUtils.findMethod(initSocketImpl, "listen", new Class<?>[]{ int.class } );
            acceptImpl = SocketHookUtils.findMethod(initSocketImpl, "accept", new Class<?>[]{ SocketImpl.class } );
            getInputStreamImpl = SocketHookUtils.findMethod(initSocketImpl, "getInputStream", new Class<?>[]{  } );
            getOutputStreamImpl = SocketHookUtils.findMethod(initSocketImpl, "getOutputStream", new Class<?>[]{  } );
            availableImpl = SocketHookUtils.findMethod(initSocketImpl, "available", new Class<?>[]{ } );
            closeImpl = SocketHookUtils.findMethod(initSocketImpl, "close", new Class<?>[]{ } );
            shutdownInputImpl = SocketHookUtils.findMethod(initSocketImpl, "shutdownInput", new Class<?>[]{ } );
            shutdownOutputImpl = SocketHookUtils.findMethod(initSocketImpl, "shutdownOutput", new Class<?>[]{ } );
            sendUrgentDataImpl = SocketHookUtils.findMethod(initSocketImpl, "sendUrgantData", new Class<?>[]{ int.class } );
            isInit = true;
        }
    }


    /**
     * socket base method impl
     */
    @Override
    protected void create(boolean stream) throws IOException {
            try
            {
                createImpl.invoke(socketImpl, stream);

            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
            {
                logger.error("createImpl failed："+ ex);
            }

    }

    @Override
    protected void connect(String host, int port) throws IOException {

            try
            {
                connectHostImpl.invoke( socketImpl, host, port);
            }
            catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException ex)
            {
                logger.error("connectHostImpl failed："+ ex);
            }

    }


    @Override
    protected void connect(InetAddress address, int port) throws IOException {
        //logger.info("connect InetAddress ："+address.toString());
            try
            {
                connectInetAddressImpl.invoke( socketImpl, address, port);

            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
            {
                logger.error("connectInetAddressImpl failed："+ ex);
            }
    }

    @Override
    protected void connect(SocketAddress address, int timeout) throws IOException {

        //logger.info("connect SocketAddress ："+address.toString());

            try
            {
                connectSocketAddressImpl.invoke( socketImpl, address, timeout);
            }
            catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException ex)
            {
                logger.error("connectSocketAddressImpl failed："+ ex);
            }
    }

    @Override
    protected void bind(InetAddress host, int port) throws IOException {
            try
            {
                bindImpl.invoke( socketImpl, host, port);
            }
            catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException ex)
            {
                logger.error("bindImpl failed："+ ex);
            }
    }

    @Override
    protected void listen(int backlog) throws IOException {
            try
            {
                listenImpl.invoke( socketImpl, backlog);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
            {
                logger.error("listenImpl failed："+ ex);
            }
    }

    @Override
    protected void accept(SocketImpl s) throws IOException {

            try
            {
                acceptImpl.invoke( socketImpl, s);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
            {
                logger.error("acceptImpl failed："+ ex);
            }
    }

    @Override
    protected InputStream getInputStream() throws IOException {
        InputStream inStream = null;

            try
            {
                inStream = (InputStream) getInputStreamImpl.invoke( socketImpl);
            }
            catch ( ClassCastException | InvocationTargetException | IllegalArgumentException | IllegalAccessException ex )
            {
                logger.error("getInputStreamImpl failed："+ ex);
            }

        return inStream;
    }

    @Override
    protected OutputStream getOutputStream() throws IOException {
        OutputStream outStream = null;

            try
            {
                outStream = (OutputStream) getOutputStreamImpl.invoke( socketImpl);
            }
            catch ( ClassCastException | IllegalArgumentException | IllegalAccessException | InvocationTargetException ex )
            {
                logger.error("getOutputStreamImpl failed："+ ex);
            }

        return outStream;
    }

    @Override
    protected int available() throws IOException {
        int result = -1;

            try
            {
                result = (Integer) availableImpl.invoke( socketImpl);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
            {
                logger.error("availableImpl failed："+ ex);
            }

        return result;
    }

    @Override
    protected void close() throws IOException {
            try
            {
                closeImpl.invoke( socketImpl);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
            {
                logger.error("closeImpl failed："+ ex);
            }
    }

    @Override
    protected void shutdownInput() throws IOException {
        try
        {
            shutdownInputImpl.invoke( socketImpl);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            logger.error("shutdownInputImpl failed："+ ex);
        }

    }

    @Override
    protected void shutdownOutput() throws IOException {
        try
        {
            shutdownOutputImpl.invoke( socketImpl);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            logger.error("shutdownOutputImpl failed："+ ex);
        }

    }

    @Override
    protected void sendUrgentData(int data) throws IOException {
            try
            {
                sendUrgentDataImpl.invoke( socketImpl, data);
            }
            catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException ex)
            {
                logger.error("sendUrgentDataImpl failed："+ ex);
            }
    }

    public void setOption(int optID, Object value) throws SocketException {
        if ( null != socketImpl )
        {
            socketImpl.setOption( optID, value );
        }
    }

    public Object getOption(int optID) throws SocketException {
        return socketImpl.getOption( optID );
    }

    /**
     * dont impl other child method now
     * dont sure where will use it
     **/


}
