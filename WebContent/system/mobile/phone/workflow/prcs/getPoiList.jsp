<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.http.util.EntityUtils"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="org.apache.http.HttpEntity"%>
<%@page import="org.apache.http.HttpResponse"%>
<%@page import="com.tianee.webframe.util.servlet.HttpUtils"%>
<%

    String lng=TeeStringUtil.getString(request.getParameter("lng"));
    String lat=TeeStringUtil.getString(request.getParameter("lat"));
    String coor=lat+","+lng;
	Map<String, String> headers = new HashMap<String, String>();
	headers.put("Content-Type", "text/html; charset=UTF-8");
	HttpResponse response2 = HttpUtils.doGet("http://api.map.baidu.com", "/geocoder/v2/?ak=jEtlvZ7UXrKl6FtnqikedMIz&callback=?&location="+coor+"&output=json&pois=1", null, headers, null);
	JSONObject jsonObject = JSONObject.fromObject(EntityUtils.toString(response2.getEntity()));
	out.print(jsonObject.getJSONObject("result").getJSONArray("pois").toString());
%>