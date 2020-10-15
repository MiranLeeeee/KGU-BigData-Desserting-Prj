# -*- coding: utf-8 -*-
"""Preprocessing_Data_for_Topic_Modeling v0.1.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1rSou-WI486OOG6AJvPBexFRTd0a1t7SV
"""

import openpyxl
from openpyxl import load_workbook
import pandas as pd

# Topic Modeling을 위해 크롤링한 네이버 플레이스 영수증 리뷰 불러오기

load_wb = load_workbook("Naver_Place_Review.xlsx", data_only=True)
load_ws = load_wb["아뜰리에_리뷰평점"]

all_values = []
for row in load_ws.rows:
    row_value = []
    for cell in row:
        row_value.append(cell.value)
    all_values.append(row_value)
print(all_values[:10])

# 조작이 편리하게 pandas DataFrame 형태로 변환

raw_data = pd.DataFrame(all_values[1:], columns=all_values[0])

# Null 값 확인
raw_data.isnull().sum()

# Null 값 제거
raw_data = raw_data.dropna(axis=0)
raw_data = raw_data.reset_index(drop=True)

raw_data.info()

# 특수문자 및 이모티콘을 지우는 함수

import re

def clean_text(texts):
    corpus = []
    for i in range(0, len(texts)):
        review = re.sub(r'[@%\\*=()/~#&\+á?\xc3\xa1\-\|\.\:\;\!\-\,\_\~\$\'\"\^]', '',str(texts[i])) #remove punctuation
        review = re.sub(r'\s+', ' ', review) #remove extra space
        review = re.sub(r'<[^>]+>','',review) #remove Html tags
        review = re.sub(r'\s+', ' ', review) #remove spaces
        review = re.sub(r"^\s+", '', review) #remove space from start
        review = re.sub(r'\s+$', '', review) #remove space from the end
        review = re.sub(r'[ㄱ-ㅎㅏ-ㅣ]+', '', review)
        corpus.append(review)
    return corpus

def remove_emoji(string):
    emoji_pattern = re.compile("["
                               u"\U0001F600-\U0001F64F"  # emoticons
                               u"\U0001F300-\U0001F5FF"  # symbols & pictographs
                               u"\U0001F680-\U0001F6FF"  # transport & map symbols
                               u"\U0001F1E0-\U0001F1FF"  # flags (iOS)
                               u"\U00002500-\U00002BEF"  # chinese char
                               u"\U00002702-\U000027B0"
                               u"\U00002702-\U000027B0"
                               u"\U0001f926-\U0001f937"
                               u"\U00010000-\U0010ffff"
                               u"\u2640-\u2642"
                               u"\u2600-\u2B55"
                               u"\u200d"
                               u"\u23cf"
                               u"\u23e9"
                               u"\u231a"
                               u"\ufe0f"  # dingbats
                               u"\u3030"
                               "]+", flags=re.UNICODE)
    
    return emoji_pattern.sub(r'', string)

basic_preprocessed_corpus = clean_text(raw_data['리뷰'])

for i in range(len(basic_preprocessed_corpus)):
  basic_preprocessed_corpus[i] = remove_emoji(str(basic_preprocessed_corpus[i]))

len(basic_preprocessed_corpus)

# 띄어쓰기 검사기 설치
!pip install git+https://github.com/haven-jeon/PyKoSpacing.git

# 맞춤법 검사기 설치
#!pip install git+https://github.com/ssut/py-hanspell.git

# 맞춤법 교정

"""
from hanspell import spell_checker
spelled_data = []
for i in range(len(basic_preprocessed_corpus)):
  try :
    spelled_sent = spell_checker.check(basic_preprocessed_corpus[i])
    checked_sent = spelled_sent.checked
    spelled_data.append(checked_sent)
  except:
    print("There is an error "+str(i)+ "index")
"""

# 띄어쓰기 교정

from pykospacing import spacing

spacing_preprocessed_corpus = []

for i in range(len(basic_preprocessed_corpus)):
  spacing_preprocessed_corpus.append(spacing(str(basic_preprocessed_corpus[i])))

# 확인
import random

index = random.randint(1, len(basic_preprocessed_corpus))
print("띄어쓰기 교정 전 \n====================\n{}\n".format(basic_preprocessed_corpus[index]))
print("띄어쓰기 교정 후 \n====================\n{}".format(spacing_preprocessed_corpus[index]))

len(spacing_preprocessed_corpus)

raw_data.columns =['Atelier', 'Review', 'rank']

final = raw_data[['Atelier', 'Review']]

final['Review'] = spacing_preprocessed_corpus

final.to_csv('final_preprocessed_data.csv', encoding='utf-8')

final_data = pd.DataFrame(spacing_preprocessed_corpus, columns=['Review'])
final_data.head()

# 데이터 csv 파일로 저장
final_data.to_csv("preprocessed_data.csv", encoding='utf-8')