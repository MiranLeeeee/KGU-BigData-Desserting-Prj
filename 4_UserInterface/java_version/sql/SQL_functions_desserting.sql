--추천받은 아뜰리에 select
CREATE OR REPLACE FUNCTION DESSERTING$findAteliers(id varchar) RETURNS refcursor AS
$$
DECLARE
    rtn_cursor refcursor := 'rcursor';
BEGIN
    OPEN rtn_cursor FOR
        SELECT ateliers.atelierId, ateliers.atelierName, ateliers.description FROM users, ateliers
		WHERE users.userId = id AND users.topicNum = ateliers.topicNum;
    return rtn_cursor;
END $$ LANGUAGE plpgsql;

--select box 모든 아뜰리에 insert
CREATE OR REPLACE FUNCTION DESSERTING$getAteliers() RETURNS refcursor AS
$$
DECLARE
    rtn_cursor refcursor := 'rcursor';
BEGIN
    OPEN rtn_cursor FOR
        SELECT ateliername FROM ateliers;
    return rtn_cursor;
END $$ LANGUAGE plpgsql;

--선택한 아뜰리에의 topic select
CREATE OR REPLACE FUNCTION DESSERTING$getTopicNum(name varchar) RETURNS refcursor AS
$$
DECLARE
    rtn_cursor refcursor := 'rcursor';
BEGIN
    OPEN rtn_cursor FOR
        SELECT topicNum FROM ateliers WHERE ateliername = name;
    return rtn_cursor;
END $$ LANGUAGE plpgsql;

--각 토픽의 아뜰리에 정보 select
CREATE OR REPLACE FUNCTION DESSERTING$getAtelierList(pNum int) RETURNS refcursor AS
$$
DECLARE
    rtn_cursor refcursor := 'rcursor';
BEGIN
    OPEN rtn_cursor FOR
        SELECT atelierid, ateliername, description FROM ateliers WHERE topicNum = pNum;
    return rtn_cursor;
END $$ LANGUAGE plpgsql;

--각 토픽별 단어랑 수치값 insert
CREATE OR REPLACE FUNCTION DESSERTING$putWords(pNum int, pword varchar, pvalNum float) RETURNS integer AS
$$
DECLARE
BEGIN
	INSERT INTO word(topicnum, word, valnum)
	VALUES(pNum, pword, pvalNum);
	return 1;
END $$ LANGUAGE plpgsql;

--각 토픽별 단어랑 수치값 select
CREATE OR REPLACE FUNCTION DESSERTING$getWords(pNum int) RETURNS refcursor AS
$$
DECLARE
    rtn_cursor refcursor := 'rcursor';
BEGIN
    OPEN rtn_cursor FOR
       	SELECT word, valnum FROM word WHERE topicNum = pNum;
    return rtn_cursor;
END $$ LANGUAGE plpgsql;

--네이버 기반 아뜰리에 정보 insert
CREATE OR REPLACE FUNCTION DESSERTING$setAteliers(aId varchar, aName varchar, pNum int, des varchar ) RETURNS integer AS
$$
DECLARE
BEGIN
	INSERT INTO ateliers(atelierid, ateliername, topicnum, description)
	VALUES(aId, aName, pNum, des);
	return 1;
END $$ LANGUAGE plpgsql;

--인스타그램 기반 사용자 정보 insert
CREATE OR REPLACE FUNCTION DESSERTING$setUsers(id varchar, name varchar, pNum int) RETURNS integer AS
$$
DECLARE
BEGIN
	INSERT INTO users(userId, userName, topicNum)
	VALUES(id, name, pNum);
	return 1;
END $$ LANGUAGE plpgsql;

--사용자의 토픽 select
CREATE OR REPLACE FUNCTION DESSERTING$getUserTopic(id varchar) RETURNS refcursor AS
$$
DECLARE
    rtn_cursor refcursor := 'rcursor';
BEGIN
    OPEN rtn_cursor FOR
       	SELECT topicNum FROM users WHERE userId = id;
    return rtn_cursor;
END $$ LANGUAGE plpgsql;

--모든 토픽 select
CREATE OR REPLACE FUNCTION DESSERTING$getTopics() RETURNS refcursor AS
$$
DECLARE
    rtn_cursor refcursor := 'rcursor';
BEGIN
    OPEN rtn_cursor FOR
       	SELECT distinct topicNum FROM word;
    return rtn_cursor;
END $$ LANGUAGE plpgsql;

--기존에 있는 user인지 확인을 위해 id select
CREATE OR REPLACE FUNCTION DESSERTING$checkUser(id varchar) RETURNS refcursor AS
$$
DECLARE
    rtn_cursor refcursor := 'rcursor';
BEGIN
    OPEN rtn_cursor FOR
       	SELECT count(*) as validuser FROM users WHERE userId = id;
    return rtn_cursor;
