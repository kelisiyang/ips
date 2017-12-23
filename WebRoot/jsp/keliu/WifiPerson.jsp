<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<meta charset="UTF-8" />
<title>匿名WIFI实时人数</title>
<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
</head>
<body>
<div id="container" style="width: 1550px; height: 800px; margin: 0 auto"></div>
<script language="JavaScript">
$(document).ready(function() {  
   var chart = {
      type: 'spline',
	  animation: Highcharts.svg, // don't animate in IE < IE 10.
      marginRight: 10,
	  events: {
         load: function () {
            // set up the updating of the chart each second
            var series = this.series[0];
            setInterval(function () {
               var x = (new Date()).getTime(), // current time
               y = getNumber();
               series.addPoint([x, y], true, true);
            }, 2000);
         }
      }
   };
    var ArrayList=new Array();
    var yy;
    function test() {
    $.ajax({
        type: "GET",
        url: "http://47.94.129.191:8080/sshcustorm3/keliu_getWifiPersonNumber.action",
        timeout: 60000,
        async: true,
        dataType:"json",
        beforeSend: function (xhr) {  
           xhr.setRequestHeader("Test", "testheadervalue");  
        },
        success: function(data) {    
            console.log(data);
            //var obj=JSON.parse(data);
    
    		
   		    var y=data.number[0];
   		    console.log(y)
            setNumber(y);
        },
   		error: function(XMLHttpRequest, textStatus, errorThrown) {
 			alert(XMLHttpRequest.status);
 			alert(XMLHttpRequest.readyState);
 			alert(textStatus);
   		}

    });
}
	setInterval(test,2000);
   function setNumber(data) {
        yy=data;
   }
   
   function getNumber() {
        return yy;
   }
   var title = {
      text: '大楼内实时人数'   
   };   
   var xAxis = {
      type: 'datetime',
      tickPixelInterval: 150
   };
   var yAxis = {
      title: {
         text: 'Value'
      },
      plotLines: [{
         value: 0,
         width: 1,
         color: '#808080'
      }]
   };
   var tooltip = {
      formatter: function () {
      return '<b>' + this.series.name + '</b><br/>' +
         Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
         Highcharts.numberFormat(this.y, 2);
      }
   };
   var plotOptions = {
      area: {
         pointStart: 1940,
         marker: {
            enabled: false,
            symbol: 'circle',
            radius: 2,
            states: {
               hover: {
                 enabled: true
               }
            }
         }
      }
   };
   var legend = {
      enabled: false
   };
   var exporting = {
      enabled: false
   };
   var series= [{
      name: '大楼内实时人数',
      data: (function () {
         // generate an array of random data
         var data = [],time = (new Date()).getTime(),i;
         for (i = -19; i <= 0; i += 1) {
            data.push({
               x: time + i * 1000,
               y: 20
            });
         }
         return data;
      }())    
   }];     
      
   var json = {};   
   json.chart = chart; 
   json.title = title;     
   json.tooltip = tooltip;
   json.xAxis = xAxis;
   json.yAxis = yAxis; 
   json.legend = legend;  
   json.exporting = exporting;   
   json.series = series;
   json.plotOptions = plotOptions;
   
   
   Highcharts.setOptions({
      global: {
         useUTC: false
      }
   });
   $('#container').highcharts(json);
  
});
</script>
</body>
</html>
