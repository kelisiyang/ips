<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="action.KeliuAction"%>
<!DOCTYPE html>
<html>
<head>
<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="http://code.highcharts.com/highcharts.js"></script>
<title></title>
<style>
#chart{
height:100%;
width:100%;
}
</style>
<script type="text/javascript" charset="UTF-8">
 
 	$(document).ready(function() {
                    var options = {
                          chart: {
                                       renderTo: 'chart', //DIV容器ID
                                       type: 'column'//报表类型
                                     },
                            //报表名称
                            title:{
                                     text:'distribution of tourist'
                                    },  
                              //补充说明
                      subtitle: {
                                      text: 'Source: casm'
                                     },
                          yAxis: {
                                       title: {
	         						text: 'person'
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
	         						text: 'level'
	      										},
                                    categories: []
                                          },
                                //数据来源(多个对比的)        
                                 series: [{
	         name: 'Numbers',
	         data: []
	      } ]
                                };
                            
                             var i,j,len;
                             	options.xAxis.categories[0]=${list[0].louceng};
                             	options.xAxis.categories[1]=${list[1].louceng};
                             	options.xAxis.categories[2]=${list[2].louceng};
                             	options.xAxis.categories[3]=${list[3].louceng};
                             	options.xAxis.categories[4]=${list[4].louceng};
                             	
                             	
                             	options.series[0].data[0]=${list[0].keliu};
                             	options.series[0].data[1]=${list[1].keliu};
                             	options.series[0].data[2]=${list[2].keliu};
                             	options.series[0].data[3]=${list[3].keliu};
                             	options.series[0].data[4]=${list[4].keliu};
                               /*
                          		for(i=0;i<5;i++){
                           			alert(options.series[0].data[i]);
                           		}  
                           		 for(i=0;i<5;i++){
                           			options.series[0].data[i]=${list[i].keliu};
                           		}  
                           		for(j=0;j<5;j++){
                           			alert(options.series[0].data[j]);
                           		} */
                           	
                           		
                             	
                            
                             var chart = new Highcharts.Chart(options);
                      });  


    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="chart">
	</div>
</body>
</html>