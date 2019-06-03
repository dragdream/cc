(function(){
	if(window.chrome){
		var cls = document.querySelector("body").className;
		document.querySelector("body").className = (cls?cls + " ":"") + "chrome";
	}
})();
