var s = "";
s += "<OBJECT id=TeeHWPostil align='middle' style='LEFT: 0px; WIDTH: 0; TOP: 0px; HEIGHT: 0'"
	//+ "classid=clsid:FF3FE7A0-0578-4FEE-A54E-FB21B277D567 codebase=HWPostil.cab#version=3,0,9,8>"
	+ "classid=clsid:FF1FE7A0-0578-4FEE-A34E-FB21B277D561 codebase="+contextPath+"/system/core/aip/HWPostil.cab#version=3,1,2,6>"
	+ "<PARAM NAME='_Version' VALUE='65536'>"
	+ "<PARAM NAME='_ExtentX' VALUE='17410'>"
	+ "<PARAM NAME='_ExtentY' VALUE='10874'>"
	+ "<PARAM NAME='_StockProps' VALUE='0'>"
	+ "</OBJECT>";
document.write(s); 