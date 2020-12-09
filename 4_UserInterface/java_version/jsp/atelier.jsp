<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.project.prj_Desserting.webapp.action.*"%>
<!DOCTYPE html>
<html>
<%=Face.loadMainHead(request, "Atelier")%>
<head>
<script src="../js/atelier.js"></script>
<link rel="stylesheet" type="text/css" href="../css/atelier.css">
</head>
<body class='pace-done mini-navbar'>
	<div id="wrapper">
		<!-- Navigator -->
		<%=Face.loadNavigator(request, "/atelier")%>
		<!-- End Navigator -->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<%=Face.loadTopNavigator(request)%>
			<div class="wrapper wrapper-content">
				<div class="row">
					<div class="col-lg-12">
						<div class="ibox">
							<div class="ibox-title">
								<h2>리뷰를 통한 아뜰리에 특성파악</h2>
							</div>
							<div class="ibox-content">
								<input type="text" style="width: 90%; display: inline" placeholder="아뜰리에를 입력해주세요." id="atelierInput" name="atelierName" class="form-control">&nbsp;
								<button class="btn btn-primary btn-sm" onclick="showInfo();">검색</button>
								<div id="showContent" style="height: 600px">
									<span id="defaultText" style="display: block">검색 결과가 없습니다. 아뜰리에를 검색해주세요.</span>
									<div id="showDiv" style="display: none"><br>
										<h3 id="resultAnalysis"></h3><hr>
										<div id="graphDiv" style="float: left; padding-right: 40px;">
											<h5>해당 아뜰리에 특성 키워드 분포도</h5>
											<canvas id="graphCanvas"></canvas>
										</div>
										<div id="listDiv" style="float: left; width: 500px; height: 450px; overflow: scroll;">
											<h4>경쟁 아뜰리에 리스트</h4>
											<ul class="list-group clear-list m-t">
												<li class="list-group-item fist-item">
													<span class="label label-success">1</span>
													<span class="atelierSpan"></span>
												</li>
												<li class="list-group-item">
													<span class="label label-info">2</span>
													<span class="atelierSpan"></span>
												</li>
												<li class="list-group-item">
													<span class="label label-primary">3</span>
													<span class="atelierSpan"></span>
												</li>
												<li class="list-group-item">
													<span class="label label-default">4</span>
													<span class="atelierSpan"></span>
												</li>
												<li class="list-group-item">
													<span class="label label-success">5</span>
													<span class="atelierSpan"></span>
												</li>
												<li class="list-group-item">
													<span class="label label-info">6</span>
													<span class="atelierSpan"></span>
												</li>
												<li class="list-group-item">
													<span class="label label-primary">7</span>
													<span class="atelierSpan"></span>
												</li>
												<li class="list-group-item">
													<span class="label label-default">8</span>
													<span class="atelierSpan"></span>
												</li>
												<li class="list-group-item">
													<span class="label label-success">9</span>
													<span class="atelierSpan"></span>
												</li>
												<li class="list-group-item">
													<span class="label label-info">10</span>
													<span class="atelierSpan"></span>
												</li>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- row end -->
				<div class="row" id="secondRow" style="display: none;">
					<div class="col-lg-12">
						<div class="ibox ">
							<div class="ibox-content" style="width: 50%; float: left">
								<h3>토픽별 키워드</h3>
								<select id="topicSelect" style="width: 100%" onchange="showWords();"></select>
								<div id="keywords"></div>
							</div>
							<!-- content end -->
							<div class="ibox-content" id="simDiv" style="width: 50%; display: inline-block;">
								<h3 id="title_topics"></h3>
								<canvas id="simCanvas"></canvas>
							</div>
							<!-- content end -->
						</div>
					</div>
					<!-- 두번째 col end -->
				</div>
				<!-- 두번째 row end -->
			</div>
			<!-- wrapper end -->
			<%=Face.loadFooter(request)%>
		</div>
	</div>
	<%=Face.loadScript(request)%>
	<script type="text/javascript" src="../js/d3.min.js"></script>
	<script type="text/javascript" src="../js/d3.v3.min.js"></script>
	<script type="text/javascript" src="../js/d3.layout.cloud.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
</body>
</html>
