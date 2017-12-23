package mac;

import java.util.ArrayList;
import java.util.List;

 /**
 * @author yangyang
 * @date 2017.11.23
 * @functions 把指纹库里的每列数据封装成一个对象
 */
public class FingerList {
		private int id;
		private double x;
		private double y;
 		private List<Device> devices;
		public static class Device{
			
			private String apMac;
			private Double rssi;
			public Device(){
		
			}
			public Device(String apMac,Double rssi){
				this.apMac=apMac;
				this.rssi=rssi;
			}
			public String getApMac() {
				return apMac;
			}
			public void setApMac(String apMac) {
				this.apMac = apMac;
			}
			public Double getRssi() {
				return rssi;
			}
			public void setRssi(Double rssi) {
				this.rssi = rssi;
			}
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public double getX() {
			return x;
		}
		public void setX(double x) {
			this.x = x;
		}
		public double getY() {
			return y;
		}
		public void setY(double y) {
			this.y = y;
		}
		public List<Device> getDevices() {
			return devices;
		}
		public void setDevices(List<Device> devices) {
			this.devices = devices;
		}
			
}
