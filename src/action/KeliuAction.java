package action;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;


import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import algorithm.KNN;
import algorithm.ReadJsonToAPlist;
import constants.CoordinateConstants;
import entity.Keliu;
import entity.xy2BL;
import mac.APandRssi;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.KeliuService;

public class KeliuAction extends ActionSupport implements ModelDriven<Keliu> {
	private KeliuService keliuService;
	private Keliu keliu2=new Keliu();
	private String message;
	private Double x,y,absx,absy,longitude,latitude;
    public String getMessage()  {
        return message;
   } 
	private String[] keliuliang = new String[10];
	public KeliuService getKeliuService() {
		return keliuService;
	}

	public void setKeliuService(KeliuService keliuService) {
		this.keliuService = keliuService;
	}
	public String toAddPage(){
		return "toAddPage";
	}
	public String add(){
		keliuService.add(keliu2);
		return "add";
	}

	@Override
	public Keliu getModel() {
		// TODO Auto-generated method stub
		return keliu2;
	}

	public Keliu getKeliu() {
		return keliu2;
	}

	public void setKeliu(Keliu keliu2) {
		this.keliu2 = keliu2;
	}
	public String list(){
		List<Keliu> list=keliuService.findAll();
		ServletActionContext.getRequest().setAttribute("list", list);
		return "list";
	}
	public String listPage() throws IOException{
		List<Keliu> list=keliuService.findAll();
		//String json=JSON.toJSONString(list);
		//HttpServletResponse response=ServletActionContext.getResponse();
		//response.getWriter().write(json);
		ServletActionContext.getRequest().setAttribute("list", list);
		return "listPage";
		
	}
	public String delete(){
		//先查再删
		int kid=keliu2.getKid();
		Keliu keliu=keliuService.findOne(kid);
		keliuService.delete(keliu);
		return "delete";
	}
	public String showKeliu(){
		int kid=keliu2.getKid();
		Keliu keliu=keliuService.findOne(kid);
		ServletActionContext.getRequest().setAttribute("keliu2",keliu);
		return"showKeliu";
	}
	public String editsave(){
		keliuService.update(keliu2);
		return "editsave";
	}
	public String toUserPage(){
		return "toUserPage";
	}
	
	public String getBtPerson() throws IOException
	{
		String url="http://27.115.0.198:8188/qpe/getTagPosition?version=2";
		message=loadJSON(url);
		JSONObject obj = JSONObject.fromObject(message);
        JSONArray tags=obj.getJSONArray("tags");
        int num=tags.size();
		String str = "{\"number\":["+num+"]}";
		System.out.println(str);
		HttpServletResponse response=ServletActionContext.getResponse();
		//跨域访问
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS,DELETE,PUT");
		response.addHeader("Access-Control-Allow-Headers", "Test");
		ServletActionContext.getResponse().getWriter().write(str);
		return null;
	}
	//获取匿名Wifi的信息
	public String AnonyWifi() throws IOException{
		
		String url = "http://47.94.129.191:8080/getWifiUdpInfo/udp_downloadJson.action";
		message=loadJSON(url);
		System.out.println(message);
		HttpServletResponse response=ServletActionContext.getResponse();
		//跨域访问
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS,DELETE,PUT");
		response.addHeader("Access-Control-Allow-Headers", "Test");
		return "anonyWifi";
		
	}
	//实时获取匿名Wifi人数
	public String toWifiPerson(){
	
        return "toWifiPerson";
	}
	
