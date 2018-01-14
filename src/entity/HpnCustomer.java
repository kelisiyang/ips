package entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yangyang
 * @date 2018/1/11
 * @function 此类是实体类 对应数据库的hpn_customerPositon表
 */
public class HpnCustomer {
	private String id;
	private Date createDatetime;
	private String deleteFlag;
	private String operater;
	private Date updateDatetime;
	private Integer azimuth;
	private Double latitude;
	private Double longitude;
	private String MACCode;
	private String channelType;
	private Integer dev_id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	public Date getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	public Integer getAzimuth() {
		return azimuth;
	}
	public void setAzimuth(Integer azimuth) {
		this.azimuth = azimuth;
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
	public String getMACCode() {
		return MACCode;
	}
	public void setMACCode(String mACCode) {
		MACCode = mACCode;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public Integer getDev_id() {
		return dev_id;
	}
	public void setDev_id(Integer dev_id) {
		this.dev_id = dev_id;
	}
	
}