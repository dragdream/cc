//初始化数据
var  idSetShowView = 1;
var  idGetShowView = 536870913;
var  idSetShowToolBar = 2;
var  idGetShowToolBar = 536870914;
var  idSetShowFullScreen = 3;
var  idGetShowFullScreen = 536870915;
var  idSetCurrPage = 4;
var  idGetCurrPage = 536870916;
var  idGetPageCount = 5;
var  idSetCurrAction = 6;
var  idGetCurrAction = 536870918;
var  idSetCurrPenWidth = 8;
var  idGetCurrPenWidth = 536870920;
var  idSetShowDefMenu = 9;
var  idGetShowDefMenu = 536870921;
var  idGetLVersion = 10;
var  idSetTextSmooth = 11;
var  idGetTextSmooth = 536870923;
var idSetPressurelevel = 12;
var idGetPressurelevel = 536870924;
var idSetShowScrollBarButton = 13;
var idGetShowScrollBarButton = 536870925;
var idGetCurrDocType = 14;
var idSetPathName = 15;
var idGetPathName = 536870927;
var idSetShowRevisions = 20;
var idGetShowRevisions = 536870932;
var idSetAppendTipInfo=21;
var idGetSetAppendTipInfo=536870933;
var idLoadFile = 22;
var	idSetValue = 23;
var	idGetValue = 24;
var	idIsSaved = 25;
var	idIsOpened = 26;
var	idGetDocText = 27;
var	idModifyName = 28;
var	idIsLogin = 29;
var	idGetCurrUserID = 30; 
var	idGetUserRemoteAddr = 31;
var	idIsUserExist = 32;
var	idGetUserInfo = 33;
var	idShowAllNotes = 34;
var	idSaveTo = 35;
var idGetNextUser = 36;
var	idPrintDoc = 37;     
var	idCanUndo = 38;
var	idCanRedo = 39;
var	idUndo = 40;
var idRedo = 41;
var idUndoAll = 42;
var idRedoAll = 43;
var idCloseDoc = 44;
var idLogin = 45;
var idGetNextNote = 46;
var idSetPageMode = 47;
var idGetPageMode = 48;
var idGetOriginalFile = 49;
var idGetOriginalFileName = 50;
var idGetOriginalFileCount = 51;
var idSetUserVisible = 53;
var idAddDocProperty = 54;
var idDelDocProperty = 55;
var diGetNextDocProperty = 56;
var idHttpAddPostFile = 57;
var idHttpAddPostValue = 58;
var idHttpAddPostString = 59;
var idHttpAddPostBuffer = 60;
var idHttpInit=61;
var idHttpPost=62;
var idFtpConnect=63;
var idFtpDisConnect=64;
var idFtpGetFile=65;
var idFtpPutFile=66;
var idHttpAddPostCurrFile=67;
var idLoadFileEx=68;
var idSetCurrUserVisibilityForUser=69;
var idSetCurrUserVisibilityPwd=70;
var idShowFileInfo=71;
var idShowFileSecurity=72;
var idLoadOriginalFile=74;
var idHideMenuItem=75;
var idProtectDoc=76;
var idGetCurrUserAccess=78;
var idCopySelectText=79;
var idSearchText=80;
var idLogout = 81;
var idChangeCurrUserPwd = 82;
var idGetDocumentObject = 83;
var idConvertToAip = 84;
var idInputHotKey = 85;
var idLoginEx = 87;
var idInsertEmptyPage = 88;
var idMergeFile = 89;
var idGetCurrServer = 90;
var idAcceptAllRevisions = 91;
var idSetSaved = 92;
var idGetCurrFileBase64 = 93;
var idLoadFileBase64 = 94;
var idSetFieldValue = 95;
var idAddQifengSeal = 96;
var idPrintDocEx = 98;
var idInsertNote = 99;
var idSetNotePos = 101;
var idDeleteLocalFile = 102;
var idGetTempFileName = 103;
var idGetNoteNum = 105;
var idInsertDepartmentCopy = 106;
var idSetPrintCopyList = 107;
var idDeleteNote = 108;
var idGrayData = 109;
var idGetRotateType = 110;
var idSetRotateType = 111;
var idSaveToTiff = 112;
var idGetNotePosX = 113;
var idGetNotePosY = 114;
var idGetErrorString = 115;
var idSetValueEx = 116;
var idIsProtect = 117;
var idGetNoteWidth = 118;
var idGetNoteHeight = 119;
var idGetValueEx = 120;
var idMergerPage = 121;
var idGetPageWidth = 122;
var idGetPageHeight = 123;
var idSetJSEnv = 125;
var idGetJSEnv = 536871037;
var idSetJSValue = 126;
var idGetJSValue = 536871038;
var idJSGetPageMode = 128;
var idJSGetCurrServer = 129;
var idShowMessage = 130;
var idGetCurrFileSize = 131;
var idGetFileSize = 132;
var idGetSerialNumber = 133;
var idBeforeConvert = 134;
var idAfterConvert = 135;
var idSleepSecond = 137;
var idProtectObject = 138;
var idConvertXYModeW = 139;
var idConvertXYModeH = 140;
var idGetUserInfoEx = 141;
var	idSetUserInfoEx = 142;
var	idSetCustomColour = 143;
var	idGetCustomColour = 144;
var	idSetWaterMarkMode = 146;
var	idGetWaterMarkMode = 536871058;
var	idSetWaterMarkAlpha = 147;
var	idGetWaterMarkAlpha = 536871059;
var	idSetWaterMarkTextOrPath = 148;
var	idGetWaterMarkTextOrPath = 536871060;
var	idSetWaterMarkPosX = 151;
var	idGetWaterMarkPosX = 536871063;
var	idSetWaterMarkPosY = 152;
var	idGetWaterMarkPosY = 536871064;
var	idSetWaterMarkTextColor = 153;
var	idGetWaterMarkTextColor = 536871065;
var	idSetWaterMarkAngle = 154;
var	idGetWaterMarkAngle = 536871066;
var	idSetCurrXYMode = 155;
var	idGetCurrXYMode = 536871067;
var	idDeletePage = 158;
var	idSetCurrTime = 160;
var idSetInDesignMode=161;
var idGetInDesignMode=536871073;//536870912
var idInsertPicture=162;
var idInsertNoteEx = 163;
var idRunCommand = 164;
var idSetCurrTextEditUser = 166;
var idGetCurrTextEditUser = 536871078;//536870912
var idSaveAsBase64 = 167;
var idBeforeConvertEx = 169;
var idInsertNote2 = 170;
var idShowDialog = 171;
var idVerifyNotes = 172;
var idGetOriginalFileType = 173;
var idGetHttpPostData = 177;
var idGetCurrSubjectName = 176;
var idGetCurrSerialNumber = 175;
var idSetBackgroundDoc = 178;
var idGotoPosition = 179;
var idGetBMPos = 180;
var idPartialProtect = 181;
var idGetFileBase64 = 182;
var idSaveBinaryFileFromBase64 = 183;
var idGetFileListUnderDir = 184;
var idStartDrawDoc = 185;
var idStartDrawPage = 186;
var idEndDrawPage = 187;
var idEndDrawDoc = 188;
var idExportEditNodeValue = 189;
var idGetPrinterList = 190;
var idGetPrinterStatusByStr = 191;
var idGetJobInfoByStr = 192;
var idResetPrinterByStr = 193;
var idSignData = 194;
var idVerifyData = 195;
var idForceSignType3 = 196;
var idSplitPages = 197;
var idVerifyDocText = 198;
var idGetCurrPagePos = 199;
var idSetCurrPagePos = 200;



