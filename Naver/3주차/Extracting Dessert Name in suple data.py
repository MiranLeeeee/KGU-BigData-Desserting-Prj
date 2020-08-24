#!/usr/bin/env python
# coding: utf-8

# In[2]:


import json # import json module

# with statement
with open('desserting_appdata.json', encoding='utf-8') as json_file:
    json_data = json.load(json_file)


# In[17]:


json_data.keys()


# In[79]:


json_data['affiliates']


# In[80]:


json_data['affiliates'].keys()


# In[37]:


json_data['affiliates']['-LGy5R2ltAMuGK69Kdkh']['menus']['-LL2KOyLxmeTgbMY9xDi']['title']


# In[109]:


menu = []

from pprint import pprint
affiliates = list(json_data['affiliates'].keys())

for i in range(len(affiliates)):
    
    try :
        temp = affiliates[i]
        menus = list(json_data['affiliates'][temp]['menus'].keys())
        for j in range(len(menus)):
            tem = menus[j]
            menu.append(json_data['affiliates'][temp]['menus'][tem]['title'])
    except:
        print('index ',str(i),' error')
    


# In[110]:


menu


# In[83]:


len(affiliates)

