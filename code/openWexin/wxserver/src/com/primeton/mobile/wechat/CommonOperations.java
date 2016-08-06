package com.primeton.mobile.wechat;

import org.apache.commons.httpclient.NameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.primeton.mobile.wechat.exception.WechatExceprion;

/**
 * 基础接口。
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class CommonOperations {

	/**
	 * 获取微信服务器的IP地址列表
	 * <p>如果公众号基于消息接收安全上的考虑，需要获知微信服务器的IP地址列表，以便识别出哪些消息是微信官方推送给你的，
	 * 哪些消息可能是他人伪造的，可以通过该接口获得微信服务器IP地址列表。</p>
	 * @param accessToken
	 * @return list of callback-IPs
	 * @throws WechatExceprion 
	 */
	public static String[] getWechatIPAddresses(AccessToken token) throws WechatExceprion{
		String uri = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair(IWechatConstants.KEY_ACCESS_TOKEN, token.getAccess_token());
		String response = HttpExecuter.executeGetAsString(uri, queryStr);
		JSONObject result =  JSONObject.parseObject(response);
		String returnCode = result.getString(IWechatConstants.ERROR_CODE);
		if(returnCode==null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return JSONObject.parseArray(result.getString("ip_list")).toArray(new String[]{});
		}
		else throw new WechatExceprion(response);
	}

}
