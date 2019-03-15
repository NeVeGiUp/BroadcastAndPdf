package com.itc.suppaperless.utils;


import com.google.gson.Gson;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.bean.IssueInfo;
import com.itc.suppaperless.meetingmodule.bean.YitichangeInfo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * TODO<解析数据类>
 */
public class HttpGsonUtil {

	private static HttpGsonUtil sHttpGsonUtil;

	public static synchronized HttpGsonUtil getInstance() {
		if (sHttpGsonUtil == null) {
			sHttpGsonUtil = new HttpGsonUtil();
		}
		return sHttpGsonUtil;
	}
	/*
     *转换json 方法
     */
	public Object jsonEnclose(Object obj){
		try{
			if(obj instanceof Map) { //如果是Map则转换为JsonObject
				Map<String, Object> map = (Map<String,Object>)obj;
				Iterator<Map.Entry<String,Object>> iterator =map.entrySet().iterator();
				JSONStringer jsonStringer =new JSONStringer().object();
				while(iterator.hasNext()){
					Map.Entry<String, Object> entry = iterator.next();
					jsonStringer.key(entry.getKey()).value(jsonEnclose(entry.getValue()));
				}
				JSONObject jsonObject = new JSONObject(new JSONTokener(jsonStringer.endObject().toString()));
				return jsonObject;
			}else if(obj instanceof List){//如果是List则转换为JsonArray
				List<Object> list = (List<Object>)obj;
				JSONStringer jsonStringer=new JSONStringer().array();
				for(int i=0;i<list.size();i++){
					jsonStringer.value(jsonEnclose(list.get(i)));
				}
				JSONArray jsonArray = new JSONArray(new JSONTokener(jsonStringer.endArray().toString()));
				return jsonArray;
			}else{
				return obj;
			}
		}catch(Exception e){
			return e.getMessage();
		}
	}
	/*
	 * 获取会议文件列表
	 */
	public CommentUploadListInfo parseCommentUpload(String result) {
		CommentUploadListInfo meetingwenjian = null;
		if (StringUtil.isEmpty(result)) {
			return meetingwenjian;
		} else {
			Gson gson = new Gson();
			meetingwenjian = gson.fromJson(result, CommentUploadListInfo.class);
		}
		return meetingwenjian;
	}

	/*
	 * 获取会议文件列表更新
	 */
	public CommentUploadListInfo.LstFileBean parseFileUpdate(String result) {
		CommentUploadListInfo.LstFileBean meetingfileupdate = null;
		if (StringUtil.isEmpty(result)) {
			return meetingfileupdate;
		} else {
			Gson gson = new Gson();
			meetingfileupdate = gson.fromJson(result, CommentUploadListInfo.LstFileBean.class);
		}
		return meetingfileupdate;
	}
	/*
* 获取议题列表
*/
	public IssueInfo parseYiti(String result) {
		IssueInfo yitiInfo = null;
		if (StringUtil.isEmpty(result)) {
			return yitiInfo;
		} else {
			Gson gson = new Gson();
			yitiInfo = gson.fromJson(result, IssueInfo.class);
		}
		return yitiInfo;
	}
	/*
 * 获取议题修改消息
 */
	public IssueInfo.LstIssue parseYitiupdata(String result) {
		IssueInfo.LstIssue yitiupdataInfo = null;
		if (StringUtil.isEmpty(result)) {
			return yitiupdataInfo;
		} else {
			Gson gson = new Gson();
			yitiupdataInfo = gson.fromJson(result, IssueInfo.LstIssue.class);
		}
		return yitiupdataInfo;
	}
	/*
 * 获取议题id
 */
	public YitichangeInfo parseYitiID(String result) {
		YitichangeInfo yitichangeInfo = null;
		if (StringUtil.isEmpty(result)) {
			return yitichangeInfo;
		} else {
			Gson gson = new Gson();
			yitichangeInfo = gson.fromJson(result, YitichangeInfo.class);
		}
		return yitichangeInfo;
	}

}