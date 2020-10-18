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

//DB와 엑셀파일에 임의의 데이터 삽입 후 test

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

}//DessertingController end