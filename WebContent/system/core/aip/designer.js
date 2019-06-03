var argObj;
var isOpenPropWindow = false;//判断是否已打开窗口
var printModulFields = new Array();
// 用户登录，并把模式改为设计模式
function userLoginDesignerAip()
{
  var obj = $("#TeeHWPostil")[0];
  var vRet = obj.Login( _userName , 5, 32767, "", "");
  if(0 == vRet) {
    obj.InDesignMode = true;
  }
}

//用户登录，不设置编辑模式
function userLoginAip()
{
  var obj = $("#TeeHWPostil")[0];
  var vRet = obj.Login( _userName , 5, 32767, "", "");
}
/**
 * 
 *InDesignMode = true 的情况下
    1 鼠标在文档上按住左键
    2.移动鼠标，此时会在页面上画出一个矩形的线框
    3.松开鼠标左键，此时会触发NotifyLineAction事件
 * @param lPage
 * @param lStartPos
 * @param lEndPos
 * @return
 */
function NotifyLineAction(lPage,lStartPos,lEndPos)
{
  if (!isOpenPropWindow ) {
    var lStartY = (lStartPos>>16)& 0x0000ffff;
    var lStartX = ((lStartPos<<16)>>16) & 0x0000ffff; 

    var lEndY = (lEndPos>>16)& 0x0000ffff;
    var lEndX = ((lEndPos<<16)>>16) & 0x0000ffff; 
    // alert(lStartX);alert(lStartY);
    //openWin();
   argObj = {"page":lPage,"StartX":lStartX,"StartY":lStartY,"EndX":lEndX,"EndY":lEndY};
   openNoteDialog();
  }
}
/**
 * 打开设置节点窗口界面
 * @returns
 */
function openNoteDialog()
{
	var url = contextPath + '/system/core/workflow/workmanage/flowprinttpl/settingNote.jsp?flowTypeId=' + flowTypeId + "&sid=" + sid;
	//dialogChangesize(url, 400, 300);
	  var width = 350;
	  var height = 320;
	// var h = document.body.clientHeight  ;
	 // var w = document.body.clientWidth  ;
	  var locX = (screen.width - width) / 2 + 80;
	  var locY = (screen.height - height) / 2 + 20;
	  var attrs = null;
	  
	  attrs = "status:no;directories:yes;scroll:yes;Resizable=yes;";
	  attrs += "dialogWidth:" + width + "px;";
	  attrs += "dialogHeight:" + height + "px;";
	  attrs += "dialogLeft:" + locX + "px;";
	  attrs += "dialogTop:" + locY + "px;";
	  isOpenPropWindow = true;
	  return window.showModalDialog(url, self, attrs);
}


/***
 * 新建节点信息并保存到数据中
 * field_type : 字段类型 1-表单 2-文本 3 - 手写签章
 * field_name : 字段名称
 * border_style: 边框类型
 * font_famil :字体
 * font_color : 字体颜色
 * font_sizefont_size:字体大小
 * halign : 水平对齐方式
 * valign :垂直对齐方式
 */

