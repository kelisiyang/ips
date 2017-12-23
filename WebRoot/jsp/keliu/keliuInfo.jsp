<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'keliuInfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<!-- <link rel="stylesheet" type="text/css" href="userInfo.css"> -->
	<style type="text/css">
	.top{
	width: 1700px;height: 80px; background-color: orange;
	font-size: medium;
	}
	.top p{
		padding-bottom: 20px;
		padding-left: 20px;
		padding-top:25px;
		padding-right:20px;
		font-size: 20px;
	}
	.mid1 {
	width: 1700px;height: 140px; background-color: blue;
	font-size: medium;
	}
	.mid11{
	width: 25%;height:140px;background-color: gray;float: left;
	}
	
	.mid2 {
	width: 1700px;height: 140px; background-color: green;
	font-size: medium;
	}
	.mid22{
	width: 25%;height:140px;background-color: green;float: left;
	}
	
	.bottom{
	width: 1700px;height: 300px; background-color: purple;
	font-size: medium;
	}
	.bottoms{
	width: 50%;height: 350px; background-color: purple;float:left;
	font-size: medium;
	}
	.card.horizontal.cardIcon .card-image {
    background: #30cc7b;
    color: #fff;
    font-size: 10px;
    width: 94px;
    text-align: center;
    vertical-align: middle;
	}
	.card.horizontal.cardIcon .card-image i {
    margin-top: 45%;
    font-size: 40px;
	}
	</style>
  </head>
  
  <body>
    	<div class="top">
    		<p>信息概览</p>
    	</div>
    	<div class="mid1">
    		<div class=mid11 >
    		<p>1</p>
    		</div>
    		<div class=mid11 >
    		<p>2</p>
    		</div>
    		<div class=mid11 >
    		<p>3</p>
    		</div>
    		<div class=mid11 >
    		<p>4</p>
    		</div>
    	</div>
    	
    	<div class="mid2">
    		<div class=mid22 >
    		<p>1</p>
    		</div>
    		<div class=mid22 >
    		<p>2</p>
    		</div>
    		<div class=mid22 >
    		<p>3</p>
    		</div>
    		<div class=mid22 >
    		<p>4</p>
    		</div>
    	</div>
    	
    	<div class="bottom">
    		<div class=bottoms >
    		<p>1</p>
    		</div>
    		<div class=bottoms >
    		<p>2</p>
    		</div>
    	</div>
  </body>
</html>
