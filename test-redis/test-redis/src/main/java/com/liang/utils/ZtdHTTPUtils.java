package com.liang.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 利用HttpClient对象发送http请求的处理工具类
 * @author wst
 * @since 2016-12-18
 *
 */
public class ZtdHTTPUtils {
	private static Log log = LogFactory.getLog(ZtdHTTPUtils.class);  
	//utf-8编码格式
    public static final String URL_UTF8 = "UTF-8";  
    //gbk编码格式
    public static final String URL_GBK = "GBK";  
    //参数分隔符
    private static final String SEPARATER_FLAG = "&";  
    //默认返回空字符串
    private static final String EMPTY = "";  
  
    //复用连接的连接管理类
    private static MultiThreadedHttpConnectionManager connectionManager = null;  
    //ConnectionManager管理的连接池中取出连接的超时时间（10秒）
    private static int connectionTimeOut = 30 * 1000;  
    //建立连接超时时间
    private static int socketTimeOut = 60 * 1000;  
    //每台主机的最大连接数
    private static int maxConnectionPerHost = 500;  
    //最大连接数
    private static int maxTotalConnections = 500;  
  
    private static HttpClient client;  
  
    static{  
    	//实例化复用连接的HttpClient对象
        connectionManager = new MultiThreadedHttpConnectionManager();  
        connectionManager.getParams().setConnectionTimeout(connectionTimeOut);  
        connectionManager.getParams().setSoTimeout(socketTimeOut);  
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);  
        connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);  
        client = new HttpClient(connectionManager);  
    }  
    public static String sendPost(String url, Map<String, String> params){  
    	return sendPost(url,params,URL_UTF8);
    }
    /**
     * httpclient发送post请求
     * @param url 请求url路径
     * @param params 请求参数
     * @param enc 请求编码
     * @return 响应结果
     */
	public static String sendPost(String url, Map<String, String> params, String enc){  
		  
        String response = EMPTY;          
        PostMethod postMethod = null;  
        try {  
            postMethod = new PostMethod(url);  
            postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);  
            postMethod.addRequestHeader("connection", "Keep-Alive");
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,enc); 
            //将表单的值放入postMethod中  
            NameValuePair[] names  = new NameValuePair[params.size()];
            Set<String> keySet = params.keySet();  
            int index = 0;
            for(String key : keySet){ 
            	names[index++] = new NameValuePair(key, params.get(key));
            }
            postMethod.setRequestBody(names);
            //执行postMethod  
            int statusCode = client.executeMethod(postMethod);  
            if(statusCode == HttpStatus.SC_OK) {  
                response = postMethod.getResponseBodyAsString();  
            }else{  
                log.error("响应状态码 = " + postMethod.getStatusCode());  
            }  
        }catch(HttpException e){  
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);  
            e.printStackTrace();  
        }catch(IOException e){  
            log.error("发生网络异常", e);  
            e.printStackTrace();  
        }finally{  
            if(postMethod != null){  
                postMethod.releaseConnection();  
                postMethod = null;  
            }  
        }  
          
        return response;  
    }  
      
	/**
     * httpclient发送post请求
     * @param url 请求url路径
     * @param params 请求参数
     * @param enc 请求编码
     * @return 响应结果
     */
	public static String sendPostSpecail(String url, Map<String, String> params){  		  
        String response = EMPTY;          
        PostMethod postMethod = null;  
        try {  
            postMethod = new PostMethod(url);  
            postMethod.addRequestHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImUxMzU2MzZlMGM3ZDIwNDM0Y2UxZDk2NzRhNmFlNDJiODllODA4ZDg5YmM5NjQ1MTFkYTg4NDlkYmRmNjNiYmJhOGMyNGNlMzQzZmRmNmRiIn0.eyJhdWQiOiI1IiwianRpIjoiZTEzNTYzNmUwYzdkMjA0MzRjZTFkOTY3NGE2YWU0MmI4OWU4MDhkODliYzk2NDUxMWRhODg0OWRiZGY2M2JiYmE4YzI0Y2UzNDNmZGY2ZGIiLCJpYXQiOjE1MzQ3NDg4MzgsIm5iZiI6MTUzNDc0ODgzOCwiZXhwIjoxNTY2Mjg0ODM4LCJzdWIiOiI2MDciLCJzY29wZXMiOltdfQ.frZrL1jCmCzGgBkLMSGAi2azVU4h3QVH4Go08T_c4mZY4dFMUiRxty7UM_lfymgrnScB6wZwAV7NTHOzHdxb0b-ZmJ_8h-G5oFB-gW0OwTKMbLvNaCs0YhvKvIjoPxjEm0qD0G-8MfrtudYn5II1864k7f8H1oYtDs_meabsSslFftHXEQToJ8vYqluklwxLFMA8dfjqpJ_b99L1_96j1f9Rsa_oumHFVjK5TaEOSxRjFDgyYrP-lyk8iefpfbv-pPbGRusJOrTcffbtPcVySGDi079h9uZRnrJwTe-VtfjmkxZ1g0QMMQWRPqtVfGjV5722XYbkTwCRxnpEZ2_tBVX2CzI8vtwPjTXWHuL0EtRXlm6QdaHEi2xmOSUXRpu5bSRwr1nRTp87EYbgavXqxmSMiR0b7MIj_S5_g_NqPR67uJtoqlsJeKlcgZm9nPsMfDMiMfzCQJTA0AdrIZDVs6KIx_fEM_ZTqbkqFJ4JvUPasIs3HtNfxeD2pX5i2EWcpZvcLBv1eefAnTC2gdTOixOUEWK_xDOv4JL9DFj24lCskX6elch3j2lvaqDXhxkWY1qu5WI7aqRafkooujujc_GpkhRLZ5LsIEo3F2NPlvaUNAsq1SzQ_csJQIpTL1CFgFp9OFs2mCn7RRtZoQMByHopanEv8dhleRrZJ3m8k-w");
            postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + URL_UTF8);  
            postMethod.addRequestHeader("connection", "Keep-Alive");
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,URL_UTF8);  
            //将表单的值放入postMethod中  
            NameValuePair[] names  = new NameValuePair[params.size()];
            Set<String> keySet = params.keySet();  
            int index = 0;
            for(String key : keySet){ 
            	names[index++] = new NameValuePair(key, params.get(key));
            }
            postMethod.setRequestBody(names);
            //执行postMethod  
            int statusCode = client.executeMethod(postMethod);  
            if(statusCode == HttpStatus.SC_OK) {  
                response = postMethod.getResponseBodyAsString();  
            }else{  
                System.out.println("响应状态码 = " + postMethod.getStatusCode());  
            }  
        }catch(HttpException e){  
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);  
            e.printStackTrace();  
        }catch(IOException e){  
            log.error("发生网络异常", e);  
            e.printStackTrace();  
        }finally{  
            if(postMethod != null){  
                postMethod.releaseConnection();  
                postMethod = null;  
            }  
        }  
          
        return response;  
    }  
	
	
	/**
     * httpclient发送post请求
     * @param url 请求url路径
     * @param params 请求参数
     * @param enc 请求编码
     * @return 响应结果
     */
	@SuppressWarnings("deprecation")
	public static String sendPostStr(String url, String json){  		  
        String response = EMPTY;          
        PostMethod postMethod = null;  
        try {
            postMethod = new PostMethod(url);  
//            postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + URL_UTF8);  
            postMethod.addRequestHeader("Content-Type", "application/json");
            postMethod.addRequestHeader("connection", "Keep-Alive");
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,URL_UTF8);  
            //将表单的值放入postMethod中 
            postMethod.setRequestBody(json);
            //执行postMethod  
            int statusCode = client.executeMethod(postMethod);  
            if(statusCode == HttpStatus.SC_OK) {  
                response = postMethod.getResponseBodyAsString();  
            }else{  
                System.out.println("响应状态码 = " + postMethod.getStatusCode());  
            }  
        }catch(HttpException e){  
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);  
            e.printStackTrace();  
        }catch(IOException e){  
            log.error("发生网络异常", e);  
            e.printStackTrace();  
        }finally{  
            if(postMethod != null){  
                postMethod.releaseConnection();  
                postMethod = null;  
            }  
        }  
          
        return response;  
    }  
	
	/**
     * httpclient发送post请求
     * @param url 请求url路径
     * @param params 请求参数
     * @param enc 请求编码
     * @return 响应结果
     */
	@SuppressWarnings("deprecation")
	public static String sendPostStr(String url, Map<String,String> heads, String json){  		  
        String response = EMPTY;          
        PostMethod postMethod = null;  
        try {  
            postMethod = new PostMethod(url);  
//            postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + URL_UTF8);  
            postMethod.addRequestHeader("connection", "Keep-Alive");
            if(heads != null && !heads.isEmpty())
			{
				for(String key: heads.keySet())
				{
					postMethod.addRequestHeader(key,heads.get(key));
				}
			}
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,URL_UTF8);  
            //将表单的值放入postMethod中 
            postMethod.setRequestBody(json);
            //执行postMethod  
            int statusCode = client.executeMethod(postMethod);  
            if(statusCode == HttpStatus.SC_OK) {  
                response = postMethod.getResponseBodyAsString();  
            }else{  
                System.out.println("响应状态码 = " + postMethod.getStatusCode());  
            }  
        }catch(HttpException e){  
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);  
            e.printStackTrace();  
        }catch(IOException e){  
            log.error("发生网络异常", e);  
            e.printStackTrace();  
        }finally{  
            if(postMethod != null){  
                postMethod.releaseConnection();  
                postMethod = null;  
            }  
        }  
          
        return response;  
    }  
	
	public static String sendGet(String url,Map<String, String> params){  
		return sendGet(url,params, URL_UTF8);
	}
	/** 
     * GET方式提交数据 
     * @param url 
     *          待请求的URL 
     * @param params 
     *          要提交的数据 
     * @param enc 
     *          编码 
     * @return 
     *          响应结果 
     * @throws IOException 
     *          IO异常 
     */  
    public static String sendGet(String url, Map<String, String> params, String enc){  
  
        String response = EMPTY;  
        GetMethod getMethod = null;       
        StringBuffer strtTotalURL = new StringBuffer(EMPTY);  
        if(strtTotalURL.indexOf("?") == -1) {  
            strtTotalURL.append(url).append("?").append(getUrl(params, enc));  
          } else {  
              strtTotalURL.append(url).append("&").append(getUrl(params, enc));  
          }  
        log.debug("GET请求URL = \n" + strtTotalURL.toString());  
          
        try {  
            getMethod = new GetMethod(strtTotalURL.toString());  
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);  
            //执行getMethod  
            int statusCode = client.executeMethod(getMethod);  
            if(statusCode == HttpStatus.SC_OK) {  
                response = getMethod.getResponseBodyAsString();  
            }else{  
                log.debug("响应状态码 = " + getMethod.getStatusCode());  
            }  
        }catch(HttpException e){  
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);  
            e.printStackTrace();  
        }catch(IOException e){  
            log.error("发生网络异常", e);  
            e.printStackTrace();  
        }finally{  
            if(getMethod != null){  
                getMethod.releaseConnection();  
                getMethod = null;  
            }  
        }  
          
        return response;  
    }     
  
    /** 
     * 据Map生成URL字符串 
     * @param map 
     *          Map 
     * @param valueEnc 
     *          URL编码 
     * @return 
     *          URL 
     */  
    private static String getUrl(Map<String, String> map, String valueEnc) {  
          
        if (null == map || map.keySet().size() == 0) {  
            return (EMPTY);  
        }  
        StringBuffer url = new StringBuffer();  
        Set<String> keys = map.keySet();  
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {  
            String key = it.next();  
            if (map.containsKey(key)) {  
                String val = map.get(key);  
                String str = val != null ? val : EMPTY;  
                try {  
                    str = URLEncoder.encode(str, valueEnc);  
                } catch (UnsupportedEncodingException e) {  
                    e.printStackTrace();  
                }  
                url.append(key).append("=").append(str).append(SEPARATER_FLAG);  
            }  
        }  
        String strURL = EMPTY;  
        strURL = url.toString();  
        if (SEPARATER_FLAG.equals(EMPTY + strURL.charAt(strURL.length() - 1))) {  
            strURL = strURL.substring(0, strURL.length() - 1);  
        }  
        return (strURL);  
    }  
    
    public static void main(String[] args) throws IOException {
    	String url = "http://115.231.73.18:8989/KJServer/basedata";
    	String methodName = "qryDATABASE_INTERFACE";
        String json = "{\"DATABASE_QRY\":[{\"SQL\":\"SELECT FEE_TYPE_NAME,FEE_TYPE_MONEYS,BL_FIRST_CALC,BL_MODIFY,BL_PARTNER_FIRST_MODIFY FROM TAB_ADDTIONAL_FEE_TYPE A WHERE 1=1 \"}],\"BaseInfo\":[{\"EMPLOYEE_CODE\":\"ztdadmin\",\"EMPLOYEE_NAME\":\"管理员\",\"OWNER_SITE\":\"华南财务中心\",\"SITE_CODE\":\"HN\",\"TSBK_CODE\":\"\",\"E_COMPUTID\":\"suteng|suteng|suteng|0C826863837E|127.0.0.1\",\"OWNER_RANGE\":\"\",\"SUPERIOR_SITE\":\"总部\",\"SUPERIOR_FINANCE_CENTER\":\"华南财务中心\",\"TYPE\":\"财务中心\",\"SUPERIOR_TWO_FINANCE_CENTER\":\"总部\",\"DTSERVERDATE\":\"2016-06-08 9:56:33\",\"BOOTSITE\":\"总部\"}]}";
        Map<String,String> params = new HashMap<String,String>();
    	params.put("method", methodName);
    	params.put("json", json);
    	for(int i = 0;i < 10000; i++)
    	{
    		Long begin = System.currentTimeMillis();
    		String result = ZtdHTTPUtils.sendPost(url, params);
        	//System.out.println("返回结果: "+GZIPUtil.uncompress(Base64.decodeBase64(result.getBytes()), "UTF-8"));
    		System.out.println("请求耗时：" + (System.currentTimeMillis() - begin) + "==毫秒");
    	}
	}
}
