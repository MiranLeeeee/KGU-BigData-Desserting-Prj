
    //Validation
	function checkSurvey() {
		let validUser = -1;
		let id = $('#id').val();
		let name = $('#name').val();
		let count1 = $("input:checked[name=choiceCheck1]:checkbox").length;
		let count2 = $("input:checked[name=choiceCheck3]:checkbox").length;

		if(id=="") {
			alert("ID를 입력해주세요.");
			return false;
		}else if(name=="") {
			alert("이름을 입력해주세요.");
			return false;
		}else if(count1!=2) {
			alert("설문1에 대해 2가지를 선택해주세요.");
			return false;

		}else if(count2!=2) {
			alert("설문3에 대해 2가지를 선택해주세요.")
			return false;
		}else{
			alert("설문이 완료되었습니다.");
		}

		//기존에 있는 사용자인지 여부를 check
		validUser = checkUser(id);

		//기존 사용자면 topic을 update
		if(validUser == 1) {
			updateUser(id);
		//신규 사용자면 topic을 insert
		}else {
			insertUser(id, name);
		}
	}


	//토픽별 단어, 수치값 insert
	function putWords(){
		$.ajax({
			dataType : 'json',
			url : '/desserting/putWords',
			data : {},
			async : false,
			success : function() {
				console.log("insert 완료");
			}//success end
		});//ajax end
	}


	//아뜰리에 정보 insert
	function setAteliers() {
		$.ajax({
			dataType : 'json',
			url : '/desserting/setAteliers',
			data : {},
			async : false,
			success : function() {
				console.log("insert 완료");
			}//success end
		});//ajax end
	}


	//사용자 정보 insert
	function setUsers() {
		$.ajax({
			dataType : 'json',
			url : '/desserting/setUsers',
			data : {},
			async : false,
			success : function() {
				console.log("insert 완료");
			}//success end
		});//ajax end
	}


	//사용자별 토픽 유사도 insert
	function setUserSim() {
		$.ajax({
			dataType : 'json',
			url : '/desserting/setUserSim',
			data : {},
			async : false,
			success : function() {
				console.log("insert 완료");
			}//success end
		});//ajax end
	}


	//아뜰리에별 토픽 유사도 insert
	function setAtelierSim() {
		$.ajax({
			dataType : 'json',
			url : '/desserting/setAtelierSim',
			data : {},
			async : false,
			success : function() {
				console.log("insert 완료");
			}//success end
		});//ajax end
	}


	//사용자 기존에 있는지 없는지 확인
	function checkUser(id) {
		let validUser = -1;
		$.ajax({
			dataType : 'json',
			url : '/desserting/checkUser',
			data : {
				'id' :id
			},
			async : false,
			success : function(rows) {
				$.each(rows, function(idx, row) {
				    //validUser: 0/1(binary)
					validUser = row.validuser;
				});//each end
			}//success end
		});//ajax end
		return validUser;
	}


	//기존 사용자면 토픽 update
	function updateUser(id) {
		let topicNum = calTopic();
		$.ajax({
			dataType : 'json',
			url : '/desserting/updateUser',
			data : {
				'id' :id,
				'topicNum':topicNum
			},
			async : false,
			success : function() {
				console.log("update 완료");
			}//success end
		});//ajax end
		updateUserSim(id,topicNum);
	}


	//신규 사용자면 토픽 insert
	function insertUser(id, name) {
		let topicNum = calTopic();
		$.ajax({
			dataType : 'json',
			url : '/desserting/insertUser',
			data : {
				'id' :id,
				'name': name,
				'topicNum':topicNum
			},
			async : false,
			success : function() {
				console.log("insert 완료");
			}//success end
		});//ajax end
		insertUserSim(id, topicNum);
	}


	//임의의 사용자 유사도 테스팅
	function insertUserSim(id,topicNum) {
		let simList = [];
		if(topicNum==1){
			simList = [0.433370948,	0.060449563, 0.187429786, 0.138650075, 0.180048645];
		}else if(topicNum==2){
			simList = [0.272915363,	0.327942222, 0.155958146, 0.157814637, 0.085367866];
		}else if(topicNum==3){
			simList = [0.050004046,	0.355467439, 0.566190541, 0.00016116, 0.028174503];
		}else if(topicNum==4){
			simList = [0.178373694,	0.141244262, 0.243402928, 0.354350835, 0.082628466];
		}else {
			simList = [0.237056181,	0.180975392, 0.222340629, 0.000766283, 0.358906895];
		}

		$.ajax({
			dataType : 'json',
			url : '/desserting/insertUserSim',
			data : {
				'id' :id,
				't1': simList[0],
				't2': simList[1],
				't3': simList[2],
				't4': simList[3],
				't5': simList[4]
			},
			async : false,
			success : function() {
				console.log("insert 완료");
			}//success end
		});//ajax end
	}


	//기존회원 유사도 update
	function updateUserSim(id,topicNum) {
		let simList = [];
		if(topicNum==1){
			simList = [0.433370948,	0.060449563,	0.187429786,	0.138650075,	0.180048645];
		}else if(topicNum==2){
			simList = [0.272915363,	0.327942222,	0.155958146,	0.157814637,	0.085367866];
		}else if(topicNum==3){
			simList = [0.050004046,	0.355467439,	0.566190541,	0.00016116,	0.028174503];
		}else if(topicNum==4){
			simList = [0.178373694,	0.141244262,	0.243402928,	0.354350835,	0.082628466];
		}else {
			simList = [0.237056181,	0.180975392,	0.222340629,	0.000766283,	0.358906895]
		}

		$.ajax({
			dataType : 'json',
			url : '/desserting/updateUserSim',
			data : {
				'id' :id,
				't1': simList[0],
				't2': simList[1],
				't3': simList[2],
				't4': simList[3],
				't5': simList[4]
			},
			async : false,
			success : function() {
				console.log("update 완료");
			}//success end
		});//ajax end
	}


	//배열의 가장 큰 값을 갖는 인덱스 반환
	function indexOfMax(arr) {
	    if (arr.length === 0) {
	        return -1;
	    }

	    let max = arr[0];
	    let maxIndex = 0;

	    for (let i = 1; i < arr.length; i++) {
	        if (arr[i] > max) {
	            maxIndex = i;
	            max = arr[i];
	        }
	    }
	    return maxIndex;
	}


	//임의 토픽 계산
	function calTopic(){
		choiceCheck1 =  $("input:checked[name=choiceCheck1]:checkbox");
		choiceCheck2 = $(":input:radio[name=choiceCheck2]:checked").val();
		choiceCheck3 = $("input:checked[name=choiceCheck3]:checkbox");
		choiceCheck4 = $(":input:radio[name=choiceCheck4]:checked").val();
		let countArray = [0,0,0,0,0];

		for(let i=0; i<choiceCheck1.length;i++){
			if(choiceCheck1[i].value==1){
				countArray[0] +=1;
			}else if(choiceCheck1[i].value==2){
				countArray[1] +=1;
			}else if(choiceCheck1[i].value==3){
				countArray[2] +=1;
			}else if(choiceCheck1[i].value==4){
				countArray[3] +=1;
			}else{
				countArray[4] +=1;
			}
		}

		if(choiceCheck2==1){
			countArray[0]+=1;
		}else if(choiceCheck2==2){
			countArray[1]+=1;
		}else if(choiceCheck2==3){
			countArray[2]+=1;
		}else if(choiceCheck2==4){
			countArray[3]+=1;
		}else {
			countArray[4]+=1;
		}

		for(let i=0; i<choiceCheck3.length;i++){
			if(choiceCheck3[i].value==1){
				countArray[0] +=1;
			}else if(choiceCheck3[i].value==2){
				countArray[1] +=1;
			}else if(choiceCheck3[i].value==3){
				countArray[2] +=1;
			}else if(choiceCheck3[i].value==4){
				countArray[3] +=1;
			}else{
				countArray[4] +=1;
			}
		}

		if(choiceCheck4==1){
			countArray[0]-=1;
		}else if(choiceCheck4==2){
			countArray[1]-=1;
		}else if(choiceCheck4==3){
			countArray[2]-=1;
		}else if(choiceCheck4==4){
			countArray[3]-=1;
		}else {
			countArray[4]-=1;
		}

		let maxTopic = -1;
		maxTopic = indexOfMax(countArray)+1;
		let topic = maxTopic; //계산 불가
		return topic;
	}
