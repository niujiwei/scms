package com.baidu.map;

import net.sf.json.JSONObject;

/**
 * 地理 点对象
 *
 * @author Administrator
 *
 */
public class GeoPoint {

	private String id;

	private double lng;
	private double lat;

	public GeoPoint() {
		this.lng = 0;
		this.lat = 0;
	}

	/**
	 * 构造函数
	 *
	 * @param lng
	 *            经度
	 * @param lat
	 *            纬度
	 *
	 */
	public GeoPoint(double lng, double lat) {
		this.lng = lng;
		this.lat = lat;
	}

	/**
	 * 构造函数
	 *
	 * @param point
	 *            point格式 ： {"lng":116.478513,"lat":39.916465} json字符串
	 */
	public GeoPoint(String id, String point) {
		this.transmit(point);
		this.id = id;

	}

	public GeoPoint(String id, double lng, double lat) {
		super();
		this.id = id;
		this.lng = lng;
		this.lat = lat;
	}

	/**
	 * 构造函数
	 *
	 * @param point
	 *            point格式 ： {"lng":116.478513,"lat":39.916465} json字符串
	 */
	public GeoPoint(String point) {
		this.transmit(point);
	}

	private void transmit(String point) {
		try {
			JSONObject jsonPoint = JSONObject.fromObject(point);
			this.lng = jsonPoint.getDouble("lng");
			this.lat = jsonPoint.getDouble("lat");
		} catch (Exception e) {
			// TODO: handle exception
			this.lng = 0;
			this.lat = 0;
		}
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取经度值
	 *
	 * @return 经度值
	 */
	public double getLng() {
		return this.lng;
	}

	/**
	 * 设置经度值
	 *
	 * @param lng
	 *            经度值
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}

	/**
	 * 获取纬度值
	 *
	 * @return 纬度值
	 */
	public double getLat() {
		return this.lat;
	}

	/**
	 * 设置纬度值
	 *
	 * @param lat
	 *            纬度值
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}

		if (this.getClass() != obj.getClass()) {
			return false;
		}

		double precision = 2e-8; // 浮点类型计算时候与0比较时候的容差

		GeoPoint pt = (GeoPoint) obj;

		// 相等
		if ((Math.abs(pt.getLat() - this.getLat()) < precision) && (Math.abs(pt.getLng() - this.getLng()) < precision)) {
			return true;
		}

		return false;

	}

	@Override
	public int hashCode() {
		long bitLng = Double.doubleToLongBits(Math.ceil(this.lng * 2e8));
		long bitLat = Double.doubleToLongBits(Math.ceil(this.lat * 2e8));
		long r1 = (int) (bitLng ^ (bitLng >>> 32));
		long r2 = (int) (bitLat ^ (bitLat >>> 32));
		return (int) (r1 ^ r2);
	}

	@Override
	public String toString() {
		return "{lat:" + this.lat + ",lng:" + this.lng + "}";
	}

}
