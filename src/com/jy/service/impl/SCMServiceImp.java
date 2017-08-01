package com.jy.service.impl;

import com.jy.dao.SCMDao;
import com.jy.model.OrderHistory;
import com.jy.model.SCMBusinessRemarkData;
import com.jy.model.SCMSupplierUser;
import com.jy.model.ShippingOrder;
import com.jy.service.SCMService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("SCMServiceImp")
public class SCMServiceImp implements SCMService {
    @Resource
    private SCMDao scmDao;

    @Override
    public boolean insertSCMShippingOrder() {

        List<ShippingOrder> list = scmDao.getShippingOrder("","");
        List<ShippingOrder> insertList= new ArrayList<ShippingOrder>();

        if (list==null||list.size()==0){
            scmDao.updateScmUpdateTime("");
            return false;
        }
        for(ShippingOrder shippingOrder :list){
            int count = scmDao.countShippingOrder(shippingOrder.getShiping_order_id());
            if (count!=0) continue;
            insertList.add(shippingOrder);
        }

        if (insertList.size()==0) {
            scmDao.updateScmUpdateTime("");
            return false;
        }

        int insertCount = scmDao.insertShippingOrder(insertList);
        scmDao.updateScmUpdateTime("");
        return insertCount>0;
    }


    @Override
    public int createSCMBusinessRemarkDataOrder() {
        List<ShippingOrder> list = scmDao.getScmShippingOrder("0","3");
        List<SCMBusinessRemarkData> scmBusinessRemarkDataList = new ArrayList<SCMBusinessRemarkData>();
        for (ShippingOrder shippingOrder:list){
            List<OrderHistory> orderHistories = scmDao.getOrderHistory(shippingOrder.getShiping_order_id());
            if (orderHistories==null||orderHistories.size()==0) continue;
            for (OrderHistory orderHistory:orderHistories){
                int i = scmDao.getSCMBusinessCount(orderHistory.getOrder_history_id());
                if(i!=0)continue;
                SCMBusinessRemarkData scmBusinessRemarkData = scmDao.getSCMBusinessRemarkDataOrder(orderHistory.getOrder_history_id());
                scmBusinessRemarkDataList.add(scmBusinessRemarkData);
            }
        }
        if (scmBusinessRemarkDataList.size()==0) return 0;

        return scmDao.insertSCMBusinessRemarkData(scmBusinessRemarkDataList);
    }

    @Override
    public List<SCMBusinessRemarkData> getSCMBusinessRemarkData(String state, String count,Integer max_size){
        return scmDao.getSCMBusinessRemarkData(state,count,max_size);
    }

    @Override
    public int updateSCMBusinessRemarkDataState(Integer state, List<String> dmsIds) {
        if(dmsIds==null||dmsIds.size()==0) return 0;
        return  scmDao.updateSCMBusinessRemarkData(state,dmsIds);
    }

    @Override
    public int updateSCMUpdateState(Integer state, List<String> dmsIds,Integer minCount,Integer maxCount) {
        if(dmsIds==null||dmsIds.size()==0) return 0;
        return scmDao.updateScmShippingOrder(state,dmsIds,minCount,maxCount);
    }

    @Override
    public int createSCMSupplierUser() {
        List<SCMSupplierUser> list = scmDao.getInsertSCMSupplierUser(null);
        List<SCMSupplierUser> insert = new ArrayList<SCMSupplierUser>();
        if (list==null||list.size()==0) return 0;
        for (SCMSupplierUser scmSupplierUser:list){
            if (scmSupplierUser==null) continue;
            int count = scmDao.selectSCMSupplierUserCount(scmSupplierUser.getSupUserId());
            if (count!=0) continue;
            insert.add(scmSupplierUser);
        }
        if (insert.size()==0) return 0;
        return scmDao.insertSCMSupplierUser(insert);
    }

    @Override
    public List<SCMSupplierUser> getSCMSupplierUser(Integer page,Integer size, String state) {
        return scmDao.getSCMSupplierUser(page,size,state);
    }

    @Override
    public int updateSCMSupplierUser(Integer state, List<String> ids) {
        if (ids==null||ids.size()==0) return 0;
        return scmDao.updateSCMSupplierUser(state,ids);
    }

    @Override
    public List<String> getSCMSupplierUserHead(Integer page,Integer size, String state) {
        return scmDao.getSCMSupplierUserHead(page,size,state);
    }

    @Override
    public int getSCMSupplierUserHeadCount(String state) {
        return scmDao.getSCMSupplierUserHeadCount(state) ;
    }

    @Override
    public int getSCMSupplierUserCount(Integer size, String state) {
        return scmDao.getSCMSupplierUserCount(size, state);
    }
}
