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
    map.addSource('earthquakes', {
        "type": "geojson",
        "data": ${geoString}
    });
    map.addLayer({
        "id": "earthquakes-heat",
        "type": "heatmap",
        "source": "earthquakes",
        "maxzoom": 21,
        "paint": {
            //Increase the heatmap weight based on frequency and property magnitude
            "heatmap-weight": {
                "property": "mag",
                "type": "exponential",
                "stops": [
                    [19, 0],
                    [20, 1]
                ]
            },
            //Increase the heatmap color weight weight by zoom level
            //heatmap-ntensity is a multiplier on top of heatmap-weight
            "heatmap-intensity": {
                "stops": [
                    [18, 1],
                    [21, 3]
                ]
            },
            //Color ramp for heatmap.  Domain is 0 (low) to 1 (high).
            //Begin color ramp at 0-stop with a 0-transparancy color
            //to create a blur-like effect.
            "heatmap-color": [
                "interpolate",
                ["linear"],
                ["heatmap-density"],
                0, "rgba(33,102,172,0)",
                0.2, "rgb(103,169,207)",
                0.4, "rgb(209,229,240)",
                0.6, "rgb(253,219,199)",
                0.8, "rgb(239,138,98)",
                1, "rgb(178,24,43)"
            ],
            //Adjust the heatmap radius by zoom level
            "heatmap-radius": {
                "stops": [
                    [18, 2],
                    [21, 20]
                ]
            },
            //Transition from heatmap to circle layer by zoom level
            "heatmap-opacity": {
                "default": 1,
                "stops": [
                    [18, 1],
                    [21, 0]
                ]
            },
        }
    }, 'waterway-label');

    map.addLayer({
        "id": "earthquakes-point",
        "type": "circle",
        "source": "earthquakes",
        "minzoom": 18,
        "paint": {
            //Size circle raidus by earthquake magnitude and zoom level
            "circle-radius": {
                "property": "mag",
                "type": "exponential",
                "stops": [
                    [{ zoom: 19, value: 1 }, 1],
                    [{ zoom: 19, value: 6 }, 4],
                    [{ zoom: 18, value: 1 }, 5],
                    [{ zoom: 18, value: 6 }, 50],
                ]
            },
            //Color circle by earthquake magnitude
            "circle-color": {
                "property": "mag",
                "type": "exponential",
                "stops": [
                    [1, "rgba(33,102,172,0)"],
                    [2, "rgb(103,169,207)"],
                    [3, "rgb(209,229,240)"],
                    [4, "rgb(253,219,199)"],
                    [5, "rgb(239,138,98)"],
                    [6, "rgb(178,24,43)"]
                ]
            },
            "circle-stroke-color": "white",
            "circle-stroke-width": 1,
            //Transition from heatmap to circle layer by zoom level
            "circle-opacity": {
                "stops": [
                    [18, 0],
                    [20, 1]
                ]
            }
        }
    }, 'waterway-label');
});

</script>

</body>
</html>