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

//실제 데이터 업로드

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

	  //아뜰리에 select box에 모든 아뜰리에 insert
	  @RequestMapping({"/desserting/getAteliers"})
	  @ResponseBody
	  public JSONArray getAteliers(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
	    JSONArray result = new JSONArray();
	    result = rows2JsonArray(getServiceManager().getDessertingCarrier().getAteliers());

	    return result;
	  }

      //각 토픽의 단어와 수치값 insert
	  @RequestMapping({"/desserting/putWords"})
	  @ResponseBody
	  public void putWords(HttpServletRequest request, @ModelAttribute DessertingForm form, ModelMap model) throws SQLException, IOException {
	    try {
	            FileInputStream file = new FileInputStream("./Desserting/WEB-INF/excel/desserting/words.xlsx");
	            XSSFWorkbook workbook = new XSSFWorkbook(file);

	            int rowindex=0;
	            int columnindex=0;
	            XSSFSheet sheet=workbook.getSheetAt(0);
	            int rows=sheet.getPhysicalNumberOfRows();

	            for(rowindex=1;rowindex<rows;rowindex++){
                    String value="";
                    float num = -1;
                    int topicNum = 0;
	                XSSFRow row=sheet.getRow(rowindex);
	                if(row !=null){
	                    int cells=row.getPhysicalNumberOfCells();
	                    for(columnindex=0; columnindex<=cells; columnindex++){
	                        XSSFCell cell=row.getCell(columnindex);
	                        if(cell==null) {
	                            continue;
	                        }else {
	                           switch (cell.getCellTypeEnum()){
	                            case FORMULA:
	                                value=cell.getCellFormula();
	                                break;
	                            case NUMERIC:
	                                num =(float)cell.getNumericCellValue();
	                                break;
	                            case STRING:
	                                value=cell.getStringCellValue()+"";
	                                break;
	                            case BLANK:
	                                value=cell.getBooleanCellValue()+"";
	                                break;
	                            case ERROR:
	                                value=cell.getErrorCellValue()+"";
	                                break;
	                            }
	                        }

	                        if(num>0) {
	                        	if(columnindex == 0) {
	                        		topicNum = (int)num;
	                        	}else {
	                        		num = num;
	                        	}
	                        }else {
	                        	value = value;
	                        }
	                        if(columnindex==2) {
	                        	System.out.println(topicNum + "/"+ value+ "/" +num);
	                        	getServiceManager().getDessertingCarrier().putWords(topicNum, value, num);
	                        }
	                    }
	                }
	            }
		}catch(Exception e) {
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
}//DessertingController end