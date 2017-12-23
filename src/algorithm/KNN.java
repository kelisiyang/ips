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
 * @function �������Զ����KNN�㷨���ȶ�ȡ����ָ�ƿ���Ϣ����������ָ�ƣ�������ƥ��
 * 			 Kֵȡ3 ���о�ֵ���㣬����x��y���ꡣ
 */
public class KNN {
	static String muMac;
	public static void setMuMac(String mac) {
		 muMac = mac;
	}
	ReadJsonToAPlist rm=new ReadJsonToAPlist();
	Map<String, List<APandRssi>> map=rm.readMysqlToRsMap();
	//��TreeMap�洢ŷʽ����  key������ value�����к�
	Map<Double,Integer> dmap=new TreeMap<Double,Integer>();
	ReadTxtToFingerLists rl=new ReadTxtToFingerLists();
	List<FingerList> fingerLists =new ArrayList<FingerList>();
	List<Device> devices=new ArrayList<Device>();
	//������ʱ���ߵ��ź�ǿ�ȼ���
	List<Double> lixianrssilist=new ArrayList<Double>();
	//������ʱ���ߵ��ź�ǿ�ȼ���
	List<Integer> zaixianrssilist=new ArrayList<Integer>();
	FingerList fingerList=new FingerList();
	//����muMac��ȡlist(�����ռ�����Aplist)
	List<APandRssi> list=map.get(muMac);
	//dlist���ŷ�Ͼ���ֵ
	Set<Double> dset=new TreeSet<Double>();
	//�洢���յľ�������λ����Ϣ
	Double[] position={0.0,0.0};
	Collection<Integer> ddlist=new ArrayList<Integer>();
	//fingerlistsizeΪָ�ƿ��fingerlist����
	int size=list.size();
	int k1,k2,k3=0;
	//w����Ȩ�أ�d������� xy��������
	Double w1,w2,w3,d1,d2,d3,x1,x2,x3,y1,y2,y3,x,y=new Double(0);
	public Double[] getPosition() throws IOException{
		fingerLists=rl.getFingerLists();
		int fingerlistsize=fingerLists.size();
		System.out.println(fingerlistsize);
		//��ѭ�� ����ÿ��fingerlist
		for(int i=0;i<fingerlistsize;i++)
		{	
			Double s = new Double(0);
			//dd��ÿһ�е�ŷ�Ͼ���
			Double dd= new Double(0);
			//�õ���i��fingerList
			fingerList=fingerLists.get(i);
			//�õ��豸��list
			devices=fingerList.getDevices();
			int devicesize=devices.size();
			//��ѭ�� ���бȶ�
			for(int j=0;j<devicesize;j++)
			{
				//����list�ĳ�ʼ����Ϊ0
				int n=0;
				Double d= new Double(0);
				
				//ÿ��ѭ�������ʼ��
				lixianrssilist.clear();
				zaixianrssilist.clear();
				//ѭ������ n<size
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
		//��treemap��value(���к�)�ŵ�collection������
		ddlist=dmap.values();
		//ת�������飬������е�����
		Object[] array=ddlist.toArray();
		//ȡ�����ǰ����ֵ
		k1=(int) array[1];
		k2=(int) array[2];
		k3=(int) array[3];
		//�������к��õ���Ӧ��XY����
		x1=fingerLists.get(k1).getX();
		x2=fingerLists.get(k2).getX();
		x3=fingerLists.get(k3).getX();
		y1=fingerLists.get(k1).getY();
		y2=fingerLists.get(k2).getY();
		y3=fingerLists.get(k3).getY();
		//��������о�ֵ����
		x=(1.0/3.0)*(x1+x2+x3);
		y=(1.0/3.0)*(y1+y2+y3);
		position[0]=x;
		position[1]=y;
		System.out.println(ddlist);
		System.out.println(x);
		System.out.println(y);
		return position;
	}
	//WKNN�㷨 k=3
	public Double[] getWknnPosition() throws IOException{
		fingerLists=rl.getFingerLists();
		int fingerlistsize=fingerLists.size();
		System.out.println(fingerlistsize);
		//��ѭ�� ����ÿ��fingerlist
		for(int i=0;i<fingerlistsize;i++)
		{	
			Double s = new Double(0);
			//dd��ÿһ�е�ŷ�Ͼ���
			Double dd= new Double(0);
			//�õ���i��fingerList
			fingerList=fingerLists.get(i);
			//�õ��豸��list
			devices=fingerList.getDevices();
			int devicesize=devices.size();
			//��ѭ�� ���бȶ�
			for(int j=0;j<devicesize;j++)
			{
				//����list�ĳ�ʼ����Ϊ0
				int n=0;
				Double d= new Double(0);
				
				//ÿ��ѭ�������ʼ��
				lixianrssilist.clear();
				zaixianrssilist.clear();
				//ѭ������ n<size
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
		//��treemap��value(���к�)�ŵ�collection������
		ddlist=dmap.values();
		//��treemap��key(ŷ�Ͼ���)�ŵ�treeSet������
		dset=dmap.keySet();
		System.out.println(dset);
		//ת�������飬������е�����
		Object[] array=ddlist.toArray();
		//ת�������飬������е�����
		Object[] darray=dset.toArray();
		//ȡ�����ǰ����ֵ(���к�)
		k1=(int) array[1];
		k2=(int) array[2];
		k3=(int) array[3];
		//ȡ�����ǰ����ֵ(����)
		d1=(Double) darray[1];
		d2=(Double) darray[2];
		d3=(Double) darray[3];
		//���Ȩ��w
		w1=(1.0/d1)/(1.0/d1+1.0/d2+1.0/d3);
		w2=(1.0/d2)/(1.0/d1+1.0/d2+1.0/d3);
		w3=(1.0/d3)/(1.0/d1+1.0/d2+1.0/d3);
		System.out.println(w1);
		System.out.println(w2);
		System.out.println(w3);
		//�������к��õ���Ӧ��XY����
		x1=fingerLists.get(k1).getX();
		x2=fingerLists.get(k2).getX();
		x3=fingerLists.get(k3).getX();
		y1=fingerLists.get(k1).getY();
		y2=fingerLists.get(k2).getY();
		y3=fingerLists.get(k3).getY();
		//��������о�ֵ����
		x=w1*x1+w2*x2+w3*x3;
		y=w1*y1+w2*y2+w3*y3;
		position[0]=x;
		position[1]=y;
		System.out.println(ddlist);
		System.out.println(x);
		System.out.println(y);
		return position;
	}
	//�����������ƶȵ�WKNN
	public Double[] getcosWknnPosition() throws IOException{
		fingerLists=rl.getFingerLists();
		int fingerlistsize=fingerLists.size();
		System.out.println(fingerlistsize);
		//��ѭ�� ����ÿ��fingerlist
		for(int i=0;i<fingerlistsize;i++)
		{	
			Double s = new Double(0);
			//dd��ÿһ�е����Ҿ���
			Double dd= new Double(0);
			Double a= new Double(0);
			Double b= new Double(0);
			//�õ���i��fingerList
			fingerList=fingerLists.get(i);
			//�õ��豸��list
			devices=fingerList.getDevices();
			int devicesize=devices.size();
			//��ѭ�� ���бȶ�
			for(int j=0;j<devicesize;j++)
			{
				//����list�ĳ�ʼ����Ϊ0
				int n=0;
				Double d= new Double(0);
				//ÿ��ѭ�������ʼ��
				lixianrssilist.clear();
				zaixianrssilist.clear();
				//ѭ������ n<size
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
		//��treemap��value(���к�)�ŵ�collection������
		ddlist=dmap.values();
		//��treemap��key(ŷ�Ͼ���)�ŵ�treeSet������
		dset=dmap.keySet();
		System.out.println(dset);
		//ת�������飬������е�����
		Object[] array=ddlist.toArray();
		//ת�������飬������е�����
		Object[] darray=dset.toArray();
		//ȡ�����ǰ����ֵ(���к�)
		k1=(int) array[1];
		k2=(int) array[2];
		k3=(int) array[3];
		//ȡ�����ǰ����ֵ(����)
		d1=(Double) darray[1];
		d2=(Double) darray[2];
		d3=(Double) darray[3];
		//���Ȩ��w
		w1=(1.0/d1)/(1.0/d1+1.0/d2+1.0/d3);
		w2=(1.0/d2)/(1.0/d1+1.0/d2+1.0/d3);
		w3=(1.0/d3)/(1.0/d1+1.0/d2+1.0/d3);
		System.out.println(w1);
		System.out.println(w2);
		System.out.println(w3);
		//�������к��õ���Ӧ��XY����
		x1=fingerLists.get(k1).getX();
		x2=fingerLists.get(k2).getX();
		x3=fingerLists.get(k3).getX();
		y1=fingerLists.get(k1).getY();
		y2=fingerLists.get(k2).getY();
		y3=fingerLists.get(k3).getY();
		//��������о�ֵ����
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
