package com.jy.model;

/**
 * zhaokejin@outlook.com 2015年5月14日 09:52:04
 * 
 * @author zkj 装货城市
 */
public class City_info {
	private int citycode; //城市编号
	
	private String cityname; //城市字母检索
	private int citye_parent_id;//父
	private int city_type;//城市类型
	private Integer province;//省    
	private Integer city;//市
	private Integer county;//县
	public int getCitycode() {
		return citycode;
	}
	public void setCitycode(int citycode) {
		this.citycode = citycode;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public int getCitye_parent_id() {
		return citye_parent_id;
	}
	public void setCitye_parent_id(int citye_parent_id) {
		this.citye_parent_id = citye_parent_id;
	}
	public int getCity_type() {
		return city_type;
	}
	public void setCity_type(int city_type) {
		this.city_type = city_type;
	}
	public Integer getProvince() {
		return province;
	}
	public void setProvince(Integer province) {
		this.province = province;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public Integer getCounty() {
		return county;
	}
	public void setCounty(Integer county) {
		this.county = county;
	}


}
