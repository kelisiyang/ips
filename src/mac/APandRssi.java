package mac;

/**
 * @author yangyang
 * @date 2017.11.22
 * @fuction ����������Ǵ洢�ź�ǿ�ȶ�Ӧ��ApMAc��ַ �������װ
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
		int i = o.getRssi()-this.getRssi() ;//�Ȱ�����������  
        if(i == 0){  
            return this.getRssi() - o.getRssi();//���������������÷�����������  
        }  
        return i;  
	}
}
