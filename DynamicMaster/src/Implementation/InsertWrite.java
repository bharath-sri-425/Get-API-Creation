package Implementation;

import java.util.ArrayList;
import java.util.HashMap;
import dynamic.DynamicController;

/*
@Author Bharath Sri - 32L - OTP - 10-09-2023 For Master GET API Creation With Pagination
*/

public class InsertWrite extends DynamicController {

	public InsertWrite() {
		// TODO Auto-generated constructor stub
	}
	String procCode="";
	String methodName = "";
	String service = "";
	String reqPath = "";
	String apiDesc = "";
	String groupName="";

	public Object insertQueryWriteSB(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		procCode=map.get("procCode");
		methodName=map.get("methodName");
		service=map.get("service");
		reqPath=map.get("reqPath");
		apiDesc=map.get("apiDesc");
		procctlOpenInsert();
		procctlGetInsert();
		procctlGriddtlInsert();
		return insertQuery;
	}

	public String aliasNamechange(String aliasName) {
		aliasName=aliasName.replace(" ", "").replace("=?", "");
		aliasName = aliasName.substring(0, 1).toLowerCase()+ aliasName.substring(1);
		String[] Strind;
		StringBuffer appStr=new StringBuffer();
		if (aliasName.contains("_")) {
			Strind=aliasName.toLowerCase().split("_");
			int size=Strind.length;
			for (int i=1;i<size;i++) {
				Strind[i]=Strind[i].substring(0,1).toUpperCase()+Strind[i].substring(1);
				appStr.append(Strind[i]);
			}
			aliasName=Strind[0].toString()+appStr.toString();
		}
		return aliasName;
	}

	private void procctlOpenInsert() {
		// TODO Auto-generated method stub
		if (service.equalsIgnoreCase("/casa/v3"))
			groupName="CASA";
		else if (service.equalsIgnoreCase("/cif/v3")) 
			groupName="CIF";
		else if (service.equalsIgnoreCase("/coreconfig/v3")) 
			groupName="Core-config";
		else if (service.equalsIgnoreCase("/deposits/v3")) 
			groupName="Deposits";
		else if (service.equalsIgnoreCase("/fintran/v3")) 
			groupName="Transactions";
		
		insertQuery.append("--PROCCTLOPENAPI\n");

		insertQuery.append("INSERT INTO PROCCTLOPENAPI (PROC_CODE, OPENAPI_VERSION, OPENAPI_TITLE, OPENAPI_DESCRIPTION, OPENAPI_SPEC_VERSION, OPENAPI_URL, OPENAPI_PATH, OPENAPI_METHOD_DESCRIPT, OPENAPI_METHOD, OPENAPI_REQUEST_PATH, OPENAPI_URL_DESCRIPT, SYSTEM_CODE, OPENAPI_GROUPNAME, OPENAPI_GROUPORDER)\r\n"
				+ "values ("+procCode+", '3.0.1', '"+apiDesc.replace(" ", "")+"', '"+apiDesc+"', '2', '"+host+"', '"+service+"', 'This API "+apiDesc+".', 'GET', '"+reqPath+"', '"+apiDesc+"', null, '"+groupName+"', null);\n\n");
	}
	
