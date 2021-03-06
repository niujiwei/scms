package com.jy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.jy.dao.MarginOrderDao;
import com.jy.model.OrderCustom;
import com.jy.model.ShippingOrder;
import com.jy.service.MarginOrderService;
@Controller
public class MarginOrderServiceImpl  implements MarginOrderService{
	@Resource
	private MarginOrderDao mo;
	public List<OrderCustom> getMarginOrder(Integer rows, Integer page,String name ,String start_date1,String end_date2,String start_date,String end_date,String car_number,String type,String send_address,String phone){
		return mo.getMarginOrder(rows, page, name, start_date1, end_date2, start_date, end_date, car_number, type, send_address, phone);
	}
	public List<ShippingOrder> getMarginShip(Integer rows, Integer page, String name,String phone_number, String type, String start_date, String end_date,String pid,String phone,String order_state,String pay_type,String send_address) {
		// TODO Auto-generated method stub
		return mo.getMarginShip(rows, page, name, phone_number, type, start_date, end_date, pid, phone, order_state, pay_type, send_address);
	}
	public int getMarginOrderCount(String name ,String start_date1,String end_date2,String start_date,String end_date,String car_number,String type,String send_address,String phone){
		return mo.getMarginOrderCount(name, start_date1, end_date2, start_date, end_date, car_number, type, send_address, phone);
	}
	public int getMarginShipCount(String name,String phone_number, String type, String start_date, String end_date,String pid,String phone,String order_state,String pay_type,String send_address){
		// TODO Auto-generated method stub
		return mo.getMarginShipCount(name, phone_number, type, start_date, end_date, pid, phone, order_state, pay_type, send_address);
	}
	public int updataRemarkShip(String id, String charge_remark1,
			String charge_remark2) {
		// TODO Auto-generated method stub
		return mo.updataRemarkShip(id, charge_remark1, charge_remark2);
	}
	public int updataRemarkCustom(String id, String remarks_fee1,
			String remarks_fee2) {
		// TODO Auto-generated method stub
		return mo.updataRemarkCustom(id, remarks_fee1, remarks_fee2);
	}
}
