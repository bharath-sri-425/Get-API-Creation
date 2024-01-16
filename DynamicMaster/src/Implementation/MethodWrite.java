package Implementation;

import java.util.HashMap;
import java.util.*;
import java.text.*;
import dynamic.DynamicController;


/*
@Author Bharath Sri - 32L - OTP - 10-09-2023 For Master GET API Creation With Pagination
*/


public class MethodWrite extends DynamicController {
	InsertWrite insWt;
	
	public MethodWrite() {
		// TODO Auto-generated constructor stub
	}
	SimpleDateFormat formDate = new SimpleDateFormat("dd-MM-yyyy");
    String strDate = formDate.format(new Date()); 
	public Object methodWriteSB(HashMap<String, String> map) {
		insWt=new InsertWrite();
		// TODO Auto-generated method stub
		methodLines=new StringBuffer();
		String aliasName="";
		methodLines.append("//Added By Bharath Sri 32L- Chn - For OTP Master API - "+strDate+" - BEG \n\n");
		methodLines.append("public DTObject "+methodName+"(DTObject InputoBj) //"+apiDesc+" Process Code :"+procCode+"\n{\n");
		methodLines.append("ResultSet objResultSet = null;\r\nResultSet countResulset = null;\r\nint page = 0;\r\nint limit = 10;\r\nint count=0;\r\n"
				+ "int offset = 0;\r\nint totalpage = 0;\r\nint totalrecord = 0;\r\nboolean paginationflag = false;\r\nString left = \"\";\r\nString right = \"\";\r\nString whereString=\"\";\r\n");
		for (int i=0;i< condStr.size();i++) {
			methodLines.append("String "+condStr.get(i)+"=\"\";\n");
		}
		methodLines.append("if (InputoBj.containsKey(\"pagination\")) {\r\npaginationflag = true;\r\n}\n");
		for (int i=0;i< condStr.size();i++) {
			aliasName=insWt.aliasNamechange(whereColumn.get(i));
			methodLines.append("if (InputoBj.containsKey(\""+aliasName+"\") && null != InputoBj.getValue(\""+aliasName+"\")"
					+ "&& !InputoBj.getValue(\""+aliasName+"\").equals(\"\")) {\r\n"
					+ condStr.get(i)+" = InputoBj.getValue(\""+aliasName+"\");\r\n"
					+ "if (count==0) {\r\n"
					+ "whereString=whereString+\""+condStr.get(i)+"|\";\r\n"
					+ "}else {\r\n"
					+ "whereString=whereString+\"and|"+condStr.get(i)+"|\";\r\n"
					+ "}\r\n"
					+ "count=count+1;\r\n"
					+ "}\n");
		}
		paginationWrite();
		return methodLines;
		
	}

