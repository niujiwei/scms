package com.jy.quartz;

import com.google.gson.Gson;
import com.jy.common.HttpRequesterUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.omg.CORBA.OBJ_ADAPTER;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by njw on 2017/7/4.
 */
public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("111111dafd");
        list.add("sdfasdfas");
        list.add("adfasdf");
        Gson gson = new Gson();
        File file = new File("\\cut2069093653555092319.png");
        try {
            byte [] read = new byte[1024];
            InputStream inputStream = new FileInputStream(file);
            int i = inputStream.read(read);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(gson.fromJson("[123123,123123,1321312,23123]",List.class));
    }


}
