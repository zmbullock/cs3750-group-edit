var wsm = new WebsocketManager();
var chosenGroup = "A";
//0 = select group
//1 = lock box
//2 = unlock box
//3 = sendText
	wsm.onReceiveUnlockTextArea = function(groupName, textAreaNumber){
		//How do i know if it's okay to unlock this box???
	        console.log("groupName: " + groupName);
	        console.log("textAreaNumber: " + textAreaNumber);
		document.getElementById(textAreaNumber).readOnly = false;
                document.getElementById("u" + textAreaNumber).checked = true;
                document.getElementById("l" + textAreaNumber).disabled = false;
                document.getElementById("u" + textAreaNumber).disabled = false;
	}
	
	wsm.onReceiveLockTextArea = function(groupName, textAreaNumber) {
		//How do i know if it's okay to lock this box???
	        console.log("groupName: " + groupName);
	        console.log("textAreaNumber: " + textAreaNumber);
		document.getElementById(textAreaNumber).readOnly = true;
                document.getElementById("l" + textAreaNumber).checked = true;
                document.getElementById("l" + textAreaNumber).disabled = true;
                document.getElementById("u" + textAreaNumber).disabled = true;
	}
	
	wsm.onReceiveText = function(groupName, textAreaNumber, text, isLocked) {
                console.log("RECEIVED TEXT");
	        console.log("groupName: " + groupName);
	        console.log("textAreaNumber: " + textAreaNumber);
                console.log(text);
		if(isLocked == 0){
			var unlocked = "u"+textAreaNumber;
			document.getElementById(textAreaNumber).readOnly = false;	
			document.getElementById(unlocked).checked = true;
                        document.getElementById("l" + textAreaNumber).disabled = false;
                        document.getElementById("u" + textAreaNumber).disabled = false;
		}
		if(isLocked == 1){
			var locked = "l"+textAreaNumber;
			document.getElementById(textAreaNumber).readOnly = true;
			document.getElementById(locked).checked = true;
                        document.getElementById("l" + textAreaNumber).disabled = true;
                        document.getElementById("u" + textAreaNumber).disabled = true;
		}
		document.getElementById(textAreaNumber).value = text;
	}
function test(option,textAreaNumber)
{
	
	//
	console.log("option: " + option);
	console.log("textAreaNumber: " + textAreaNumber);
        switch (option)
        {
          case 0:
            chosenGroup = textAreaNumber;
            wsm.setGroup(chosenGroup);
            break;
          case 1:
	    wsm.lockTextArea(chosenGroup, textAreaNumber);
            break;
          case 2:
	    wsm.unlockTextArea(chosenGroup, textAreaNumber);
            break;
          case 3:
            var text = document.getElementById(textAreaNumber).value;
            wsm.sendTextAreaText(chosenGroup, textAreaNumber, text);
            break;
        }
}
	//Group Selected
	/*
	if(option == 0){
		chosenGroup = textAreaNumber;
                console.log(chosenGroup);
                console.log(typeof chosenGroup);
                if(chosenGroup != "")
                {
		  wsm.setGroup(chosenGroup);
                }
	}
	//User requests to lock box
	if(option == 1){
		wsm.lockTextArea(chosenGroup, textAreaNumber);
	}
	//User requests to unlock box
	if(option == 2){
		wsm.unlockTextArea(chosenGroup, textAreaNumber);
	}
	
	//send text
	if(option == 3){
          var text = document.getElementById(textAreaNumber).value;
          wsm.sendTextAreaText(chosenGroup, textAreaNumber, text);
		
//	/*	var text1 = document.getElementById('1').value;
//		var text2 = document.getElementById('2').value;
//		var text3 = document.getElementById('3').value;
//		var text4 = document.getElementById('4').value;
//
//                console.log(text1);
//                console.log(text2);
//                console.log(text3);
//                console.log(text4);
//		
//		wsm.sendTextAreaText(chosenGroup, 1, text1);
//		wsm.sendTextAreaText(chosenGroup, 2, text2);
//		wsm.sendTextAreaText(chosenGroup, 3, text3);
//		wsm.sendTextAreaText(chosenGroup, 4, text4);		
*/

