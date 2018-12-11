package websocket;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonReader;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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

  //open connection to client
  @OnOpen
  public void handleOpen(Session userSession) {

  }
  
  //exchange message with client
  @OnMessage
  public void handleMessage(String message, Session userSession) throws IOException{
    JsonObject json = Json.createReader(new StringReader(message)).readObject();
    switch(json.getString("messageType"))
    {
      case "setGroup":
        gm.setGroup(userSession, json.getString("groupName");
        break;
      case "lockTextArea":
        int textAreaNumber = json.getInt("textAreaNumber");
        String groupName = json.getString("groupName");

        gm.lockTextArea(userSession, textAreaNumber);
        List<Session> users = gm.getUsersFromGroup(groupName);
        Map<String, String> messageMap = new HashMap<>();

        messageMap.put("responseType", "lock");
        messageMap.put("groupName", groupName);
        messageMap.put("textAreaNumber", textAreaNumber);

        for(Session user : users)
        {
          if(user != userSession)
          {
            user.getBasicRemote().sendText(buildJsonData(messageMap));
          }
        }
        break;
      case "unlockTextArea":
        break;
      case "sendTextAreaText":
        break;
      case "deleteTextArea":
        break;
      case "addTextArea":
        break;
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
  }
  
  private String buildJsonData(Map<String, String> map) {
    JsonObject jsonObject = new JsonObject();
    for(Map.Entry<String, String> entry : map.entrySet())
    {
      
    }
    JsonObject jsonObject = Json.createObjectBuilder().add(key, value).build();
    StringWriter stringWriter = new StringWriter();
    try(JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
      jsonWriter.write(jsonObject);
    }
    return stringWriter.toString();
  }
  private String buildJsonData(String key, String value) {
     JsonObject jsonObject = Json.createObjectBuilder().add(key, value).build();
     StringWriter stringWriter = new StringWriter();
     try(JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
       jsonWriter.write(jsonObject);
     }
    return stringWriter.toString();
  }
}
