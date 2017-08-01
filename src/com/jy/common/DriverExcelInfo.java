package com.jy.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.jy.csaction.ExeclRead;
import com.jy.model.User;
import com.jy.model.UserInfo;
import com.jy.service.DriverInfoService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jy.model.Customer;
import com.jy.model.Driver;
import com.jy.service.impl.DriverInfoServiceImpl;

/*
 * 司机信息导入
 */
public class DriverExcelInfo {
	private DriverInfoService DriverInfoServiceImpl= SpringContextHolder.getBean("DriverInfoService");
	//StringBuffer order_num = new StringBuffer();
	/*private static ApplicationContext ac = new ClassPathXmlApplicationContext(
			new String[] { "classpath:mybatis-config.xml",
					"classpath:spring.xml" });*/

	public String impExcel(String execelFile, String username, String pid)
			throws Exception {
		/*
		 * ac = new ClassPathXmlApplicationContext(new String[] {
		 * "classpath:mybatis-config.xml", "classpath:spring.xml" });
		 */

		Driver tli;
		User user;
		// OrderCustom oc=OrderInfoServiceImpl.getUpdateOrderCustom(pid);
		List<Driver> tlist = new ArrayList<Driver>();
		List<User> userList = new ArrayList<User>();
		File file = new File(execelFile);
		String[][] result = ExeclRead.getData(file, 1);
		int rowLength = result.length;
		for (int i = 0; i < rowLength; i++) {
			tli = new Driver();
			user=new User();
			// ----------------------循环插入
			for (int j = 0; j < result[i].length; j++) {
				// 姓名
				if (j == 0) {
					tli.setDriver_name(result[i][j]);
				} else if (j == 1) {
					// 供应商
					tli.setDriver_suppliersname(result[i][j]);
				} else if (j == 2) {
					// 供应商公司
					tli.setSupplie_company(result[i][j]);
				} else if (j == 3) {
					// 司机电话
					tli.setDriver_phone(result[i][j]);
				} else if (j == 4) {
					// 车辆类型
					tli.setDriver_cartype(result[i][j]);
				} else if (j == 5) {
					// 车牌号
					tli.setDriver_carnumber(result[i][j]);
				} else if (j == 6) {
					// 手机品牌
					tli.setDriver_phonebrand(result[i][j]);
				} else if (j == 7) {
					// 手机型号
					tli.setDriver_phonemodel(result[i][j]);
				} else if (j == 8) {
					// 身份证号
					tli.setDriver_cardnumber(result[i][j]);
				} else if (j == 9) {
					tli.setDriver_address(result[i][j]);
				} else if (j == 10) {
					// 更新时间
					Date d = new Date();
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					tli.setDriver_updatedate(df.format(d));
				} else if (j == 11) {
					// 更新人
					tli.setDriver_updatepeople(username);
				} else if (j == 12) {
					// 创建时间
					Date d = new Date();
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					tli.setDriver_createtime(df.format(d));
				}
				tli.setDriver_id(UUIDUtils.getUUID());
				createUser(tli,user);
			}

			/*
			 * if (tli.getShiping_order_num() != "") {
			 * tli.setShipping_order(usersname); tli.setCreat_type(1);
			 * tli.setShipping_order_state(0);
			 * tli.setShiping_order_id(UUIDUtils.getUUID());
			 * tli.setUpdatetime(DateFormat.getDateTimeInstance().format( new
			 * Date()));
			 * tli.setOrder_time(DateFormat.getDateTimeInstance().format( new
			 * Date()));// 制单时间
			 * 
			 * //System.out.println(tli.getShiping_yueid()+"???????????????????????"
			 * );
			 * 
			 * }
			 */
			tlist.add(tli);
			userList.add(user);
		}
		// 筛选订单
		//tlist = removeDuplicate(tlist);
		// List<ShippingOrder> listdata=OrderInfoServiceImpl.getAorder();
		// tlist=removeDataDuplicate(tlist,listdata);
		int js = tlist.size();// 插入成功条数
		int zs = rowLength;// 总数

		String rb = zs + "#" + js + "#";
		/*
		 * for (int i = 0; i < tlist.size(); i++) { System.out.println("订单号：" +
		 * tlist.get(i).getShiping_order_num()); }
		 */
		//int num = 0;
		if (tlist.size() > 0) {
			 DriverInfoServiceImpl.saveDriverInfo(tlist);
			 DriverInfoServiceImpl.saveUserInfo(userList);
			 DriverInfoServiceImpl.saveRole(userList,"1");

		}

		return rb;

	}




	public List<Driver> removeDuplicate(List<Driver> list) {
	/*	for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				*//*
				 * if (list.get(j).getShiping_order_num().equals(list.get(i).
				 * getShiping_order_num())) { if(order_num.length()>0){
				 * order_num.append("," + list.get(j).getShiping_order_num());
				 * }else{ order_num.append(list.get(j).getShiping_order_num());
				 * } list.remove(j); }
				 *//*
			}
		}*/
		return list;
	}

	public List<Customer> removeDataDuplicate(List<Customer> list,
			List<Customer> listdata) {
		for (int i = 0; i < listdata.size(); i++) {
			/*
			 * System.out.println("循环次数："+i+"外层顶大号："+listdata.get(i).
			 * getShiping_order_num());
			 */for (int j = 0; j < list.size(); j++) {
				/*
				 * System.out.println("内层订单号："+list.get(j).getShiping_order_num()
				 * );
				 *//*
					 * if
					 * (list.get(j).getShiping_order_num().equals(listdata.get
					 * (i).getShiping_order_num())) {
					 * System.out.println("相同订单号："
					 * +list.get(j).getShiping_order_num());
					 * if(order_num.length()>0){ order_num.append("," +
					 * list.get(j).getShiping_order_num()); }else{
					 * order_num.append(list.get(j).getShiping_order_num()); }
					 * list.remove(j); }
					 */
			}
		}
		return list;
	}

	private void createUser(Driver driver,User user){
		String id = UUIDUtils.getUUID();
		user.setId(id);
		user.setUsername(PinYin4J.ToPinyin(driver.getDriver_name()));
		user.setDriver_id(driver.getDriver_id());
		user.setPassword(MD5.toMD5("123456"));
		user.setRealName(driver.getDriver_name());
		user.setDid("");
		user.setFlag("1");
	}
}
