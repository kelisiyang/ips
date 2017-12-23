<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="action.KeliuAction"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'keliuInfo2.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="assets/materialize/css/materialize.min.css" media="screen,projection" />
    <!-- Bootstrap Styles-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FontAwesome Styles-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
    <!-- Morris Chart Styles-->
    <link href="assets/js/morris/morris-0.4.3.min.css" rel="stylesheet" />
    <!-- Custom Styles-->
    <link href="assets/css/custom-styles.css" rel="stylesheet" />
    <!-- Google Fonts-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
    <link rel="stylesheet" href="assets/js/Lightweight-Chart/cssCharts.css"> 
	
    
	<script src="assets/js/jquery-3.2.0.min.js"></script>
	<!-- Bootstrap Js -->
    <script src="assets/js/bootstrap.min.js"></script>
	
	<script src="assets/materialize/js/materialize.min.js"></script>
	
    <!-- Metis Menu Js -->
    <script src="assets/js/jquery.metisMenu.js"></script>
    <!-- Morris Chart Js -->
    <script src="assets/js/morris/raphael-2.1.0.min.js"></script>
    <script src="assets/js/morris/morris.js"></script>
	
	
	<script src="assets/js/easypiechart.js"></script>
	<script src="assets/js/easypiechart-data.js"></script>
	
	<script src="assets/js/Lightweight-Chart/jquery.chart.js"></script>
	
    <!-- Custom Js -->
    <script src="assets/js/custom-scripts.js"></script> 
	
	<!-- <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script> -->
	
	<script src="http://code.highcharts.com/highcharts.js"></script>
	
	<script type="text/javascript" charset="UTF-8">
	//yy是wifi人数，btnum是蓝牙在线人数
	var yy,btnum;
	var maxyy=0;
    //获取实时的游客数量
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
           
   		    var y=data.number[0];
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
    //获取蓝牙阵列实时在线人数
	function test2() {
    $.ajax({
        type: "GET",
        url: "http://47.94.129.191:8080/sshcustorm3/keliu_getBtPerson.action",
        timeout: 60000,
        async: true,
        dataType:"json",
        beforeSend: function (xhr) {  
           xhr.setRequestHeader("Test", "testheadervalue");  
        },
        success: function(data) {    
           	
   		    var y=data.number[0];
            setBtNumber(y);
        },
   		error: function(XMLHttpRequest, textStatus, errorThrown) {
 			alert(XMLHttpRequest.status);
 			alert(XMLHttpRequest.readyState);
 			alert(textStatus);
   		}

    });
}
	setInterval(test2,2000);
	
   //设置前端页面显示人数
   function setNumber(data) {
        yy=data;
        console.log(yy);
        if(yy>maxyy)
        maxyy=yy;
         $("#nownumbers").text(yy);
         $("#maxnumbers").text(maxyy);
   }
   //设置前端蓝牙在线人数
   function setBtNumber(data) {
        bt=data;
        console.log(bt);
         $("#btnumbers").text(bt);
   }
   
  
 	$(document).ready(function() {
                    var options = {
                          chart: {
                                       renderTo: 'chart', //DIV容器ID
                                       type: 'column'//报表类型
                                     },
                            //报表名称
                            title:{
                                     text:'楼层客流分布图'
                                    },  
                              //补充说明
                      subtitle: {
                                      text: 'Source: 中国测绘科学研究院'
                                     },
                          yAxis: {
                                       title: {
	         						text: '人数'
	      										},
	     							 plotLines: [{
	         							value: 0,
	        						    width: 1,
	        						    color: '#808080'
	      									    }]
                                        },
                                //x轴显示内容
                              xAxis: {
                                    
                                    title: {
	         						text: '楼层'
	      										},
                                    categories: [1,2,3,4,5]
                                          },
                                //数据来源(多个对比的)        
                                 series: [{
	         name: 'Numbers',
	         data: [256,277,314,281,186]
	      } ]
                                };
                            
                             var chart = new Highcharts.Chart(options);
                      });  


    </script>
    
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
   $('#chart2').highcharts(json);
  
});
</script>

  </head>
  
  <body>
    <div id="page-inner">

			
                <div class="row">
                    <div class="col-xs-12 col-sm-6 col-md-3">
					
						<div class="card horizontal cardIcon waves-effect waves-dark">
						<div class="card-image red">
						<i class="fa fa-eye fa-5x"></i>
						</div>
						<div class="card-stacked">
						<div class="card-content">
						<h3 id="nownumbers"></h3> 
						</div>
						<div class="card-action">
						<strong> 实时客流量</strong>
						</div>
						</div>
						</div>
	 
                    </div>
                    <div class="col-xs-12 col-sm-6 col-md-3">
					
						<div class="card horizontal cardIcon waves-effect waves-dark">
						<div class="card-image orange">
						<i class="fa fa-shopping-cart fa-5x"></i>
						</div>
						<div class="card-stacked">
						<div class="card-content">
						<h3 id="btnumbers"></h3> 
						</div>
						<div class="card-action">
						<strong>蓝牙阵列在线人数（博冕接口测试）</strong>
						</div>
						</div>
						</div> 
                    </div>
                    <div class="col-xs-12 col-sm-6 col-md-3">
					
							<div class="card horizontal cardIcon waves-effect waves-dark">
						<div class="card-image blue">
						<i class="fa fa-comments fa-5x"></i>
						</div>
						<div class="card-stacked">
						<div class="card-content">
						<h3 id="maxnumbers"></h3> 
						</div>
						<div class="card-action">
						<strong> 当日客流峰值</strong>
						</div>
						</div>
						</div> 
						 
                    </div>
                    <div class="col-xs-12 col-sm-6 col-md-3">
					
					<div class="card horizontal cardIcon waves-effect waves-dark">
						<div class="card-image">
						<i class="fa fa-users fa-5x"></i>
						</div>
						<div class="card-stacked">
						<div class="card-content">
						<h3>55</h3> 
						</div>
						<div class="card-action">
						<strong> 当日累计顾客</strong>
						</div>
						</div>
						</div> 
						 
                    </div>
                </div>
			
                <!-- /. ROW  --> 
		<div class="row">
			<div class="col-xs-12 col-sm-6 col-md-3"> 
					<div class="card-panel text-center">
						<h4>进馆率</h4>
						<div class="easypiechart" id="easypiechart-blue" data-percent="82" ><span class="percent">82%</span>
						</div> 
					</div>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-3"> 
					<div class="card-panel text-center">
						<h4>驻留率</h4>
						<div class="easypiechart" id="easypiechart-red" data-percent="46" ><span class="percent">46%</span>
						</div>
					</div>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-3"> 
					<div class="card-panel text-center">
						<h4>新游客占比</h4>
						<div class="easypiechart" id="easypiechart-teal" data-percent="84" ><span class="percent">84%</span>
						</div> 
					</div>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-3"> 
					<div class="card-panel text-center">
						<h4>Wifi使用率</h4>
						<div class="easypiechart" id="easypiechart-orange" data-percent="55" ><span class="percent">55%</span>
						</div>
					</div>
			</div> 
		</div><!--/.row-->
			
		
				
				<div class="row">
				<div class="col-md-5"> 
						<div class="card">
						<div class="card-image">
						 <div id="chart2"></div>
						</div> 
						<div class="card-action">
						  <b>客流量趋势图</b>
						</div>
						</div>
		  
					</div>		
					
						<div class="col-md-7"> 
					<div class="card">
					<div class="card-image">
					  <div id="chart"></div>
					</div> 
					<div class="card-action">
					  <b> 楼层信息表</b>
					</div>
					</div>					
					</div>
					
				</div> 
 	</div>
  </body>
</html>
