/*-----------------------------------------------------------------------------
*作者:zhp , email:1051471778@qq.com
*version:1.0  , 时间：2013-12-01
*请将此文件的引入 放在 jquery 文件引入的后面
-----------------------------------------------------------------------------*/
/**
 * jbox 的引入 
 */
$.includePath = '/common/jbox-v2.3/jBox/';
 $.include(['jquery.jBox-2.3.min.js',
  		'i18n/jquery.jBox-zh-CN.js',
  		'Skins/Blue/jbox.css'
 ]);
function _winAlert(msg){
	$.jBox.info(msg);
}
if(_winAlert){
	window.alert = _winAlert;
}
