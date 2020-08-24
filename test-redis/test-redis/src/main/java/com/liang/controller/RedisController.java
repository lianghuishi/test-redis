package com.liang.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.liang.service.OrderService;
import com.liang.utils.ZtdDateUtils;
import com.liang.utils.ZtdJsonUtils;

@Controller
@RequestMapping({ "/test" })
public class RedisController {

	@Autowired
	private CacheContext redis;

	@Autowired
	private OrderService orderService;

	private String prefix = "test";

	@RequestMapping({ "/test.do" })
	@ResponseBody
	public String test(/* @RequestParam("test") String test */ /*
																 * @RequestBody
																 * String
																 * data, @RequestParam
																 * ("test")
																 * String test
																 */) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "test");
		map.put("name2", "测试2");
		map.put("name3", "测试3");
		map.put("name4", "测试4");

		String jsonString = JSONObject.toJSONString(map);

		redis.setValue("test", jsonString);
		String value = redis.getValue("test");

		return value;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping({ "/updOrder.do" })
	@ResponseBody
	public String updOrder(@RequestBody String reqBody) throws InterruptedException {
		Map<String, String> reqMap = ZtdJsonUtils.paserObject(reqBody, Map.class);
		String orderId = reqMap.get("orderId");
		String date = reqMap.get("date");
		
		if (StringUtils.isBlank(date)) {
			date = ZtdDateUtils.getDateStr();
		}

		redis.del(prefix + orderId);
		String addOrder = orderService.addOrder(orderId, date);
		Thread.sleep(1000);
		redis.del(prefix + orderId);

		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "true");
		result.put("msg", "保存成功");
		result.put("data", addOrder);
		String retStr = JSONObject.toJSONString(result);

		return retStr;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping({ "/getOrder.do" })
	@ResponseBody
	public Map<String,String> getOrder(@RequestBody String reqBody) throws InterruptedException {
		Map<String, String> reqMap = ZtdJsonUtils.paserObject(reqBody, Map.class);
		String orderId = reqMap.get("orderId");
		String date = reqMap.get("date");
		
		String getOrder = redis.getValueResetExpire(prefix + orderId);

		if (StringUtils.isBlank(getOrder)) {
			getOrder = orderService.getOrder(orderId, date);
			redis.setValue(prefix + orderId, getOrder);
		}

		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "true");
		result.put("msg", "保存成功");
		result.put("data", getOrder);
//		String retStr = JSONObject.toJSONString(result);

		return result;
	}

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "test");
		map.put("name2", "测试2");
		map.put("name3", "测试3");

		String jsonString = JSONObject.toJSONString(map);

		System.out.println(jsonString);
	}
}
