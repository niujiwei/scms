package com.jy.quartz;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jy.common.HttpRequesterUtils;
import com.jy.common.SpringContextHolder;
import com.jy.model.SCMBusinessRemarkData;
import com.jy.model.SCMResult;
import com.jy.model.SCMSupplierUser;
import com.jy.service.SCMService;
import common.Logger;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by njw on 2017/7/11.
 */
public class SCMQuartz {
    private SCMService scmService = SpringContextHolder.getBean("SCMServiceImp");
    private static final String SCM_Url="http://60.208.27.155:5410/NSCM/";
    //private static final String SCM_Url="http://192.168.5.48:8080/NSCM/";
    private Logger log = Logger.getLogger(this.getClass());
    public void crateShippingOrder() {
        scmService.insertSCMShippingOrder();
        scmService.createSCMBusinessRemarkDataOrder();
    }

    public void transmissionShippingOrder(){
        List<SCMBusinessRemarkData> list = scmService.getSCMBusinessRemarkData("0", "3", 50);
        Gson gson = new Gson();
        String url = SCM_Url+"bsre.do?method=saveBusRemark";
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userJson",gson.toJson(list));
        try {
            String result = HttpRequesterUtils.doPost(url,map,"utf-8");
            JSONObject jsonObject = JSONObject.fromObject(result);
            List<String> errorList =  gson.fromJson(jsonObject.getString("errorList"),new TypeToken<List<String>>(){}.getType());
            List<String> successList =  gson.fromJson(jsonObject.getString("successList"),new TypeToken<List<String>>(){}.getType());

            SCMResult scmResult = gson.fromJson(result, new TypeToken<SCMResult>() {
            }.getType());
            scmResult.setErrorList(errorList);
            scmResult.setSuccessList(successList);
            scmService.updateSCMBusinessRemarkDataState(1,scmResult.getSuccessList());
            scmService.updateSCMUpdateState(1,scmResult.getSuccessList(),0 ,3);
            scmService.updateSCMBusinessRemarkDataState(0,scmResult.getErrorList());
            scmService.updateSCMUpdateState(0,scmResult.getErrorList(),4 ,6);
        } catch (Exception e) {
            log.error(e,e);
        }
    }


    public void createSupplies(){
        scmService.createSCMSupplierUser();
    }
    public void tSuppliers(){
        int size=50;
        int  count = scmService.getSCMSupplierUserCount(size,"0");
        int page = (int) Math.ceil(count/size);
        List<SCMSupplierUser> list;
        Map<String,Object> map;
        String url = "http://60.208.27.155:5410/NSCM/supu.do?method=saveSuppUser";
        for (int i=0;i<=page;i++){
            list = scmService.getSCMSupplierUser(i*size,size, "0");
            if (list==null||list.size()==0) return;
            Gson gson = new Gson();
            gson.toJson(list);
            map = new HashMap<String, Object>();
            map.put("userJson",gson.toJson(list));
            try {
                String result = HttpRequesterUtils.doPost(url,map,"utf-8");
                System.out.println(result);
                JSONObject jsonObject = JSONObject.fromObject(result);
                List<String> errorList =  gson.fromJson(jsonObject.getString("errorList"),new TypeToken<List<String>>(){}.getType());
                List<String> successList =  gson.fromJson(jsonObject.getString("successList"),new TypeToken<List<String>>(){}.getType());
                SCMResult scmResult = gson.fromJson(result, new TypeToken<SCMResult>() {
                }.getType());
                scmResult.setErrorList(errorList);
                scmResult.setSuccessList(successList);
                scmService.updateSCMSupplierUser(1,successList);
                scmService.updateSCMSupplierUser(0,errorList);
            } catch (Exception e) {
                System.out.println("出错了");
                e.printStackTrace();
            }
        }

    }

    public void tSuppliersHead(){
        int size=2;
        String state="1";
        String url = "http://192.168.5.48:8080/NSCM/supu.do?method=saveSuppHead";
        int count = scmService.getSCMSupplierUserHeadCount(state);
        if (count==0) return;
        int page = (int) Math.ceil(count/size);
        Map<String,List<String>> map;
        for (int i=0;i<=page;i++){
            List<String> list = scmService.getSCMSupplierUserHead(i*size,size,state);
            map=new HashMap<String, List<String>>();
            map.put("userFile",list);
            String result = HttpRequesterUtils.formListUpload(url,null,map);
            log.info(result);
        }
    }





}
