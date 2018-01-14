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
import entity.HpnCustomer;
import entity.MPoint;
import entity.PageBean;
import entity.xy2BL;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.CustomerService;
import service.HpnCustomerService;

public class HpnCustomerAction extends ActionSupport implements ModelDriven<HpnCustomer>{
	
	private String jsonString;
	private Double longitude,latitude;
	private HpnCustomer hpncustomer=new HpnCustomer();
	private HpnCustomerService hpncustomerService;
	@Override
	public HpnCustomer getModel() {
		// TODO Auto-generated method stub
		return hpncustomer;
	}
	public HpnCustomer getHpncustomer() {
		return hpncustomer;
	}
	public void setHpncustomer(HpnCustomer hpncustomer) {
		this.hpncustomer = hpncustomer;
	}
	public HpnCustomerService getHpncustomerService() {
		return hpncustomerService;
	}
	public void setHpncustomerService(HpnCustomerService hpncustomerService) {
		this.hpncustomerService = hpncustomerService;
	}
	//轨迹查询
	public String traceMap() throws Exception{
		
		HttpServletRequest request = ServletActionContext.getRequest();  
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		
		//System.out.println(date);
		String muMac=request.getParameter("MACCode");
		System.out.println(muMac+starttime+endtime);
		if(muMac!=null&&starttime!=null&&endtime!=null)
		{
			List<HpnCustomer> list=hpncustomerService.findCondition2(hpncustomer,starttime,endtime);
			StringBuilder resultBuilder = new StringBuilder("{").append("\"type\": \"Feature\",")
					.append("\"geometry\": {").append("\"type\": \"LineString\",").append("\"coordinates\":[");
			StringBuilder recordBuilder = new StringBuilder();
			ServletActionContext.getRequest().setAttribute("list", list);
			//System.out.println(list.size());
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
			return "apptracemap2";
		}
		return "apptracemap";
	}
	
	//轨迹回放
	public String tracedisplay() throws ParseException{
    	HttpServletRequest request = ServletActionContext.getRequest();  
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		
		//System.out.println(date);
		String muMac=request.getParameter("MACCode");
		System.out.println(muMac+starttime+endtime);
		if(muMac!=null&&starttime!=null&&endtime!=null)
		{
			List<HpnCustomer> list=hpncustomerService.findCondition2(hpncustomer,starttime,endtime);
			StringBuilder resultBuilder = new StringBuilder("{").append("\"type\": \"Feature\",")
					.append("\"geometry\": {").append("\"type\": \"LineString\",").append("\"coordinates\":[");
			StringBuilder recordBuilder = new StringBuilder();
			ServletActionContext.getRequest().setAttribute("list", list);
			//System.out.println(list.size());
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
}
