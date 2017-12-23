package action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author yangyang
 * @time  2017/11/14
 * @description 服务器接收Udp数据包，解析成json字符串显示到index.jsp页面。
 */
public class UdpAction extends ActionSupport {
	private String message;
    
    public String getMessage()  {
        return message;
   } 

	//请求返回json数据
	public String downloadJson() throws IOException{
		int i=0;
		int num=0;
		//循环判定flag
        boolean flag = true;
        //UDP数据包头文件判定udpflag
        boolean udpflag = false;
		byte [] b=new byte[256];
		DatagramPacket inPacket=new DatagramPacket(b, b.length);
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(3333);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int count=0;
		long start=new Date().getTime();
		long end;
		StringBuilder resultBuilder = new StringBuilder("{").append("\"tags\":")
				.append("[");
		StringBuilder recordBuilder = new StringBuilder();
		StringBuilder muMacBuilder = new StringBuilder();
		StringBuilder rssiBuilder = new StringBuilder();
		StringBuilder isAssociatedBuilder = new StringBuilder();
		List<String> MuMaclist=new ArrayList<String>();
		List<Byte> Rssilist=new ArrayList<Byte>();
		List<Integer> IsAssociatedlist=new ArrayList<Integer>();
		while(flag)
		{
			//初始化头文件标识
			udpflag = false;
			//通过新建对象初始化Builder
			muMacBuilder = new StringBuilder();
			rssiBuilder = new StringBuilder();
			isAssociatedBuilder = new StringBuilder();	
			//接收UDP数据包
			try {
					socket.receive(inPacket);
					b=inPacket.getData();
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				StringBuffer sb=new StringBuffer();
				String str=new String();
				str = DatatypeConverter.printHexBinary(b);  
				sb=new StringBuffer(str);
				
				int index;

				for(index=32;index<sb.length();index+=33){

				sb.insert(index, '\n');

				}
				
			
				System.out.println(sb);
				//根据数据包的头文件字节判定是否为udp数据包
				if((b[0]&0xFF)==0x7C&&(b[1]&0xFF)==0x83&&(b[4]&0xFF)==0xD8&&(b[5]&0xFF)==0x03)
						
					
					{
						i=0;
						udpflag=true;
					}
				else udpflag=false;
						
					
				//第9个字节为数据包个数
				num=b[9]&0xFF;
				System.out.println(num);
				if(num==0)
					break;
				//如果为udp包 进行解析
				if(udpflag==true)
			
				{
					
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				    String times = format.format(new Date());  
				   
				    recordBuilder.append("{\"recvTime\":").append("\"").append(times).append("\",");
					i=i+12;
					String Mac=new String();
					Mac=String.format("%02x:%02x:%02x:%02x:%02x:%02x", b[i]&0xFF,b[i+1]&0xFF,b[i+2]&0xFF,b[i+3]&0xFF,b[i+4]&0xFF,b[i+5]&0xFF);
					
					
					i=i+6;
					String bRadio=new String();
					bRadio=String.format("%02x:%02x:%02x:%02x:%02x:%02x", b[i]|0x06,b[i+1]&0xFF,b[i+2]&0xFF,b[i+3]&0xFF,b[i+4]&0xFF,b[i+5]&0xFF);
					//System.out.println(bRadio);
					recordBuilder.append("\"Mac\":").append("\"").append(bRadio).append("\",");
					i=i+6;
					String aRadio=new String();
					aRadio=String.format("%02x:%02x:%02x:%02x:%02x:%02x", b[i]&0xFF,b[i+1]&0xFF,b[i+2]&0xFF,b[i+3]&0xFF,b[i+4]&0xFF,b[i+5]&0xFF);
					//System.out.println(aRadio);
					i=i+6;
					for(int j=0;j<num;j++)
					{
						String MuMac=new String();
						MuMac=String.format("%02x:%02x:%02x:%02x:%02x:%02x", b[i]&0xFF,b[i+1]&0xFF,b[i+2]&0xFF,b[i+3]&0xFF,b[i+4]&0xFF,b[i+5]&0xFF);
					
						MuMaclist.add(MuMac);
						i=i+6;
						byte Rssi=(byte) (b[i]&(0xFF));
						double d=Math.pow(10.0, (-45-Rssi)/20.0);
					
						Rssilist.add(Rssi);
						//System.out.println("d:"+d);
						i=i+1;
						int IsAssociated=0;
						IsAssociated= b[i]&0xFF;
					
						IsAssociatedlist.add(IsAssociated);
						
						i=i+3;
						
					}
					for(int m=0;m<num;m++)
					{
						muMacBuilder.append("\"").append(MuMaclist.get(m)).append("\"").append(",");
						rssiBuilder.append(Rssilist.get(m)).append(",");
						isAssociatedBuilder.append(IsAssociatedlist.get(m)).append(",");
					}
					muMacBuilder.setLength(muMacBuilder.length()-1);
					rssiBuilder.setLength(rssiBuilder.length()-1);
					isAssociatedBuilder.setLength(isAssociatedBuilder.length()-1);
					
					recordBuilder.append("\"muMac\":[").append(muMacBuilder).append("],").append("\"Rssi\":[").append(rssiBuilder).append("],").append("\"IsAssociate\":[").append(isAssociatedBuilder).append("]},");
				//获取当前时间
				end=new Date().getTime();	
				//判断时间差大于1s，设置flag=flase
				if(end-start>1000)
				{
					flag=false;
				}
			    i=0;
			    //初始化list数组
			    MuMaclist.clear();
				IsAssociatedlist.clear();
				Rssilist.clear();
				}
				
		}
		//拼接json字符串
		recordBuilder.setLength(recordBuilder.length()-1);
		resultBuilder.append(recordBuilder).append("]").append("}");
		String jsonString=resultBuilder.toString();
        //前端index.jsp页面显示message
		message=jsonString;
		HttpServletResponse response=ServletActionContext.getResponse();
		//服务器端解决跨域问题
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS,DELETE,PUT");
		response.addHeader("Access-Control-Allow-Headers", "Test");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.getWriter().write(jsonString);
		socket.close();
		return null;
	}
}
