package com.as1124.server.wxsapp.database.mapper;

import java.util.HashMap;
import java.util.List;

/**
 * APP配置信息查询器
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface AppClientMapper {

	public List<HashMap<String, Object>> queryAppSetting(int clientType, String clientVersion);
}
