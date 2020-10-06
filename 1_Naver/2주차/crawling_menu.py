import time
import re

'''
*Variables
storeId, storeName: 각 아뜰리에 고유 아이디(url key), 이름
menu(s): 각 아뜰리에 메뉴 데이터 -> menuList: 메뉴 데이터 리스트
reviewPage: 크롤링할 리뷰 페이지 수 
title(s): 각 아뜰리에 리뷰 제목 데이터 -> titleList: 제목 데이터 리스트
review(s): 각 아뜰리에 리뷰 요약 데이터 -> reviewList: 리뷰 요약 데이터 리스트

*Functions
getMenu: 각 아뜰리에 메뉴 크롤링
getBlogReviews: 각 아뜰리에 블로그 리뷰(블로그 제목, 리뷰 요약) 크롤링  
'''


def getMenu(storeId, storeName):
    menuList = []
    searchURL = "https://store.naver.com/restaurants/detail?entry=plt&id=" + storeId + "&query=" + storeName + "&tab=menu"
    driver.get(searchURL)

    menus = driver.find_elements_by_class_name("tit")

    for menu in menus:
        text = menu.text
        if text != '':
            menuList.append(text)

    return menuList


def getBlogReviews(storeId, storeName, reviewPage):
    titleList = []
    reviewList = []

    for i in range(0, reviewPage + 1):
        searchURL = "https://store.naver.com/restaurants/detail?entry=pll&id=" + storeId + "&query=" + storeName + "&tab=fsasReview&tabPage=" + str(i)
        driver.get(searchURL)

        titles = driver.find_elements_by_tag_name(".tit>.name")
        reviews = driver.find_elements_by_class_name("ellp2")

        for title in titles:
            text = title.text
            titleList.append(text)

        for review in reviews:
            text = review.text
            only_BMP_pattern = re.compile("["u"\U00010000-\U0010FFFF""]+", flags=re.UNICODE)
            text = only_BMP_pattern.sub(r'', text)
            reviewList.append(text)

    return titleList, reviewList
