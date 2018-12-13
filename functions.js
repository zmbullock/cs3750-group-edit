//0 = select group
//1 = lock box
//2 = unlock box
//3 = sendText
var chosenGroup;
function test(option,textAreaNumber)
{
	var wsm = new WebsocketManager();
	
	//
	wsm.onReceiveUnlockTextArea = function(groupName, textAreaNumber){
		//How do i know if it's okay to unlock this box???
		document.getElementById(textAreaNumber).readOnly = false;
	}
	
	wsm.onReceiveLockTextArea = function(groupName, textAreaNumber) {
		//How do i know if it's okay to lock this box???
		document.getElementById(textAreaNumber).readOnly = true;
	}
	
	wsm.onReceiveText = function(groupName, textAreaNumber, text, isLocked) {
		if(isLocked == 0){
			var unlocked = "u"+textAreaNumber;
			document.getElementById(textAreaNumber).readOnly = false;	
			document.getElementById(unlocked).checked = true;
		}
		if(isLocked == 1){
			var locked = "l"+textAreaNumber;
			document.getElementById(textAreaNumber).readOnly = true;
			document.getElementById(locked).checked = true;
		}
		document.getElementById(textAreaNumber).value = text;
	}
	
	//Group Selected
	if(option == 0){
		chosenGroup = textAreaNumber;
		wsm.setGroup(chosenGroup);
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
		
		var text1 = document.getElementById('1').value;
		var text2 = document.getElementById('2').value;
		var text3 = document.getElementById('3').value;
		var text4 = document.getElementById('4').value;
		
		wsm.sendTextAreaText(chosenGroup, 1, text1);
		wsm.sendTextAreaText(chosenGroup, 2, text2);
		wsm.sendTextAreaText(chosenGroup, 3, text3);
		wsm.sendTextAreaText(chosenGroup, 4, text4);		
	}
}

