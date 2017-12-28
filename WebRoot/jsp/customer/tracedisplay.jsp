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
		method=post>
 	<TABLE cellSpacing=0 cellPadding=2 border=0> 	
											<TBODY>
												<TR>
													<TD>MAC地址：</TD>
													<TD><INPUT class=textbox id=sChannel2
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
/* var geojson="${pageContext.request.contextPath }/geojson/earthquakes2.geojson"; */
// Add zoom and rotation controls to the map.
map.addControl(new mapboxgl.NavigationControl());
map.on('load', function () {
    // We use D3 to fetch the JSON here so that we can parse and use it separately
    // from GL JS's use in the added source. You can use any request method (library
    // or otherwise) that you want.
    d3.json('http://47.94.129.191:8080/sshcustorm3/customer_downloadGeoJson2.action', function(err, data) {
        if (err) throw err;
		//console.log(data.geometry.coordinates);
        // save full coordinate list for later
        var coordinates = data.geometry.coordinates;

        // start by showing just the first coordinate
        data.geometry.coordinates = [coordinates[0]];

        // add it to the map
        map.addSource('trace', { type: 'geojson', data: data });
        map.addLayer({
            "id": "trace",
            "type": "line",
            "source": "trace",
            "paint": {
                "line-color": "red",
                "line-opacity": 0.75,
                "line-width": 4
            }
        });

        // setup the viewport
        map.jumpTo({ 'center': coordinates[0], 'zoom': 19 });
        map.setPitch(30);

        // on a regular basis, add more coordinates from the saved list and update the map
        var i = 0;
        var timer = window.setInterval(function() {
            if (i < coordinates.length) {
                data.geometry.coordinates.push(coordinates[i]);
                map.getSource('trace').setData(data);
                map.panTo(coordinates[i]);
                i++;
            } else {
                window.clearInterval(timer);
            }
        }, 200);
    });
});
</script>

</body>
</html>