package websocket;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

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

  //open connection to client
  @OnOpen
  public void handleOpen(Session userSession) {

  }
  
  //exchange message with client
  @OnMessage
  public void handleMessage(String message, Session userSession) throws IOException{
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
  
  private String buildJsonData(String key, String value) {
     JsonObject jsonObject = Json.createObjectBuilder().add(key, value).build();
     StringWriter stringWriter = new StringWriter();
     try(JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
       jsonWriter.write(jsonObject);
     }
    return stringWriter.toString();
  }
}
