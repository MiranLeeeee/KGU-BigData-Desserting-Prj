
		$(document).ready(function() {
		    //아뜰리에 이름 셀렉트박스에 insert
			getTopics();

		});


		//검색한 아뜰리에 여부 check
		function checkAtelier(atelierName) {
			let validAtelier = -1;

			$.ajax({
				dataType : 'json',
				url : '/desserting/checkAtelier',
				data : {
					'name' :atelierName
				},
				async : false,
				success : function(rows) {
					$.each(rows, function(idx, row) {
					    //validUser: 0/1(binary)
						validAtelier = row.validatelier;
					});//each end
				}//success end
			});//ajax end
			return validAtelier;
		}


		//셀렉트 박스 변화에 따른 display속성 변경
		function showInfo() {
			let valid = -1;
			wordArray = new Array();
			valNumArray = new Array();
			atelierSearched = $('#atelierInput').val();
			valid = checkAtelier(atelierSearched);

			if(valid==0){
				alert("검색결과가 없습니다.");
				$('#showDiv').css('display', 'none');
				$('#defaultText').css('display', 'block');
				$('#secondRow').css('display', 'none');
				return false;
			}
			else {
				if(atelierSearched != ""){
					$('#showDiv').css('display', 'block');
					$('#defaultText').css('display', 'none');
					$('#secondRow').css('display', 'block');

				}
				else {
					$('#showDiv').css('display', 'none');
					$('#defaultText').css('display', 'block');
					$('#secondRow').css('display', 'none');
				}

				//변경 완료 후 아뜰리에 토픽별 단어 그래프 시각화
				topicNum = getTopicNum();
				wordArray,valNumArray = getWords(topicNum);
				showGraph(wordArray, valNumArray);
				showList(topicNum);
				showSimGraph(atelierSearched);
			}
		}


        //토픽별 단어, 수치값 select
		function getWords(topicNum){
			wordArray = new Array();
			valNumArray = new Array();

			$.ajax({
				dataType : 'json',
				url : '/desserting/getWords',
				data : {
					'topicNum': topicNum
				},
				async : false,
				success : function(rows) {
				    $.each(rows, function(idx, row) {
					    wordArray.push(row.word);
						valNumArray.push(row.valnum);
					});//each end
				}//success end
			});//ajax end
			return wordArray, valNumArray;
		}


		//각 아뜰리에의 키워드 radar 그래프 시각화
		function showGraph(){
			//그래프 초기화
			let graphDiv = $('#graphDiv').empty();
			graphDiv.append('<canvas id="graphCanvas" height="450px">');
			let ctx = $('#graphCanvas');
			let data = {
				labels: wordArray,
				datasets :[{
					label: "아뜰리에 키워드 분포",
					backgroundColor: "rgba(255,99,132,0.2)",
					borderColor: "rgba(255,99,132,1)",
					pointBackgroundColor: "rgba(255,99,132,1)",
					pointHoverBackgroungColor : "rgba(255,99,132,1)",
					data: valNumArray
				}]
			};
			let chart = new Chart(ctx, {
				type : 'radar',
				data : data,
				options: {
					legend: {
			            labels: {
			            	display: true,
			                fontSize: 15,
			                fontStyle: "bold"
			            }
					},
					scale: {
						pointLabels: {
							fontSize :14
						}
					},
					display: false
				}
			});
		}


        //아뜰리에 토픽 select
		function getTopicNum() {
			atelierSearched = $('#atelierInput').val();
			let topicNum;

			$.ajax({
				dataType : 'json',
				url : '/desserting/getTopicNum',
				data : {
					'name': atelierSearched
				},
				async : false,
				success : function(rows) {
				    $.each(rows, function(idx, row) {
					    topicNum = row.topicnum
				    });//each end
				}//success end
			});//ajax end
			return topicNum;
		}


        //각 아뜰리에와 토픽이 동일한 경쟁 아뜰리에 리스트 시각화(10개 테스팅)
		function showList(topicNum) {
			atelierSearched = $('#atelierInput').val();
			let i = 0;
			atelierSpans = $('.atelierSpan');

			$.ajax({
				dataType : 'json',
				url : '/desserting/getAtelierList',
				data : {
					'topicNum': topicNum
				},
				async : false,
				success : function(rows) {
					$.each(rows, function(idx, row) {
						if(i<10){
							if(row.ateliername != atelierSearched){
								atelierSpans[i].innerHTML ="";
								atelierSpans[i].innerHTML = "<b>"+row.ateliername+"</b> | ";
								atelierSpans[i].innerHTML += "<div style='display:inline; overflow: scroll;'>"+row.description+"</div>";
								atelierSpans[i].innerHTML += "&nbsp;<button class='btn btn-primary btn-sm' onclick=\"window.open('https://store.naver.com/restaurants/detail?entry=pll&id="+row.atelierid+"&query="+row.ateliername+"')\">더보기</button>";
								i+=1;
							}
						}
					});//each end
				}//success end
			});//ajax end
		}


		//셀렉트 박스에 모든 토픽 넣기
		function getTopics(){
            topicSelect = $('#topicSelect');
            $.ajax({
                dataType : 'json',
                url : '/desserting/getTopicNames',
                data : {},
                async : false,
                success : function(rows) {
                    topicSelect.empty();
                    topicSelect.append("<option>토픽을 선택해주세요.</option>");
                    $.each(rows, function(idx, row) {
                        topicSelect.append("<option value="+row.topicnum+">"+row.subj+"</option>");
                    });//each end
                }//success end
            });//ajax end
	    }


		//토픽선택 시 키워드 시각화
		function showWords(){
            topicSelected = $('#topicSelect option:selected').text();
            keyDiv = $('#keywords');

            if(topicSelected != "토픽을 선택해주세요."){
                topicSelectedVal = 	$('#topicSelect option:selected').val();
                wordArray = new Array();

                $.ajax({
                    dataType : 'json',
                    url : '/desserting/getWords',
                    data : {
                        'topicNum': topicSelectedVal
                    },
                    async : false,
                    success : function(rows) {
                        $.each(rows, function(idx, row) {
                            wordArray.push(row.word);
                        });//each end
                    }//success end
                });//ajax end

                //단어 버튼 시각화
                keyDiv.empty();
                for (var i =0; i<10; i++){
                    if((i)%2==0){
                        keyDiv.append("<br><br>");
                    }
                    keyDiv.append("<button style='width:170px' class='btn btn-primary btn-sm btn_tag' disabled>#"+wordArray[i]+"</button >&nbsp;&nbsp;");
                }
                $('#keywords').css('display', 'block');

            }else {
                $('#keywords').css('display', 'none');
            }
	    }


		//유사도 그래프 시각화
		function showSimGraph(ateliername) {
			var atelierSearched = $('#atelierInput').val();
			var simList = new Array();
			var simDiv = $('#simDiv').empty();
			simDiv.append('<canvas id="simCanvas" height="500px" ">');
			var ctx = $('#simCanvas');
			topicList = ['디저트에 진심인 파','빵지순례파','무거운 디저트파', '갬성뿜뿜파', '달다구리파'];
			var atelierId = getAtelierId(atelierSearched);
			simList = getAtelierSim(atelierId);
			colorList = ["#FF6384","#4BC0C0","#FFCE56","#36A2EB","#D0A9F5"];

			new Chart(ctx,
				{"type":"bubble",
				 "data":
				    {"datasets":
                        [{"label":topicList[0],
                          "data":[{"x":10,"y":30,"r":(simList[0]*100)}],
                          "backgroundColor":colorList[0]},
                         {"label":topicList[1],
                          "data":[{"x":20,"y":10,"r":(simList[1]*100)}],
                          "backgroundColor":colorList[1]},
                         {"label":topicList[2],
                          "data":[{"x":60,"y":20,"r":(simList[2]*100)}],
                          "backgroundColor":colorList[2]},
                         {"label":topicList[3],
                          "data":[{"x":50,"y":90,"r":(simList[3]*100)}],
                          "backgroundColor":colorList[3]},
                         {"label":topicList[4],
                          "data":[{"x":30,"y":80,"r":(simList[4]*100)}],
                          "backgroundColor":colorList[4]}]
                    },
				 "options":
					{"scales": {
				        "xAxes": [{
				            "gridLines": {
				                "display":false
				            },"ticks": {
				            	"min" : 0,
				            	"max" : 100
				            }
				        }],
				        "yAxes": [{
				            "gridLines": {
				                "display":false
				            },"ticks": {
				            	"min" : 0,
				            	"max" : 100
				            }
				        }]
				      }
					}
			     }
			);//chart end
		}


        //아뜰리에 고유 아이디 select
		function getAtelierId(name) {
			var atelierId ="";

			$.ajax({
				dataType : 'json',
				url : '/desserting/getAtelierId',
				data : {
					'name': name
				},
				async : false,
				success : function(rows) {
					$.each(rows, function(idx, row) {
						atelierId = row.atelierid;
					});//each end
				}//success end
			});//ajax end
			return atelierId;
		}


        //아뜰리에 유사도 select
		function getAtelierSim(atelierId) {
			var simList = new Array();

			$.ajax({
				dataType : 'json',
				url : '/desserting/getAtelierSim',
				data : {
					'id': atelierId
				},
				async : false,
				success : function(rows) {
					$.each(rows, function(idx, row) {
						simList.push(row.t1);
						simList.push(row.t2);
						simList.push(row.t3);
						simList.push(row.t4);
						simList.push(row.t5);
					});///each end
				}//success end
			});//ajax end
			return simList;
		}