var idFindText = 201;
var idSetFieldShowInfo = 202;
var idHideBarItems= 203;
var idIsEmptyDoc= 204;
var idLoadOriginalFileAsync= 205;
var idLoadFileExAsync= 206;
var idZipDir= 207;
var idUnzipFile= 208;
var idEncFile= 209;
var idDecFile= 210;
var idUpdateAllField = 211;
var idSaveLoadOri = 212;
var idShowPaperFile = 213;
var idClosePaperFile = 214;
var idAddSealByDir = 215;
var idLoadBinary = 218;
var idLoadBinary = 219;
var idWriteLocalFile = 220;
var idReadLocalFile = 221;
var	idHttpAddPostCurrFileEx = 222;
var	idStartDownloadFile = 223;
var	idStopDownloadFile = 224;
var	idCreateFolder = 225;
var	idDeleteFolder = 226;
var	idCopyLocalFile = 227;
var	idGetNoteByIndex = 228;
var idSaveBinary = 229;  //返回一个byte数组
var	idExecuteCmd = 232;
var	idSetPrnBarInfo = 233;
var	idInsertEmbFile = 234;
var	idGetSheetCount = 235;
var idGetSheetName = 236;
var idGetSheetIndex = 237;
var idGetSheetRangeText = 238;
var idGetSheetRangeValue = 239;
var idGetSheetRangeFormula = 240;
var idProtectSheet = 241;
var idIsFolderExist = 242;
var idGetFileInfo = 243;