	private void procctlGetInsert() {
		// TODO Auto-generated method stub
		insertQuery.append("--PROCCTLGET\n");
		int count =2;
		String aliasName="";
		insertQuery.append("insert into procctlget (PROC_CODE, DIRECTION, SL, SOURCE_ELEMENT, METHOD_TO_INVOKE, PARENT_TAG, MANDATORY_REQ, GET_DESC, GET_MAXLN, GET_TYPE, GET_ELEMENT_TYPE, GET_ALIAS_NAME, GET_EXAMPLE, GET_MINLN, GET_HTTP_STATUS, GET_PARAMETER_TYPE, GET_PARAMETER_POSITION, GET_API_MOCK)\r\n"
				+ "values ("+procCode+", 'I', 1, 'USERID', null, null, '1', 'User ID making this API request - captured for traceability. If not supplied, would be defaulted to IDCAPI.', 8, 'string', 'E', 'userID', 'IDCAPI', 1, null, 'query', null, null);\n\n");
		
		for (int i=0; i<condStr.size();i++) {
			String sourceKey=whereColumn.get(i).replace("=?", "");
			aliasName=aliasNamechange(whereColumn.get(i));
			insertQuery.append("insert into procctlget (PROC_CODE, DIRECTION, SL, SOURCE_ELEMENT, METHOD_TO_INVOKE, PARENT_TAG, MANDATORY_REQ, GET_DESC, GET_MAXLN, GET_TYPE, GET_ELEMENT_TYPE, GET_ALIAS_NAME, GET_EXAMPLE, GET_MINLN, GET_HTTP_STATUS, GET_PARAMETER_TYPE, GET_PARAMETER_POSITION, GET_API_MOCK)\r\n"
		+ "values ("+procCode+", 'I', "+count+", '"+aliasName+"', null, null, '1', 'This field indicates "+sourceKey.toLowerCase().replace("_", " ")+".', 50, 'string', 'E', '"+aliasName+"', '', 1, null, 'query', null, null);\n\n");
			count =count+1;
		}
		
		insertQuery.append("insert into procctlget (PROC_CODE, DIRECTION, SL, SOURCE_ELEMENT, METHOD_TO_INVOKE, PARENT_TAG, MANDATORY_REQ, GET_DESC, GET_MAXLN, GET_TYPE, GET_ELEMENT_TYPE, GET_ALIAS_NAME, GET_EXAMPLE, GET_MINLN, GET_HTTP_STATUS, GET_PARAMETER_TYPE, GET_PARAMETER_POSITION, GET_API_MOCK)\r\n"
				+ "values ("+procCode+", 'O', 1, 'GetGridData', null, null, '1', '"+apiDesc+".', 0, 'string', 'G', '"+methodName+"', null, 0, '200', null, null, null);\n\n");
		
		insertQuery.append("insert into procctlget (PROC_CODE, DIRECTION, SL, SOURCE_ELEMENT, METHOD_TO_INVOKE, PARENT_TAG, MANDATORY_REQ, GET_DESC, GET_MAXLN, GET_TYPE, GET_ELEMENT_TYPE, GET_ALIAS_NAME, GET_EXAMPLE, GET_MINLN, GET_HTTP_STATUS, GET_PARAMETER_TYPE, GET_PARAMETER_POSITION, GET_API_MOCK)\r\n"
				+ "values ("+procCode+", 'O', 2, 'ErrorMsg', null, null, '1', 'Response indicating the final confirmation of the action or highlighting the issue with input parameters.\r\n"
				+ "(eg. SUCCESS or the message indicating the failure reason).', 250, 'string', 'E', 'responseMessage', 'Invalid customer number.', 1, '412', null, null, null);\n\n");
		
		insertQuery.append("insert into procctlget (PROC_CODE, DIRECTION, SL, SOURCE_ELEMENT, METHOD_TO_INVOKE, PARENT_TAG, MANDATORY_REQ, GET_DESC, GET_MAXLN, GET_TYPE, GET_ELEMENT_TYPE, GET_ALIAS_NAME, GET_EXAMPLE, GET_MINLN, GET_HTTP_STATUS, GET_PARAMETER_TYPE, GET_PARAMETER_POSITION, GET_API_MOCK)\r\n"
				+ "values ("+procCode+", 'O', 3, 'ErrorCode', null, null, '1', 'Response code indicating the final confirmation of the action (0-Success, 1-Failure,9-Request Queued).', 1, 'string', 'E', 'responseCode', '0', 1, '412', null, null, null);\n\n");
		
	}
	private void procctlGriddtlInsert() {
		// TODO Auto-generated method stub
		int count=3;
		aliasNameLst=new ArrayList<>();
		insertQuery.append("--PROCCTLGRIDDTL\n");
		insertQuery.append("insert into procctlgriddtl (SL, PROC_CODE, GRID_CLASS, GRID_SOURCE_ELEMENT, GRID_MANDATORY_REQ, GRID_DESC, GRID_TYPE, GRID_ELEMENT_TYPE, GRID_MAXLN, GRID_PARENT, GRID_MULTIPLE, GRID_ALIAS_NAME, GRID_EXAMPLE, GRID_MINLN, GRID_API_MOCK, GRID_API_MOCK_EXAMPLE)\r\n"
				+ "values (1, "+procCode+", 'GetGridData', 'GetGridData', '1', '"+apiDesc+".', 'string', 'H', 0, 'null', 0, '"+methodName+"', null, null, null, null);\n\n");
		
		insertQuery.append("insert into procctlgriddtl (SL, PROC_CODE, GRID_CLASS, GRID_SOURCE_ELEMENT, GRID_MANDATORY_REQ, GRID_DESC, GRID_TYPE, GRID_ELEMENT_TYPE, GRID_MAXLN, GRID_PARENT, GRID_MULTIPLE, GRID_ALIAS_NAME, GRID_EXAMPLE, GRID_MINLN, GRID_API_MOCK, GRID_API_MOCK_EXAMPLE)\r\n"
				+ "values (2, "+procCode+", 'GetGridData', 'Record', '1', 'Record', 'string', 'S', 0, 'null', 1, 'record', null, null, null, null);\n\n");
		
		for (int i=0; i<condStr.size();i++) {
			String conditionStr="";
			String aliasName="";
			String sourceKey="";
			conditionStr=condStr.get(i);
			sourceKey=jsonWhere.get(conditionStr).replace(" ", "").replace("=?", "");
			aliasName=aliasNamechange(sourceKey);
			aliasNameLst.add(aliasName);
			insertQuery.append("insert into procctlgriddtl (SL, PROC_CODE, GRID_CLASS, GRID_SOURCE_ELEMENT, GRID_MANDATORY_REQ, GRID_DESC, GRID_TYPE, GRID_ELEMENT_TYPE, GRID_MAXLN, GRID_PARENT, GRID_MULTIPLE, GRID_ALIAS_NAME, GRID_EXAMPLE, GRID_MINLN, GRID_API_MOCK, GRID_API_MOCK_EXAMPLE)\r\n"
					+ "values ("+count+", "+procCode+", 'Record', '"+sourceKey+"', '1', 'This field indicates "+sourceKey.toLowerCase().replace("_", " ")+".', 'string', 'E', 50, 'GetGridData', 0, '"+aliasName+"', '', 1, null, null);\n\n");
			
			count=count+1;
		}
		insertQuery.append("COMMIT;");
	}
}
