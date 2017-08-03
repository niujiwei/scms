package com.jy.service.impl;

import com.jy.dao.DriverAccountDao;
import com.jy.dao.SuppliersAccountDao;
import com.jy.model.Customer;
import com.jy.model.DriverAccount;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.DriverAccountService;
import com.jy.service.DriverInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by njw on 2017/8/1.
 */
@Service
public class DriverAccountServiceImp implements DriverAccountService{
    @Resource
    private  DriverAccountDao driverAccountDao;
    @Resource
    private SuppliersAccountDao suppliersAccountDao;
    @Override
    public Map<String, Object> getDriverAccountMapInfo(User user, int page, int length, String startTime, String endTime, String driver_name, String driver_phone) {
       Map<String,Object> map = new HashMap<String, Object>();
       if (user==null) return map;
        List<DriverAccount> list = new ArrayList<DriverAccount>();
        int count = 0;
        if (user.getFlag().equals("0")) {// 管理员
            list = driverAccountDao.getDriverAccountInfo(page,length,startTime,endTime,driver_name,driver_phone);
            count = driverAccountDao.getDriverAccountCount(startTime, endTime, driver_name, driver_phone);
        } else if (user.getFlag().equals("1")) {// 司机
            list = driverAccountDao.driverGetDriverAccountInfo(page,length,startTime,endTime,driver_name,driver_phone,user.getDriver_id());
            count = driverAccountDao.driverGetDriverAccountCount(startTime, endTime, driver_name, driver_phone,user.getDriver_id());
        } else if (user.getFlag().equals("2")) {// 供应商

        } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
            list = driverAccountDao.otherGetDriverAccountInfo(page,length,startTime,endTime,driver_name,driver_phone,user.getCustomer_id());
            count = driverAccountDao.otherGetDriverAccountCount(startTime, endTime, driver_name, driver_phone,user.getCustomer_id());
        }
        map.put("total", count);
        map.put("rows", list);

        return map;
    }

    @Override
    public Map<String, Object> getDriverAccountOrderMapInfo(User user, int page, int length, String pid, String shiping_order_num, String end_address, String custom_name, String receipt_name, String receipt_tel, String send_mechanism, String send_time, String min_send_time, String max_send_time, String driverId, String shipperorder_id, String clearing_state) {
        Map<String,Object> map = new HashMap<String, Object>();
        if (user==null) return map;
        List<ShippingOrder> list = new ArrayList<ShippingOrder>();
        int count = 0;
        if (user.getFlag().equals("0")) {// 管理员
            count = driverAccountDao.getDriverAccountOrderCount(pid,shiping_order_num,end_address,custom_name,receipt_name,receipt_tel,send_mechanism,send_time,min_send_time,max_send_time,driverId,shipperorder_id,clearing_state);
            list = driverAccountDao.getDriverAccountOrder(page,length,pid,shiping_order_num,end_address,custom_name,receipt_name,receipt_tel,send_mechanism,send_time,min_send_time,max_send_time,driverId,shipperorder_id,clearing_state);
        } else if (user.getFlag().equals("1")) {// 司机
            count = driverAccountDao.getDriverAccountOrderCount(pid,shiping_order_num,end_address,custom_name,receipt_name,receipt_tel,send_mechanism,send_time,min_send_time,max_send_time,user.getDriver_id(),shipperorder_id,clearing_state);
            list = driverAccountDao.getDriverAccountOrder(page,length,pid,shiping_order_num,end_address,custom_name,receipt_name,receipt_tel,send_mechanism,send_time,min_send_time,max_send_time,user.getDriver_id(),shipperorder_id,clearing_state);
        } else if (user.getFlag().equals("2")) {// 供应商

        } else if (user.getFlag().equals("3") || user.getFlag().equals("4")) {// 信息员
            Customer customer = suppliersAccountDao.getCustomer(user.getCustomer_id());
            String customerName= customer==null?"":customer.getCustomer_name();
            count = driverAccountDao.getDriverAccountOrderCount(pid,shiping_order_num,end_address,customerName,receipt_name,receipt_tel,send_mechanism,send_time,min_send_time,max_send_time,driverId,shipperorder_id,clearing_state);
            list = driverAccountDao.getDriverAccountOrder(page,length,pid,shiping_order_num,end_address,customerName,receipt_name,receipt_tel,send_mechanism,send_time,min_send_time,max_send_time,driverId,shipperorder_id,clearing_state);
        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }

    public List<ShippingOrder> getOutPutDriverAccountShippingOrder(User user, String driver_id, String min_send_time, String max_send_time){
        if (user==null) return new ArrayList<ShippingOrder>();
        Customer customer = suppliersAccountDao.getCustomer(user.getCustomer_id());
        String customerName= customer==null?null:customer.getCustomer_name();
        return driverAccountDao.getOutPutDriverAccountOrder(driver_id,min_send_time,max_send_time,customerName);
    }
}
