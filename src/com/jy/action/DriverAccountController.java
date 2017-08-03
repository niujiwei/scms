package com.jy.action;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jy.common.ExportUtils;
import com.jy.common.SessionInfo;
import com.jy.model.*;
import com.jy.service.DriverAccountService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping(value="/driverAccount.do")
public class DriverAccountController {

    @Resource
    private DriverAccountService driverAccountService;
    /**
     * 跳转供应商结算页面
     *
     * @return
     */
    @RequestMapping(params = "method=toDriverAccount")
    public String toDriverAccount() {
        return "driverAccount/driverAccount";
    }


    @RequestMapping(params = "method=getDriverAccountInfo")
    public @ResponseBody Map<String, Object> getDriverAccountInfo(String rows, String page, String startTime, String endTime, String driver_name, String driver_phone, HttpSession session){
        Integer rows1 = 0;// 当前有几行
        Integer page1 = 1;// 当前有几页
        if (rows != null && !"".equals(rows)) {
            rows1 = Integer.parseInt(rows);
        }
        if (page != null && !"".equals(page)) {
            page1 = Integer.parseInt(page);
        }
        User user = (User) session.getAttribute(SessionInfo.SessionName);

        return driverAccountService.getDriverAccountMapInfo(user,(page1 - 1) * rows1,
                rows1,startTime,endTime,driver_name,driver_phone);
    }


    @RequestMapping(params = "method=toDriverAccountOrder")
    public String toDriverAccountOrder(String driverId, String pid, String min_send_time,
                                   String max_send_time, HttpServletRequest request) {
        request.setAttribute("flg", pid);
        request.setAttribute("min_send_time", min_send_time);
        request.setAttribute("max_send_time", max_send_time);
        request.setAttribute("driverId", driverId);
        return "driverAccount/driverOrder";
    }


    @RequestMapping(params = "method=getDriverAccountOrder")
    public @ResponseBody
    Map getDriverAccountOrder(String rows, String page,  String pid, String shiping_order_num, String end_address, String custom_name, String receipt_name, String receipt_tel, String send_mechanism, String send_time, String min_send_time, String max_send_time, String driverId, String shipperorder_id, String clearing_state, HttpSession session) {
        Integer rows1 = 0;// 当前有几行
        Integer page1 = 1;// 当前有几页

        if (rows != null && !"".equals(rows)) {
            rows1 = Integer.parseInt(rows);
        }
        if (page != null && !"".equals(page)) {
            page1 = Integer.parseInt(page);
        }
        User user = (User) session.getAttribute(SessionInfo.SessionName);

        return driverAccountService.getDriverAccountOrderMapInfo(user,(page1 - 1) * rows1,
                rows1,pid,shiping_order_num,end_address,custom_name,receipt_name,receipt_tel,send_mechanism,send_time,min_send_time,max_send_time,driverId,shipperorder_id,clearing_state);
    }


    @RequestMapping(params = "method=outDriverAccountOrder")
    public @ResponseBody
    String outSuppOrder(String[] dataIds, String suppliersName,
                        String suppliersSendTime, String suSendTime, String driverId,
                        String suppliersAddress, HttpServletResponse response,
                        HttpSession session) {
        // System.out.println(suppliersName+suppliersSendTime+suSendTime);

        User user = (User) session.getAttribute(SessionInfo.SessionName);
        String send_mechanism = "";
        String propertiesPath ="driverAccountData.properties";
        String filename = "司机结算信息导出";
        InputStream in = AbnormalReportController.class
                .getResourceAsStream("/../exportData/"+propertiesPath);
        Map<String, List<String>> map = ExportUtils.getHeadTitle(in);//通用获取headtitle fieldName
        List<String> headTitle =map.get("headTitle");
        List<String> fieldName =map.get("fieldName");
        try {
            // 声明一个工作薄
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 生成一个表格
            response.setContentType("application/vnd.ms-excel;");
            response.setHeader("Content-Disposition", "attachment;filename=\""
                    + new String(filename.getBytes("UTF-8"), "iso-8859-1")
                    + ".xls\"");
            OutputStream ouputStream = response.getOutputStream();
            Gson gson = new Gson();
            if (driverId != null) {
                List<DriverAccount> driverAccounts= gson.fromJson(driverId,
                        new TypeToken<List<DriverAccount>>() {
                        }.getType());
                if (driverAccounts==null) return null ;
                for (int i=0 ;i<driverAccounts.size();i++){
                    DriverAccount driverAccount = driverAccounts.get(i);
                    if (driverAccount != null) {
                        if (workbook.getSheetIndex(driverAccount.getDriver_name())==-1){
                            ExportUtils.outputHeaders((String[]) headTitle.toArray(new String[headTitle.size()]), workbook,
                                    i+"."+driverAccount.getDriver_name());
                        }

                        List<ShippingOrder> list  = driverAccountService.getOutPutDriverAccountShippingOrder(user,driverAccount.getDriver_id(),driverAccount.getMin_send_time(),driverAccount.getMax_send_time());
                      /*  = sa.getOutSuppOrder(
                                jySuppliers2.getSuppliersName(),
                                jySuppliers2.getSuppliersSendTime(),
                                jySuppliers2.getSuSendTime(), send_mechanism,jySuppliers2.getSuppliersId());*/
                        ExportUtils.outputColums((String[]) fieldName.toArray(new String[fieldName.size()]), list, workbook, 1,
                                "司机结算信息导出");
                    }

					/*
					 * JySuppliers ship = sa.getOutClass(suppliersName,
					 * suppliersSendTime, suSendTime);
					 */

                }

            }

            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
