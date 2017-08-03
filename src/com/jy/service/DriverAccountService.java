package com.jy.service;

import com.jy.dao.DriverAccountDao;
import com.jy.model.ShippingOrder;
import com.jy.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by njw on 2017/8/1.
 */
public interface DriverAccountService {


    Map<String, Object> getDriverAccountMapInfo(User user, int page, int length, String startTime, String endTime, String driver_name, String driver_phone);


    Map<String, Object> getDriverAccountOrderMapInfo(User user, int page, int length, String pid, String shiping_order_num, String end_address, String custom_name, String receipt_name, String receipt_tel, String send_mechanism, String send_time, String min_send_time, String max_send_time, String driverId, String shipperorder_id, String clearing_state);

    List<ShippingOrder> getOutPutDriverAccountShippingOrder(User user, String driver_id, String min_send_time, String max_send_time);
}
