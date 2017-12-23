package parse;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mac.FingerList;
import mac.FingerList.Device;

/**
 * @author yangyang
 * @date 2017/11/23
 * @function 读取指纹库txt文件 解析转换成fingerLists(包含封装对象fingerList的list集合)
 *
 */
public class ReadTxtToFingerLists {
	List<FingerList> fingerLists =new ArrayList<FingerList>();
	public  List<FingerList> getFingerLists() throws IOException{
		File file=new File("G:\\wififingerprint.txt");
		BufferedReader br=new BufferedReader(new FileReader(file));
		//FingerLists存的是FingerList，每个FingerList是每个id对应的一条数据。
		
		String str=null;
		//循环读取每一列文本赋值给str
		while((str=br.readLine())!=null)
		{
			//解析每一列字符串，返回每一个封装对象fingerlist
			FingerList fingerlist=parsefingerlist(str);
			//将封装对象fingerlist添加到fingerlists
			fingerLists.add(fingerlist);
		}
		//关闭流
		br.close();
		System.out.println(fingerLists.get(1).getDevices().get(2).getRssi());
		return fingerLists;
	}
	private static FingerList parsefingerlist(String str) {
		
		FingerList fingerlist=new FingerList();
		List<Device> devices=new ArrayList<Device>();
		//分割字符串
		String[] split=str.split(",");
		fingerlist.setId(Integer.parseInt(split[0]));
		fingerlist.setX(Double.parseDouble(split[1]));
		fingerlist.setY(Double.parseDouble(split[2]));
		for(int i=3;i<split.length;i=i+2)
		{
			FingerList.Device device=new FingerList.Device();
			device.setApMac(split[i]);
			device.setRssi(Double.parseDouble(split[i+1]));
			devices.add(device);
		}
		fingerlist.setDevices(devices);
		return fingerlist;
		
	}
}
