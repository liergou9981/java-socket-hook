# java-socks-hook

## how to hook socket in java

### socket hook用法

### how use scoket hook：
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

### 配置hook规则
### 修改SocketHookImpl.java中socket的基础函数内部逻辑，即可任意hook

### edit hook rule
### edit the socket base function impl logic in SocketHookImpl.java to hook socket

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
#Author
[liergou](https://github.com/liergou9981 "liergou") 

# AND MORE....
Welcome to contribute for the project.

