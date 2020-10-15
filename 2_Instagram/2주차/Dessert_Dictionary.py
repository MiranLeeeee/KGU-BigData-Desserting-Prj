# -*- coding: utf-8 -*-
import pandas as pd
import re 
import math
from unicodedata import normalize
import re
import openpyxl
from collections import Counter
# 한글 자모분리 모듈
from jamo import h2j, j2hcj

# 아뜰리에 메뉴 사전 dictionary형태로 변환
menu_df = pd.read_csv('아뜰리에메뉴사전_real.csv', encoding='utf-8')
menu_df = menu_df.fillna("")
menu_df.set_index('Unnamed: 0')

menu_df = menu_df.set_index('Unnamed: 0')
menu_df = menu_df.transpose()

menu_dic = menu_df.to_dict('list')
#''값 제거하기
for i in menu_dic:                              
  menu_dic[i] = list(set(menu_dic[i]))
  if '' in menu_dic[i]:
    menu_dic[i].remove('')
    
# 동사사전csv 가져와서 dictionary형태로 구성
verb_df = pd.read_csv('동사사전.csv', encoding='utf-8')
verb_df = verb_df.fillna("")
verb_df.set_index('Unnamed: 0')

verb_df = verb_df.set_index('Unnamed: 0')
verb_df = verb_df.transpose()

verb_dic = verb_df.to_dict('list')
#''값 제거하기
for i in verb_dic:                              
  verb_dic[i] = list(set(verb_dic[i]))
  if '' in verb_dic[i]:
    verb_dic[i].remove('')


def con_menu(post):
  #post내에 있는 nng들을 모두 가지는 nng_list생성
  nng_list=[]
  ####nn = ['NNG','NNP','NNB','NP']####
  for word in api.analyze(post):
    for morph in word.morphs:
      if morph.tag == 'NNG':
        nng_list.append(morph.lex)
  
  #nng_list내에 디저트 메뉴사전에 있는 단어가 3개 이상이면 nng_count=1, 단어가 하나도 없다면 0으로 break
  count = 0
  nng_count = 0
  while True:
    if nng_count >= 3:
      return 1
      break
    elif count >= len(nng_list):
      return 0
      break

    nng_name = nng_list[count]
    nng_first = j2hcj(h2j(nng_name))[0]
    if nng_name in menu_dic[nng_first]:
      nng_count +=1
    count+=1

def con_verb(post):
  #post내에 있는 동사들을 모두 가지는 verb_list생성
  verb_list=[]
  need_mm = ['VV','VA','VX']
  for word in api.analyze(post):
    for morph in word.morphs:
      if morph.tag in need_mm:
        verb_list.append(morph.lex)
  
  #nng_list내에 디저트 메뉴사전에 있는 단어가 2개 이상이면 nng_count=1, 단어가 하나도 없다면 0으로 break
  count = 0
  verb_count = 0
  while True:
    if verb_count >= 2:
      return 1
      break
    elif count >= len(verb_list):
      return 0
      break

    verb = verb_list[count]
    verb_first = j2hcj(h2j(verb))[0]
    if verb in verb_dic[verb_first]:
      verb_count +=1
    count+=1
    
def confirm(post):
  result = con_verb(post)*con_menu(post)
  if result == 1:
    return 1
  else:
    return 0

kr='ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎㄲㄸㅉㅃㅆ'
en='abcdefghijknmlopqrstuvwxyz'
kr_list = [i for i in kr]
en_list = [i for i in en]
all_list = kr_list+en_list

is_kr = re.compile('[^가-힣]')
is_en = re.compile('[-a-zA-Z]')
is_num = re.compile('[0-9]')

verb_dic = {}
for i in all_list:
  verb_dic[i]=[]

for ii in v_list:
  try:
    first_ii = j2hcj(h2j(ii))[0]
    verb_dic[first_ii].append(ii)
  except:
    continue