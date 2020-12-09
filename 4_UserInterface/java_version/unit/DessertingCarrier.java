package com.project.prj_Desserting.unit.carrier;

import framework.db.IDBController;
import framework.db.Rows;

public class DessertingCarrier extends Carrier {

	  private final static String PACKAGE_NAME = "DESSERTING";

	  public DessertingCarrier(IDBController dc) {
	    super(dc);
	  }

	  //추천 아뜰리에 select
	  public Rows findAteliers(String id) {
		    return getRows(makeProcedure(PACKAGE_NAME + "@findAteliers", id));
	 }

	 //아뜰리에 select box insert
	 public Rows getAteliers() {
		    return getRows(makeProcedure(PACKAGE_NAME + "@getAteliers"));
	 }

	 //선택한 아뜰리에의 토픽 select
	 public Rows getTopicNum(String name) {
		    return getRows(makeProcedure(PACKAGE_NAME + "@getTopicNum", name));
	 }

	 //각 토픽의 아뜰리에 정보 select
	 public Rows getAtelierList(int topicNum) {
		    return getRows(makeProcedure(PACKAGE_NAME + "@getAtelierList", topicNum));
	 }

	 //토픽별 단어와 수치 값 insert
	 public void putWords(int topicNum, String word, float valNum) {
		    execute(makeProcedure(PACKAGE_NAME + "@putWords", topicNum, word, valNum));
	 }

	 //토픽별 단어와 수치값 select
	 public Rows getWords(int topicNum) {
		    return getRows(makeProcedure(PACKAGE_NAME + "@getWords", topicNum));
	 }

	 //아뜰리에 정보 insert
	 public void setAteliers(String atelierId, String atelierName, int topicNum, String description) {
		    execute(makeProcedure(PACKAGE_NAME + "@setAteliers", atelierId, atelierName, topicNum, description));
	 }

	 //사용자 정보 insert
	 public void setUsers(String userId, String userName, int topicNum) {
		    execute(makeProcedure(PACKAGE_NAME + "@setUsers", userId, userName, topicNum));
	 }

	 //사용자의 토픽 select
	 public Rows getUserTopic(String id) {
		 return getRows(makeProcedure(PACKAGE_NAME + "@getUserTopic", id));
	 }

	 //모든 토픽 select
	 public Rows getTopics() {
		 return getRows(makeProcedure(PACKAGE_NAME + "@getTopics"));
	 }

	 //기존 사용자 있는지 없는지 여부를 check
	 public Rows checkUser(String id) {
		 return getRows(makeProcedure(PACKAGE_NAME + "@checkUser", id));
	 }

	 //검색한 아뜰리에가 있는지 여부를 check
	 public Rows checkAtelier(String atelierName) {
		 return getRows(makeProcedure(PACKAGE_NAME + "@checkAtelier", atelierName));
	 }

	 //기존 사용자면 토픽을 update
	 public void updateUser(int topicNum, String id) {
		    execute(makeProcedure(PACKAGE_NAME + "@updateUser", topicNum, id));
	 }

	 //신규 사용자면 사용자 정보 자체를 insert
	 public void insertUser(String id, String name, int topicNum) {
		    execute(makeProcedure(PACKAGE_NAME + "@insertUser", id, name, topicNum));
	 }

	 //사용자의 토픽 유사도를 임의로 insert
	 public void insertUserSim(String id, float t1, float t2, float t3, float t4, float t5) {
		    execute(makeProcedure(PACKAGE_NAME + "@insertUserSim", id, t1, t2, t3, t4, t5));
	 }

	 //기존 사용자 토픽 유사도 임의로 update
	 public void updateUserSim(String id, float t1, float t2, float t3, float t4, float t5) {
		    execute(makeProcedure(PACKAGE_NAME + "@updateUserSim", id, t1, t2, t3, t4, t5));
	 }

	 //사용자의 토픽별 유사도 수치 insert
	 public void setUserSim(String id, float t1, float t2, float t3, float t4, float t5) {
		    execute(makeProcedure(PACKAGE_NAME + "@setUserSim", id, t1, t2, t3, t4, t5));
	 }

	 //아뜰리에 토픽별 유사도 수치 insert
	 public void setAtelierSim(String id, float t1, float t2, float t3, float t4, float t5) {
		    execute(makeProcedure(PACKAGE_NAME + "@setAtelierSim", id, t1, t2, t3, t4, t5));
	 }

	 //사용자 토픽별 유사도 수치 select
	 public Rows getUserSim(String userId) {
		 return getRows(makeProcedure(PACKAGE_NAME + "@getUserSim", userId));
	 }

	 //아뜰리에 이름으로 아뜰리에 id select
	 public Rows getAtelierId(String atelierName) {
		 return getRows(makeProcedure(PACKAGE_NAME + "@getAtelierId", atelierName));
	 }

	 //아뜰리에 토픽별 유사도 수치 select
	 public Rows getAtelierSim(String atelierId) {
		 return getRows(makeProcedure(PACKAGE_NAME + "@getAtelierSim", atelierId));
	 }

	 //토픽 이름 전부 select
	 public Rows getTopicNames() {
		 return getRows(makeProcedure(PACKAGE_NAME + "@getTopicNames"));
	 }

	 //사용자 토픽 select
	 public Rows findTopicName(int topicNum) {
		 return getRows(makeProcedure(PACKAGE_NAME + "@findTopicName", topicNum));
	 }

}