	private StringBuffer paginationWrite() {
		// TODO Auto-generated method stub
		String paginationDBKey=javaFileName+"_"+methodName+"_pagination";
		methodLines.append("int _sqlExtIdxs = 1;\r\n"
				+ "String _sqlCondstr=\"\";\r\n"
				+ "String _sqlExtCondStr=\"\";\r\n"
				+ "_sqlExtIdx =1; \r\n"
				+ "sqlExtDtoInput.clearMap(); \r\n"
				+ "sqlExtDtoInput.setValue(\"DB_KEY\",\""+paginationDBKey+"\"); \r\n"
				+ "sqlExtDtoInput.setValue(\"ENTITY_NUM\",get_entity_num()); \r\n"
				+ "sqlExtDtoInput.setValue(\"COND_STR\",\"\");");
		methodLines.append("if (InputoBj.containsKey(\"requestedPage\")) {\r\n"
				+ "page = Integer.parseInt(InputoBj.getValue(\"requestedPage\"));\r\n"
				+ "if (page <= 0) {\r\n"
				+ "page = 0;\nif (!whereString.equalsIgnoreCase(\"\")) {\r\n"
				+ "_sqlExtCondStr=_sqlExtCondStr+\"left0|mainQuery|where|\"+whereString;\r\n"
				+ "}else {\r\n"
				+ "_sqlExtCondStr=_sqlExtCondStr+\"left0|mainQuery|\";\r\n"
				+ "}\n");
		valueSetter();
		methodLines.append(" _sqlExtCondStr=_sqlExtCondStr+\"right0|\";\r\n"
					+ "} else if (InputoBj.containsKey(\"recordCount\")) {\r\n"
					+ "page = page - 1;\r\n"
					+ "limit = Integer.parseInt(InputoBj.getValue(\"recordCount\"));\r\n"
					+ "	offset = page * limit;"
					+ "if (!whereString.equalsIgnoreCase(\"\")) {\r\n"
					+ "_sqlExtCondStr=_sqlExtCondStr+\"left1|mainQuery|where|\"+whereString;\r\n"
					+ "}else {\r\n"
					+ "_sqlExtCondStr=_sqlExtCondStr+\"left1|mainQuery|\";\r\n"
					+ "}\r\n");
		valueSetter();
		methodLines.append("_sqlExtCondStr=_sqlExtCondStr+\"right1|\";\r\n"
				+ "_dbexec.set_IO_value(_sqlExtIdx++,offset,DatatypeConstants.DC_INTEGER,\"I\");\r\n"
				+ "_dbexec.set_IO_value(_sqlExtIdx++,limit,DatatypeConstants.DC_INTEGER,\"I\");\r\n"
				+ "_dbexec.set_IO_value(_sqlExtIdx++,offset,DatatypeConstants.DC_INTEGER,\"I\");\n} \nelse \n{\n"
				+ "if (!whereString.equalsIgnoreCase(\"\")) {\r\n"
				+ "_sqlExtCondStr=_sqlExtCondStr+\"left2|mainQuery|where|\"+whereString;\r\n"
				+ "}else {\r\n"
				+ "_sqlExtCondStr=_sqlExtCondStr+\"left2|mainQuery|\";\r\n"
				+ "}\r\n");
		valueSetter();
		methodLines.append("_sqlExtCondStr=_sqlExtCondStr+\"right2|\";\r\n"
				+ "_dbexec.set_IO_value(_sqlExtIdx++,limit,DatatypeConstants.DC_INTEGER,\"I\");\r\n"
				+ "}\r\n"
				+ "\n}\n else if (InputoBj.containsKey(\"recordCount\")) {\r\n"
				+ "limit = Integer.parseInt(InputoBj.getValue(\"recordCount\"));\r\n"
				+ "if (!whereString.equalsIgnoreCase(\"\")) {\r\n"
				+ "_sqlExtCondStr=_sqlExtCondStr+\"left3|mainQuery|where|\"+whereString;\r\n"
				+ "}else {\r\n"
				+ "_sqlExtCondStr=_sqlExtCondStr+\"left3|mainQuery|\";\r\n"
				+ "}\n");
		valueSetter();
		methodLines.append("_sqlExtCondStr=_sqlExtCondStr+\"right3|\";\r\n"
				+ "_dbexec.set_IO_value(_sqlExtIdx++,limit,DatatypeConstants.DC_INTEGER,\"I\");\r\n"
				+ "\n } \n");
		countMethodWrite();
		
		return methodLines;
	}

	private StringBuffer countMethodWrite() {
		// TODO Auto-generated method stub
		String countDBKey=javaFileName+"_"+methodName+"_count";

		methodLines.append("if (Resultobj.getValue(\"ErrorMsg\") == null) {\r\n"
				+ "try {\r\n"
				+ "if (paginationflag) {\r\n"
				+ "sqlExtDtoInputPag.clearMap();\r\n"
				+ "sqlExtDtoInputPag.setValue(\"DB_KEY\", \""+countDBKey+"\");\r\n"
				+ "sqlExtDtoInputPag.setValue(\"ENTITY_NUM\", get_entity_num());\r\n"
				+ "sqlExtDtoInputPag.setValue(\"COND_STR\", \"\");\r\n"
				+ "if (!whereString.equalsIgnoreCase(\"\")) {\r\n"
				+ "_sqlCondstr=\"1|mainQuery|where|\"+whereString+\"2|\";\r\n"
				+ "}\nelse \n{\n"
				+ "_sqlCondstr=\"1|mainQuery|2|\";\r"
				+ "\n}\n");
		countQueryFlag=true;
		valueSetter();
		methodLines.append("if (!_sqlCondstr.equalsIgnoreCase(\"\")) { \r\n"
				+ "_sqlCondstr = _sqlCondstr.substring(0,_sqlCondstr.length()-1);  \r\n"
				+ "sqlExtDtoInputPag.setValue(\"COND_STR\",_sqlCondstr.trim()); \r\n"
				+ "}\n");
		methodLines.append("try {\r\n"
				+ "openConnection();\r\n"
				+ "countResulset = _dbexecPag.qryExecutor(sqlExtDtoInputPag, connDB);\r\n"
				+ "} catch (DBEngineException e) {\r\n"
				+ "IDCLogger.logDebug(e);\r\n"
				+ "throw new DBEngineException();\n}\n");
		methodLines.append("if (countResulset.next()) {\r\n"
				+ "totalrecord = countResulset.getInt(1);\r\n"
				+ "}\n");
		methodLines.append("if (_sqlExtCondStr.equalsIgnoreCase(\"\")) {\r\n"
				+ "if (!whereString.equalsIgnoreCase(\"\")) {\r\n"
				+ "_sqlExtCondStr=_sqlExtCondStr+\"mainQuery|where|\"+whereString;\n");
		countQueryFlag=false;
		valueSetter();
		methodLines.append("}else {\r\n"
				+ "_sqlExtCondStr=_sqlExtCondStr+\"mainQuery|\";\r\n"
				+ "}\r\n"
				+ "}\r\n"
				+ "}\r\n"
				+ "if (!_sqlExtCondStr.equalsIgnoreCase(\"\")) { \r\n"
				+ "_sqlExtCondStr = _sqlExtCondStr.substring(0,_sqlExtCondStr.length()-1);  \r\n"
				+ "sqlExtDtoInput.setValue(\"COND_STR\",_sqlExtCondStr); \r\n"
				+ "}\r\n"
				+ "try {\r\n"
				+ "openConnection();\r\n"
				+ "objResultSet = _dbexec.qryExecutor(sqlExtDtoInput, connDB);\r\n"
				+ "} catch (DBEngineException e) {\r\n"
				+ "IDCLogger.logDebug(e);\r\n"
				+ "}");
		resultDTDObjset();
		return methodLines;

	}

