package dynamic;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
@Author Bharath Sri - 32L - OTP - 10-09-2023 For Master GET API Creation With Pagination
*/

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Implementation.DocumentWrite;
import Implementation.InsertWrite;
import Implementation.JsonWrite;
import Implementation.MethodWrite;
import Implementation.ValidationConfig;
import write.DynamicWrite;  

public class DynamicController {

	public static StringBuffer sbJSONLines = new StringBuffer();
	public static StringBuffer methodLines = new StringBuffer();
	public static StringBuffer FinalmethodLines = new StringBuffer();
	public static StringBuffer validationConfig = new StringBuffer();
	public static StringBuffer insertQuery = new StringBuffer();
	public static StringBuffer jsonWrite = new StringBuffer();
	public static StringBuffer documentLines = new StringBuffer();
	public static StringBuffer fullmethodLines = new StringBuffer();
	public static StringBuffer fullvalidationConfig = new StringBuffer();
	public static StringBuffer fullinsertQuery = new StringBuffer();
	public static StringBuffer fulljsonWrite = new StringBuffer();

	
	static Path fileName;
	static Path closeErrorLogPath;
	static Path jsonPath;
	static Path jsonFileName;
	static Path fullPath;
	static Path fullLogPath;
	static Path validationName;
	static Path valConfigPath;
	static Path insertQueryName;
	static Path insertQueryPath;
	static Path doucumentName;
	static Path documentPath;
	static Path subPath;
	public Date today;
	public static String procCode = "";
	public static String methodName = "";
	public static String service = "";
	public static String reqPath = "";
	public static String query = "";
	public static String apiDesc = "";
	public static String mainQuery = "";
	public static String paginationQuery = "";
	public static String host="";
	public static String javaFileName ="";

	public HashMap<String, String> queryMap = new HashMap<>();
	public static HashMap<String, String> jsonWhere = new HashMap<>();
	public static List<String> whereColumn = new ArrayList<>();
	public static List<String> condStr = new ArrayList<>();
	public static List<String> aliasNameLst = new ArrayList<>();

	public static boolean pagination = true;
	public boolean countQueryFlag = false;

	public DynamicController() {
		// TODO Auto-generated constructor stub
		super();
	}

	static JsonWrite dynImpl = new JsonWrite();
	static DynamicWrite write = new DynamicWrite();
	static MethodWrite methodWt = new MethodWrite();
	static ValidationConfig valConfig = new ValidationConfig();
	static InsertWrite instWt = new InsertWrite();
	static DocumentWrite docWtr = new DocumentWrite();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			Properties prop = new Properties();
			InputStream property = DynamicController.class.getClassLoader().getResourceAsStream("config.properties");
			prop.load(property);
			Path pathInJava = Paths.get(prop.getProperty("INPUT_DIR"));
			Path pathOutJava = Paths.get(prop.getProperty("OUTPUT_DIR"));
			javaFileName = prop.getProperty("FILE_NAME");
			host = prop.getProperty("HOST");
			HashMap<String, String> map = new HashMap<>();
			try (Stream<Path> walk = Files.walk(pathInJava)) {
				// We want to find only regular files with java
				List<String> javaFileList = walk.filter(Files::isRegularFile)
						.filter(path -> path.toString().toLowerCase().endsWith(".json")).map(x -> x.toString())
						.collect(Collectors.toList());
				for (String path : javaFileList) {
					List<String> allJsonLine = Files.readAllLines(Paths.get(path));
					String json = String.join("\n", allJsonLine);
					@SuppressWarnings("deprecation")
					JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
					JsonArray arr = jsonObject.getAsJsonArray("record");
					FinalmethodLines = new StringBuffer();
					for (int i = 0; i < arr.size(); i++) {
						try {
							sbJSONLines = new StringBuffer();
							FinalmethodLines = new StringBuffer();
							validationConfig = new StringBuffer();
							insertQuery = new StringBuffer();
							documentLines = new StringBuffer();
							pagination = true;
							methodName="";
							procCode="";
							service="";
							reqPath="";
							query="";
							apiDesc="";
							procCode = arr.get(i).getAsJsonObject().get("procCode").getAsString();
							methodName = arr.get(i).getAsJsonObject().get("methodName").getAsString();
							service = arr.get(i).getAsJsonObject().get("service").getAsString();
							reqPath = arr.get(i).getAsJsonObject().get("reqPath").getAsString();
							query = arr.get(i).getAsJsonObject().get("query").getAsString();
							apiDesc = arr.get(i).getAsJsonObject().get("apiDesc").getAsString();
							assignMap(map);
							// File Name - Beg
							Path subPathOut = Paths.get(procCode+"_"+methodName+"/"); 
							Path fileOut = pathOutJava.resolve(subPathOut);
							Files.createDirectories(fileOut);
							fileName = Paths.get(methodName+".java");
							fullPath = fileOut.resolve(fileName);
							jsonFileName = Paths.get("SQLResource.json");
							jsonPath = fileOut.resolve(jsonFileName);
							validationName = Paths.get("Validation.XML");
							valConfigPath = fileOut.resolve(validationName);
							insertQueryName= Paths.get("INSERT_SCRIPTS_"+procCode+".SQL");
							insertQueryPath= fileOut.resolve(insertQueryName);
							doucumentName= Paths.get(procCode+"_"+methodName+"_Document.txt");
							documentPath= fileOut.resolve(doucumentName);
							// File Name - End
							sbJSONLines.append(dynImpl.jsonWriteSB(map,jsonWhere,whereColumn));
							validationConfig.append(valConfig.validationConfigWriteSB(map));
							FinalmethodLines.append(methodWt.methodWriteSB(map));
							insertQuery.append(instWt.insertQueryWriteSB(map));
							docWtr.documentWriteSB(aliasNameLst);
							write.jsonWriteFile(sbJSONLines, jsonPath);
							write.methodWrite(FinalmethodLines, fullPath);
							write.validationWrite(validationConfig, valConfigPath);
							write.insertQueryWrite(insertQuery, insertQueryPath);
							write.documentWrite(documentLines,documentPath);
							
							Path fileOutDocWrite = pathOutJava.resolve(Paths.get("allDocInsert/"));//add
							Files.createDirectories(fileOutDocWrite);
							Path fullDocPath= fileOutDocWrite.resolve(doucumentName);//add
							Path fullInsertDocPath= fileOutDocWrite.resolve(insertQueryName);//add
							write.allDocumentWrite(insertQuery,documentLines,fullDocPath,fullInsertDocPath);
							fullmethodLines.append("\n"+FinalmethodLines.toString());
							fulljsonWrite.append(",\n"+sbJSONLines.toString());
							fullvalidationConfig.append("\n"+validationConfig.toString());

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
					//For full files in one method
					Path subPathOut = Paths.get("fullLines"); 
					Path fileOut = pathOutJava.resolve(subPathOut);
					Files.createDirectories(fileOut);
					fileName = Paths.get("java.java");
					fullPath = fileOut.resolve(fileName);
					jsonFileName = Paths.get("SQLResource.json");
					jsonPath = fileOut.resolve(jsonFileName);
					validationName = Paths.get("Validation.XML");
					valConfigPath = fileOut.resolve(validationName);
					write.fullMethodWrite(FinalmethodLines,fulljsonWrite,fullvalidationConfig, fullPath,jsonPath,valConfigPath);
					//For full files in one method
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void assignMap(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		map.put("procCode", procCode);
		map.put("methodName", methodName);
		map.put("service", service);
		map.put("reqPath", reqPath);
		map.put("query", query);
		map.put("apiDesc", apiDesc);
	}

}