END $$ LANGUAGE plpgsql;

--검색한 아뜰리에 여부 check
CREATE OR REPLACE FUNCTION DESSERTING$checkAtelier(name varchar) RETURNS refcursor AS
$$
DECLARE
    rtn_cursor refcursor := 'rcursor';
BEGIN
    OPEN rtn_cursor FOR
       	SELECT count(*) as validAtelier FROM ateliers WHERE atelierName = name;
    return rtn_cursor;
END $$ LANGUAGE plpgsql;

--기존의 user의 topic을 update
CREATE OR REPLACE FUNCTION DESSERTING$updateUser(pNum int, id varchar) RETURNS integer AS
$$
DECLARE
BEGIN
	UPDATE users SET topicNum = pNum WHERE userId = id;
	return 1;
END $$ LANGUAGE plpgsql;

--신규 사용자 정보를 insert
CREATE OR REPLACE FUNCTION DESSERTING$insertUser(id varchar, name varchar, pNum int) RETURNS integer AS
$$
DECLARE
BEGIN
	INSERT INTO users(userid, username, topicnum)
	VALUES(id, name, pNum);
	return 1;
END $$ LANGUAGE plpgsql;

--user들의 유사도 수치 값을 insert
CREATE OR REPLACE FUNCTION DESSERTING$setUserSim(id varchar, s1 float, s2 float, s3 float, s4 float, s5 float) RETURNS integer AS
$$
DECLARE
BEGIN
	INSERT INTO users_sim(userId, t1, t2, t3, t4, t5)
	VALUES(id, s1, s2, s3, s4, s5);
	return 1;
END $$ LANGUAGE plpgsql;

--아뜰리에들의 유사도 수치 값을 insert
CREATE OR REPLACE FUNCTION DESSERTING$setAtelierSim(id varchar, s1 float, s2 float, s3 float, s4 float, s5 float) RETURNS integer AS
$$
DECLARE
BEGIN
	INSERT INTO ateliers_sim(atelierId, t1, t2, t3, t4, t5)
	VALUES(id, s1, s2, s3, s4, s5);
	return 1;
END $$ LANGUAGE plpgsql;

--사용자의 토픽별 유사도 select
CREATE OR REPLACE FUNCTION DESSERTING$getUserSim(id varchar) RETURNS refcursor AS
$$
DECLARE
    rtn_cursor refcursor := 'rcursor';
BEGIN
    OPEN rtn_cursor FOR
       	SELECT t1, t2, t3, t4, t5 FROM users_sim WHERE userId = id;
    return rtn_cursor;
END $$ LANGUAGE plpgsql;

--아뜰리에의 id 값을 select
CREATE OR REPLACE FUNCTION DESSERTING$getAtelierId(name varchar) RETURNS refcursor AS
$$
DECLARE
    rtn_cursor refcursor := 'rcursor';
BEGIN
    OPEN rtn_cursor FOR
       	SELECT atelierId FROM ateliers WHERE ateliername = name;
    return rtn_cursor;
END $$ LANGUAGE plpgsql;

--아뜰리에의 토픽별 유사도 select
CREATE OR REPLACE FUNCTION DESSERTING$getAtelierSim(id varchar) RETURNS refcursor AS
$$
DECLARE
    rtn_cursor refcursor := 'rcursor';
BEGIN
    OPEN rtn_cursor FOR
       	SELECT t1, t2, t3, t4, t5 FROM ateliers_sim WHERE atelierid = id;
    return rtn_cursor;
END $$ LANGUAGE plpgsql;

--모든 토픽 이름 select
CREATE OR REPLACE FUNCTION DESSERTING$getTopicNames() RETURNS refcursor AS
$$
DECLARE
    rtn_cursor refcursor := 'rcursor';
BEGIN
    OPEN rtn_cursor FOR
       	SELECT topicNum, subj FROM subject;
    return rtn_cursor;
END $$ LANGUAGE plpgsql;

--사용자 토픽 이름 select
CREATE OR REPLACE FUNCTION DESSERTING$findTopicName(pNum int) RETURNS refcursor AS
$$
DECLARE
    rtn_cursor refcursor := 'rcursor';
BEGIN
    OPEN rtn_cursor FOR
       	SELECT subj FROM subject WHERE topicNum = pNum;
    return rtn_cursor;
END $$ LANGUAGE plpgsql;

--기존 사용자에게 임의의 토픽 유사도 update
CREATE OR REPLACE FUNCTION DESSERTING$updateUserSim(id varchar, s1 float, s2 float, s3 float, s4 float, s5 float) RETURNS integer AS
$$
DECLARE
BEGIN
	UPDATE users_sim SET t1 = s1, t2 = s2, t3 = s3, t4 = s4, t5 = s5 WHERE userId = id;
	return 1;
END $$ LANGUAGE plpgsql;
