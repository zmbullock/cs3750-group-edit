//alert("Stop!");

"use strict";

/* When the user clicks on the button, 
toggle between hiding and showing the dropdown content */
function menuOptions() {
    document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {

    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}

/* function buttonClick() {
	toggle = !toggle;
	start++;
	var buttonStatus = start % 3;
	if (buttonStatus == 0) {
		document.getElementById("gameButton").style.backgroundColor = "#FF0000";
		document.getElementById("gameButton").style.color = "white";
		gameButton.innerHTML = "Start";
	}
	else if (buttonStatus == 1) {
		document.getElementById("gameButton").style.backgroundColor = "yellow";
		document.getElementById("gameButton").style.color = "black";
		gameButton.innerHTML = "waiting...";
}
	else {
		document.getElementById("gameButton").style.backgroundColor = "lightgray";
		document.getElementById("gameButton").style.color = "black";
		gameButton.innerHTML = "Quit";
	}		
} */