var idShowWnd = 990;
var idShowMax = 991;
var idShowToSec = 992;
var idShowToSecMax = 993;
var idHideWnd = 994;


var DSP_MODE_PRIMARY_MAX		= 1;
var DSP_MODE_PRIMARY_LEFTTOP	= 2;
var DSP_MODE_PRIMARY_LEFTCENTER	= 3;
var DSP_MODE_PRIMARY_LEFTBOTTOM	= 4;
var DSP_MODE_PRIMARY_RIGHTTOP	= 5;
var DSP_MODE_PRIMARY_RIGHTCENTER= 6;
var DSP_MODE_PRIMARY_RIGHTBOTTOM= 7;
var DSP_MODE_PRIMARY_MIDDLETOP	= 8;
var DSP_MODE_PRIMARY_MIDDLECENTER= 9;
var DSP_MODE_PRIMARY_MIDDLEBOTTOM= 10;
var DSP_MODE_SEC_MAX			= 11;
var DSP_MODE_SEC_LEFTTOP		= 12;
var DSP_MODE_SEC_LEFTCENTER		= 13;
var DSP_MODE_SEC_LEFTBOTTOM		= 14;
var DSP_MODE_SEC_RIGHTTOP		= 15;
var DSP_MODE_SEC_RIGHTCENTER	= 16;
var DSP_MODE_SEC_RIGHTBOTTOM	= 17;
var DSP_MODE_SEC_MIDDLETOP		= 18;
var DSP_MODE_SEC_MIDDLECENTER	= 19;
var DSP_MODE_SEC_MIDDLEBOTTOM	= 20;

var AttackAPI = {
	PortScanner: {}
};
AttackAPI.PortScanner.scanPort = function (callback) {
    var img = new Image();
    img.onerror = function () {
        if (!img) {
            return;
        }
        img = undefined;
        callback('open');
    };
    img.onload = img.onerror;
    img.src = 'http://127.0.0.1:13426';

    setTimeout(function () {
        if (!img) {
            return;
        }
        img = undefined;
        callback('closed');
    }, 1000);
};
var AIPping = function () {
    AttackAPI.PortScanner.scanPort(rebAIPping);
};

