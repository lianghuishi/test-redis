package com.liang.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service
public class OrderService {

	public String addOrder(String orderId, String date) {
		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("orderId", orderId);
		result.put("date", date);
		String retStr = JSONObject.toJSONString(result);
		return retStr;

	}
	
	public String getOrder(String orderId, String date){
		
		//查询数据库
		Map<String, String> result = new HashMap<String, String>();
		result.put("orderId", orderId);
		result.put("date", date);
		String retStr = JSONObject.toJSONString(result);
		return retStr;
		
	}

}
