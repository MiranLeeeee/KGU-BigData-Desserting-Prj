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

}//DessertingController end