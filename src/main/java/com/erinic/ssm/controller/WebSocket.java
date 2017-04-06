package com.erinic.ssm.controller;

import com.erinic.ssm.domain.Content;
import com.erinic.ssm.service.ContentService;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by asus on 2017/4/5.
 */
@ServerEndpoint(value = "/webSocket",configurator = SpringConfigurator.class)
public class WebSocket {

    private static int onlineCount = 0;

    public WebSocket(){

    }

    @Resource
    private ContentService contentService;

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    private Session session;

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSockets.add(this);
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    @OnClose
    public void onClose(){
        webSockets.remove(this);
        subOnlineCount();
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message,Session session){
        System.out.println("来自客户端的消息:" + message);

        for (WebSocket webSocket : webSockets) {

            try {

                webSocket.sendMessage(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException{
        Content content = new Content();
        content.setContent(message);
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
        content.setCreatedate(sm.format(new Date()));
        contentService.insertSelective(content);

        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount(){
        return onlineCount;
    }
    public static synchronized void addOnlineCount(){
        onlineCount++;
    }
    public static synchronized void subOnlineCount(){
        onlineCount--;
    }
}
