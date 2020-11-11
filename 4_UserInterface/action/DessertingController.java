package com.project.prj_Desserting.webapp.action.desserting;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.prj_Desserting.webapp.action.prj_DessertingController;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Controller
public class DessertingController extends prj_DessertingController {

	  @RequestMapping(value = "/atelier")
	  public String DessertingPage(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException, ParseException {
		  return "/main/atelier";
	  }


	  @RequestMapping(value = "/result")
	  public String setDessertingPage(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException, ParseException {
		  return "/main/result";
	  }


	  //추천된 아뜰리에 select
	  @RequestMapping({"/desserting/findAteliers"})
	  @ResponseBody
	  public JSONArray findAteliers(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		  JSONArray result = new JSONArray();
		  String id = form.getId();
		  result = rows2JsonArray(getServiceManager().getDessertingCarrier().findAteliers(id));
		  return result;
	  }


	  //아뜰리에 셀렉트박스에 모든 아뜰리에 insert
	  @RequestMapping({"/desserting/getAteliers"})
	  @ResponseBody
	  public JSONArray getAteliers(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		  JSONArray result = new JSONArray();
		  result = rows2JsonArray(getServiceManager().getDessertingCarrier().getAteliers());
		  return result;
	  }


	  //선택한 아뜰리에 토픽 select
	  @RequestMapping({"/desserting/getTopicNum"})
	  @ResponseBody
	  public JSONArray getTopicNum(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		  String name = form.getName();
		  JSONArray result = new JSONArray();
		  result = rows2JsonArray(getServiceManager().getDessertingCarrier().getTopicNum(name));
		  return result;
	  }


	  //각 토픽의 아뜰리에 정보 select
	  @RequestMapping({"/desserting/getAtelierList"})
	  @ResponseBody
	  public JSONArray getAtelierList(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		  int topicNum = form.getTopicNum();
		  JSONArray result = new JSONArray();
		  result = rows2JsonArray(getServiceManager().getDessertingCarrier().getAtelierList(topicNum));
		  return result;
	  }


	  //각 토픽의 단어와 수치값 insert
	  @RequestMapping({"/desserting/putWords"})
	  @ResponseBody
	  public void putWords(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		try {
			FileInputStream file = new FileInputStream("./desserting/excel/words.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			int rowindex = 0;
			int columnindex = 0;
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();

			for (rowindex = 1; rowindex < rows; rowindex++) {
				String value = "";
				float num = -1;
				int topicNum = 0;
				XSSFRow row = sheet.getRow(rowindex);

				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();

					for (columnindex = 0; columnindex <= cells; columnindex++) {
						XSSFCell cell = row.getCell(columnindex);

						if (cell == null) {
							continue;
						}
						else {
							switch (cell.getCellTypeEnum()) {
								case FORMULA:
									value = cell.getCellFormula();
									break;
								case NUMERIC:
									num = (float) cell.getNumericCellValue();
									break;
								case STRING:
									value = cell.getStringCellValue() + "";
									break;
								case BLANK:
									value = cell.getBooleanCellValue() + "";
									break;
								case ERROR:
									value = cell.getErrorCellValue() + "";
									break;
							}
						}

						if (num > 0) {
							if (columnindex == 0) {
								topicNum = (int) num;
							}
							else {
								num = num;
							}
						}
						else {
							value = value;
						}

						if (columnindex == 2) {
							System.out.println(topicNum + "/" + value + "/" + num);
							getServiceManager().getDessertingCarrier().putWords(topicNum, value, num);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }


	  //각 토픽별 단어와 수치값 select
	  @RequestMapping({"/desserting/getWords"})
	  @ResponseBody
	  public JSONArray getWords(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		  int topicNum = form.getTopicNum();
		  JSONArray result = new JSONArray();
		  result = rows2JsonArray(getServiceManager().getDessertingCarrier().getWords(topicNum));
		  return result;
	  }


	  //각 아뜰리에 정보를 insert
	  @RequestMapping({"/desserting/setAteliers"})
	  @ResponseBody
	  public void setAteliers(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		try {
			FileInputStream file = new FileInputStream("./desserting/excel/ateliers.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			int rowindex = 0;
			int columnindex = 0;
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();

			for (rowindex = 1; rowindex < rows; rowindex++) {
				String value = "";
				int id = 0;
				String atelierId = "";
				String atelierName = "";
				int topicNum = -1;
				String description = "";
				XSSFRow row = sheet.getRow(rowindex);

				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();

					for (columnindex = 0; columnindex <= cells; columnindex++) {
						XSSFCell cell = row.getCell(columnindex);

						if (cell == null) {
							continue;
						}
						else {
							switch (cell.getCellTypeEnum()) {
								case FORMULA:
									value = cell.getCellFormula();
									break;
								case NUMERIC:
									topicNum = (int) cell.getNumericCellValue();
									break;
								case STRING:
									value = cell.getStringCellValue() + "";
									break;
								case BLANK:
									value = cell.getBooleanCellValue() + "";
									break;
								case ERROR:
									value = cell.getErrorCellValue() + "";
									break;
							}
						}

						// topicNum (index=2)인 것만 숫자형처리
						if (columnindex == 0) {
							id = topicNum;
							atelierId = Integer.toString(id);
						}
						else if (columnindex == 1) {
							atelierName = value;
						}
						else if (columnindex == 2) {
							topicNum = topicNum;
						}
						else {
							description = value;
							getServiceManager().getDessertingCarrier().setAteliers(atelierId, atelierName, topicNum, description);
							System.out.println(atelierId + "/" + atelierName + "/" + topicNum + "/" + description);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }


	  //인스타기반 사용자정보 insert
	  @RequestMapping({"/desserting/setUsers"})
	  @ResponseBody
	  public void setUsers(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		try {
			FileInputStream file = new FileInputStream("./desserting/excel/users.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			int rowindex = 0;
			int columnindex = 0;
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();

			for (rowindex = 1; rowindex < rows; rowindex++) {
				String value = "";
				int id = 0;
				String userId = "";
				String userName = "";
				int topicNum = -1;
				XSSFRow row = sheet.getRow(rowindex);

				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();

					for (columnindex = 0; columnindex <= cells; columnindex++) {
						XSSFCell cell = row.getCell(columnindex);

						if (cell == null) {
							continue;
						}
						else {
							switch (cell.getCellTypeEnum()) {
								case FORMULA:
									value = cell.getCellFormula();
									break;
								case NUMERIC:
									topicNum = (int) cell.getNumericCellValue();
									break;
								case STRING:
									value = cell.getStringCellValue() + "";
									break;
								case BLANK:
									value = cell.getBooleanCellValue() + "";
									break;
								case ERROR:
									value = cell.getErrorCellValue() + "";
									break;
							}
						}

						// topicNum (index=2)인 것만 숫자형처리
						if (columnindex == 0) {
							userId = value;
						}
						else if (columnindex == 1) {
							userName = value;
						}
						else {
							topicNum = topicNum;
							getServiceManager().getDessertingCarrier().setUsers(userId, userName, topicNum);
							System.out.println(userId + "/" + userName + "/" + topicNum);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }

	  //사용자 토픽별 유사도 insert
	  @RequestMapping({"/desserting/setUserSim"})
	  @ResponseBody
	  public void setUserRatio(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		try {

			FileInputStream file = new FileInputStream("./desserting/excel/users_sim.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			int rowindex = 0;
			int columnindex = 0;
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();

			for (rowindex = 1; rowindex < rows; rowindex++) {
				String value = "";
				String userId = "";
				float sim = 0;
				float t1 = 0;
				float t2 = 0;
				float t3 = 0;
				float t4 = 0;
				float t5 = 0;
				XSSFRow row = sheet.getRow(rowindex);

				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();

					for (columnindex = 0; columnindex <= cells; columnindex++) {
						XSSFCell cell = row.getCell(columnindex);

						if (cell == null) {
							continue;
						}
						else {
							switch (cell.getCellTypeEnum()) {
								case FORMULA:
									value = cell.getCellFormula();
									break;
								case NUMERIC:
									sim = (float) cell.getNumericCellValue();
									break;
								case STRING:
									value = cell.getStringCellValue() + "";
									break;
								case BLANK:
									value = cell.getBooleanCellValue() + "";
									break;
								case ERROR:
									value = cell.getErrorCellValue() + "";
									break;
							}
						}

						if (columnindex == 0) {
							userId = value;
						}
						else if (columnindex == 1) {
							t1 = sim;
						}
						else if (columnindex == 2) {
							t2 = sim;
						}
						else if (columnindex == 3) {
							t3 = sim;
						}
						else if (columnindex == 4) {
							t4 = sim;
						}
						else {
							t5 = sim;
							System.out.println(userId + "/" + t1 + "/" + t2 + "/" + t3 + "/" + t4 + "/" + t5);
							getServiceManager().getDessertingCarrier().setUserSim(userId, t1, t2, t3, t4, t5);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }


	  //아뜰리에별 토픽 유사도 insert
	  @RequestMapping({"/desserting/setAtelierSim"})
	  @ResponseBody
	  public void setAtelierRatio(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		try {

			FileInputStream file = new FileInputStream("./desserting/excel/ateliers_sim.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			int rowindex = 0;
			int columnindex = 0;
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();

			for (rowindex = 1; rowindex < rows; rowindex++) {
				String value = "";
				int id = -1;
				String atelierId = "";
				float sim = 0;
				float t1 = 0;
				float t2 = 0;
				float t3 = 0;
				float t4 = 0;
				float t5 = 0;
				XSSFRow row = sheet.getRow(rowindex);

				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();

					for (columnindex = 0; columnindex <= cells; columnindex++) {
						XSSFCell cell = row.getCell(columnindex);
						if (cell == null) {
							continue;
						} else {
							switch (cell.getCellTypeEnum()) {
								case FORMULA:
									value = cell.getCellFormula();
									break;
								case NUMERIC:
									sim = (float) cell.getNumericCellValue();
									break;
								case STRING:
									value = cell.getStringCellValue() + "";
									break;
								case BLANK:
									value = cell.getBooleanCellValue() + "";
									break;
								case ERROR:
									value = cell.getErrorCellValue() + "";
									break;
							}
						}

						if (columnindex == 0) {
							id = (int) sim;
							atelierId = Integer.toString(id);
						} else if (columnindex == 1) {
							t1 = sim;
						} else if (columnindex == 2) {
							t2 = sim;
						} else if (columnindex == 3) {
							t3 = sim;
						} else if (columnindex == 4) {
							t4 = sim;
						} else {
							t5 = sim;
							System.out.println(atelierId + "/" + t1 + "/" + t2 + "/" + t3 + "/" + t4 + "/" + t5);
							getServiceManager().getDessertingCarrier().setAtelierSim(atelierId, t1, t2, t3, t4, t5);
							;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }


	  //사용자 토픽 select
	  @RequestMapping({"/desserting/getUserTopic"})
	  @ResponseBody
	  public JSONArray getUserTopic(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		String id = form.getId();
		JSONArray result = new JSONArray();
		result = rows2JsonArray(getServiceManager().getDessertingCarrier().getUserTopic(id));
		return result;
	  }


	  //모든 토픽 select
	  @RequestMapping({ "/desserting/getTopics" })
	  @ResponseBody
	  public JSONArray getTopics(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		  JSONArray result = new JSONArray();
		  result = rows2JsonArray(getServiceManager().getDessertingCarrier().getTopics());
		  return result;
	  }


	  //기존 사용자인지 아닌지 check
	  @RequestMapping({"/desserting/checkUser"})
	  @ResponseBody
	  public JSONArray checkUser(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {			 JSONArray result = new JSONArray();
		   String id = form.getId();
		   result = rows2JsonArray(getServiceManager().getDessertingCarrier().checkUser(id));
		   return result;
	  }


	  //기존 사용자면 토픽을 update
	  @RequestMapping({"/desserting/updateUser"})
	  @ResponseBody
	  public void updateUser(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		  int topicNum = form.getTopicNum();
		  String id = form.getId();
		  getServiceManager().getDessertingCarrier().updateUser(topicNum, id);
	  }


	  //신규 사용자면 사용자 정보를 insert
	  @RequestMapping({"/desserting/insertUser"})
	  @ResponseBody
	  public void insertUser(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		  String id = form.getId();
		  String name = form.getName();
		  int topicNum = form.getTopicNum();
		  getServiceManager().getDessertingCarrier().insertUser(id, name, topicNum);
	  }


	  //신규 사용자의 유사도값 임의로 insert
	  @RequestMapping({"/desserting/insertUserSim"})
	  @ResponseBody
	  public void insertUserSim(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		  String id = form.getId();
		  float t1 = form.getT1();
		  float t2 = form.getT2();
		  float t3 = form.getT3();
		  float t4 = form.getT4();
		  float t5 = form.getT5();
		  getServiceManager().getDessertingCarrier().setUserSim(id, t1, t2,t3,t4,t5);
	  }


	  //기존 사용자 유사도 값 임의로 update
	  @RequestMapping({"/desserting/updateUserSim"})
	  @ResponseBody
	  public void updatetUserSim(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		  String id = form.getId();
		  float t1 = form.getT1();
		  float t2 = form.getT2();
		  float t3 = form.getT3();
		  float t4 = form.getT4();
		  float t5 = form.getT5();
		  getServiceManager().getDessertingCarrier().updateUserSim(id, t1, t2,t3,t4,t5);
	  }


	  //검색한 아뜰리에가 존재하는지 여부 check
	  @RequestMapping({"/desserting/checkAtelier"})
	  @ResponseBody
	  public JSONArray checkAtelier(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		  JSONArray result = new JSONArray();
		  String atelierName = form.getName();
		  result = rows2JsonArray(getServiceManager().getDessertingCarrier().checkAtelier(atelierName));
		  return result;
	  }


	  //사용자의 토픽별 유사도 수치 select
	  @RequestMapping({"/desserting/getUserSim"})
	  @ResponseBody
	  public JSONArray getUserSim(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		  JSONArray result = new JSONArray();
		  String userId = form.getId();
		  result = rows2JsonArray(getServiceManager().getDessertingCarrier().getUserSim(userId));
		  return result;
	  }


	  //아뜰리에 id select
	  @RequestMapping({"/desserting/getAtelierId"})
	  @ResponseBody
	  public JSONArray getAtelierId(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		  JSONArray result = new JSONArray();
		  String atelierName = form.getName();
		  result = rows2JsonArray(getServiceManager().getDessertingCarrier().getAtelierId(atelierName));
		  return result;
	  }


	  //아뜰리에 토픽별 유사도 수치 select
	  @RequestMapping({"/desserting/getAtelierSim"})
	  @ResponseBody
	  public JSONArray getAtelierSim(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		  JSONArray result = new JSONArray();
		  String atelierId = form.getId();
		  result = rows2JsonArray(getServiceManager().getDessertingCarrier().getAtelierSim(atelierId));
		  return result;
	  }


	  //아뜰리에 토픽이름 select
	  @RequestMapping({"/desserting/getTopicNames"})
	  @ResponseBody
	  public JSONArray getTopicNames(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		  JSONArray result = new JSONArray();
		  result = rows2JsonArray(getServiceManager().getDessertingCarrier().getTopicNames());
		  return result;
	  }


	  //사용자 토픽 이름 select
	  @RequestMapping({"/desserting/findTopicName"})
	  @ResponseBody
	  public JSONArray findTopicName(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
		  JSONArray result = new JSONArray();
		  int topicNum = form.getTopicNum();
		  result = rows2JsonArray(getServiceManager().getDessertingCarrier().findTopicName(topicNum));
		  return result;
	  }

}