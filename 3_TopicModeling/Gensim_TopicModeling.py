# -*- coding: utf-8 -*-
"""Gensim_Topic_Modeling v0.3.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1v8j79PtCfudp4Palud9Qp6kMbsDx8tGj
"""

# Colab에 Khaiii 설치

import os
!git clone https://github.com/kakao/khaiii.git
!pip install cmake
!mkdir build
!cd build && cmake /content/khaiii
!cd /content/build/ && make all
!cd /content/build/ && make resource
!cd /content/build && make install
!cd /content/build && make package_python
!pip install /content/build/package_python

import shutil

filename = "preanal.manual"
src = '/content/'
dir = '/content/khaiii/rsc/src/'

shutil.move(src+filename, dir+filename)

# Khaiii 사용자 사전 추가
''' 
** Khaiii 사용자 사전 추가를 참고  **
preanal.manual 데이터를 download 받아서,
colab -> /content/khaiii/rsc/src 파일에 붙여넣기
'''

!cd /content/khaiii/rsc
!mkdir -p /content/build/share/khaiii
!PYTHONPATH=/content/khaiii/src/main/python /content/khaiii/rsc/bin/compile_preanal.py --rsc-src=/content/khaiii/rsc/src --rsc-dir=/content/build/share/khaiii

"""## Gensim Topic Modeling"""

import pandas as pd


data = pd.read_csv('/content/final_input.csv', encoding='utf-8').drop(['Unnamed: 0'], axis=1)

data.info()

"""# Gensim LDA를 위한 데이터 전처리
## Experiment 1) Khaiii에서 명사, 어근만 추출해 Tokenizing
"""

from khaiii import KhaiiiApi
api = KhaiiiApi(rsc_dir="/content/build/share/khaiii")

n_tags = ['NNG', 'NNP', 'NNB', 'XR']#, 'VV', "VA"] # 동사도 넣고 싶으면 추가

'''
input : 추출할 Review의 list ;
output : n_tags의 tag와 일치하는 text list ; 
'''

def extract_corpus_khaiii(texts):
    extract_corpus = []
    for line in texts:
      if str(line) != 'nan':
        nouns = []

        for word in api.analyze(str(line)):
          for morphs in word.morphs:
            if morphs.tag in n_tags:
              if len(morphs.lex) > 1:
                nouns.append(morphs.lex)
              else:
                continue

        extract_corpus.append(nouns)

    return extract_corpus

# 네이버 영수증 리뷰, 인스타 리뷰에서 명사, 어근을 추출
khaiii_xr = extract_corpus_khaiii(data['Review']) 
#khaiii_all = extract_corpus_khaiii(data['Review']) # 명사, 동사, 형용사, 어근 모두 추출

# 추출 전 데이터와 비교

print(len(data), len(khaiii_xr))
#print(len(data), len(khaiii_all))

import gensim
from gensim.utils import simple_preprocess
# Build the bigram and trigram models
bigram = gensim.models.Phrases(khaiii_xr, min_count=5, threshold=100) # higher threshold fewer phrases.
trigram = gensim.models.Phrases(bigram[khaiii_xr], threshold=100)

# Faster way to get a sentence clubbed as a trigram/bigram
bigram_mod = gensim.models.phrases.Phraser(bigram)
trigram_mod = gensim.models.phrases.Phraser(trigram)

stop_words = ['멍멍','오늘','내일','어제','신발','발','커피','카페']

def remove_stopwords(texts):
    return [[word for word in simple_preprocess(str(doc)) if word not in stop_words] for doc in texts]
    
def make_bigrams(texts):
    return [bigram_mod[doc] for doc in texts]

def make_trigrams(texts):
    return [trigram_mod[bigram_mod[doc]] for doc in texts]

# Stop words 제거 및 trigram 만들기

khaiii_xr = remove_stopwords(khaiii_xr)

khaiii_xr = make_trigrams(khaiii_xr)

# countvectorize를 위한 역토큰화 진행
'''
input : n_tags만 뽑힌 token list
 Ex. ['얼그레이', '마카롱', '맛']

output : 역토큰화된 detoken list
 Ex. ['얼그레이 마카롱 맛']
'''
def detokenize(token_list):
  detokenized_doc = []
  for i in range(len(token_list)):
    if token_list[i] != []:
      t = ' '.join(token_list[i])

      detokenized_doc.append(t)
  return detokenized_doc
    #detokenized_doc.append([data['Nickname'][i], t]) -> 옆에 닉네임 붙여서 내보낼거면 활성화

for i in range(len(khaiii_xr)):
  for j in range(len(khaiii_xr[i])):
    khaiii_xr[i][j] = khaiii_xr[i][j].replace("_", "")

# trigram으로 형성된 토큰 역토큰화

