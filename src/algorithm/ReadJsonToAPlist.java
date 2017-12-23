package algorithm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import mac.APandRssi;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author yangyang
 * @date 2017.11.22
 * @function 本类的作用是 请求url 把解析的json串 再封装成map value：muMac ，key：包含apMac和Rssi的一一对应的list
 *           查找muMac对应的list 并对其排序 取出前三的list 按照三角定位的算法进行位置结算
 */ 

public class ReadJsonToAPlist {
	int sum=0;
	String message;
	//map key：MuMac ，value：List<APandRssi>
	Map<String,List<APandRssi>> map=new HashMap<String,List<APandRssi>>();
    Map<Integer, List<APandRssi>> listmap=new HashMap<Integer,List<APandRssi>>();
    List<APandRssi> list0=new ArrayList<APandRssi>();
    List<APandRssi> list1=new ArrayList<APandRssi>();
    List<APandRssi> list2=new ArrayList<APandRssi>();
    List<APandRssi> list3=new ArrayList<APandRssi>();
    List<APandRssi> list4=new ArrayList<APandRssi>();
    List<APandRssi> list5=new ArrayList<APandRssi>();
    List<APandRssi> list6=new ArrayList<APandRssi>();
    List<APandRssi> list7=new ArrayList<APandRssi>();
    List<APandRssi> list8=new ArrayList<APandRssi>();
    List<APandRssi> list9=new ArrayList<APandRssi>();
    List<APandRssi> list10=new ArrayList<APandRssi>();
    List<APandRssi> sortedlist=new ArrayList<APandRssi>();
    //构造函数把每个MuMac对应的list存入listmap里
    public ReadJsonToAPlist() {
		// TODO Auto-generated constructor stub
    	listmap.put(0, list0);
    	listmap.put(1, list1);
    	listmap.put(2, list2);
    	listmap.put(3, list3);
    	listmap.put(4, list4);
    	listmap.put(5, list5);
    	listmap.put(6, list6);
    	listmap.put(7, list7);
    	listmap.put(8, list8);
    	listmap.put(9, list9);
    	listmap.put(10, list10);
	}
	public  Map<String, List<APandRssi>> readMysqlToRsMap() {
		    String url = "http://47.94.129.191:8080/getWifiUdpInfo/udp_downloadJson.action";
		    message=loadJSON(url);
		    JSONObject obj = JSONObject.fromObject(message);
	        JSONArray tags=obj.getJSONArray("tags");
	        int apNum=tags.size();
	        JSONObject[] apObject=new JSONObject[tags.size()];
	        
	        for(int i=0;i<apNum;i++)
	        {
	        	apObject[i]=(JSONObject) tags.get(i);
	        	int muNum=apObject[i].getJSONArray("muMac").size();
	        	for(int j=0;j<muNum;j++)
	        	{
	        		//每内循环一次，创建一个APandRssi对象
	        		APandRssi ar=new APandRssi(apObject[i].getString("Mac"),(int) apObject[i].getJSONArray("Rssi").get(j));
	        		//把对应的list添加APandRssi对象
	        		listmap.get(j).add(ar);
	        		map.put(apObject[i].getJSONArray("muMac").getString(j), listmap.get(j));
	        
	        	}
	        }
			
		return map;  
	}
	
	//按信号强度由大到小进行排序
	public void sortRssiList(List<APandRssi> list){
		
		
		Collections.sort(list);
		
	}
	//对排序后的list取出前三个
	public List<APandRssi> getThirdList(List<APandRssi> list){
		
		List<APandRssi> thirdList=new ArrayList<APandRssi>();
		thirdList.add(list.get(0));
		thirdList.add(list.get(1));
		thirdList.add(list.get(2));
		return thirdList;
			
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
