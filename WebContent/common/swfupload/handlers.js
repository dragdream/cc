/* Demo Note:  This demo uses a FileProgress class that handles the UI for displaying the file name and percent complete.
The FileProgress class is not part of SWFUpload.
*/


/* **********************
   Event Handlers
   These are my custom event handlers to make my
   web application behave the way I went when SWFUpload
   completes different tasks.  These aren't part of the SWFUpload
   package.  They are part of my application.  Without these none
   of the actions SWFUpload makes will show up in my application.
   ********************** */
function swfUploadLoaded() {
	try {
		if(this.customSettings.swfUploadLoaded){
			this.customSettings.swfUploadLoaded();
		}		
	} catch (ex) {
		this.debug(ex);
	}
}

function fileQueued(file) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setStatus("等待上传……");
		progress.toggleCancel(true, this);
		if(this.customSettings.quickUpload){//实行快速上传
			this.startUpload();
		}
	} catch (ex) {
		this.debug(ex);
	}

}

function fileQueueError(file, errorCode, message) {
	try {
		if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
			var fileUploadLimit = this.settings['file_upload_limit'];
			alert("上传文件个数超出限制。\n（最多只能上传" + fileUploadLimit +  "个文件）");
			//alert("You have attempted to queue too many files.\n" + (message === 0 ? "You have reached the upload limit." : "You may select " + (message > 1 ? "up to " + message + " files." : "one file.")));
			return;
		}

		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);

		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			progress.setStatus("File is too big.");
			this.debug("Error Code: File too big, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			progress.setStatus("Cannot upload Zero Byte files.");
			this.debug("Error Code: Zero byte file, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			progress.setStatus("Invalid File Type.");
			this.debug("Error Code: Invalid File Type, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		default:
			if (file !== null) {
				progress.setStatus("Unhandled Error");
			}
			this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		}
	} catch (ex) {
        this.debug(ex);
    }
}

function fileDialogComplete(numFilesSelected, numFilesQueued) {
	try {
		var startBtn = this.customSettings.startBtn;
		var cancelBtn = this.customSettings.cancelBtn;
		var showUploadBtn = this.customSettings.showUploadBtn;
		
		if (numFilesSelected > 0) {
			var fileContainer = document.getElementById(this.customSettings.progressTarget);
			if(showUploadBtn){
				startBtn.style.display="";
				cancelBtn.style.display="";
				fileContainer.appendChild(startBtn);
				fileContainer.appendChild(cancelBtn);
			}
		}else{
			if(showUploadBtn){
				startBtn.style.display="none";
				cancelBtn.style.display="none";
			}
		}
		/* I want auto start the upload and I can do that here */
		//this.startUpload();
	} catch (ex)  {
        this.debug(ex);
	}
}

function uploadStart(file) {
	try {
		/* I don't want to do any file validation or anything,  I'll just update the UI and
		return true to indicate that the upload should start.
		It's important to update the UI here because in Linux no uploadProgress events are called. The best
		we can do is say we are uploading.
		 */
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setStatus("正在上传……");
		if(this.customSettings.uploadStart){
			if(this.customSettings.uploadStart(file,progress)){
				progress.toggleCancel(true, this);
			}else{
				progress.toggleCancel(false, this);
				progress.setStatus("已取消");
				progress.setCancelled();
				progress.setError();
				progress.disappear();
			}
		}else{
			progress.toggleCancel(true, this);
		}
		
	}
	catch (ex) {
		
	}
	
	return true;
}