	private void resultDTDObjset() {
		// TODO Auto-generated method stub
		methodLines.append("DTDObject Dtdinfo = new DTDObject();\r\n"
				+ "Dtdinfo.clear();\r\n"
				+ "int i = 1;\r\n"
				+ "int row = 0;\r\n"
				+ "int k = 0;\r\n"
				+ "for (i = 1; i <= objResultSet.getMetaData().getColumnCount(); i++) {\r\n"
				+ "Dtdinfo.addColumn(k, objResultSet.getMetaData().getColumnName(i).toString());\r\n"
				+ "k++;\r\n"
				+ "}\r\n"
				+ "while (objResultSet.next()) {\r\n"
				+ "Dtdinfo.addRow();\r\n"
				+ "for (i = 1; i <= objResultSet.getMetaData().getColumnCount(); i++) {\r\n"
				+ "Dtdinfo.setValue(row, objResultSet.getMetaData().getColumnName(i).toString(),objResultSet.getString(i));\r\n"
				+ "}\r\n"
				+ "row = row + 1;\r\n"
				+ "}\r\n"
				+ "if (paginationflag) {\r\n"
				+ "Resultobj.setValue(\"pagination\", totalrecord + \"\");\r\n"
				+ "}\r\n"
				+ "if (totalrecord==0) {\r\n"
				+ "Resultobj.setValue(\"ErrorCode\", \"0\");\r\n"
				+ "Resultobj.setValue(\"ErrorMsg\", panacea.common.ResponseMsgUtility.getRespMsg(\"CB00018141\"));\r\n"
				+ "}else {\r\n"
				+ "Resultobj.setValue(\"ErrorCode\", \"0\");\r\n"
				+ "Resultobj.setValue(\"ErrorMsg\", \"\");\r\n"
				+ "Resultobj.setDTDObject(\"GetGridData\", Dtdinfo);\r\n"
				+ "}"
				+ "} catch (Exception objException) {\r\n"
				+ "objException.printStackTrace();\r\n"
				+ "Resultobj.setValue(\"ErrorMsg\", \"\");\r\n"
				+ "Resultobj.setValue(\"ErrorCode\", \"1\");\r\n"
				+ "return Resultobj.copyofDTO();\r\n"
				+ "} finally {\r\n"
				+ "try {\r\n"
				+ "if (objResultSet != null) {\r\n"
				+ "objResultSet.close();\r\n"
				+ "} \r\n"
				+ "if (countResulset != null) {\r\n"
				+ "countResulset.close();\r\n"
				+ "}\r\n"
				+ "closeConnection();\r\n"
				+ "} catch (Exception objException) {\r\n"
				+ "}\r\n"
				+ "}\r\n"
				+ "}\r\n"
				+ "return Resultobj.copyofDTO();\r\n"
				+ "}\r\n");
		methodLines.append("//Added By Bharath Sri 32L- Chn - For OTP Master API - "+strDate+" - END \n\n");

		
	}

	private void valueSetter() {
		// TODO Auto-generated method stub
		  for (String i : condStr) {
			  if(!countQueryFlag) {
			  methodLines.append("if (!"+i+".equalsIgnoreCase(\"\")) {\r\n"
						+ "_dbexec.set_IO_value(_sqlExtIdx++,"+i+",DatatypeConstants.DC_STRING,\"I\");\r\n"
						+ "}\n");
			  }else {
				  methodLines.append("if (!"+i+".equalsIgnoreCase(\"\")) {\r\n"
							+ "_dbexecPag.set_IO_value(_sqlExtIdxs++,"+i+",DatatypeConstants.DC_STRING,\"I\");\r\n"
							+ "}\n");
			  }
		  }
	}

}