detoken_xr = detokenize(khaiii_xr)

# 모든 토큰을 한 줄로 만듦
import numpy as np

#detoken_data = [" ".join(detoken_xr)]

#detoken_data = np.array(detoken_data)

# 닉네임 붙여서 내보낼거면 활성화
#df = pd.DataFrame(detokenized_doc, columns=['Nickname', 'detoken Review'])
#df.to_csv('Final TDM.csv', encoding='utf-8')

"""## Using Gensim"""

# install gensim

!pip install gensim

# Gensim 사용을 위한 벡터화

from sklearn.feature_extraction.text import CountVectorizer

vectorizer = CountVectorizer()

cv = vectorizer.fit_transform(detoken_xr)

print("shape : ",cv.shape)

# 단어문서행렬을 gensim 형태로 변환

from gensim.matutils import Sparse2Corpus

corpus = Sparse2Corpus(cv.T)

# 단어 번호와 단어를 사전으로 정리

id2word = dict(enumerate(vectorizer.get_feature_names()))

print(di)

from gensim.models.ldamodel import LdaModel
from gensim.models.coherencemodel import CoherenceModel
from gensim.models.ldamulticore import LdaMulticore
from gensim import corpora


# Warning 무시
import warnings
warnings.filterwarnings(action='ignore', category=UserWarning, module='gensim')
warnings.filterwarnings(action='ignore', category=Warning, module='gensim')
warnings.filterwarnings('ignore')

"""* **num_topics**: 최종 분석의 주제 수
* **passes**: 총 훈련 과정의 수. 신경망 학습에서 에포크와 같다
* **iteration** : 각 문서에 대해 업데이트를 반복하는 횟수
* **random_state**: 재현 가능한 결과를 위해 임의의 숫자를 설정한다.
"""

model = LdaModel(corpus=corpus,
                 id2word=id2word,
                 num_topics=100, 
                 iterations=500,
                 passes=10)

# Coherence 계산을 위한 dictionary 만들기

from gensim.corpora.dictionary import Dictionary

dic = Dictionary()
dic.token2id = {t: i for i, t in enumerate(vectorizer.get_feature_names())}

coherence_model_lda = CoherenceModel(model=model, texts=khaiii_xr, dictionary=dic, coherence='c_v')

coherence = coherence_model_lda.get_coherence()

detoken_xr[:10]

"""### 최적의 number of words 찾기
* 지표 : Coherence
"""

def compete_number_of_words(detoken_data, token_data, min_num, max_num, step, random_state=None):

  '''
  number_of_words를 찾기 위한 함수 

  Parameters :
  -------------
  detoken_data : list 형태의 역토큰화된 데이터
  token_data : coherence 값을 계산하기 위한 token_data
  min_num : number of words range의 최솟값 min_num부터 시작
  max_num : number of words range의 최댓값 max_num까지 찾음
  step : min_num ~ max_num 까지 가기 위해 step을 얼마나 갈것인지
  random_state : 재현성을 주기 위해 설정, default = None

  Output :
  -------------
  coherence_value : Num of Words와 그에 따른 Coherence Value가 있는 DataFrame 반환
  
  '''

  coherence_value = pd.DataFrame(columns=['Num of Words', 'Coherence Value', 'Perplexity Value'])

  i = 0

  for feature in range(min_num, max_num, step):
    print("{} 번째, {}'th of words training".format(i+1, feature))

    vectorizer = CountVectorizer(max_features=feature) # CountVectorizer 생성
    cv = vectorizer.fit_transform(detoken_data) # fit and transform

    dictionary = corpora.Dictionary([vectorizer.get_feature_names()])

    corpus = Sparse2Corpus(cv.T)

    lda_model = LdaModel(corpus=corpus, id2word=dictionary, random_state=random_state)

    coherence_lda = CoherenceModel(model=lda_model, texts=token_data, dictionary=dictionary, coherence='c_v')

    coherence_value.loc[i] = [feature, coherence_lda.get_coherence(), lda_model.log_perplexity(corpus)]
    i += 1

  return coherence_value

compete_num_words = compete_number_of_words(detoken_xr, khaiii_xr, 1000, 32000, 1000, random_state=42)

# Commented out IPython magic to ensure Python compatibility.
# %matplotlib inline 
import matplotlib.pyplot as plt
import numpy as np

# 글씨 크기 조정
plt.rc('font', size=14)

plt.figure(figsize=(15, 10))
plt.xlabel('The Number of words')
plt.ylabel('Coherence Value')
plt.xticks(np.arange(1000, 32000, 1000), rotation=45)
plt.yticks(np.arange(0.31, 0.36, 0.01))
plt.ylim(0.31, 0.35)
plt.grid(True)
plt.title("Coherence Value for chainging number of words")
plt.plot(compete_num_words['Num of Words'], compete_num_words['Coherence Value'])
plt.scatter(compete_num_words['Num of Words'], compete_num_words['Coherence Value'])
plt.show()

