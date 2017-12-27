<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
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

<div id='map'></div>
<script>
mapboxgl.accessToken = 'pk.eyJ1IjoiamluZy1zYW0iLCJhIjoiY2l6ZXgxcDA3MDAzbjJ3b3d5c3V0cTdxMSJ9.lncV85QVu9XzKlsOzUf9TA';
var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/dark-v9',
    center: [-120, 50],
    zoom: 2
});
map.on('load', function() {
    //Add a geojson point source.
    //Heatmap layers also work with a vector tile source.
    map.addSource('earthquakes', {
        "type": "geojson",
        "data": "${pageContext.request.contextPath }/geojson/earthquakes.geojson"
    });

   map.addLayer({
        "id": "earthquakes-heat",
        "type": "heatmap",
        "source": "earthquakes",
        "maxzoom": 9,
        "paint": {
            //Increase the heatmap weight based on frequency and property magnitude
            "heatmap-weight": {
                "property": "mag",
                "type": "exponential",
                "stops": [
                    [0, 0],
                    [6, 1]
                ]
            },
            //Increase the heatmap color weight weight by zoom level
            //heatmap-ntensity is a multiplier on top of heatmap-weight
            "heatmap-intensity": {
                "stops": [
                    [0, 1],
                    [9, 3]
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
                    [0, 2],
                    [9, 20]
                ]
            },
            //Transition from heatmap to circle layer by zoom level
            "heatmap-opacity": {
                "default": 1,
                "stops": [
                    [7, 1],
                    [9, 0]
                ]
            },
        }
    }, 'waterway-label');

    map.addLayer({
        "id": "earthquakes-point",
        "type": "circle",
        "source": "earthquakes",
        "minzoom": 7,
        "paint": {
            //Size circle raidus by earthquake magnitude and zoom level
            "circle-radius": {
                "property": "mag",
                "type": "exponential",
                "stops": [
                    [{ zoom: 7, value: 1 }, 1],
                    [{ zoom: 7, value: 6 }, 4],
                    [{ zoom: 16, value: 1 }, 5],
                    [{ zoom: 16, value: 6 }, 50],
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
                    [7, 0],
                    [8, 1]
                ]
            }
        }
    }, 'waterway-label');
});
</script>

</body>
</html>