	public String getWifiPersonNumber() throws IOException
	{
		String url = "http://47.94.129.191:8080/getWifiUdpInfo/udp_downloadJson.action";
		message=loadJSON(url);
		HttpServletResponse response=ServletActionContext.getResponse();
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS,DELETE,PUT");
		response.addHeader("Access-Control-Allow-Headers", "Test");
		//System.out.println(message);
		JSONObject obj = JSONObject.fromObject(message);
        JSONArray tags=obj.getJSONArray("tags");
        //System.out.println(tags);
        Set<String> muMacSet=new HashSet<String>();
        int apNum=tags.size();
        JSONObject[] apObject=new JSONObject[tags.size()];
        
        for(int i=0;i<apNum;i++)
        {
        	apObject[i]=(JSONObject) tags.get(i);
        	int muNum=apObject[i].getJSONArray("muMac").size();
        	String[] muMacArray=new String[muNum];
        	for(int j=0;j<muNum;j++)
        	{
        		muMacArray[j]=(String) apObject[i].getJSONArray("muMac").get(j);
        		muMacSet.add(muMacArray[j]);
        	}
        }
		System.out.println(muMacSet.size());
		int num=muMacSet.size();
		String str = "{\"number\":["+num+"]}";
		System.out.println(str);
		ServletActionContext.getResponse().getWriter().write(str);
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
	//wifi三边测距定位
	public String toWifitriPositon(){
		

		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();  
		String muMac=request.getParameter("muMac");
		if(muMac!=null)
		{
			ReadJsonToAPlist rm=new ReadJsonToAPlist();
			Map<String, List<APandRssi>> map=rm.readMysqlToRsMap();
			//根据muMac获取list
			List<APandRssi> list=map.get(muMac);
			int size=list.size();
			//对list按照信号强度由大到小进行排序
			rm.sortRssiList(list);
			//取出list的前三个值
			list=rm.getThirdList(list);
			for(int i=0;i<3;i++)
			{
				String str=list.get(i).getApMac()+"："+list.get(i).getRssi();
				System.out.println(str);
			}
		
			double xa=CoordinateConstants.apcoordHashMap.get(list.get(0).getApMac()).getX();
			double	xb=CoordinateConstants.apcoordHashMap.get(list.get(1).getApMac()).getX();
			double	xc=CoordinateConstants.apcoordHashMap.get(list.get(2).getApMac()).getX();
			double	ya=CoordinateConstants.apcoordHashMap.get(list.get(0).getApMac()).getY();
			double	yb=CoordinateConstants.apcoordHashMap.get(list.get(1).getApMac()).getY();
			double	yc=CoordinateConstants.apcoordHashMap.get(list.get(2).getApMac()).getY();
			double	da=Math.pow(10.0, (-50-(int)list.get(0).getRssi())/35.0);
			double	db=Math.pow(10.0, (-50-(int)list.get(1).getRssi())/35.0);
			double	dc=Math.pow(10.0, (-50-(int)list.get(2).getRssi())/35.0);
			 
			System.out.println("xa="+xa);
			System.out.println("xb="+xb);
			System.out.println("xc="+xc);
			System.out.println("ya="+ya);
			System.out.println("yb="+yb);
			System.out.println("yc="+yc);
			System.out.println("da="+da);
			System.out.println("db="+db);
			System.out.println("dc="+dc);
			//三边测距定位算法
			double m=(2*(yb-yc))/(4*(xa-xc)*(yb-yc)-4*(ya-yc)*(xb-xc));
			double n=(2*(yc-ya))/(4*(xa-xc)*(yb-yc)-4*(ya-yc)*(xb-xc));
			double p=(2*(xc-xb))/(4*(xa-xc)*(yb-yc)-4*(ya-yc)*(xb-xc));
			double q=(2*(xa-xc))/(4*(xa-xc)*(yb-yc)-4*(ya-yc)*(xb-xc));
			double r=xa*xa-xc*xc+ya*ya-yc*yc+dc*dc-da*da;
			double s=xb*xb-xc*xc+yb*yb-yc*yc+dc*dc-db*db;
			double x=m*r+n*s;
			double y=p*r+q*s;
			System.out.println(x);
			System.out.println(y);

			absx=1.004614916617*Math.sin(0.0001577329)*x+1.004614916617*Math.cos(0.0001577329)*y+5081797.342644;
			
			absy=1.004614916617*Math.cos(0.0001577329)*x-1.004614916617*Math.sin(0.0001577329)*y+538015.238729;
			
			xy2BL bl=new xy2BL(absx, absy, 126);
			latitude=bl.getB();
			longitude=bl.getL();
			System.out.println(longitude+","+latitude);
			ServletActionContext.getRequest().setAttribute("longitude", longitude);
			ServletActionContext.getRequest().setAttribute("latitude", latitude);
		}
		
		return "toWifitriPositon";		
	}
	//wifi匿名指纹定位
	public String toWififpPositon() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();  
		String muMac=request.getParameter("muMac");
		Double[] position={0.0,0.0};
		if(muMac!=null)
		{
			KNN.setMuMac(muMac);
			KNN knn=new KNN();
			position=knn.getPosition();
			absx=position[1];
			absy=position[0];
			xy2BL bl=new xy2BL(absx, absy, 126);
			latitude=bl.getB();
			longitude=bl.getL();
			System.out.println(longitude+","+latitude);
			ServletActionContext.getRequest().setAttribute("longitude", longitude);
			ServletActionContext.getRequest().setAttribute("latitude", latitude);
			
		}
		
		return "toWififpPositon";
		
	}
	
	//wifi匿名WKNN定位
		public String toWifiWknnPositon() throws Exception{
			HttpServletRequest request = ServletActionContext.getRequest();  
			String muMac=request.getParameter("muMac");
			Double[] position={0.0,0.0};
			if(muMac!=null)
			{
				KNN.setMuMac(muMac);
				KNN knn=new KNN();
				position=knn.getWknnPosition();
				absx=position[1];
				absy=position[0];
				xy2BL bl=new xy2BL(absx, absy, 126);
				latitude=bl.getB();
				longitude=bl.getL();
				System.out.println(longitude+","+latitude);
				ServletActionContext.getRequest().setAttribute("longitude", longitude);
				ServletActionContext.getRequest().setAttribute("latitude", latitude);
				
			}
			
			return "toWifiWknnPositon";
			
		}
		
		//wifi匿名余弦相似度CKNN定位
				public String toWifiCknnPositon() throws Exception{
					HttpServletRequest request = ServletActionContext.getRequest();  
					String muMac=request.getParameter("muMac");
					Double[] position={0.0,0.0};
					if(muMac!=null)
					{
						KNN.setMuMac(muMac);
						KNN knn=new KNN();
						position=knn.getcosWknnPosition();
						absx=position[1];
						absy=position[0];
						xy2BL bl=new xy2BL(absx, absy, 126);
						latitude=bl.getB();
						longitude=bl.getL();
						System.out.println(longitude+","+latitude);
						ServletActionContext.getRequest().setAttribute("longitude", longitude);
						ServletActionContext.getRequest().setAttribute("latitude", latitude);
						
					}
					
					return "toWifiCknnPositon";
					
				}
}
