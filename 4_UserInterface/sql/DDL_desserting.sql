--토픽 정보 (토픽, 토픽이름) 테이블
CREATE TABLE subject(
    topicnum integer,
    subj character varying(30),
    CONSTRAINT subject_pk PRIMARY KEY (topicnum)
);

--토픽 키워드 정보(토픽, 키워드, 키워드차지비율) 테이블
CREATE TABLE word(
    topicnum integer,
    word character varying(30),
    valnum double precision,
    CONSTRAINT word_pk PRIMARY KEY (topicnum, word)
);

--사용자 정보(아이디, 이름, 토픽) 테이블
CREATE TABLE users(
    userid character varying(25),
    username character varying(25),
    topicnum integer,
    CONSTRAINT users_pk PRIMARY KEY (userid, username)
);

--사용자별 토픽별 유사도 정보 테이블
CREATE TABLE users_sim(
    userid character varying(25),
    t1 double precision,
    t2 double precision,
    t3 double precision,
    t4 double precision,
    t5 double precision,
    CONSTRAINT users_sim_pk PRIMARY KEY (userid)
);

--아뜰리에 정보(아이디, 이름, 토픽, 설명) 테이블
CREATE TABLE ateliers(
    atelierid character varying(25),
    ateliername character varying(80),
    topicnum integer,
    description text,
    CONSTRAINT ateliers_pk PRIMARY KEY (atelierid, ateliername)
);

--아뜰리에별 토픽별 유사도 정보 테이블
CREATE TABLE ateliers_sim(
    atelierid character varying(50),
    t1 double precision,
    t2 double precision,
    t3 double precision,
    t4 double precision,
    t5 double precision,
    CONSTRAINT ateliers_sim_pk PRIMARY KEY (atelierid)
);

