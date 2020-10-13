--토픽 정보 (토픽, 토픽이름) 테이블
CREATE TABLE subject
(
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
