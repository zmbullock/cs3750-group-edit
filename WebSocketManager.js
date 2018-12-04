
/// Class used to communicate to the server from the client.
function WebsocketManager() 
{
  // ----------------
  // --- Public: ----
  // ----------------

  /// @param string groupName
  /// @param int textAreaNumber
  this.lockTextArea = function(groupName, textAreaNumber)
  {
    // Will send request here.
    websocket.send("");
  }

  /// @param string groupName
  /// @param int textAreaNumber
  this.unlockTextArea = function(groupName, textAreaNumber)
  {
    // Will send request here.
    websocket.send("");
  }

  /// @param string groupName
  /// @param int textAreaNumber
  /// @param string text
  this.sendTextAreaText = function(groupName, textAreaNumber, text)
  {
    // Will send update here.
    websocket.send("");
  }

  // Override this function

  /// @param string text
  this.onReceiveText = function(text) {}

 
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
        case 0:
          that.onReceiveText(response.text);
          break;
        default:
          break;
      }
    }
  }

  websocket.onmessage = processMessage(message)
}

