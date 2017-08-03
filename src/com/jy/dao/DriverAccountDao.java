package com.jy.dao;

import com.jy.model.DriverAccount;
import com.jy.model.ShippingOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by njw on 2017/8/2.
 */
public interface DriverAccountDao {

    List<DriverAccount> getDriverAccountInfo(@Param("page") Integer page, @Param("length") Integer length, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("driver_name") String driver_name, @Param("driver_phone") String driver_phone);

    List<DriverAccount> driverGetDriverAccountInfo(@Param("page") Integer page, @Param("length") Integer length, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("driver_name") String driver_name, @Param("driver_phone") String driver_phone,@Param("driver_id") String driver_id);

    List<DriverAccount> otherGetDriverAccountInfo(@Param("page") Integer page, @Param("length") Integer length, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("driver_name") String driver_name, @Param("driver_phone") String driver_phone,@Param("customer_id") String customer_id);



    int getDriverAccountCount(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("driver_name") String driver_name, @Param("driver_phone") String driver_phone);


    int driverGetDriverAccountCount(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("driver_name") String driver_name, @Param("driver_phone") String driver_phone,@Param("driver_id") String driver_id);

    int otherGetDriverAccountCount(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("driver_name") String driver_name, @Param("driver_phone") String driver_phone,@Param("customer_id") String customer_id);





    int getDriverAccountOrderCount(@Param("pid") String pid, @Param("shiping_order_num") String shiping_order_num,
                          @Param("end_address") String end_address, @Param("custom_name") String custom_name, @Param("receipt_name") String receipt_name,
                          @Param("receipt_tel") String receipt_tel, @Param("send_mechanism") String send_mechanism, @Param("send_time") String send_time,
                             @Param("min_send_time") String min_send_time, @Param("max_send_time") String max_send_time, @Param("driverId") String driverId,
                          @Param("shipperorder_id") String shipperorder_id, @Param("clearing_state") String clearing_state);


    List<ShippingOrder> getDriverAccountOrder(@Param("rows") int rows, @Param("page") int page, @Param("pid") String pid, @Param("shiping_order_num") String shiping_order_num,
                                              @Param("end_address") String end_address, @Param("custom_name") String custom_name, @Param("receipt_name") String receipt_name,
                                              @Param("receipt_tel") String receipt_tel, @Param("send_mechanism") String send_mechanism, @Param("send_time") String send_time,
                                              @Param("min_send_time") String min_send_time, @Param("max_send_time") String max_send_time, @Param("driverId") String driverId,
                                              @Param("shipperorder_id") String shipperorder_id, @Param("clearing_state") String clearing_state);

    List<ShippingOrder> getOutPutDriverAccountOrder(@Param("driverId")String driverId, @Param("min_send_time") String min_send_time, @Param("max_send_time") String max_send_time,@Param("send_mechanism") String custom_name);

}
