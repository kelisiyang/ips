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
		action="${pageContext.request.contextPath }/customer_heatMap.action"
		method=post>
 	<TABLE cellSpacing=0 cellPadding=2 border=0> 	
											<TBODY>
												<TR>
													<TD>设置开始时间</TD>	
													<TD><input type="text" id="starttime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="starttime" style="width:150px"/></TD>
													<TD>设置结束时间</TD>	
													<TD><input type="text" id="endtime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="endtime" style="width:150px"/></TD>
													<TD>按日期查询</TD>	
													<TD><input type="text" id="day" onclick="WdatePicker()" name="day" style="width:150px"/></TD>
													<TD><INPUT class=button id=sButton2 type=submit
														value="查询热点图" name=sButton2></TD>
														
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
map.on('load', function() {

    // Add a new source from our GeoJSON data and set the
    // 'cluster' option to true.
    map.addSource("earthquakes", {
        type: "geojson",
        // Point to GeoJSON data. This example visualizes all M1.0+ earthquakes
        // from 12/22/15 to 1/21/16 as logged by USGS' Earthquake hazards program.
        data:${geoString},
        cluster: true,
        clusterMaxZoom: 25, // Max zoom to cluster points on
        clusterRadius: 20 // Use small cluster radius for the heatmap look
    });

    // Use the earthquakes source to create four layers:
    // three for each cluster category, and one for unclustered points

    // Each point range gets a different fill color.
    var layers = [
       [0, 'green'],
       [5, 'orange'],
       [10, 'red']
    ];

    layers.forEach(function (layer, i) {
        map.addLayer({
            "id": "cluster-" + i,
            "type": "circle",
            "source": "earthquakes",
            "paint": {
                "circle-color": layer[1],
                "circle-radius": 70,
                "circle-blur": 1 // blur the circles to get a heatmap look
            },
            "filter": i === layers.length - 1 ?
                [">=", "point_count", layer[0]] :
                ["all",
                    [">=", "point_count", layer[0]],
                    ["<", "point_count", layers[i + 1][0]]]
        }, 'waterway-label');
    });

    map.addLayer({
        "id": "unclustered-points",
        "type": "circle",
        "source": "earthquakes",
        "paint": {
            "circle-color": 'rgba(0,255,0,0.5)',
            "circle-radius": 20,
            "circle-blur": 1
        },
        "filter": ["!=", "cluster", true]
    }, 'waterway-label');
});
// Add zoom and rotation controls to the map.
map.addControl(new mapboxgl.NavigationControl());
</script>

</body>
</html>