# Log Perplexity 값 확인

plt.figure(figsize=(15, 10))
plt.xlabel('The Number of words')
plt.ylabel('Coherence Value')
plt.xticks(np.arange(1000, 32000, 1000), rotation=45)
plt.grid(True)
plt.title("abs(Peplexity Value) for chainging number of words")
plt.plot(compete_num_words['Num of Words'], abs(compete_num_words['Perplexity Value']), 'r')
plt.scatter(compete_num_words['Num of Words'], abs(compete_num_words['Perplexity Value']), c='r')
plt.show()

# 계산된 c_v와 p_v 값 같이 보여주기

fig, ax1 = plt.subplots(figsize=(10, 10))

x = np.arange(1000, 32000, 1000)
y1 = compete_num_words['Coherence Value']
y2 = abs(compete_num_words['Perplexity Value'])

ax1.set_xlabel('Number Of Words')
ax1.set_ylabel('Coherence Value', color='blue')
ax1.set_ylim(0.31, 0.35)
ax1.plot(x, y1, c='b')
ax1.scatter(x, y1, c='b')
ax1.tick_params(axis='y', labelcolor='b')

ax2 = ax1.twinx()  
ax2.set_ylabel('Perplexity Value', color='red')  
ax2.plot(x, y2, c='r')
ax2.scatter(x, y2, c='r')
ax2.tick_params(axis='y', labelcolor='r')
fig.legend(['Coherence Value','c_v', 'abs(Perplexity Value', 'p_v'])
fig.tight_layout()

plt.grid()
plt.show()

"""# 최적의 LDA Model Parameter 찾기
찾을 하이퍼 파라미터 : n_topics, alpha, beta
지표 : Coherence Value
"""

# 이전의 결과로 나타난 max_featues : 25000을 사용

best_count = CountVectorizer(max_features=25000)

count_vec =  best_count.fit_transform(detoken_xr)

corpus = Sparse2Corpus(count_vec.T)

dic = corpora.Dictionary([best_count.get_feature_names()])

def compete_coherence_values(corpus, token_data, id2word, k, a, b):

  lda_model = gensim.models.LdaMulticore(corpus=corpus,
                                         id2word=id2word,
                                         num_topics=k,
                                         random_state=42, 
                                         chunksize=256, 
                                         passes=10,
                                         alpha=a,
                                         eta=b,
                                         per_word_topics=True,
                                         iterations=500)
  
  coherence_model = CoherenceModel(model=lda_model, texts=token_data, dictionary=id2word, coherence='c_v')

  return coherence_model.get_coherence()

# Topic Range
min_topics = 5
max_topics = 105
step_size = 5
topic_range = range(min_topics, max_topics, step_size)

# Alpha parameter
alpha = list(np.arange(0.01, 1, 0.3))
alpha.append('symmetric')
alpha.append('asymmetric')

# Beta parameter
beta = list(np.arange(0.01, 1, 0.3))
beta.append('symmetric')

model_result = pd.DataFrame(columns=['n_topic', 'alpha', 'beta', 'coherence'])
i = 0
for n_topic in topic_range:
  for a in alpha:
    for b in beta:
      print("i: {}, n_topic : {}, a : {}, b : {}".format(i+1, n_topic, a, b))
      
      cv = compete_coherence_values(corpus=corpus, token_data=khaiii_xr, id2word=dic, k=n_topic, a=a, b=b)

      model_result.loc[i] = [n_topic, a, b, cv]
      print("{}번째의 coherence의 score {}".format(i+1, cv))
      print("=====================================================================")
      i += 1

# 최적의 coherence value 찾기

n_topic = np.arange(5, 505, 5)

co_value = pd.DataFrame(columns=['Num Topic', 'c_v'])
i = 0
for topic in n_topic:
  print("{}번째, {} 개의 topic".format(i, topic))
  LDA = LdaModel(corpus=corpus, id2word=id2word, num_topics=topic)

  c_v_LDA = CoherenceModel(model=LDA, texts=khaiii_xr, dictionary=dictionary, coherence='c_v')
  co_value.loc[i] = [topic, c_v_LDA.get_coherence()]
  i+=1

plt.figure(figsize=(10, 10))
plt.plot(co_value['Num Topic'], co_value['c_v'])
plt.scatter(co_value['Num Topic'], co_value['c_v'])
plt.ylim(0.30, 0.35)
#plt.xticks(np.arange(5, 505, 5), rotation=90)
plt.grid()
plt.tight_layout()
plt.show()