package com.liergou.hook.socket;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketHookImpl extends SocketImpl implements SocketOptions
{

    private SocketImpl socketImpl = null;
    private Method createImpl;
    private Method connectHostImpl;
    private Method connectInetAddressImpl;
    private Method connectSocketAddressIMPL;
    private Method bindImpl;
    private Method listenImpl;
    private Method acceptImpl;
    private Method getInputStreamImpl;
    private Method getOutputStreamImpl;
    private Method availableImpl;
    private Method closeImpl;
    private Method sendUrgentDataImpl;

    /**
     * class init 初始化获取反射类和方法
     * @param initSocketImpl
     */
    public SocketHookImpl(SocketImpl initSocketImpl) {

        if ( initSocketImpl == null){
            throw new RuntimeException("");
            //TODO close hook
        }

        this.socketImpl = initSocketImpl;
        final Class<?>  clazz = this.socketImpl.getClass();
        createImpl = Utils.findMethod( clazz,"create", new Class<?>[]{ boolean.class } );
        connectHostImpl = Utils.findMethod( clazz, "connect", new Class<?>[]{ String.class, int.class } );
        connectInetAddressImpl = Utils.findMethod( clazz, "connect", new Class<?>[]{ InetAddress.class, int.class } );
        connectSocketAddressIMPL = Utils.findMethod( clazz, "connect", new Class<?>[]{ SocketAddress.class, int.class } );
        bindImpl = Utils.findMethod( clazz, "bind", new Class<?>[]{ InetAddress.class, int.class } );
        listenImpl = Utils.findMethod( clazz, "listen", new Class<?>[]{ int.class } );
        acceptImpl = Utils.findMethod( clazz, "accept", new Class<?>[]{ SocketImpl.class } );
        getInputStreamImpl = Utils.findMethod( clazz, "getInputStream", new Class<?>[]{  } );
        getOutputStreamImpl = Utils.findMethod( clazz, "getOutputStream", new Class<?>[]{  } );
        availableImpl = Utils.findMethod( clazz, "available", new Class<?>[]{ } );
        closeImpl = Utils.findMethod( clazz, "close", new Class<?>[]{ } );
        sendUrgentDataImpl = Utils.findMethod( clazz, "sendUrgantData", new Class<?>[]{ int.class } );

    }

    @Override
    protected void create(boolean stream) throws IOException {
            try
            {
                this.createImpl.invoke( this.socketImpl, stream);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
            {
                Logger.getLogger(SocketHookImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

    @Override
    protected void connect(String host, int port) throws IOException {
        Logger.getLogger(SocketHookImpl.class.getName()).log(Level.INFO, "host=" + host + ",port=" + port );

            try
            {
                this.connectHostImpl.invoke( this.socketImpl, host, port);
            }
            catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException ex)
            {
                Logger.getLogger(SocketHookImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

    }


    @Override
    protected void connect(InetAddress address, int port) throws IOException {
            Logger.getLogger(SocketHookImpl.class.getName()).log(Level.INFO, "InetAddr=" + address.toString() + ",port=" + port );

            try
            {
                this.connectInetAddressImpl.invoke( this.socketImpl, address, port);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
            {
                Logger.getLogger(SocketHookImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    protected void connect(SocketAddress address, int timeout) throws IOException {
            Logger.getLogger(SocketHookImpl.class.getName()).log(Level.INFO, "SocketAddr=" + address.toString() + ",port=" + port );
            try
            {
                this.connectSocketAddressIMPL.invoke( this.socketImpl, address, timeout);
            }
            catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException ex)
            {
                Logger.getLogger(SocketHookImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    protected void bind(InetAddress host, int port) throws IOException {
            try
            {
                this.bindImpl.invoke( this.socketImpl, host, port);
            }
            catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException ex)
            {
                Logger.getLogger(SocketHookImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    protected void listen(int backlog) throws IOException {

            try
            {
                this.listenImpl.invoke( this.socketImpl, backlog);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
            {
                Logger.getLogger(SocketHookImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    protected void accept(SocketImpl s) throws IOException {

            try
            {
                this.acceptImpl.invoke( this.socketImpl, s);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
            {
                Logger.getLogger(SocketHookImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    protected InputStream getInputStream() throws IOException {
        InputStream inStream = null;

            try
            {
                inStream = (InputStream)this.getInputStreamImpl.invoke( this.socketImpl);
            }
            catch ( ClassCastException | InvocationTargetException | IllegalArgumentException | IllegalAccessException ex )
            {
                Logger.getLogger(SocketHookImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        BufferedReader in = new BufferedReader(new InputStreamReader(new BufferedInputStream(inStream))); //send request
        String inputLine;
        StringBuilder html = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            html.append(inputLine);
        }
        in.close();
        System.out.println(html.toString());

        return inStream;
    }

    @Override
    protected OutputStream getOutputStream() throws IOException {
        OutputStream outStream = null;

            try
            {
                outStream = (OutputStream)this.getOutputStreamImpl.invoke( this.socketImpl);
            }
            catch ( ClassCastException | IllegalArgumentException | IllegalAccessException | InvocationTargetException ex )
            {
                Logger.getLogger(SocketHookImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        return outStream;
    }

    @Override
    protected int available() throws IOException {
        int result = -1;

            try
            {
                result = (Integer)this.availableImpl.invoke( this.socketImpl);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
            {
                Logger.getLogger(SocketHookImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        return result;
    }

    @Override
    protected void close() throws IOException {
            try
            {
                this.closeImpl.invoke( this.socketImpl);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
            {
                Logger.getLogger(SocketHookImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    protected void sendUrgentData(int data) throws IOException {
            try
            {
                this.sendUrgentDataImpl.invoke( this.socketImpl, data);
            }
            catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException ex)
            {
                Logger.getLogger(SocketHookImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public void setOption(int optID, Object value) throws SocketException {
        if ( null != this.socketImpl )
        {
            this.socketImpl.setOption( optID, value );
        }
    }

    public Object getOption(int optID) throws SocketException {
        return this.socketImpl.getOption( optID );
    }

}
