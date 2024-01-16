package Implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import dynamic.DynamicController;
import write.DynamicWrite;


/*
@Author Bharath Sri - 32L - OTP - 10-09-2023 For Master GET API Creation With Pagination
*/


public class JsonWrite extends DynamicController {

	DynamicWrite dynWrite;
	public JsonWrite() {
		// TODO Auto-generated constructor stub
		dynWrite=new DynamicWrite();
	}

	public Object jsonWriteSB(HashMap<String, String> map,HashMap<String, String> jsonWhere, List<String> whereColumn) {
		// TODO Auto-generated method stub
		queryMap = new HashMap<>();
		String query ="";
		query=map.get("query");
		queryMap.put("fullQuery", query);
		jsonWhere=queryCheck(queryMap);
		jsonWrite=jsonWrite(jsonWhere);
		return jsonWrite;
	}

	private StringBuffer jsonWrite(HashMap<String, String> jsonWhere) {
		// TODO Auto-generated method stub
		jsonWrite=new StringBuffer();
		pagination=true;
		for (int i=0;i<2;i++) {
			jsonWrite=jsonPagination(jsonWhere);
		pagination=false;
		}
		return jsonWrite;
	}

	private StringBuffer jsonPagination(HashMap<String, String> jsonWhere) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		String paginationDBKey=javaFileName+"_"+methodName+"_pagination";
		String countDBKey=javaFileName+"_"+methodName+"_count";
		String leftCond=paginationQuery+" FROM (\"\r\n"+"},\n";
		String jsonDbKey="";
		if (pagination)
		jsonDbKey = "\"code\" : \"" + paginationDBKey + "\"," + "\n";
		else
		jsonDbKey = "\"code\" : \"" + countDBKey + "\"," + "\n";	
		jsonWrite.append("\n {" + '\n');
		jsonWrite.append(jsonDbKey);
		jsonWrite.append("\"query\": \"\","+'\n');
		jsonWrite.append(" \"type\": \"50\","+'\n');
		jsonWrite.append("\"_condqflg\" : \"1\"," + '\n');
		jsonWrite.append("\"_pkgvarflg\" : \"1\"," + '\n');
		jsonWrite.append("\"_IOSerial\" : \"\"  ");
		jsonWrite.append(", \n \"_valcfg\": \n [ \n");
		if(pagination) {
			jsonWrite.append("{\r\n\"_cfgsl\": \"left0\",\r\n\"_cfgstr\": \""+leftCond);
			jsonWrite.append("{\r\n\"_cfgsl\": \"right0\",\r\n\"_cfgstr\": \") where rownum <=0\"\r\n"+"},");
			jsonWrite.append("{\r\n\"_cfgsl\": \"left1\",\r\n\"_cfgstr\": \"");
			jsonWrite.append(paginationQuery+" FROM ("+paginationQuery +" , rownum rnum from (\"\r\n"+"},\n");
			jsonWrite.append("{\r\n\"_cfgsl\": \"right1\",\r\n\"_cfgstr\": \")temp where rownum <=?+?) where rnum >?\"\r\n"+"},");
			jsonWrite.append("{\r\n\"_cfgsl\": \"left2\",\r\n\"_cfgstr\": \""+leftCond);
			jsonWrite.append("{\r\n\"_cfgsl\": \"right2\",\r\n\"_cfgstr\": \") where rownum <=?\"\r\n"+"},");
			jsonWrite.append("{\r\n\"_cfgsl\": \"left3\",\r\n\"_cfgstr\": \""+leftCond);
			jsonWrite.append("{\r\n\"_cfgsl\": \"right3\",\r\n\"_cfgstr\": \") where rownum <=?\"\r\n"+"},");
			jsonWrite.append("{\r\n\"_cfgsl\": \"mainQuery\",\r\n\"_cfgstr\": \""+mainQuery+"\"\r\n"+"},\n");
			jsonWrite.append("{\r\n\"_cfgsl\": \"where\",\r\n\"_cfgstr\": \" WHERE \"\r\n"+"},\n");
			jsonWrite.append("{\r\n\"_cfgsl\": \"and\",\r\n\"_cfgstr\": \" AND \"\r\n"+"},\n");
		}else
		{
			jsonWrite.append("{\r\n\"_cfgsl\": \"1\",\r\n\"_cfgstr\": \"SELECT COUNT(1) FROM (\"\r\n"+"},");
			jsonWrite.append("{\r\n\"_cfgsl\": \"mainQuery\",\r\n\"_cfgstr\": \""+mainQuery+"\"\r\n"+"},\n");
			jsonWrite.append("{\r\n\"_cfgsl\": \"2\",\r\n\"_cfgstr\": \" ) \"\r\n"+"},");
			jsonWrite.append("{\r\n\"_cfgsl\": \"where\",\r\n\"_cfgstr\": \" WHERE \"\r\n"+"},\n");
			jsonWrite.append("{\r\n\"_cfgsl\": \"and\",\r\n\"_cfgstr\": \" AND \"\r\n"+"},\n");
		}
		String conditionJsonData = jsonWhere.entrySet().stream()
				.map(e -> "\n { \n \"_cfgsl\": \"" + e.getKey() + "\", \n" + "\"_cfgstr\": \""
						+ e.getValue().replace("\"", "")
						+ "\" \n } \n")
				.collect(Collectors.joining(","));
		jsonWrite.append(conditionJsonData);
		jsonWrite.append("] \n");
		if(pagination) 
		jsonWrite.append("}, \n");
		else
		jsonWrite.append("}");
		return jsonWrite;
	
		
	}

	private HashMap<String, String> queryCheck(HashMap<String, String> queryMap) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		String fullQuery="";
		String beforeWhere="";
		String afterWhere="";
		String condStringFull="";
		mainQuery="";
		paginationQuery="";

		try {
			whereColumn = new ArrayList<>();
			condStr = new ArrayList<>();
			jsonWhere = new  HashMap<>();
			String[] splitQuery=null;
			String[] where=null;
			String[] condStringArr=null;
			String[] paginationQue=null;

			fullQuery=queryMap.get("fullQuery");
			if (fullQuery.toUpperCase().contains(" WHERE ")) {
				splitQuery=fullQuery.toUpperCase().split("WHERE");
				if (splitQuery!=null) {
					beforeWhere=splitQuery[0];
					afterWhere=splitQuery[1];
					}
				where=afterWhere.toUpperCase().split(" AND ");
				for(String column : where)
				{
					if(column.contains("?"))
					{
						column=column.strip();
						whereColumn.add(column);
					}
				}
				condStringFull=afterWhere.strip().toLowerCase().replace("_", "");
				condStringArr = condStringFull.split(" and ");
				for(String condsrts : condStringArr)
				{
					if(condsrts.contains("?"))
					{
						condsrts=condsrts.strip().replace(" ", "").replace("=?","");
						condStr.add(condsrts);
					}
				}
				 for (int i = 0; i < condStr.size(); i++)
				    {
					 jsonWhere.put(condStr.get(i), whereColumn.get(i));
					 }
				}
			paginationQue=beforeWhere.toUpperCase().split(" FROM ");
			paginationQuery=paginationQue[0];
			mainQuery=beforeWhere;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jsonWhere;
	}

}
