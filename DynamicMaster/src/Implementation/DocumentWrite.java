package Implementation;

import java.util.List;

import dynamic.DynamicController;

/*
@Author Bharath Sri - 32L - OTP - 10-09-2023 For Master GET API Creation With Pagination
*/

public class DocumentWrite extends DynamicController {

	public DocumentWrite() {
		// TODO Auto-generated constructor stub
	}

	public StringBuffer documentWriteSB(List<String> aliasNameLst) {
		// TODO Auto-generated method stub
		documentLines.append("Name			:"+apiDesc+"\n");
		documentLines.append("Description		:"+apiDesc+"\n");
		documentLines.append("Process Code	:"+procCode+"\n");
		documentLines.append("Method			:GET"+"\n");
		documentLines.append("URL				:"+host+service+reqPath+"?userID=IDCADMIN\n");
		documentLines.append("Parameters eligible to send:\n");
		for (String i:aliasNameLst) {
			documentLines.append("\t\t\t\t\t\t\t"+i+"\n");
		}
		return documentLines;
	}

}
