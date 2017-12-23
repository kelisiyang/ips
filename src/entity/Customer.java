package entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Customer {
	private Integer dev_id;
	private String muMac;
	private Double X;
	private Double Y;
	private Double Z;
	private Double absX;
	private Double absY;
	private Double absZ;
	private Double latitude;
	private Double longitude;
	private Double accuracy;
	private String floor;
	private Date recvtime;
	public Integer getDev_id() {
		return dev_id;
	}
	public void setDev_id(Integer dev_id) {
		this.dev_id = dev_id;
	}
	public String getMuMac() {
		return muMac;
	}
	public void setMuMac(String muMac) {
		this.muMac = muMac;
	}
	public Double getX() {
		return X;
	}
	public void setX(Double X) {
		this.X = X;
	}
	public Double getY() {
		return Y;
	}
	public void setY(Double Y) {
		this.Y= Y;
	}
	public Double getZ() {
		return Z;
	}
	public void setZ(Double Z) {
		this.Z = Z;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	
	public Double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
	}
	public Date getRecvtime() {
		return recvtime;
	}
	public void setRecvtime(Date recvtime) {
		this.recvtime = recvtime;
	}
	public Double getAbsX() {
		return absX;
	}
	public void setAbsX(Double absX) {
		this.absX = absX;
	}
	public Double getAbsY() {
		return absY;
	}
	public void setAbsY(Double absY) {
		this.absY = absY;
	}
	public Double getAbsZ() {
		return absZ;
	}
	public void setAbsZ(Double absZ) {
		this.absZ = absZ;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}