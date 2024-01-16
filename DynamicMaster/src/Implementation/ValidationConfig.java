package Implementation;

import java.util.HashMap;

import dynamic.DynamicController;


/*
@Author Bharath Sri - 32L - OTP - 10-09-2023 For Master GET API Creation With Pagination
*/


public class ValidationConfig extends DynamicController {

	public ValidationConfig() {
		// TODO Auto-generated constructor stub
	}

	public Object validationConfigWriteSB(HashMap<String, String> map) {
		String valConfig="";
		validationConfig=new StringBuffer();
		valConfig="<ValidationInfo><Validation>"+map.get("procCode")+"</Validation><Class>panacea.Validator."+javaFileName+"</Class><Method>"+map.get("methodName")+"</Method></ValidationInfo>\r\n";
		validationConfig.append(valConfig+"\n");
		return validationConfig;
	}

}
