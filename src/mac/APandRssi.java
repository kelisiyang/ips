package mac;

/**
 * @author yangyang
 * @date 2017.11.22
 * @fuction 该类的作用是存储信号强度对应的ApMAc地址 并对其封装
 */
public class APandRssi implements Comparable<APandRssi> {
	private String apMac;
	private int rssi;
	public String getApMac() {
		return apMac;
	}
	public void setApMac(String apMac) {
		this.apMac = apMac;
	}
	public Integer getRssi() {
		return rssi;
	}
	public void setRssi(Integer rssi) {
		this.rssi = rssi;
	}
	public APandRssi(String apMac,int rssi) {
		// TODO Auto-generated constructor stub
		this.apMac=apMac;
		this.rssi=rssi; }
	@Override
	public int compareTo(APandRssi o) {
		// TODO Auto-generated method stub
		int i = o.getRssi()-this.getRssi() ;//先按照年龄排序  
        if(i == 0){  
            return this.getRssi() - o.getRssi();//如果年龄相等了再用分数进行排序  
        }  
        return i;  
	}
}
