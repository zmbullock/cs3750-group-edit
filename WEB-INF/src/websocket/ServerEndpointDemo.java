package websocket;

import groupManager.GroupManager;
import database.DatabaseManager;

import javax.json.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.StringReader;
import java.util.*;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
    
// Notes ...
//
// userSession.getBasicRemote().sendText(buildJsonData(...key, value...));

@ServerEndpoint("/serverendpointdemo")
public class ServerEndpointDemo {

  GroupManager gm = new GroupManager();
  DatabaseManager dbm = new DatabaseManager();
  String groupName = "";
  int textAreaNumber = 0;
  List<Session> usersInGroup = new ArrayList<>();

  //open connection to client
  @OnOpen
  public void handleOpen(Session userSession) 
  {
    gm.onOpen(userSession);
  }
  
  //exchange message with client
  @OnMessage
  public void handleMessage(String message, Session userSession) throws IOException{
    JsonObject json = Json.createReader(new StringReader(message)).readObject();
    switch(json.getString("messageType"))
    {
      case "setGroup":
        gm.onSetGroup(userSession, json.getString("groupName"));
       
        for(int i = 1; i <= dbm.getNumOfTextAreas(groupName); i++)
        { 
          String text = dbm.getText(groupName, i);
          Map<String, String> messageMap = new HashMap<>();
          messageMap.put("responseType", "text");
          messageMap.put("groupName", groupName);
          messageMap.put("textAreaNumber", Integer.toString(i));
          messageMap.put("text", text);
          messageMap.put("isLocked", 
            gm.isLocked(groupName, i)
              ? "1" : "0");

          userSession.getBasicRemote().sendText(buildJsonData(messageMap));
        }
        
        break;
      case "lockTextArea":

        groupName = json.getString("groupName");

        gm.onLockTextArea(userSession, textAreaNumber);
        usersInGroup = gm.getUsersFromGroup(groupName);
        if(usersInGroup == null)
        {
          break;
        }
        Map<String, String> messageMap = new HashMap<>();

        messageMap.put("responseType", "lock");
        messageMap.put("groupName", groupName);
        messageMap.put("textAreaNumber", Integer.toString(textAreaNumber));
      
        for(Session user : usersInGroup)
        {
          if(user != userSession)
          {
            user.getBasicRemote().sendText(buildJsonData(messageMap));
          }
        }
        break;
      case "unlockTextArea":
        textAreaNumber = json.getInt("textAreaNumber");
        groupName = json.getString("groupName");

        boolean sendUnlock = gm.onUnlockTextArea(userSession, textAreaNumber);
        if(!sendUnlock)
        {
          break;
        }
        
        usersInGroup = gm.getUsersFromGroup(groupName);
        if(usersInGroup == null)
        {
          break;
        }
        messageMap = new HashMap<>();
        messageMap.put("responseType", "unlock");
        messageMap.put("groupName", groupName);
        messageMap.put("textAreaNumber", Integer.toString(textAreaNumber));

        for(Session user : usersInGroup)
        {
          if(user != userSession)
          {
            user.getBasicRemote().sendText(buildJsonData(messageMap));
          }
        }

        break;
      case "sendTextAreaText":
        textAreaNumber = json.getInt("textAreaNumber");
        groupName = json.getString("groupName");
        String text = json.getString("text");

        if(!gm.hasLock(userSession, groupName, textAreaNumber))
        {
          break;
        }
        if(!dbm.updateText(groupName, textAreaNumber, text))
        {
          break;
        }
        
        usersInGroup = gm.getUsersFromGroup(groupName);
        if(usersInGroup == null)
        {
          break;
        }
        messageMap = new HashMap<>();
        messageMap.put("responseType", "text");
        messageMap.put("groupName", groupName);
        messageMap.put("textAreaNumber", Integer.toString(textAreaNumber));
        messageMap.put("text", text);
        messageMap.put("isLocked", "1");

        for(Session user : usersInGroup)
        {
          if(user != userSession)
          {
            user.getBasicRemote().sendText(buildJsonData(messageMap));
          }
        }
        break;
      /*case "deleteTextArea":
        break;*/
      /*case "addTextArea":
        break;*/
    }


    /* // Chatroom reference code
     String username = (String)userSession.getUserProperties().get("username");
     if(username == null) {
       userSession.getUserProperties().put("username", message);
       userSession.getBasicRemote().sendText(buildJsonData("System", "you are now connected as "+message));
     }else {
       Iterator<Session> iterator = chatroomUsers.iterator();
       while(iterator.hasNext()) {
         iterator.next().getBasicRemote().sendText(buildJsonData(username,message));
       }
     }*/
  }
  
  //close connection
  @OnClose
  public void handleClose(Session userSession) {
    /* // Chatroom reference code
    chatroomUsers.remove(userSession);
    System.out.println("client is now disconnected..");
    */
    gm.onClose(userSession);
  }
  
  private String buildJsonData(Map<String, String> map) {
    JsonObjectBuilder job = Json.createObjectBuilder();
    for(Map.Entry<String, String> entry : map.entrySet())
    {
      job.add(entry.getKey(), entry.getValue());
    }
    JsonObject json = job.build();
    StringWriter stringWriter = new StringWriter();
    try(JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
      jsonWriter.write(json);
    }
    return stringWriter.toString();
  }
}
