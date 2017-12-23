package algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import mac.APandRssi;
import mac.FingerList;
import mac.FingerList.Device;
import parse.ReadJsonToAPlist;
import parse.ReadTxtToFingerLists;

/**
 * @author yangyang
 * @date 2017/11/25
 * @function 此类是自定义的KNN算法，先读取离线指纹库信息，在线请求指纹，并进行匹配
 * 			 K值取3 进行均值运算，返回x，y坐标。
 */
public class KNN {
	static String muMac;
	public static void setMuMac(String mac) {
		 muMac = mac;
	}
	ReadJsonToAPlist rm=new ReadJsonToAPlist();
	Map<String, List<APandRssi>> map=rm.readMysqlToRsMap();
	//用TreeMap存储欧式距离  key：距离 value：序列号
	Map<Double,Integer> dmap=new TreeMap<Double,Integer>();
	ReadTxtToFingerLists rl=new ReadTxtToFingerLists();
	List<FingerList> fingerLists =new ArrayList<FingerList>();
	List<Device> devices=new ArrayList<Device>();
	//创建临时离线的信号强度集合
	List<Double> lixianrssilist=new ArrayList<Double>();
	//创建临时在线的信号强度集合
	List<Integer> zaixianrssilist=new ArrayList<Integer>();
	FingerList fingerList=new FingerList();
	//根据muMac获取list(在线收集到的Aplist)
	List<APandRssi> list=map.get(muMac);
	//dlist存放欧氏距离值
	Set<Double> dset=new TreeSet<Double>();
	//存储最终的绝对坐标位置信息
	Double[] position={0.0,0.0};
	Collection<Integer> ddlist=new ArrayList<Integer>();
	//fingerlistsize为指纹库的fingerlist数量
	int size=list.size();
	int k1,k2,k3=0;
	//w代表权重，d代表距离 xy代表坐标
	Double w1,w2,w3,d1,d2,d3,x1,x2,x3,y1,y2,y3,x,y=new Double(0);
	public Double[] getPosition() throws IOException{
		fingerLists=rl.getFingerLists();
		int fingerlistsize=fingerLists.size();
		System.out.println(fingerlistsize);
		//外循环 遍历每个fingerlist
		for(int i=0;i<fingerlistsize;i++)
		{	
			Double s = new Double(0);
			//dd是每一列的欧氏距离
			Double dd= new Double(0);
			//拿到第i个fingerList
			fingerList=fingerLists.get(i);
			//拿到设备的list
			devices=fingerList.getDevices();
			int devicesize=devices.size();
			//内循环 进行比对
			for(int j=0;j<devicesize;j++)
			{
				//设置list的初始序列为0
				int n=0;
				Double d= new Double(0);
				
				//每次循环清零初始化
				lixianrssilist.clear();
				zaixianrssilist.clear();
				//循环条件 n<size
				while(true&&(n<size))
				{
					
					if(devices.get(j).getApMac().equals(list.get(n).getApMac()))
					{
						lixianrssilist.add(devices.get(j).getRssi());
						zaixianrssilist.add(list.get(n).getRssi());
						d=devices.get(j).getRssi()-list.get(n).getRssi().doubleValue();
						s=s+d*d;
						break;
					}
					else
						n++;
				}
				
				
			}
			dd=Math.sqrt(s);
			dmap.put(dd, i);
			
			
		}
		System.out.println(dmap);
		//把treemap的value(序列号)放到collection集合里
		ddlist=dmap.values();
		//转换成数组，存放序列的数组
		Object[] array=ddlist.toArray();
		//取数组的前三个值
		k1=(int) array[1];
		k2=(int) array[2];
		k3=(int) array[3];
		//根据序列号拿到对应的XY坐标
		x1=fingerLists.get(k1).getX();
		x2=fingerLists.get(k2).getX();
		x3=fingerLists.get(k3).getX();
		y1=fingerLists.get(k1).getY();
		y2=fingerLists.get(k2).getY();
		y3=fingerLists.get(k3).getY();
		//对坐标进行均值处理
		x=(1.0/3.0)*(x1+x2+x3);
		y=(1.0/3.0)*(y1+y2+y3);
		position[0]=x;
		position[1]=y;
		System.out.println(ddlist);
		System.out.println(x);
		System.out.println(y);
		return position;
	}
	//WKNN算法 k=3
	public Double[] getWknnPosition() throws IOException{
		fingerLists=rl.getFingerLists();
		int fingerlistsize=fingerLists.size();
		System.out.println(fingerlistsize);
		//外循环 遍历每个fingerlist
		for(int i=0;i<fingerlistsize;i++)
		{	
			Double s = new Double(0);
			//dd是每一列的欧氏距离
			Double dd= new Double(0);
			//拿到第i个fingerList
			fingerList=fingerLists.get(i);
			//拿到设备的list
			devices=fingerList.getDevices();
			int devicesize=devices.size();
			//内循环 进行比对
			for(int j=0;j<devicesize;j++)
			{
				//设置list的初始序列为0
				int n=0;
				Double d= new Double(0);
				
				//每次循环清零初始化
				lixianrssilist.clear();
				zaixianrssilist.clear();
				//循环条件 n<size
				while(true&&(n<size))
				{
					
					if(devices.get(j).getApMac().equals(list.get(n).getApMac()))
					{
						lixianrssilist.add(devices.get(j).getRssi());
						zaixianrssilist.add(list.get(n).getRssi());
						d=devices.get(j).getRssi()-list.get(n).getRssi().doubleValue();
						s=s+d*d;
						break;
					}
					else
						n++;
				}
				
				
			}
			dd=Math.sqrt(s);
			dmap.put(dd, i);
			
			
		}
		System.out.println(dmap);
		//把treemap的value(序列号)放到collection集合里
		ddlist=dmap.values();
		//把treemap的key(欧氏距离)放到treeSet集合里
		dset=dmap.keySet();
		System.out.println(dset);
		//转换成数组，存放序列的数组
		Object[] array=ddlist.toArray();
		//转换成数组，存放序列的数组
		Object[] darray=dset.toArray();
		//取数组的前三个值(序列号)
		k1=(int) array[1];
		k2=(int) array[2];
		k3=(int) array[3];
		//取数组的前三个值(距离)
		d1=(Double) darray[1];
		d2=(Double) darray[2];
		d3=(Double) darray[3];
		//算出权重w
		w1=(1.0/d1)/(1.0/d1+1.0/d2+1.0/d3);
		w2=(1.0/d2)/(1.0/d1+1.0/d2+1.0/d3);
		w3=(1.0/d3)/(1.0/d1+1.0/d2+1.0/d3);
		System.out.println(w1);
		System.out.println(w2);
		System.out.println(w3);
		//根据序列号拿到对应的XY坐标
		x1=fingerLists.get(k1).getX();
		x2=fingerLists.get(k2).getX();
		x3=fingerLists.get(k3).getX();
		y1=fingerLists.get(k1).getY();
		y2=fingerLists.get(k2).getY();
		y3=fingerLists.get(k3).getY();
		//对坐标进行均值处理
		x=w1*x1+w2*x2+w3*x3;
		y=w1*y1+w2*y2+w3*y3;
		position[0]=x;
		position[1]=y;
		System.out.println(ddlist);
		System.out.println(x);
		System.out.println(y);
		return position;
	}
	//基于余弦相似度的WKNN
	public Double[] getcosWknnPosition() throws IOException{
		fingerLists=rl.getFingerLists();
		int fingerlistsize=fingerLists.size();
		System.out.println(fingerlistsize);
		//外循环 遍历每个fingerlist
		for(int i=0;i<fingerlistsize;i++)
		{	
			Double s = new Double(0);
			//dd是每一列的余弦距离
			Double dd= new Double(0);
			Double a= new Double(0);
			Double b= new Double(0);
			//拿到第i个fingerList
			fingerList=fingerLists.get(i);
			//拿到设备的list
			devices=fingerList.getDevices();
			int devicesize=devices.size();
			//内循环 进行比对
			for(int j=0;j<devicesize;j++)
			{
				//设置list的初始序列为0
				int n=0;
				Double d= new Double(0);
				//每次循环清零初始化
				lixianrssilist.clear();
				zaixianrssilist.clear();
				//循环条件 n<size
				while(true&&(n<size))
				{
					
					if(devices.get(j).getApMac().equals(list.get(n).getApMac()))
					{
						lixianrssilist.add(devices.get(j).getRssi());
						zaixianrssilist.add(list.get(n).getRssi());
						d=devices.get(j).getRssi()*list.get(n).getRssi().doubleValue();
						s=s+d;
						a=a+devices.get(j).getRssi()*devices.get(j).getRssi();
						b=b+list.get(n).getRssi()*list.get(n).getRssi();
						break;
					}
					else
						n++;
				}
				
				
			}
			dd=s/(Math.sqrt(a)*Math.sqrt(b));
			dmap.put(dd, i);
			
			
		}
		System.out.println(dmap);
		//把treemap的value(序列号)放到collection集合里
		ddlist=dmap.values();
		//把treemap的key(欧氏距离)放到treeSet集合里
		dset=dmap.keySet();
		System.out.println(dset);
		//转换成数组，存放序列的数组
		Object[] array=ddlist.toArray();
		//转换成数组，存放序列的数组
		Object[] darray=dset.toArray();
		//取数组的前三个值(序列号)
		k1=(int) array[1];
		k2=(int) array[2];
		k3=(int) array[3];
		//取数组的前三个值(距离)
		d1=(Double) darray[1];
		d2=(Double) darray[2];
		d3=(Double) darray[3];
		//算出权重w
		w1=(1.0/d1)/(1.0/d1+1.0/d2+1.0/d3);
		w2=(1.0/d2)/(1.0/d1+1.0/d2+1.0/d3);
		w3=(1.0/d3)/(1.0/d1+1.0/d2+1.0/d3);
		System.out.println(w1);
		System.out.println(w2);
		System.out.println(w3);
		//根据序列号拿到对应的XY坐标
		x1=fingerLists.get(k1).getX();
		x2=fingerLists.get(k2).getX();
		x3=fingerLists.get(k3).getX();
		y1=fingerLists.get(k1).getY();
		y2=fingerLists.get(k2).getY();
		y3=fingerLists.get(k3).getY();
		//对坐标进行均值处理
		x=w1*x1+w2*x2+w3*x3;
		y=w1*y1+w2*y2+w3*y3;
		position[0]=x;
		position[1]=y;
		System.out.println(ddlist);
		System.out.println(x);
		System.out.println(y);
		return position;
	}
}
