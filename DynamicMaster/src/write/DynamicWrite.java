package write;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import dynamic.DynamicController;


/*
@Author Bharath Sri - 32L - OTP - 10-09-2023 For Master GET API Creation With Pagination
*/


public class DynamicWrite extends DynamicController {

	public DynamicWrite() {
		// TODO Auto-generated constructor stub
	}

	public void jsonWriteFile(StringBuffer sbJSONLines, Path jsonPath) {
		// TODO Auto-generated method stub
		try {
			Files.write(jsonPath, sbJSONLines.toString().getBytes(StandardCharsets.ISO_8859_1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void methodWrite(StringBuffer methodLines, Path fullPath) {
		// TODO Auto-generated method stub
		try {
			Files.write(fullPath, methodLines.toString().getBytes(StandardCharsets.ISO_8859_1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void validationWrite(StringBuffer validationConfig, Path valConfigPath) {
		try {
			Files.write(valConfigPath, validationConfig.toString().getBytes(StandardCharsets.ISO_8859_1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertQueryWrite(StringBuffer insertQuery, Path insertQueryPath) {
		// TODO Auto-generated method stub
		try {
			Files.write(insertQueryPath, insertQuery.toString().getBytes(StandardCharsets.ISO_8859_1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void documentWrite(StringBuffer documentLines, Path documentPath) {
		// TODO Auto-generated method stub
		try {
			Files.write(documentPath, documentLines.toString().getBytes(StandardCharsets.ISO_8859_1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void fullmethodWrite(StringBuffer fullmethodLines, Path pathOutJava) {
		// TODO Auto-generated method stub
		try {
			Files.write(pathOutJava, fullmethodLines.toString().getBytes(StandardCharsets.ISO_8859_1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void fullMethodWrite(StringBuffer finalmethodLines, StringBuffer sbJSONLines, StringBuffer validationConfig,
			Path fullPath, Path jsonPath, Path valConfigPath) {
		// TODO Auto-generated method stub
		try {
			Files.write(fullPath, fullmethodLines.toString().getBytes(StandardCharsets.ISO_8859_1));
			Files.write(jsonPath, sbJSONLines.toString().getBytes(StandardCharsets.ISO_8859_1));
			Files.write(valConfigPath, validationConfig.toString().getBytes(StandardCharsets.ISO_8859_1));

		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public void allDocumentWrite(StringBuffer insertQuery, StringBuffer documentLines, Path fullDocPath,
			Path fullInsertDocPath) {
		// TODO Auto-generated method stub
		try {
			Files.write(fullDocPath, documentLines.toString().getBytes(StandardCharsets.ISO_8859_1));
			Files.write(fullInsertDocPath, insertQuery.toString().getBytes(StandardCharsets.ISO_8859_1));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
