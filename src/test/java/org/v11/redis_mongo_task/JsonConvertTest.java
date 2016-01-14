package org.v11.redis_mongo_task;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.v11.redis_mongo_task.utils.TypeConverter;

import com.mongodb.DBObject;

public class JsonConvertTest {
//	@Test
//	public void testConvertString2Json() throws JSONException{
//		JSONObject jobj = new JSONObject();
//		jobj.put("name", "wy");
//		jobj.put("age", "17");
//		System.out.println(jobj);
//		String jstr = jobj.toString();
//		System.out.println(jstr);
//		for(int i = 0 ; i<jobj.length();i++){
//			
//		}
//		JSONObject cobj = new JSONObject(jstr);
//		System.out.println(cobj.get("name"));
//	}
//	@Test
//	public void testConvertString2JsonArray() throws JSONException{
//		JSONObject j1 = new JSONObject();
//		j1.put("name", "wy");
//		j1.put("age", "17");
//		JSONObject j2 = new JSONObject();
//		j2.put("name", "zh");
//		j2.put("age", "16");
//		JSONArray js = new JSONArray();
//		js.put(j1);
//		js.put(j2);
//		System.out.println(js);
//		for(int i = 0;i<js.length();i++){
//			JSONObject obj = js.getJSONObject(i);
//			System.out.println(obj);
//		}
//		JSONArray cj = new JSONArray(js.toString());
//		System.out.println(cj);
//	}
	@Test
	public void strsSampleTest() throws JSONException{
		String str = "[\"从开始到未来，只为王俊凯\",\"门面担当王俊凯\",\"王俊凯\",\"王俊凯触电张艺谋长城\"]";
		System.out.println(str);
		List<String> ls = TypeConverter.str2strs(str);
		System.out.println(ls);
		
	}
	@Test
	public void dbsSampleTest(){
		String str = "[ { \"t\" : \"t5526\", \"v\" : 0.46211689275686596 }, { \"t\" : \"t5529\", \"v\" : 0.46211689275686596 }"
				+ ", { \"name\" : \"ttt\"} ]";
		System.out.println(str);
		List<DBObject> ls = TypeConverter.str2DBObjects(str);
		System.out.println(ls);
	}
}
