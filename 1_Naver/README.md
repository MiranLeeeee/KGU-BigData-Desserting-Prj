<p></p>
<p><a target="_blank" href="https://www.naver.com"><img src="https://t1.daumcdn.net/cfile/tistory/99117C3F5D04CEE519" width="80" align="right" data-canonical-src="https://t1.daumcdn.net/cfile/tistory/99117C3F5D04CEE519" style="max-width:10%;"></a></p>
<h1 align="center">NAVER</h1>
<img src="https://camo.githubusercontent.com/c8e731861319e0de793d621c8cf3fdf98f7e883c/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f707974686f6e2d76332e372d677265656e" data-canonical-src="https://img.shields.io/badge/python-v3.7-green" style="max-width:100%;">

<p><a target="_blank" rel="noopener noreferrer" href="https://camo.githubusercontent.com/3540a7c082ffac150fec6f70110f84382a43c6ed/68747470733a2f2f6966682e63632f672f30723073486c2e706e67"><img src="https://camo.githubusercontent.com/3540a7c082ffac150fec6f70110f84382a43c6ed/68747470733a2f2f6966682e63632f672f30723073486c2e706e67" border="0" data-canonical-src="https://ifh.cc/g/0r0sHl.png" style="width:506px; height:273px;"></a></p>
<h2>📙 1주차</h2>

- [X] 지역별 아뜰리에 개수 확인 및 조정
- [X] 지역별 아뜰리에명, 아뜰리에 고유 아이디(URL key) 크롤링
- [X] 프랜차이즈 아뜰리에 제거
- [X] 아뜰리에별 네이버 영수증 리뷰 크롤링 ⭐

<table>
<tr><th>#</th><th>File name</th><th>Description</th><th>Input</th><th>Output</th></tr>
<tr><td>1</td><td>get_atelier_name.py</td><td>-아뜰리에 개수를 확인 및 조정하고 지역명, 아뜰리에명, 아뜰리에 고유 아이디 크롤링</td><td>X</td><td>atelier_info.xlsx</td></tr>
<tr><td>2</td><td>remove_franchise.py</td><td>-프렌차이즈형 카페를 제거하기 위해 아뜰리에 정보에서 빈도수가 3번 이하인 아뜰리에만 가지고옴</td><td>atelier_info.xlsx</td><td>remove_franchise_atelier_info.xlsx</td></tr>
<tr><td>3</td><td>crawling_reviews.py</td><td>-아뜰리에별 네이버 영수증 리뷰 크롤링 (추가적으로 요약설명, 테마키워드, 리뷰, 별점 정보 크롤링)</td><td>remove_franchise_atelier_info.xlsx</td><td>atelier_reviews.xlsx</td></tr>
</table>
<br>
<h2>📘 2주차 </h2>

- [X] 네이버 영수증 리뷰가 30개 이상인 아뜰리에명, 아뜰리에 고유 아이디 저장 
- [X] 네이버 아뜰리에별 메뉴 크롤링 

<table>
<tr><th>#</th><th>File name</th><th>Description</th><th>Input</th><th>Output</th></tr>
<tr><td>1</td><td>crawling_menu.py</td><td>-네이버 아뜰리에별 메뉴 크롤링 (추가적으로 아뜰리에별 블로그 리뷰 요약정보 크롤링) </td><td>atelier_info_reviews.xlsx</td><td>atelier_menu.xlsx</td></tr>
</table>
<br>
