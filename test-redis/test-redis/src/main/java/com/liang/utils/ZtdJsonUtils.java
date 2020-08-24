package com.liang.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class ZtdJsonUtils {
	/***
     * 基于fastjson获取json对象的属性值
     * @param json字符串
     * @return 属性值
     */
    public static String getAttrValFromJsonObj(String jsonStr,String nodeName)
    {
    	String nodeVal = "";
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        if(jsonObject.containsKey(nodeName))
        {
        	nodeVal =  jsonObject.getString(nodeName);
        }
        return nodeVal;
    }
    /***
     * 基于fastjson获取json对象的属性值
     * @param json字符串
     * @return 属性值
     */
    public static String getAttrValFromJsonArray(String jsonStr,String nodeName)
    {
    	String nodeVal = "";
        JSONArray jsonArr = JSON.parseArray(jsonStr);
        if(jsonArr != null && jsonArr.size() > 0)
        {
        	nodeVal = getAttrValFromJsonObj(jsonArr.getString(0), nodeName);
        }
        return nodeVal;
    }
    /**
     * 基于fastjson将map转成json对象
     * @param map map对象
     * @return map对应的json对象
     */
    public static String toJsonFromMap(Map<String,Object> map)
    {
    	return JSON.toJSONString(map,true);
    }
    
    /**
     * 基于fastjson将map转成json对象
     * @param map map对象
     * @return map对应的json对象
     */
	public static <T> T paserObject(String objStr,Class<T> clazz)
    {
    	return JSON.parseObject(objStr,clazz);
    }
	
	/**
     * 基于fastjson将字符串数组转集合
	 * @param <T>
     * @param objStr json字符串
     * @return json对应的实体集合
     */
	public static <T> List<T> paserArray(String objStr,Class<T> clazz)
    {
    	return JSON.parseArray(objStr,clazz);
    }
    
    public static void main(String[] args) {
		String data = "{\"success\":true,\"errorCode\":\"\",\"errorMsg\":\"\",\"data\":[{\"details\":\"<response>  <is_success>true</is_success>  <receiver_name>隋卫卫</receiver_name>  <receiver_mobile_phone>18764577757</receiver_mobile_phone>  <goods_value>0</goods_value>  <items_value>0</items_value>  <good_list>    <item>      <item_name>****</item_name>      <number>0</number>      <item_value>0</item_value>    </item>  </good_list></response>\"}]}";
		/*System.out.println(getAttrValFromJsonObj(jsonStr,"data"));
		String json = getAttrValFromJsonObj(jsonStr,"data");
		System.err.println(getAttrValFromJsonArray(json, "details"));
		
		Map<String,Object> result = new HashMap<String, Object>();
		String success = ZtdJsonUtils.getAttrValFromJsonObj(data, "success");
		String errorCode = ZtdJsonUtils.getAttrValFromJsonObj(data, "errorCode");
		String errorMsg = ZtdJsonUtils.getAttrValFromJsonObj(data, "errorMsg");
		String dataInfo = ZtdJsonUtils.getAttrValFromJsonObj(data, "data");
		result.put("success", success);
		result.put("errorCode",errorCode);
		result.put("errorMsg", errorMsg);
		//result.put("data", dataInfo);
		String details = ZtdJsonUtils.getAttrValFromJsonArray(dataInfo, "details");
		if(details.indexOf("<response>") > -1)
		{
			Map<String,Object> map = new HashMap<String, Object>();
			*//**
			 * { "is_success":true, "receiver_name":"李", "receiver_telephone":"334", "receiver_mobile_phone":"12341" }
			 *//*
			String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + details;
			map.put("is_success", ZtdXmlUtils.getSingleNodeValByPath(xmlData, "/response/is_success"));
			map.put("receiver_name", ZtdXmlUtils.getSingleNodeValByPath(xmlData, "/response/receiver_name"));
			map.put("receiver_telephone", ZtdXmlUtils.getSingleNodeValByPath(xmlData, "/response/receiver_telephone"));
			map.put("receiver_mobile_phone", ZtdXmlUtils.getSingleNodeValByPath(xmlData, "/response/receiver_mobile_phone"));
			result.putAll(map);
		}
		System.err.println(JSON.toJSONString(result,true));
		
		System.out.println(JSON.toJSON(getMapFromJson("{\"returnCode\":\"000000\",\"returnMsg\":\"成功\"}")));
   */
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		map1.put("name", "name");
		map1.put("age", 11);
		map2.put("name", "name2");
		map2.put("age", 11.10);
		list.add(map1);
		list.add(map2);
		/*Message bean = new Message();
		bean.setSuccess(true);
		bean.setData("data");
		bean.setListData(list);
		System.out.println(JSON.toJSON(bean));*/
    }
    /**
     * 将JSon
     * @param json
     * @return
     */
	public static Map<String, String> getMapFromJson(String json) {
		Map<String,String> resultMap = new HashMap<String,String>();
		if(json != null && !"".equals(json))
		{
			JSONObject obj = JSON.parseObject(json);
			for(String key:obj.keySet())
			{
				resultMap.put(key, obj.getString(key));
			}
		}
		return resultMap;
	}
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJsonStr(Object obj)
	{
		JSON.DEFFAULT_DATE_FORMAT=ZtdDateUtils.DATE_SIMPLE_PATTERN;
		return JSON.toJSONString(obj,
				SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteDateUseDateFormat);
	}
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static List<?> getListFromJsonStr(String jsonStr,Class<?> clazz)
	{
		return JSON.parseArray(jsonStr, clazz);
	}
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static Object getObjFromJsonStr(String jsonStr,Class<?> clazz)
	{
		return JSON.parseObject(jsonStr, clazz);
	}
}