# Topic Modeling
 

![TopicModeling](https://user-images.githubusercontent.com/51108153/95821072-3ad72100-0d64-11eb-8c81-b433a837ed2f.JPG)

## 💡 구현 내용

* [X] Khaiii를 사용해 명사(NNG), 어근(XR)만 추출
* [X] gensim을 사용해 Topic Modeling 실시
* [X] Khaiii 사용자 사전 추가
* [X] Coherence와 Perplexity를 비교하여 최적의 # of Words와 Topic 개수 도출


<br>

## 📄 Files

<table>
<tr><th>#</th><th>File name</th><th>Description</th><th>Input</th><th>Output</th></tr>
<tr><td>1</td><td>Preprocessing_Data_for_Topic_Modeling.py</td><td>Topic Modeling을 위한 데이터 준비 소스 코드</td><td>Real_Review.csv</td><td>final_data.csv</td></tr>
<tr><td>2</td><td>Using_Mecab.py</td><td>Mecab 라이브러리 활용한 Topic Modeling</td><td>final_data.csv</td><td>X</td></tr>
<tr><td>3</td><td>Khaiii_Dictionary.py</td><td>Khaiii 분석기에 사용자 사전 추가</td><td>X</td><td>X</td></tr>
<tr><td>4</td><td>Khaiii_Gensim.py</td><td>Khaiii와 Gensim Topic Modeling</td><td>X</td><td>X</td></tr>
<tr><td>5</td><td>Gensim_TopicModeling.py</td><td>Gensim만을 사용한 Topic Modeling</td><td>X</td><td>X</td></tr>
<tr><td>6</td><td>Gensim_Tuning.py</td><td>Parameter Tuning</td><td>X</td><td>X</td></tr>
<tr><td>7</td><td>preanal.manual</td><td>Khaiii 사용자 사전</td><td>X</td><td>X</td></tr>
</table>