function setFieldInfo(field_type,field_name,border_style,font_family,font_color
    ,font_size,halign ,valign  ){
  if(argObj == null){return;}
  var obj = $("#TeeHWPostil")[0];
  var field_width = argObj.EndX - argObj.StartX;
  var field_height = argObj.EndY - argObj.StartY; 
  var CurrPage = 0;
  CurrPage = argObj.page;//鼠标点击的当前页数a
  //var fieldNameDb = "page" + CurrPage + ".field-" + munber ;
  var fieldName =  field_name  ;
  var munber = 0;
  if(field_type == 2){
	  field_type = 2;
  }else{
	  field_type = 3;
  }
  for(var j = 0 ; ;j++){// 如果有的话就一直循环到没有的，避免名称重复
      var vRet = obj.InsertNote(fieldName,CurrPage,field_type,argObj.StartX,argObj.StartY,field_width,field_height);
      if(vRet){//新增成功
        	break;
       }
      if(j > 0){//如果存在
    	  munber = j;
          fieldName =   field_name + "_"+ munber;  
      }
     
   }
  
    font_size = font_size.replace(/pt/ig,"");
    font_color = font_color.replace(/#/ig,"0x00");
    obj.SetValue(fieldName,":PROP:BORDER:"+border_style);//边框
    obj.SetValue(fieldName,":PROP:FACENAME:"+font_family);//字体
    obj.SetValue(fieldName,":PROP:FONTSIZE:"+font_size);//字体大小
    if(font_color == ''){
        obj.SetValue(fieldName,":PROP:FRONTCOLOR:0x00000000");//字体颜色
    }
    if(field_type == 2){//手写区域
    	obj.SetValue(fieldName, ":PROP::LABEL:1");//可点击
    	obj.SetValue(fieldName,":PROP::CLICKPOP:1");
    }else{
    	obj.SetValue(fieldName, ":PROP::LABEL:3");//可点击
    }
 
    obj.SetValue(fieldName,":PROP:HALIGN:"+halign);//水平对齐方式
    obj.SetValue(fieldName,":PROP:VALIGN:"+valign);//纵向对齐方式
    
    var feildInfo = {
    		fieldType:field_type,
    		fieldName:field_name,
    		noteName : fieldName,
    		border:border_style,
    		fieldFont:font_family,
    		fieldFontColor:font_color,
    	    fontSize:font_size,
    	    fieldHalign:halign,
    	    fieldValign:valign
    };
    if(!printModulFields){
    	printModulFields = new Array();
    }
    printModulFields.push(feildInfo);
}

/**
 * 增加节点
 * @param obj  AIP对象
 * @param noteInto 节点对象
 */
function insertNote(noteInfo){
	 var obj = $("#TeeHWPostil")[0];
	 var fieldType = noteInfo.fieldType;
	 var fieldName = noteInfo.fieldName;
	 var noteName = noteInfo.noteName;
	 var fieldPage = noteInfo.fieldPage;
	 var border = noteInfo.border;
	 var fieldXa = noteInfo.fieldXa;
	 var fieldYa = noteInfo.fieldYa;
	 var fieldWidth = noteInfo.fieldWidth;
	 var fieldHeight = noteInfo.fieldHeight;
	 var fieldFont = noteInfo.fieldFont;
	 var fieldSize = noteInfo.fieldSize;
	 var fieldValign = noteInfo.fieldValign;
	 var fieldHalign = noteInfo.fieldHalign;
	 //var isAutoShrink = noteInfo.isAutoShrink;
	 fieldPage = fieldPage -1;
	 if(fieldType == 2){
		 fieldType = 2;
	  }else{
		  fieldType = 3;
	  }
	 var vRet = obj.InsertNote(noteName,0,fieldType,fieldXa,fieldYa,fieldWidth,fieldHeight);
	// font_size = fieldSize.replace(/pt/ig,"");
	/// font_color = font_color.replace(/#/ig,"0x00");
	 obj.SetValue(noteName,":PROP:BORDER:"+border);//边框
	 obj.SetValue(noteName,":PROP:FACENAME:"+fieldFont);//字体
	 obj.SetValue(noteName,":PROP:FONTSIZE:"+fieldSize);//字体大小
	// if(font_color == ''){
	      obj.SetValue(noteName,":PROP:FRONTCOLOR:0x00000000");//字体颜色
	// }
	 if(fieldType == 2){//手写区域
	      obj.SetValue(fieldName, ":PROP::LABEL:1");//可点击
	      obj.SetValue(fieldName,":PROP::CLICKPOP:1");
	  }else{
	      obj.SetValue(fieldName, ":PROP::LABEL:3");//可点击
	  }
	  obj.SetValue(noteName,":PROP:HALIGN:"+fieldHalign);//水平对齐方式
	  obj.SetValue(noteName,":PROP:VALIGN:"+fieldValign);//纵向对齐方式
	  
	 return vRet;
}

/*
 * 判断有没有相同的节点
 */

function CheckNote(obj,fieldName){
  var NoteInfo = "";
  while(NoteInfo=obj.GetNextNote(_userName,0,NoteInfo)){
    if(NoteInfo.split(".")[1] == fieldName){
      return false;
    }
     
  } ; 
  return true;
}






/**
 * 获取所有AIP节点
 */
function getAllAipNote()
{//可以两种方法实现 ：循环文件中的多有节点
	var newPrintModulNote = new Array();
	var obj = $("#TeeHWPostil")[0];
	var NoteInfo = "";
	while(NoteInfo=obj.GetNextNote(_userName,0,NoteInfo))
	{
		var noteInfoArray =  NoteInfo.split(".");
		var noteName =noteInfoArray[1];
		var newFiledPage = noteInfoArray[0].substring(4,noteInfoArray[0].length); 
		var field = getPrintModelField(noteName , printModulFields);//获取能匹配的节点信息
		if(field){
			var fieldHeight = obj.GetNoteHeight(NoteInfo);//高度
			var fieldWidth = obj.GetNoteWidth(NoteInfo);//苦熬度
			var fieldXa = obj.GetNotePosX(NoteInfo);//X轴
			var fieldYa = obj.GetNotePosY(NoteInfo);//Y轴
			field.fieldPage = newFiledPage;
			field.fieldXa = fieldXa;
			field.fieldYa = fieldYa;
			field.fieldHeight = fieldHeight;
			field.fieldWidth = fieldWidth;
			newPrintModulNote.push(field);
		}
  
	} ; 
	return newPrintModulNote;
}



/**
 * 取得从结点数组里取得结点对象
 * 
 * @param field
 * @return
 */
function getPrintModelField(noteName , printModulFields) {
  for (var i = 0 ;i < printModulFields.length;i++) {
    var field = printModulFields[i];
    var fieldNote = field.noteName;
    if (fieldNote == noteName) {
      return field;
    }
  }
  return null;
}













//保存文件
function saveFile()
{
var obj = $("#TeeHWPostil")[0];
if($("T_NAME").value=="")
{
 alert("请输入模板名称！");
 return;
}

var content = obj.GetCurrFileBase64();
// 保存文件到服务器
}

/*
* 节点批量设置更新结束
*/
/**
* aip操作函数 设置JSEvn = 1时起作用
* 
* @param lActionType
* @param lType
* @param strName
* @param strValue
* @return
*/
function JSNotifyBeforeAction(lActionType,lType,strName,strValue) {
// 单击结点
if(7==lActionType){
 selectedNote = strName;
 selectNote();
 var div = $(strName + "-div");
 if(div){
   selecteDiv(div);
 
 }
 setNoteSelect(strName);
}
}

/**
 * aip操作函数设置JSEvn = 0时起作用
 * 
 * @param lActionType
 * @param lType
 * @param strName
 * @param strValue
 * @return
 */
function NotifyBeforeAction(lActionType,lType,strName,strValue,plContinue) {
  var obj = $("#TeeHWPostil")[0];
  //打印
  if (1 == lActionType) {
    //alert("dsss");
  }

  // 单击结点
  if(7==lActionType){
 
    selectedNote = strName;
    selectNote();
    var div = $(strName + "-div");
    if(div){
      selecteDiv(div);
     
    }
    setNoteSelect(strName);
  }
}
/**
 * aip操作函数设置 是触发事件后执行
 * 
 * @param lActionType
 * @param lType
 * @param strName
 * @param strValue
 * @return
 */
function NotifyAfterAction(lActionType,lType,strName,strValue,plContinue) {
  var obj = $("#TeeHWPostil")[0];
  if (3 == lActionType) {
	  alert("ddd")
   // delNote(strName,0);
  }
}




/**
 * 拖动的时候出发事件
 * 通过aip里的位置，宽度和高度设置属性面板
 * 
 * @param pcNoteName
 * @param prex
 * @return
 */
function setPosValue(pcNoteName , prex) {
  var obj = $('HWPostil1');
 // if (pcNoteName ==  prex + selectedNote) {
    var fieldHeight = obj.GetNoteHeight(pcNoteName);
    $('fieldHeight').value = fieldHeight;
    var fieldWidth = obj.GetNoteWidth(pcNoteName);
    $('fieldWidth').value = fieldWidth;
    var fieldXa = obj.GetNotePosX(pcNoteName);
    $('fieldXa').value = fieldXa;
    var fieldYa = obj.GetNotePosY(pcNoteName);
    $('fieldYa').value = fieldYa;
    if (pcNoteName !=  prex + selectedNote) {//如果移动的不的被选中的节点，则更改被选中的节点为拖动的节点
      selectedNote = pcNoteName.split(".")[1];
      selectNote();
      var div = $(selectedNote + "-div");
      if(div){
        selecteDiv(div);
       
      }
      setNoteSelect(selectedNote);
    }
}

/*
 * 
 *添加一页
 *
 */
function addMergeFileJs(){
  var obj = $('HWPostil1');
  obj.MergeFile(99,"");
}

/**
 * 打印 
 * @param type 1-套打 0-正常打印 2-不打印节点
 */
function AIPPrint(type){
    var obj = $("#TeeHWPostil")[0];
    alert(type)
    if(type=='1'){
    	obj.ForceSignType2 = 1;//设置套打
    	obj.PrintDoc(1,1); 
    }else if(type=='2') {
    	obj.PrintDoc(0,1);//设置不打印节点
    }else if(type == '0'){
    	obj.ForceSignType2 = 0;
    	obj.PrintDoc(1,1); 
    }
}