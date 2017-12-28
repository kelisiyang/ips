package action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import entity.Customer;
import entity.xy2BL;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author yangyang
 * @date 2017/12/26
 * @description geojsonAction请求博冕的接口返回一个geojson数据 用于前端展示实时位置
 */
public class GeojsonAction extends ActionSupport{
		String message;
		public String downloadGeoJson() throws IOException{
			String url="http://221.212.36.82:1234/qpe/getTagPosition?version=2&maxAge=5000";
			    //循环变量i
				int i=0;	
	        	//解析url转换成字符串
				String json = loadJSON(url);
				//str转换成JsonObject
	        	JSONObject obj = JSONObject.fromObject(json);
	            //Json数组 数组里的每个元素代表一个标签
	        	JSONArray tags=obj.getJSONArray("tags");
	            //标签的个数
	        	int num=tags.size();
	            //JSON对象的数组
	        	JSONObject[] arr=new JSONObject[num];
	        	//json数组的数组 每个数组存放一个位置信息
	            JSONArray[] locationarr=new JSONArray[num];
	            
	         
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
	            //拼接geojson字符串
	            StringBuilder resultBuilder = new StringBuilder("{").append("\"type\": \"FeatureCollection\",")
	    				.append("\"features\": [");
	    		StringBuilder recordBuilder = new StringBuilder();
	        	for(i=0;i<num;i++)
	        	{
	        		arr[i]=tags.getJSONObject(i);
	        		locationarr[i]=arr[i].getJSONArray("position");
	        		x=locationarr[i].getDouble(0);        
	 	           	y=locationarr[i].getDouble(1);
	 	           	z=locationarr[i].getDouble(2);
	 	           	absx=1.004614916617*Math.sin(0.0001577329)*x+1.004614916617*Math.cos(0.0001577329)*y+5081797.342644;
	 	           	absy=1.004614916617*Math.cos(0.0001577329)*x-1.004614916617*Math.sin(0.0001577329)*y+538015.238729;
	 	            absz=1.0;
	 	            xy2BL bl=new xy2BL(absx, absy, 126.0);
	 	            latitude=bl.getB();
	 	            longitude=bl.getL();
	 	            recordBuilder.append("{ \"type\": \"Feature\", \"geometry\": { \"type\": \"Point\", \"coordinates\": [ ")
	 				.append(longitude).append(", ").append(latitude).append(" ] } },");
	        	}
	            
	        	recordBuilder.setLength(recordBuilder.length() - 1);
	    		resultBuilder.append(recordBuilder).append("]").append("}");
	    		String geoString=resultBuilder.toString();
	    		System.out.println(geoString);
	           		   
			    ServletActionContext.getResponse().getWriter().write(geoString);	
			return null;
		}
		
		//网页加载json
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
