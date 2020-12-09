<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.project.prj_Desserting.webapp.action.*"%>
<!DOCTYPE html>
<html>
<%=Face.loadMainHead(request, "Home")%>
<head>
<script src="../js/home.js"></script>
<link rel="stylesheet" type="text/css" href="../css/home.css">
</head>
<body class='pace-done mini-navbar'>
	<div id="wrapper">
		<!-- Navigator -->
		<%=Face.loadNavigator(request, "/home")%>
		<!-- End Navigator -->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<%=Face.loadTopNavigator(request)%>
			<div class="wrapper wrapper-content">
				<div class="row">
                	<div class="col-lg-12">
                    	<div class="ibox ">
                        	<div class="ibox-title">
                            	<h2>MBTI로 알아보는 나와 찰떡궁합 디저트 가게</h2>
                            	<!-- <button onclick="putWords()">단어 넣기</button> -->
                            	<!-- <button onclick="setAteliers();">아뜰리에 정보 넣기</button> -->
                            	<!-- <button onclick="setUsers();">사용자 정보 넣기</button> -->
                            	<!-- <button onclick="setUserSim();">사용자별 토픽유사도 넣기</button> -->
                            	<!-- <button onclick="setAtelierSim();">아뜰리에별 토픽유사도 넣기</button> -->
                        	</div>
                        	<div class="ibox-content">
                            	<form method="post" action="/result" onsubmit="return checkSurvey();">
                            	    <div class="form-group  row">
                            	    	<label class="col-sm-3 col-form-label">아이디</label>
                                    	<div class="col-sm-9">
                                    		<input type="text" id="id" name="id" class="form-control">
                                    	</div>
                                	</div>
                                	<div class="hr-line-dashed"></div>
                                	<div class="form-group  row">
                                		<label class="col-sm-3 col-form-label">이름</label>
                                    	<div class="col-sm-9">
                                    		<input type="text" id="name" name="name" class="form-control">
                                    	</div>
                                	</div>
                                	<div class="hr-line-dashed"></div>
                                	<div class="form-group row">
                                		<label class="col-sm-3 col-form-label">
                                			1. 현재 당신 앞에 다음과 같이 있다면<br>무엇을 선택하시겠습니까? <br>(2가지 선택)<br>
                                    	</label>
                                    	<div class="col-sm-9">
                                        	<div>
	                                        	<label><input type="checkbox" value="1" id="" name="choiceCheck1"><img class= "choiceImg" src="/res/img/cake.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#케익</button></label>
	                                        	<label><input type="checkbox" value="2" id="" name="choiceCheck1"><img class= "choiceImg" src="/res/img/bread2.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#식빵</button></label>&nbsp;
	                                        	<label><input type="checkbox" value="3" id="" name="choiceCheck1"><img class= "choiceImg" src="/res/img/ice.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#빙수</button></label>&nbsp;
	                                        	<label><input type="checkbox" value="4" id="" name="choiceCheck1"><img class= "choiceImg" src="/res/img/latte.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#라떼</button></label>&nbsp;
	                                        	<label><input type="checkbox" value="5" id="" name="choiceCheck1"><img class= "choiceImg" src="/res/img/cheese.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#치즈</button></label>
                                        	</div>
                                    	</div>
                                	</div>
                                	<div class="hr-line-dashed"></div>
                                	<div class="form-group row">
                                		<label class="col-sm-3 col-form-label">
                                    		2. 당신앞에 (  )이(가) 특화된 카페가 있습니다. 어딜 가시겠습니까?<br> (1가지 선택)
                                    	</label>
                                    	<div class="col-sm-9">
                                        	<div>
                                        		<label><input type="radio" value="1" name="choiceCheck2"><img class= "choiceImg" src="/res/img/macaron2.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#마카롱</button></label>
                                        		<label><input type="radio" value="2" name="choiceCheck2"><img class= "choiceImg"  src="/res/img/scones.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#스콘</button></label>&nbsp;
                                        		<label><input type="radio" value="3" name="choiceCheck2"><img class= "choiceImg" src="/res/img/ice.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#빙수</button></label>&nbsp;
                                        		<label><input type="radio" value="4" name="choiceCheck2"><img class= "choiceImg" src="/res/img/mood2.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#인테리어</button></label>&nbsp;
                                        		<label><input type="radio" value="5" name="choiceCheck2"><img class= "choiceImg" src="/res/img/cookie.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#쿠키</button></label>
                                        	</div>
                                    	</div>
                                	</div>
                                	<div class="hr-line-dashed"></div>
                                	<div class="form-group row">
                                		<label class="col-sm-3 col-form-label">
                                			3.카페에 도착한 당신, <br>다 품절되고 다음과 같이 5가지 메뉴만  남았을 때 무엇을 주문하시겠습니까?<br>(2가지 선택)<br>
                                    	</label>
                                    	<div class="col-sm-9">
                                        	<div>
	                                        	<label><input type="checkbox" value="1" id="" name="choiceCheck3"><img class= "choiceImg" src="/res/img/strawberry.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#딸기마카롱</button ></label>
	                                        	<label><input type="checkbox" value="2" id="" name="choiceCheck3"><img class= "choiceImg" src="/res/img/bread.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#고구마식빵</button></label>&nbsp;
	                                        	<label><input type="checkbox" value="3" id="" name="choiceCheck3"><img class= "choiceImg" src="/res/img/brunch.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#브런치</button></label>&nbsp;
	                                        	<label><input type="checkbox" value="4" id="" name="choiceCheck3"><img class= "choiceImg" src="/res/img/coffee2.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#음료</button></label>&nbsp;
	                                        	<label><input type="checkbox" value="5" id="" name="choiceCheck3"><img class= "choiceImg" src="/res/img/scones.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#치즈스콘</button></label>
                                        	</div>
                                    	</div>
                                	</div>
                                	<div class="hr-line-dashed"></div>
                                	<div class="form-group row">
                                		<label class="col-sm-3 col-form-label"><br>
                                    		4. 힘들게 카페에 들어왔는데 <br>남은 메뉴는 단하나,<br> 이 메뉴만은 피하고 싶다. <br>(1가지 선택)
                                    	</label>
                                    	<div class="col-sm-9">
                                        	<div>
	                                        	<label><input type="radio" value="1" name="choiceCheck4"><img class= "choiceImg" src="/res/img/strawberry.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#딸기마카롱</button></label>
	                                        	<label><input type="radio" value="2" name="choiceCheck4"><img class= "choiceImg"  src="/res/img/scones.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#버터스콘</button></label>&nbsp;
	                                        	<label><input type="radio" value="3" name="choiceCheck4"><img class= "choiceImg" src="/res/img/cookie2.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#구운과자</button></label>&nbsp;
	                                        	<label><input type="radio" value="4" name="choiceCheck4"><img class= "choiceImg" src="/res/img/latte.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#라떼</button></label>&nbsp;
	                                        	<label><input type="radio" value="5" name="choiceCheck4"><img class= "choiceImg" src="/res/img/chocolate.jpg"><button class="btn btn-primary btn-sm btn_tag" disabled>#초콜릿</button></label>
                                        	</div>
                                    	</div>
                                	</div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group row">
                                    <div class="col-sm-4 col-sm-offset-2" >
                                        <button class="btn btn-white btn-sm" type="reset">초기화</button>
                                        <button class="btn btn-primary btn-sm" type="submit">완료</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
		</div>
			<%=Face.loadFooter(request)%>
	</div>
</div>
	<%=Face.loadScript(request)%>
	<script type="text/javascript" src="/js/prj_Desserting/d3.min.js"></script>
	<script type="text/javascript" src="/js/prj_Desserting/d3.v3.min.js"></script>
	<script type="text/javascript" src="/js/prj_Desserting/d3.layout.cloud.js"></script>
</body>
</html>
