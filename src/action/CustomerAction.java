package action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import entity.CGCS2000Point;
import entity.ConvertGauss2Geodetic;
import entity.Customer;
import entity.MPoint;
import entity.PageBean;
import entity.xy2BL;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.CustomerService;

public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{
	private CustomerService customerService;
	private CGCS2000Point cPoint=new CGCS2000Point();
	private ConvertGauss2Geodetic convertGauss2Geodetic=new ConvertGauss2Geodetic();
	private MPoint mPoint=new MPoint();
	private String muMac;
	private String jsonString;
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	private Customer customer=new Customer();
	private Integer currentPage;
	private Double x,y,absx,absy,longitude,latitude;
	public Customer getModel() {
		
		return customer;
	}
	public CustomerService getCustomerService() {
		return customerService;
	}
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	public String toAddPage(){
		return "toAddPage";
	}
	public String add(){
		
		customerService.add(customer);
		return "add";
	}
	public String list(){
		List<Customer> list=customerService.findAll();
		ServletActionContext.getRequest().setAttribute("list", list);
		return "list";
	}

	public String delete(){
		
		int dev_id=customer.getDev_id();
		//先查询再删除
		Customer c=customerService.findOne(dev_id);
		customerService.delete(c);
		return "delete";
		
	}
	public String showCustomer(){
		int dev_id=customer.getDev_id();
		Customer c=customerService.findOne(dev_id);
		ServletActionContext.getRequest().setAttribute("customer",c);
		return "showCustomer";
	}
	public String update(){
		customerService.update(customer);
		return "update";
		
	}
	public String listPage(){
		PageBean pageBean=customerService.listPage(currentPage);
		ServletActionContext.getRequest().setAttribute("pageBean", pageBean);
		return "listPage";
		
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public String conditionQuery(){
		if(customer.getMuMac()!=null&&!"".equals(customer.getMuMac()))
		{
			List<Customer> list=customerService.findCondition(customer);
			ServletActionContext.getRequest().setAttribute("list", list);
		}
		else{
			list();
		}
		return "conditionQuery";
	}
	//单用户位置信息查询
	public String mapQuery(){
		if(customer.getMuMac()!=null&&!"".equals(customer.getMuMac()))
		{
			List<Customer> list=customerService.findCondition(customer);
			ServletActionContext.getRequest().setAttribute("list", list);
			x=list.get(0).getX();
			y=list.get(0).getY();
	
	
			absx=1.004614916617*Math.sin(0.0001577329)*x+1.004614916617*Math.cos(0.0001577329)*y+5081797.342644;
					
			absy=1.004614916617*Math.cos(0.0001577329)*x-1.004614916617*Math.sin(0.0001577329)*y+538015.238729;
			
			xy2BL bl=new xy2BL(absx, absy, 126);
			
			latitude=bl.getB();
			longitude=bl.getL();
		
			
			System.out.println(latitude);
			System.out.println(longitude);
			ServletActionContext.getRequest().setAttribute("longitude", longitude);
			ServletActionContext.getRequest().setAttribute("latitude", latitude);
			
		}
		else{
			list();
		}
		return "heatMap";
	}
	//单用户轨迹查询
	public String traceMap() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();  
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		
		//System.out.println(date);
		String muMac=request.getParameter("muMac");
		System.out.println(muMac+starttime+endtime);
		if(muMac!=null&&starttime!=null&&endtime!=null)
		{
			List<Customer> list=customerService.findCondition2(customer,starttime,endtime);
			StringBuilder resultBuilder = new StringBuilder("{").append("\"type\": \"Feature\",")
					.append("\"geometry\": {").append("\"type\": \"LineString\",").append("\"coordinates\":[");
			StringBuilder recordBuilder = new StringBuilder();
			ServletActionContext.getRequest().setAttribute("list", list);
			//System.out.println(list.size());
			List<Double> X=new ArrayList<Double>();
			List<Double> Y=new ArrayList<Double>();
			List<Double> longitudelist=new ArrayList<Double>();
			List<Double> latitudelist=new ArrayList<Double>();
			for(int i=0;i<list.size();i++)
			{
				latitude=list.get(i).getLatitude();
				longitude=list.get(i).getLongitude();
				longitudelist.add(longitude);
				latitudelist.add(latitude);
			}
			ServletActionContext.getRequest().setAttribute("latitudelist", latitudelist);
			ServletActionContext.getRequest().setAttribute("longitudelist", longitudelist);
			
			for(int i=0;i<list.size();i++)
			{
				System.out.println(latitudelist.get(i));
				System.out.println(longitudelist.get(i));
				recordBuilder.append("[").append(longitudelist.get(i)).append(",").append(latitudelist.get(i)).append("],");
			}
			recordBuilder.setLength(recordBuilder.length()-1);
			resultBuilder.append(recordBuilder).append("]}}");
			String jsonString=resultBuilder.toString();
			System.out.println(jsonString);
			//Writer w=new FileWriter("C:\\Users\\lenovo\\Desktop\\ips-master\\WebRoot\\geojson\\tracemap.geojson");
			//BufferedWriter bw=new BufferedWriter(w);
			//bw.write(jsonString);
			//bw.close();
			ServletActionContext.getRequest().setAttribute("jsonString", jsonString);
			
		}
		else{
			return "traceMap2";
		}
		return "traceMap";
	}
	
	public String customerJson() throws IOException{
		
		List<Customer> list=customerService.findAll();
		String json=JSON.toJSONString(list);
		HttpServletResponse response=ServletActionContext.getResponse();
		response.getWriter().write(json);
		return NONE;
	}
	public String showMap(){
		return "showMap";
	}
	//热点分析
	public String heatMap() throws IOException, ParseException{
		HttpServletRequest request = ServletActionContext.getRequest();  
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		String day=request.getParameter("day");
		System.out.println(starttime+endtime);
		if(!"".equals(day)&&day!=null)
		{
			starttime=day+" 00:00:00";
			endtime=day+" 23:59:59";
			List<Customer> list=customerService.findCondition3(starttime,endtime);
			StringBuilder resultBuilder = new StringBuilder("{").append("\"type\": \"FeatureCollection\",")
					.append("\"features\": [");
			StringBuilder recordBuilder = new StringBuilder();
			for(int i=0;i<list.size();i++)
			{
				Double X=list.get(i).getX();
				Double Y=list.get(i).getY();
				absx=1.004614916617*Math.sin(0.0001577329)*list.get(i).getX()+1.004614916617*Math.cos(0.0001577329)*list.get(i).getY()+5081797.342644;
				
				absy=1.004614916617*Math.cos(0.0001577329)*list.get(i).getX()-1.004614916617*Math.sin(0.0001577329)*list.get(i).getY()+538015.238729;
				//System.out.println(absx+absy);
				xy2BL bl=new xy2BL(absx, absy, 126);
				latitude=bl.getB();
				longitude=bl.getL();
				recordBuilder.append("{ \"type\": \"Feature\", \"geometry\": { \"type\": \"Point\", \"coordinates\": [ ")
				.append(longitude).append(", ").append(latitude).append(" ] } },");
				
			}
			recordBuilder.setLength(recordBuilder.length() - 1);
			resultBuilder.append(recordBuilder).append("]").append("}");
			String geoString=resultBuilder.toString();

			ServletActionContext.getRequest().setAttribute("geoString", geoString);
			System.out.println(geoString);
		}
		else
		{
			if(starttime!=null&&endtime!=null)
			{
				List<Customer> list=customerService.findCondition3(starttime,endtime);
				StringBuilder resultBuilder = new StringBuilder("{").append("\"type\": \"FeatureCollection\",")
						.append("\"features\": [");
				StringBuilder recordBuilder = new StringBuilder();
				for(int i=0;i<list.size();i++)
				{
					Double X=list.get(i).getX();
					Double Y=list.get(i).getY();
					absx=1.004614916617*Math.sin(0.0001577329)*list.get(i).getX()+1.004614916617*Math.cos(0.0001577329)*list.get(i).getY()+5081797.342644;
					
					absy=1.004614916617*Math.cos(0.0001577329)*list.get(i).getX()-1.004614916617*Math.sin(0.0001577329)*list.get(i).getY()+538015.238729;
					//System.out.println(absx+absy);
					xy2BL bl=new xy2BL(absx, absy, 126);
					latitude=bl.getB();
					longitude=bl.getL();
					recordBuilder.append("{ \"type\": \"Feature\", \"geometry\": { \"type\": \"Point\", \"coordinates\": [ ")
					.append(longitude).append(", ").append(latitude).append(" ] } },");
					
				}
				recordBuilder.setLength(recordBuilder.length() - 1);
				resultBuilder.append(recordBuilder).append("]").append("}");
				String geoString=resultBuilder.toString();

				ServletActionContext.getRequest().setAttribute("geoString", geoString);
				System.out.println(geoString);
			}
			else
			{
				return "heatMap2";
		    }		
		}
		return "heatMap";
	}
	
	
	    //实时位置显示
	    public String showPosition() throws IOException{
	    	//获取requset
	    	HttpServletRequest request = ServletActionContext.getRequest();  
	    	//由于action是多例模式 所以全局变量muMac不能方法之间共享 需要存入session
	    	Map<String, Object> session=ActionContext.getContext().getSession();
	    	muMac=request.getParameter("muMac"); 
	    	session.put("muMac", muMac);
	    	System.out.println(muMac);
			    return "showPosition";
			}

	
			
	//}	
	    
	  //ajax请求action
	    public String ajax() throws IOException{
	    	Map<String, Object> session=ActionContext.getContext().getSession();
	    	//从session里取出muMac值
	    	muMac=(String) session.get("muMac");
	    	System.out.println(muMac);
	    	//定义请求url
	    	String url = "http://221.212.36.82:1234/qpe/getTagPosition?version=2&humanReadable=true&tag="+muMac;
			         	
	    	        int i=0;	
		        	String json = loadJSON(url);
		        	JSONObject obj = JSONObject.fromObject(json);
		            JSONArray tags=obj.getJSONArray("tags");
		            JSONObject[] arr=new JSONObject[tags.size()];
		            JSONArray[] locationarr=new JSONArray[tags.size()];
		            
		         
		            Double x=new Double(0.0);
		            Double y=new Double(0.0);
		            Double z=new Double(0.0);
		            Double absx=new Double(0.0);
		            Double absy=new Double(0.0);
		            Double absz=new Double(0.0);
		            Double latitude=new Double(0.0);
		            Double longitude=new Double(0.0);
		            String position=new String();
		            String floor=new String();
		        	
		            arr[0]=tags.getJSONObject(0);
		           
		  
		           locationarr[0]=arr[0].getJSONArray("position");
		           x=locationarr[0].getDouble(0);        
		           y=locationarr[0].getDouble(1);
		           z=locationarr[0].getDouble(2);
		           absx=1.004614916617*Math.sin(0.0001577329)*x+1.004614916617*Math.cos(0.0001577329)*y+5081797.342644;
		           absy=1.004614916617*Math.cos(0.0001577329)*x-1.004614916617*Math.sin(0.0001577329)*y+538015.238729;
		           absz=1.0;
		           xy2BL bl=new xy2BL(absx, absy, 126.0);
				   latitude=bl.getB();
				   longitude=bl.getL();
				   position="["+longitude.toString()+","+latitude.toString()+"]";
				   String str = "{\"position\":["+longitude.toString()+","+latitude.toString()+"]}";
				   System.out.println(str);
				   
				   ServletActionContext.getResponse().getWriter().write(str);		  
				  
		           System.out.println("--------------------------");
		           
				   return null;
				
			}
	    //mapbox测试
	    public String monitor(){
			
	    	return "monitor";
	    	
	    }
	    //tracedisplay测试
	    public String tracedisplay() throws ParseException{
	    	HttpServletRequest request = ServletActionContext.getRequest();  
			String starttime=request.getParameter("starttime");
			String endtime=request.getParameter("endtime");
			
			//System.out.println(date);
			String muMac=request.getParameter("muMac");
			System.out.println(muMac+starttime+endtime);
			if(muMac!=null&&starttime!=null&&endtime!=null)
			{
				List<Customer> list=customerService.findCondition2(customer,starttime,endtime);
				StringBuilder resultBuilder = new StringBuilder("{").append("\"type\": \"Feature\",")
						.append("\"geometry\": {").append("\"type\": \"LineString\",").append("\"coordinates\":[");
				StringBuilder recordBuilder = new StringBuilder();
				ServletActionContext.getRequest().setAttribute("list", list);
				//System.out.println(list.size());
				List<Double> X=new ArrayList<Double>();
				List<Double> Y=new ArrayList<Double>();
				List<Double> longitudelist=new ArrayList<Double>();
				List<Double> latitudelist=new ArrayList<Double>();
				for(int i=0;i<list.size();i++)
				{
					latitude=list.get(i).getLatitude();
					longitude=list.get(i).getLongitude();
					longitudelist.add(longitude);
					latitudelist.add(latitude);
				}
				ServletActionContext.getRequest().setAttribute("latitudelist", latitudelist);
				ServletActionContext.getRequest().setAttribute("longitudelist", longitudelist);
				
				for(int i=0;i<list.size();i++)
				{
					System.out.println(latitudelist.get(i));
					System.out.println(longitudelist.get(i));
					recordBuilder.append("[").append(longitudelist.get(i)).append(",").append(latitudelist.get(i)).append("],");
				}
				recordBuilder.setLength(recordBuilder.length()-1);
				resultBuilder.append(recordBuilder).append("]}}");
				jsonString=resultBuilder.toString();
				Map<String, Object> session=ActionContext.getContext().getSession();
				session.put("jsonString", jsonString);
				//System.out.println(jsonString);
	    	
	    }   
			
			return "tracedisplay";
	   	
		}
	//动态轨迹请求数据
	public String downloadGeoJson2() throws IOException, ParseException{
			
			Map<String, Object> session=ActionContext.getContext().getSession();
			jsonString=(String) session.get("jsonString");
			//System.out.println(jsonString);
			ServletActionContext.getResponse().getWriter().write(jsonString);
			//初始化session
			session.put("jsonString", null);
			return null;
			
			}    
	public CGCS2000Point getcPoint() {
		return cPoint;
	}
	public void setcPoint(CGCS2000Point cPoint) {
		this.cPoint = cPoint;
	}
	public MPoint getmPoint() {
		return mPoint;
	}
	public void setmPoint(MPoint mPoint) {
		this.mPoint = mPoint;
	}
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getAbsy() {
		return absy;
	}
	public void setAbsy(Double absy) {
		this.absy = absy;
	}
	public Double getAbsx() {
		return absx;
	}
	public void setAbsx(Double absx) {
		this.absx = absx;
	}
	public static String loadJSON (String url) {
         StringBuilder jsonString = new StringBuilder();
         try {
             URL oracle = new URL(url);
             URLConnection yc = oracle.openConnection();
             BufferedReader in = new BufferedReader(new InputStreamReader(
                                         yc.getInputStream()));
             String inputLine = null;
             while ( (inputLine = in.readLine()) != null) {
                 jsonString.append(inputLine);
             }
             in.close();
         } catch (MalformedURLException e) {
         } catch (IOException e) {
         }
         return jsonString.toString();
     }   

}
