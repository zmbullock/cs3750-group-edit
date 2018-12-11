
/// Class used to communicate to the server from the client.
function WebsocketManager() 
{
  // ----------------
  // --- Public: ----
  // ----------------

  /// @param string groupName
  /// @param int textAreaNumber
  this.setGroup = function(groupName)
  {
    // Will send request here.
    websocket.send(JSON.Stringify({
      messageType: "setGroup",
      groupName: groupName
    }));
  }

  /// @param string groupName
  /// @param int textAreaNumber
  this.lockTextArea = function(groupName, textAreaNumber)
  {
    // Will send request here.
    websocket.send(JSON.Stringify({
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
    websocket.send(JSON.Stringify({
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
    websocket.send(JSON.Stringify({
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
    websocket.send(JSON.Stringify({
      messageType: "deleteTextArea",
      groupName: groupName,
      textAreaNumber: textAreaNumber
    }));
  }
  
  /// @param string groupName
  this.addTextArea = function(groupName)
  {
    // Will send update here.
    websocket.send(JSON.Stringify({
      messageType: "addTextArea",
      groupName: groupName
    }));
  }

  // Override these functions

  /// @param string groupName
  /// @param int textAreaNumber
  /// @param string text
  this.onReceiveText = function(groupName, textAreaNumber, text) {}

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
          that.onReceiveText(response.groupName, response.textAreaNumber, response.text);
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

