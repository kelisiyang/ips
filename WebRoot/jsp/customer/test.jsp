<%@ page language="java" import="java.util.*,org.apache.struts2.ServletActionContext" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!--本jsp文件显示的是蓝牙阵列实时的位置页面  -->
<!DOCTYPE html>
<html>
<head>
	<meta charset='utf-8' />
    <title></title>
    <meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />
    <script src='https://api.mapbox.com/mapbox-gl-js/v0.41.0/mapbox-gl.js'></script>
    <link href='https://api.mapbox.com/mapbox-gl-js/v0.41.0/mapbox-gl.css' rel='stylesheet' /> 
    <script src="http://cdn.static.runoob.com/libs/jquery/1.10.2/jquery.min.js">
    </script> 
    <style>
        body { margin:0; padding:0; }
        #map { position:absolute; top:0; bottom:0; width:100%; }  
    </style>
</head>
<body>
 <div id='map'>
 	<FORM id="customerForm" name="customerForm"
		action="${pageContext.request.contextPath }/customer_showPosition.action"
		method=post>
 	<TABLE cellSpacing=0 cellPadding=2 border=0> 	
											<TBODY>
												<TR>
													<TD>MAC地址：</TD>
													<TD><INPUT type="text"  class=textbox id=sChannel2
														style="WIDTH: 80px" maxLength=50 name="muMac"></TD>
													
													<TD><INPUT class=button id=sButton2 type="submit"
														value="查询实时位置" name=sButton2></TD>
														
													
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

  var json=null;
  
 function loadXMLDoc()//ajax发送请求并显示  
{  
var xmlhttp;  
if (window.XMLHttpRequest)  
  {// code for IE7+, Firefox, Chrome, Opera, Safari  
  xmlhttp=new XMLHttpRequest();  
  }  
else  
  {// code for IE6, IE5  
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");  
  }  
xmlhttp.onreadystatechange=function()  
  {  
  if (xmlhttp.readyState==4 && xmlhttp.status==200)  
    {  
    //document.getElementById("map").innerHTML=xmlhttp.responseText; 
     json= xmlhttp.responseText;
     console.log(json);
     
    var obj=JSON.parse(json);
    //alert(obj.tags[0]);
    //document.getElementById("map").innerHTML=obj.tags[0].position[0];
    x=obj.position[0];
    y=obj.position[1];
    map.getSource('point').setData(getPoint(x, y));
    }  
  }  
xmlhttp.open("GET","http://localhost:8080/sshcustorm3/customer_ajax.action",false);  
xmlhttp.send();  
setTimeout("loadXMLDoc()",500);//递归调用  
}  
loadXMLDoc();//先执行一次

 function getPoint(x,y) {  
   
    return {
        "type": "Point",
        "coordinates":[x,y]
    };
}

map.on('load', function () {
    // Add a source and layer displaying a point which will be animated in a circle.
    map.addSource('point', {
        "type": "geojson",
        "data": getPoint(x,y)
    });

    map.addLayer({
        "id": "point",
        "source": "point",
        "type": "circle",
        "paint": {
            "circle-radius": 5,
            "circle-color": "#ff0000"
        }
    }); 

}); 

  

</script>

</body>
</html>