
# coding: utf-8

# In[26]:


from bs4 import BeautifulSoup as bs
import pandas as pd
import requests
import os
url = "https://www.kdd.org/kdd2018/accepted-papers"
headers = {
    "accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
    "accept-encoding": "gzip, deflate, br",
    "accept-language": "ja,en-US;q=0.9,en;q=0.8,zh-CN;q=0.7,zh;q=0.6,zh-TW;q=0.5",
    "cookie": "__atssc=google%3B1; _ga=GA1.2.2124998357.1560146229; _gid=GA1.2.882821486.1560146229; exp_last_visit=1560146211; exp_csrf_token=116a1c7ed0899f686890acf683810475f8dad85e; __atuvc=2%7C24; __atuvs=5cfe178afa99ffb1000; exp_last_activity=1560156210; exp_tracker=%7B%220%22%3A%22assets%2Fvendor%2Fbootstrap%2Fbootstrap.min.css.map%22%2C%221%22%3A%22accepted-papers%22%2C%22token%22%3A%22c52526f5873798718131ec2db1dc1969%22%7D",
    "upgrade-insecure-requests": "1",
    "user-agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36"}
r = requests.get(url,headers=headers)
soup = bs(r.text,"lxml")
for d in soup.find_all(name ='div',attrs={"class":"media-body"}):
    for span in d.find_all('span'):
        print(span.text)

