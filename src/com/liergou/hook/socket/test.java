package com.liergou.hook.socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class test {

    public static void main(String[] args) {
        try {
            SocketHook.startHook();
            URL u = new URL("http://www.baidu.com");
            URLConnection urlConnection = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); //send request
            String inputLine;
            StringBuilder html = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                html.append(inputLine);
            }
            in.close();
            System.out.println(html.toString());
            SocketHook.startHook();

        }catch(Exception e) {
            System.out.println(e.toString());
        }
    }
}
