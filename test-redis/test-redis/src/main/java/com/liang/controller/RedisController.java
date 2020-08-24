package com.liang.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping({ "/test" })
public class RedisController {

	@Autowired
	private CacheContext redis;

	 
	@RequestMapping({"/test.do"})
	@ResponseBody
	public String test(/*@RequestParam("test") String test*/ /*@RequestBody String data, @RequestParam("test") String test*/) {
		
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

	
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "test");
		map.put("name2", "测试2");
		map.put("name3", "测试3");
		
		String jsonString = JSONObject.toJSONString(map);
		
		System.out.println(jsonString);
	}
}
