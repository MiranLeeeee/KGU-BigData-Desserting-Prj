from selenium import webdriver
from bs4 import BeautifulSoup
import time
import re
import pandas as pd

# 검색으로 선정한 리뷰어 리스트

REVIEWER_LIST = pd.read_csv('URL_list.csv', encoding='utf-8')

len(REVIEWER_LIST)

# 인스타그램 사용자의 게시물의 URL을 가져오는 코드
URL = []

for i in range(106, len(REVIEWER_LIST['ID'])):

    url = "https://www.instagram.com/" + REVIEWER_LIST['ID'][i]
    browser = webdriver.Chrome()
    browser.get(url)
    page = browser.page_source
    soup = BeautifulSoup(page, 'html.parser')

    while True:

        page = browser.page_source
        soup = BeautifulSoup(page, "html.parser")

        links = soup.find_all('div', {'class': 'v1Nh3 kIKUG _bz0w'})

        SCROLL_PAUSE_TIME = 5

        for link in links:
            new_url = 'https://www.instagram.com/' + link.a['href']
            URL.append(new_url)

        last_height = browser.execute_script("return document.body.scrollHeight")
        browser.execute_script("window.scrollTo(0, document.body.scrollHeight);")
        time.sleep(SCROLL_PAUSE_TIME)
        new_height = browser.execute_script("return document.body.scrollHeight")

        if new_height == last_height:
            browser.execute_script("window.scrollTo(0, document.body.scrollHeight);")
            time.sleep(SCROLL_PAUSE_TIME)

            if new_height == last_height:
                break

            else:
                last_height == last_height

                continue

    browser.quit()

URL = list(set(URL))
len(URL)

browser = webdriver.Chrome()

result = []

for i in range(len(URL)):
    try:
        tags = []
        review = []

        browser.get(URL[i])
        # time.sleep(2)

        nickname = browser.find_element_by_css_selector("._6lAjh").text
        text = browser.find_element_by_css_selector(".C7I1f.X7jCj .C4VMK span:nth-child(2)").text

        tag = re.findall('#\w+', text)
        tag = "".join(tag)

        for word in text.split():
            if "#" in word:
                continue
            else:
                review.append(" ")
                review.append(word)

        review = "".join(review)

        result.append([nickname, review, tag])

    except:
        print("There is an error with " + str(i) + " index")

browser.quit()

import pandas as pd

final = pd.DataFrame(result, columns=['Nickname', 'Review', 'Hashtag'])

display(final)

final.to_csv("Instagram Crawling.csv", encoding='utf-8')