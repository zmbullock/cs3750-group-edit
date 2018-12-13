
/// Class used to communicate to the server from the client.
function WebsocketManager() 
{
  // ----------------
  // --- Public: ----
  // ----------------

  /// @param string groupName
  // wsm.setGroup("B");
  this.setGroup = function(groupName)
  {
    // Will send request here.
    websocket.send(JSON.stringify({
      messageType: "setGroup",
      groupName: groupName
    }));
  }

  /// @param string groupName
  /// @param int textAreaNumber
  this.lockTextArea = function(groupName, textAreaNumber)
  {
    // Will send request here.
    websocket.send(JSON.stringify({
      messageType: "lockTextArea",
      groupName: groupName,
      textAreaNumber: textAreaNumber
    }));
  }

  /// @param string groupName
  /// @param int textAreaNumber
  this.unlockTextArea = function(groupName, textAreaNumber)
  {
    // Will send request here.
    websocket.send(JSON.stringify({
      messageType: "unlockTextArea",
      groupName: groupName,
      textAreaNumber: textAreaNumber
    }));
  }

  /// @param string groupName
  /// @param int textAreaNumber
  /// @param string text
  this.sendTextAreaText = function(groupName, textAreaNumber, text)
  {
    // Will send update here.
    websocket.send(JSON.stringify({
      messageType: "sendTextAreaText",
      groupName: groupName,
      textAreaNumber: textAreaNumber,
      text: text
    }));
  }

  /// @param string groupName
  /// @param int textAreaNumber
  this.deleteTextArea = function(groupName, textAreaNumber)
  {
    // Will send update here.
    websocket.send(JSON.stringify({
      messageType: "deleteTextArea",
      groupName: groupName,
      textAreaNumber: textAreaNumber
    }));
  }
  
  /// @param string groupName
  this.addTextArea = function(groupName)
  {
    // Will send update here.
    websocket.send(JSON.stringify({
      messageType: "addTextArea",
      groupName: groupName
    }));
  }

  // Override these functions

  /// @param string groupName
  /// @param int textAreaNumber
  /// @param string text
  /// @param int (0 or 1) isLocked
  this.onReceiveText = function(groupName, textAreaNumber, text, isLocked) {}

  /// @param string groupName
  /// @param int textAreaNumber
  this.onReceiveDeleteTextArea = function(groupName, textAreaNumber) {}

  /// @param string groupName
  this.onReceiveAddTextArea = function(groupName) {}

  /// @param string groupName
  this.onReceiveAddGroup = function(groupName) {}

  /// @param string groupName
  /// @param int textAreaNumber
  this.onReceiveLockTextArea = function(groupName, textAreaNumber) {}

  /// @param string groupName
  /// @param int textAreaNumber
  this.onReceiveUnlockTextArea = function(groupName, textAreaNumber) {}
 
  // ----------------
  // --- Private: ---
  // ----------------
 
  /// Private accessor for the 'this' object.
  var that = this;  
  var websocket = new WebSocket("ws://34.218.225.144:8080/cs3750-group-edit/serverendpointdemo");

  /// @param  message
  function processMessage(message)
  {
    var response = JSON.parse(message.data);
    if(response.responseType != null)
    {
      switch(response.responseType)
      {
        case "text":
          that.onReceiveText(response.groupName, response.textAreaNumber, response.text, response.isLocked);
          break;
        case "delete":
          that.onReceiveDeleteTextArea(response.groupName, response.textAreaNumber);
          break;
        case "addTextArea":
          that.onReceiveAddTextArea(response.groupName);
          break;
        case "addGroup":
          that.onReceiveAddGroup(response.groupName);
          break;
        case "lock":
          that.onReceiveLockTextArea(response.groupName, response.textAreaNumber);
          break;
        case "unlock":
          that.onReceiveUnlockTextArea(response.groupName, response.textAreaNumber);
          break;
        default:
          break;
      }
    }
  }

  websocket.onmessage = processMessage(message)
}

function testWebSocketManager()
{
  WebSocketManager wsm = new WebSocketManager();

  wsm.onReceiveUnlockTextArea = function(groupName, textAreaNumber)
  {
    // What do I do with info from the server...
  }

  // groupName - String
  // textAreaNumber - int
  // text - String
  wsm.onReceiveText = function(groupName, textAreaNumber, text)
  {
    textAreaNumber.text = text;
    // What do I do with info from the server...
  }

  // To send a message to the server...
  // var groupName = document.getElementById("Dropdownmenu").getDropdownElemt...;
  // var textAreaNumber = 2;
  wsm.unlockTextArea(groupName, textAreaNumber);
}

/*
<html>
<script src="WebSocketManager.js"></script>
<script src="Your.js"></script>
</html>
*/
