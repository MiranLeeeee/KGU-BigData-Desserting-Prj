# Instagram

<img src="https://user-images.githubusercontent.com/63633074/94366623-70e59580-0114-11eb-96e7-825ad6ea836f.png" data-canonical-src="https://img.shields.io/badge/python-v3.7-green" style="max-width:100%;">

## 📙 1주차

* [X] 인스타그램에서 디저트 전문 리뷰어들 탐색 
* [X] 위의 리뷰어들 ID 저장 및 크롤링
* [X] 아이디, 리뷰, 태그 크롤링
* [X] 추후 이모지 및 특수문자 전처리
<table>
<tr><th>#</th></th><th>File name</th><th>Description</th><th>Input</th><th>Output</th></tr>
<tr><td>1</td></td><td>Instagram Crawling.py</td><td>-탐색한 전문 리뷰어들의 아이디를 접속하면서 닉네임, 리뷰, 태그를 크롤링</td><td>X</td><td>raw_data.csv</td></tr>
<tr><td>2</td></td><td>Remove Emoji.py</td><td>크롤링한 리뷰에서 한글만 가져오고, Emoji를 삭제</td><td>raw_data.csv</td><td>preprocessed_data.csv</td></tr>
</table>


## 📘 2주차

* [X] 리뷰에서 디저트 리뷰만 뽑아내기 위한 동사 사전 생성
* [X] 디저트 메뉴만 뽑아서 메뉴 사전 구축
<table>
<tr><th>#</th></th><th>File name</th><th>Description</th><th>Input</th><th>Output</th></tr>
<tr><td>1</td></td><td>Verb_Dictionary.py</td><td>-먹는 것과 관련된 동사 사전 생성 Code</td><td>preprcoessed_data.csv</td><td>동사사전.csv</td></tr>
<tr><td>2</td></td><td>Menu_Dictionary.py</td><td>-디저트 관련 사전 구축 Code</td><td>preprcoessed_data.csv</td><td>메뉴사전.csv</td></tr>
<tr><td>3</td></td><td>Dessert_Dictionary.py</td><td>-위의 두개의 코드를 통해 Review들 중에서 디저트와 먹는 것과 관련된 Review만 선별하는 Code</td><td>1) 동사사전.csv<br>2) 메뉴사전.csv</td><td>Real_Review.csv</td></tr>
</table>