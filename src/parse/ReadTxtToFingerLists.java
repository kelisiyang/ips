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
 * @function ��ȡָ�ƿ�txt�ļ� ����ת����fingerLists(������װ����fingerList��list����)
 *
 */
public class ReadTxtToFingerLists {
	List<FingerList> fingerLists =new ArrayList<FingerList>();
	public  List<FingerList> getFingerLists() throws IOException{
		File file=new File("G:\\wififingerprint.txt");
		BufferedReader br=new BufferedReader(new FileReader(file));
		//FingerLists�����FingerList��ÿ��FingerList��ÿ��id��Ӧ��һ�����ݡ�
		
		String str=null;
		//ѭ����ȡÿһ���ı���ֵ��str
		while((str=br.readLine())!=null)
		{
			//����ÿһ���ַ���������ÿһ����װ����fingerlist
			FingerList fingerlist=parsefingerlist(str);
			//����װ����fingerlist��ӵ�fingerlists
			fingerLists.add(fingerlist);
		}
		//�ر���
		br.close();
		System.out.println(fingerLists.get(1).getDevices().get(2).getRssi());
		return fingerLists;
	}
	private static FingerList parsefingerlist(String str) {
		
		FingerList fingerlist=new FingerList();
		List<Device> devices=new ArrayList<Device>();
		//�ָ��ַ���
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
