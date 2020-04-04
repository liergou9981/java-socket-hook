package com.liergou.hook.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class test {

    public static void main(String[] args) {
        try {
            SocketHook.startHook();
            //SocketHook.stopHook();
            long startTime=System.currentTimeMillis();
            System.out.println("开始时间： "+startTime+"ms");
            //SocketHook.startHook();
            URL u = new URL("http://127.0.0.1:10809");
            URLConnection urlConnection = u.openConnection();
            long start=System.currentTimeMillis();
            urlConnection.getHeaderFields();
            //BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); //send request
            long end=System.currentTimeMillis();
            System.out.println("socket times： "+(end-start)+"ms");

            long endTime=System.currentTimeMillis(); //获取结束时间
            System.out.println("运行时间： "+(endTime-startTime)+"ms");
            //System.out.println(html.toString());
            //SocketHook.stopHook();

        }catch(Exception e) {
            System.out.println(e.toString());
        }
    }
}
