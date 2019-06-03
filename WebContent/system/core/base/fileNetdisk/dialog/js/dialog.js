var jy = function(id) {return document.getElementById(id);};
function ShowDialog(id,vTopOffset){
  if(typeof arguments[1] == "undefined"){
    vTopOffset = 10;
  }
  var bb=(document.compatMode && document.compatMode!="BackCompat") ? document.documentElement : document.body;
  jy("overlay").style.width = Math.max(parseInt(bb.scrollWidth),parseInt(bb.offsetWidth))+"px";
  jy("overlay").style.height = Math.max(parseInt(bb.scrollHeight),parseInt(bb.offsetHeight))+"px";

  jy("overlay").style.display = "block";
  jy(id).style.display = 'block';

  jy(id).style.left = ((bb.offsetWidth - jy(id).offsetWidth)/2)+"px";
  jy(id).style.top  = (vTopOffset + bb.scrollTop)+"px";
}

function HideDialog(id){
  jy("overlay").style.display = 'none';
  jy(id).style.display = 'none';
}


function doInitFunc(){
  ShowDialog('detail');
  getItemsFunc();
}





/*第一种形式 第二种形式 更换显示样式*/
function setTab(name,cursel,n){
   if(cursel== 1){
    document.getElementById("userPriv").style.display = "block";
    document.getElementById("deptPriv").style.display  = "none"
    document.getElementById("rolePriv").style.display  = "none"
  }else if(cursel== 2){
    document.getElementById("userPriv").style.display = "none";
    document.getElementById("deptPriv").style.display  = "block"
    document.getElementById("rolePriv").style.display  = "none"
  }else if(cursel== 3){
    document.getElementById("userPriv").style.display = "none";
    document.getElementById("deptPriv").style.display  = "none"
    document.getElementById("rolePriv").style.display  = "block"
  }
  for(i=1;i<=n;i++){
    var menu=document.getElementById(name+i);
    var con=document.getElementById("con_"+name+"_"+i);
    menu.className=i==cursel?"hover":"";
    con.style.display=i==cursel?"block":"none";
  }
}
