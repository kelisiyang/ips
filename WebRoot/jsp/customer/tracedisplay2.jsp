<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8' />
    <title></title>
    <meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />
    <script src='https://api.tiles.mapbox.com/mapbox-gl-js/v0.38.0/mapbox-gl.js'></script>
    <script src="https://d3js.org/d3.v4.min.js"></script>
    <script src='${pageContext.request.contextPath }/My97DatePicker/WdatePicker.js'></script>
    <link href='https://api.tiles.mapbox.com/mapbox-gl-js/v0.38.0/mapbox-gl.css' rel='stylesheet' />
    <style>
        body { margin:0; padding:0; }
        #map { position:absolute; top:0; bottom:0; width:100%; }
    </style>
</head>
<body>

 <div id='map'>
 	<FORM id="customerForm" name="customerForm"
		action="${pageContext.request.contextPath }/customer_tracedisplay.action"
		method=post onsubmit="return ckeckForm()">
 	<TABLE cellSpacing=0 cellPadding=2 border=0> 	
											<TBODY>
												<TR>
													<TD>MAC地址：</TD>
													<TD><INPUT class=textbox id="muMac"
														style="WIDTH: 80px" maxLength=50 name="muMac"></TD>
													<TD>设置开始时间</TD>	
													<TD><input type="text" id="starttime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="starttime" style="width:150px"/></TD>
													<TD>设置结束时间</TD>	
													<TD><input type="text" id="endtime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="endtime" style="width:150px"/></TD>
													
													<TD><INPUT class=button id=sButton2 type=submit
														value="轨迹回放" name=sButton2></TD>
														
												</TR>
											</TBODY>
										</TABLE>
										</FORM>
 </div>
<script>
mapboxgl.accessToken = 'pk.eyJ1IjoiamluZy1zYW0iLCJhIjoiY2l6ZXgxcDA3MDAzbjJ3b3d5c3V0cTdxMSJ9.lncV85QVu9XzKlsOzUf9TA';
var styleLocation='${pageContext.request.contextPath }/json/style.json';
var map = new mapboxgl.Map({
    container: 'map', // container id
    style: styleLocation
});
map.addControl(new mapboxgl.NavigationControl());
		function ckeckForm()
	{
		var muMac=document.getElementById("muMac").value;
		var starttime=document.getElementById("starttime").value;
		var endtime=document.getElementById("endtime").value;
		if((muMac==null||muMac=='')||(starttime==null||starttime=='')||(endtime==null||endtime==''))
		{
			alert("请输入MAC地址和时间段!");
			return false;
		}
		else return true;
	}
</script>

</body>
</html>