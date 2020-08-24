package com.liang.test;

import java.util.HashMap;
import java.util.Map;

import com.liang.utils.ZtdHTTPUtils;
import com.liang.utils.ZtdJsonUtils;

public class TestPostGetOrder {

	public static void main(String[] args) {
		
		String url = "http://localhost:8080/test-redis/test/getOrder.do";
		
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("orderId", "003");
		reqMap.put("date", "测试003");
		String jsonStr = ZtdJsonUtils.toJsonStr(reqMap);

		String sendPostStr = ZtdHTTPUtils.sendPostStr(url, jsonStr);
		
		System.out.println(sendPostStr);
		
	}
	
}
