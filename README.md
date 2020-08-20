
# KGU-BigData-Desserting-Prj

경기대학교 빅데이터 청년인재 산학 프로젝트 - (주)디저팅 Desserting 

 <img src="https://img.shields.io/badge/python-v3.7-green" />
<img src="https://img.shields.io/badge/Conda-win--64-green" >

 ## 📆 기간 
### 2020-08-10 ~ 2020-09-04 

|**#**|**소속**|**역할**|**이름**|**E-mail**|
|--|--|--|--|--|
|```1```|```경기대학교```|```팀장```|```심교진```|rywls3597@naver.com
|```2```|```상명대학교```|```팀원```|```이미란```|miranleeeee@gmail.com
|```3```|```경기대학교```|```팀원```|```박선익```|parksimis@gmail.com
|```4```|```국민대학교```|```팀원```|```김세홍```|sehongkim@kookmin.ac.kr

## 🍩 Desserting - Suple

<p align="center">
 <a href="https://suple.me"><img src="https://image.rocketpunch.com/company/37903/desserting-1_logo_1517983583.png?s=400x400&t=inside" alt="desserting">
</p></a>

디저트 통합 플랫폼을 만들고 있는 회사로, 기존의 맛집 Application과는 달리 디저트 아뜰리에만의 특성을 살린 디저트 맛집을 추천한다.

* 디저트 아뜰리에의 특성을 맛집 추천 서비스
* 택배 중개 서비스

## 💡 주제 
### 디저트 아틀리에 추천 시스템 개발

디저트 통합 플랫폼을 제공하는 주식회사디저팅에서 개발한 "Suple"  Application에 들어갈 디저트 아틀리에 추천 시스템 알고리즘 제안


### 계획
[아뜰리에 별 특징]과 [사용자 선호별 특징]을 매칭하여 추천하고자 함.
이를 위해 슈플의 데이터로는 부족하다 판단하여, SNS의 사용자 상호작용 데이터를 수집

#### 사용자 상호작용 데이터 
- **Instagram Data**
인스타그램에서 음식을 리뷰하는 사람들의 Nickname, Review, Hashtag 수집

- **Naver Data**
전국의 디저트 가게의 영수증 리뷰(상호작용 데이터) 수집

### 역할
1.  **Team Naver (박선익, 이미란)**
	* Naver 영수증 리뷰 전처리
	* 전처리한 리뷰에 대해 각기 다른 라이브러리(```Okt```, ```Khaiii```)를 활용해 명사구 Tagging
	* 교차 명사를 사용해 TDM(Term Document Matrix) 생성
	* 이를 통해 Topic Modeling 실시 및 최적의 Topic 수 결정
	
2.  **Team Instagram (심교진, 김세홍)**
: 인스타그램 데이터를 통해 "식감사전" 및 "디저트 사전" 구축



