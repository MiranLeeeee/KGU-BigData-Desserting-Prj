import time
import re

'''
*Variables
storeId, storeName: 각 아뜰리에 고유 아이디(url key), 이름
reviewPage: 크롤링할 리뷰 페이지 수 
review(s): 각 아뜰리에 리뷰 데이터 -> reviewList: 리뷰 데이터 리스트
score(s): 각 아뜰리에 별점 데이터 -> scoreList: 별점 데이터 리스트
keyword(s): 각 아뜰리에 테마키워드 데이터 -> keywordList: 키워드 데이터 리스트 
description: 각 아뜰리에 요약정보
totalReviewPage: 각 아뜰리에 전체 리뷰 페이지 수

*Functions
getData: 각 아뜰리에 페이지별 리뷰데이터 크롤링
getKeywords: 각 아뜰리에 테마키워드 크롤링
'''

def getData(storeId, storeName, reviewPage):
    reviewList = []
    scoreList = []

    for i in range(0, reviewPage + 1):
        searchURL = "https://store.naver.com/restaurants/detail?entry=pll&id=" + storeId + "&query=" + storeName + "&tab=receiptReview&tabPage=" + str(i)
        driver.get(searchURL)

        reviews = driver.find_elements_by_class_name("review_txt")
        scores = driver.find_elements_by_class_name("score")

        for review in reviews:
            text = review.text
            #크롤링한 리뷰안에 이모지가 있을 경우 제거
            only_BMP_pattern = re.compile("["u"\U00010000-\U0010FFFF""]+", flags=re.UNICODE)
            text = only_BMP_pattern.sub(r'', text)
            reviewList.append(text)

        for score in scores:
            scoreList.append(float(score.text))  #실수값으로 저장

    return reviewList, scoreList


def getKeywords(storeId, storeName):
    keywordList = []
    description = ""

    searchURL = "https://store.naver.com/restaurants/detail?entry=pll&id=" + storeId + "&query=" + storeName + "&tab=main"
    driver.get(searchURL)
    keywords = driver.find_elements_by_class_name("kwd")
    description = driver.find_elements_by_class_name("ellipsis_area")[0]
    description = (description.find_elements_by_tag_name("span")[0]).text

    for keyword in keywords:
        keywordList.append(keyword.text)

    return description, keywordList