function uploadProgress(file, bytesLoaded, bytesTotal) {
	try {
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);

		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setProgress(percent);
		progress.setStatus("正在上传……");
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadSuccess(file, serverData) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.toggleCancel(false);
		
		var json = tools.strToJson(serverData);
		if(json.rtState){
			progress.setComplete();
			progress.setStatus(json.rtMsg);
			
			var fileInfo = json.rtData[0];
			var target = document.getElementById(this.customSettings.progressTarget);
			var fileContainer = $(this.customSettings.fileContainer);
			//var fileItem = $("<div>"+fileInfo.fileName+"&nbsp;<span>("+(fileInfo.sizeDesc)+")</span></div>");
			//fileContainer.append(fileItem);
			var customSettingsTmp = this.customSettings;
			
			if(this.customSettings.uploadSuccess){
				this.customSettings.uploadSuccess(fileInfo,this.customSettings);
			}
			
			var swf = this;
			
			var fileItem = tools.getAttachElement(fileInfo,{deleteEvent:function(attachModel){
				var arrays = customSettingsTmp.valuesArray;
				var namearrays = customSettingsTmp.namesArray;
				for(var i=0;i<arrays.length;i++){
					if((arrays[i]+"")==(fileInfo.sid+"")){
						arrays.splice(i,1);
						namearrays.splice(i,1);
						fileItem.remove();
						break;
					}
				}
				customSettingsTmp.valuesArray = arrays;
				customSettingsTmp.valuesHolder.value = arrays;
				customSettingsTmp.namesArray = namearrays;
				if(customSettingsTmp.namesHolder){					
					customSettingsTmp.namesHolder.value = namearrays;
				}
				
				var url = contextPath +"/attachmentController/deleteFile.action?attachIds="+fileInfo.sid;
				var json = tools.requestJsonRs(url);
				
				if(json.rtState){
					var stats = swf.getStats();
					stats.successful_uploads --;
					swf.setStats(stats);
				}
			}});
			if(this.customSettings.renderFiles){
				$(fileContainer).append(fileItem);
			}
//			var menuData = new Array();
//			menuData.push({ name:'移除',action:function(sid){
//				var arrays = customSettingsTmp.valuesArray;
//				for(var i=0;i<arrays.length;i++){
//					if((arrays[i]+"")==(sid+"")){
//						tools.requestJsonRs(context);
//						arrays.splice(i,1);
//						fileItem.remove();
//						break;
//					}
//				}
//				customSettingsTmp.valuesArray = arrays;
//				customSettingsTmp.valuesHolder.value = arrays;
//			},extData:[fileInfo.sid]});
//			fileItem.TeeMenu({menuData:menuData,eventPosition:false});
			
			
			//renderFilesForFileLoader(fileContainer[0],fileInfo);
			//fileContainer.append($(target));
			this.customSettings.valuesArray.push(fileInfo.sid);
			if(this.customSettings.namesArray){
				this.customSettings.namesArray.push(fileInfo.fileName);
			}
			this.customSettings.valuesHolder.value = this.customSettings.valuesArray;
			if(this.customSettings.namesHolder){
				this.customSettings.namesHolder.value = this.customSettings.namesArray;
			}
			
		}else{
			progress.setError();
			progress.setStatus("<b style='color:black'>失败原因：["+json.rtMsg+"]</b>");
		}
		
		//如果不去渲染文件，则保留其中的上传镜像
		if(this.customSettings.renderFiles){
			setTimeout(function(){
				progress.disappear();
			},1000);
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadError(file, errorCode, message) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);

		setTimeout(function(){
			progress.disappear();
		},1000);
		
		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			progress.setStatus("Upload Error: " + message);
			this.debug("Error Code: HTTP Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
			progress.setStatus("Upload Failed.");
			this.debug("Error Code: Upload Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			progress.setStatus("Server (IO) Error");
			this.debug("Error Code: IO Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
			progress.setStatus("Security Error");
			this.debug("Error Code: Security Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			progress.setStatus("Upload limit exceeded.");
			this.debug("Error Code: Upload Limit Exceeded, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
			progress.setStatus("Failed Validation.  Upload skipped.");
			this.debug("Error Code: File Validation Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			// If there aren't any files left (they were all cancelled) disable the cancel button
//			if (this.getStats().files_queued === 0) {
//				document.getElementById(this.customSettings.cancelButtonId).disabled = true;
//			}
			progress.setStatus("已取消");
			progress.setCancelled();
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			progress.setStatus("Stopped");
			break;
		default:
			progress.setStatus("Unhandled Error: " + errorCode);
			this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		}
	} catch (ex) {
        this.debug(ex);
    }
}

function uploadComplete(file) {
	
}

// This event comes from the Queue Plugin
function queueComplete(numFilesUploaded) {
	var startBtn = this.customSettings.startBtn;
	var cancelBtn = this.customSettings.cancelBtn;
	var showUploadBtn = this.customSettings.showUploadBtn;
	if(showUploadBtn){
		startBtn.style.display="none";
		cancelBtn.style.display="none";
		if(this.customSettings.queueComplele){
			this.customSettings.queueComplele();
		}
	}
}
