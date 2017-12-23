<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

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
    <link href='https://api.tiles.mapbox.com/mapbox-gl-js/v0.38.0/mapbox-gl.css' rel='stylesheet' />
    <style>
        body { margin:0; padding:0; }
        #map { position:absolute; top:0; bottom:0; width:100%; }
    </style>
</head>
<body>
 <div id='map'>
 	<FORM id="customerForm" name="customerForm"
		action="${pageContext.request.contextPath }/customer_showPositionContinue.action"
		method=post>
 	<TABLE cellSpacing=0 cellPadding=2 border=0> 	
											<TBODY>
												<TR>
													<TD>MAC地址：</TD>
													<TD><INPUT class=textbox id=sChannel2
														style="WIDTH: 80px" maxLength=50 name="muMac"></TD>
													
													<TD><INPUT class=button id=sButton2 type="submit"
														value="查询轨迹信息" name=sButton2></TD>
														
													
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
var radius = 0.00002;
var i=1;
function pointOnCircle(angle) {
    return {
        "type": "Point",
        "coordinates": 
    };
}

map.on('load', function () {
    // Add a source and layer displaying a point which will be animated in a circle.
    map.addSource('point', {
        "type": "geojson",
        "data": pointOnCircle(0)
    });

    map.addLayer({
        "id": "point",
        "source": "point",
        "type": "circle",
        "paint": {
            "circle-radius": 5,
            "circle-color": "#007cbf"
        }
    });

    /* function animateMarker(timestamp) {
        // Update the data to a new position based on the animation timestamp. The
        // divisor in the expression `timestamp / 1000` controls the animation speed.
        map.getSource('point').setData(pointOnCircle(timestamp / 1000));

        // Request the next frame of the animation.
        requestAnimationFrame(animateMarker);
    } */

    // Start the animation.
    //animateMarker(0);
});

//ajax请求
$('#sButton2').click( function(){ 
             //$("#formjs").submit();
               $.ajax({
                    //提交数据的类型 POST GET
                     type : "GET",
                    //提交的网址
                     url : "http://27.115.0.198:8188/qpe/getTagPosition?version=2", 
                    //提交的数据
                     data: {},       
                    //返回数据的格式
                    datatype : "json",//"xml", "html", "script", "json", "jsonp", "text".
                    //在请求之前调用的函数
                    //beforeSend : function() { $("#msg").html("logining");},
                    //成功返回之后调用的函数             
                    success : function(data) {
                        $("#msg").html(decodeURI(data)   
            
                    },
                    //调用执行后调用的函数
                    complete : function(XMLHttpRequest, textStatus) {
                        //HideLoading();
                    },
                    //调用出错执行的函数
                    error : function() {
                        //请求出错处理
                    }
                });          

         });
</script>

</body>
</html>