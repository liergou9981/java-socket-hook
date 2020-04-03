# java-socks-hook
hook socks in java

怎么开启scoket hook
how to hook java scoket：
```
SocketHook.starthook();
//开启hook之后，socket都会走hook逻辑
//after SocketHook.startHook()    
//the socket operation will be hooked
...
socket request
...
SocketHook.stopHook();
//关闭hook,socket 逻辑还原恢复
//after SocketHook.stopHook()    
//the socket operation hook will cancel
```

配置hook规则
修改SocketHookImpl.java中socket的基础函数内部逻辑，即可实现自定义hook规则

edit hook rule
edit the socket base function logic in SocketHookImpl.java

such as
```
    @Override
    protected void connect(SocketAddress address, int timeout) throws IOException {
            Logger.getLogger(SocketHookImpl.class.getName()).log(Level.INFO, "SocketAddr=" + address.toString() + ",port=" + port );    
            /*
            YOU CAN HOOK  socket.connect()  AT HERE    
            YOU CAN HOOK  socket.connect()  AT HERE
            */
            try
            {
                this.connectSocketAddressIMPL.invoke( this.socketImpl, address, timeout);
            }
            catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException ex)
            {
                Logger.getLogger(SocketHookImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
```
