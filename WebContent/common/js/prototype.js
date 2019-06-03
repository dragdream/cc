  String.prototype.toString=function(){
		return this+'';
	}
	Number.prototype.toString=function(){
		return this+'';
	}

	Array.prototype.exist=function(e){
		for(var i=0;i<this.length;i++){
			if(this[i].toString()==e.toString()){
				return true;
			}
		}
		return false;
	}

	Array.prototype.toString=function(){
		var str = '';
		for(var i=0;i<this.length;i++){
			str+=this[i];
			if(i!=this.length-1){
				str+=',';
			}
		}
	}