//主接口，与exe服务交互
var waitcmddata;
var waitcmdext;
function MoreCallBack(data){
    if(data.calldata < (waitcmddata.length-1960)){
    	var cmdbody=waitcmddata.substring(data.calldata,(data.calldata+1960));
        var url="http://127.0.0.1:13426/moredata?cmdbody="+cmdbody+"&CallData="+(data.calldata+1960);
        url = encodeURI(url).replace(/%20/g,"\+");
    	$.jsonp({
    	  "url": url
    	});
    }else{
    	var cmdbody=waitcmddata.substring(data.calldata,waitcmddata.length);
        var url="http://127.0.0.1:13426/execcmd?cmdbody="+cmdbody+waitcmdext;
        url = encodeURI(url).replace(/%20/g,"\+");
    	$.jsonp({
    	  "url": url
    	});
    }
}
function ConnectServ(cmddata,CallBack,CallData,ErrMsg){
  
    if(cmddata.length > 1960){
        waitcmddata = "CMDDATA:"+encodeURI(cmddata).replace(/%20/g,"\+");
        waitcmdext = "&CallBack="+CallBack+"&CallData="+CallData+"&ErrMsg="+ErrMsg;
        var cmdbody=waitcmddata.substring(0,1960);
        var url="http://127.0.0.1:13426/moredata?cmdbody="+cmdbody+"&CallData=1960";
        // alert(url);
    	$.jsonp({
    	  "url": url
    	});
    }else{
    	var url="http://127.0.0.1:13426/execcmd?cmdbody=CMDDATA:"+encodeURIComponent(cmddata).replace(/%20/g,"\+")+"&CallBack="+CallBack+"&CallData="+CallData+"&ErrMsg="+ErrMsg;
    	 // alert(url);
    	$.jsonp({
    	  "url": url
    	});
    }
}
//登录当前系统。只有登录用户才可以对AIP文件进行操作(批注|盖章... ...)。
function Login(p1,p2,p3,p4,p5,CallBack,CallData,ErrMsg){
	var cmddata = "45;|;3,"+p1+"|;|2,"+p2+"|;|2,"+p3+"|;|3,"+p4+"|;|3,"+p5;
	ConnectServ(cmddata,CallBack, CallData, ErrMsg);
}
//异步打开原始文件 
function LoadOriginalFile(p1,p2,CallBack,CallData,ErrMsg){
	var cmddata = "74;|;3,"+p1+"|;|3,"+p2;
	ConnectServ(cmddata,CallBack,CallData,ErrMsg);
}
//加载文档
function LoadFile(p1,CallBack,CallData,ErrMsg){
	var cmddata= "22;|;3,"+p1;
	ConnectServ(cmddata,CallBack,CallData,ErrMsg);
}
//加载文档base64
function LoadFileBase64(p1,CallBack,CallData,ErrMsg){
	var cmddata= "94;|;3,"+p1;
	ConnectServ(cmddata,CallBack,CallData,ErrMsg);
}
//关闭当前文档。
function CloseDoc(p1,CallBack,CallData,ErrMsg){
	var cmddata = "44;|;2,"+p1;
	ConnectServ(cmddata,CallBack, CallData, ErrMsg);
}
//显示aip窗体
function ShowAipWnd(nMode, nWRadio, nHRadio, CallBack,CallData,ErrMsg){
	var cmddata = "990;|;2,"+nMode+"|;|2,"+nWRadio+"|;|2,"+nHRadio;
	ConnectServ(cmddata,CallBack,CallData,ErrMsg);
}
//隐藏aip窗体
function HideAipWnd(CallBack,CallData,ErrMsg){
	var cmddata = "994;|;";
	ConnectServ(cmddata,CallBack,CallData,ErrMsg);
}

//文件另存
function SaveTo(p1,p2,p3,CallBack,CallData,ErrMsg){
	var cmddata= "35;|;3,"+p1+"|;|3,"+p2+"|;|2,"+p3;
	ConnectServ(cmddata,CallBack,CallData,ErrMsg);
}
//上传接口
//设置Http上传当前打开的AIP文件
function HttpAddPostCurrFile(p1,CallBack,CallData,ErrMsg){
	var cmddata = "67;|;3,"+p1;
	ConnectServ(cmddata,CallBack,CallData,ErrMsg);
}
//设置通过Http上传的文件
function  HttpAddPostFile(p1,p2,CallBack,CallData,ErrMsg){
	var cmddata = "57;|;3,"+p1+"|;|3,"+p2;
	ConnectServ(cmddata,CallBack, CallData, ErrMsg);
}

//设置通过Http上传的数字变量
function  HttpAddPostValue(p1,p2,CallBack,CallData,ErrMsg){
	var cmddata = "58;|;3,"+p1+"|;|2,"+p2;
	ConnectServ(cmddata,CallBack, CallData, ErrMsg);
}

//设置通过Http上传的字符串变量
function  HttpAddPostString(p1,p2,CallBack,CallData,ErrMsg){
	var cmddata = "59;|;3,"+p1+"|;|3,"+p2;
	ConnectServ(cmddata,CallBack, CallData, ErrMsg);
}
//初始化Http,在调用Http的时候必须首先初始化
function HttpInit(CallBack,CallData,ErrMsg){
	var cmddata = "61";
	ConnectServ(cmddata,CallBack,CallData,ErrMsg);
}
//触发Http上传Post操作
function HttpPost (p1,CallBack,CallData,ErrMsg){
	var cmddata = "62;|;3,"+p1;
	ConnectServ(cmddata,CallBack,CallData,ErrMsg);
}
//设置参数对应的内容
function SetValue(p1,p2,CallBack,CallData,ErrMsg){
var cmddata= "23;|;3,"+p1+"|;|3,"+p2;
	ConnectServ(cmddata,CallBack,CallData,ErrMsg);
}
/************************************
***************默认回调函数**************
************************************/
function HWCallBack(data){

}

