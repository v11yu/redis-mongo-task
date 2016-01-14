package org.v11.redis_mongo_task.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
/**
 * 中间类型转换器
 * @author v11
 *
 */
public class TypeConverter {
	/**
	 * redis中的string转为List<String> <br />
	 * 格式：["从开始到未来，只为王俊凯","门面担当王俊凯","王俊凯","王俊凯触电张艺谋长城"]
	 * <br />jsonarray的string数组类型，还原。
	 * @param str
	 * @return
	 */
	public static List<String> str2strs(String str){
		List<String> ls = new ArrayList<String>();
		try {
			JSONArray js = new JSONArray(str);
			for(int i=0;i<js.length();i++){
				String tmp = js.getString(i);
				ls.add(tmp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error("string转换jsonArray出错"+e);
			return null;
		}
		return ls;
	}
	/**
	 * jsonObject convert to DBObject
	 * @param obj
	 * @return
	 */
	public static DBObject json2DBObject(JSONObject obj){
		DBObject dbobj = new BasicDBObject();
		Iterator<?> keys = obj.keys();
		while(keys.hasNext()){
			String key = (String)keys.next();
			try {
				dbobj.put(key, obj.get(key));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.error("jsonException取值异常"+e);
				return null;
			}
		}
		return dbobj;
		
	}
	/**
	 * str convert to List<DbObject>
	 * @param str
	 * @return
	 */
	public static List<DBObject> str2DBObjects(String str){
		List<DBObject> ls = new ArrayList<DBObject>();
		try {
			JSONArray js = new JSONArray(str);
			for(int i=0;i<js.length();i++){
				JSONObject tmp = js.getJSONObject(i);
				DBObject dbobj = json2DBObject(tmp);
				if(dbobj!=null) ls.add(dbobj);		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error("string转换jsonArray出错"+e);
			return null;
		}
		return ls;
		
	}
}
