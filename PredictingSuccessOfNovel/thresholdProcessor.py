'''
Created on Apr 19, 2015

@author: RAJ
'''

import os
import glob
import sys

def thresholdGenerator(file,genre,basePath,threshold):
    file=open(file,"r")
    withDate=[]
    withoutDate=[]
    fileThreshold={}
    out=open("threshold.txt","w+")
    for line in file.readlines():
        features=line.split(",:;")
        try:
            date=int(features[3])
            if features[4]==" English":
                withDate.append(features)
        except ValueError:
            if features[3]==" English":
                withoutDate.append(features)    
    
    for feature in withoutDate:
        fileName=feature[0]
        ESI=float(feature[4])/100
        no_Of_Downloads=feature[5].lstrip()
        no_Of_Downloads=int(no_Of_Downloads.replace(",",""))
        user_ratings=float(feature[7])
        no_Of_Votes=float(feature[8])
        threshold=ESI*(0.7*no_Of_Downloads+0.3*user_ratings*no_Of_Votes)
        fileThreshold["file_"+fileName]=threshold
        
    for feature in withDate:
        fileName=feature[0]
        ESI=float(feature[5])/100
        no_Of_Downloads=feature[6].lstrip()
        no_Of_Downloads=int(no_Of_Downloads.replace(",",""))
        user_ratings=float(feature[8])
        no_Of_Votes=float(feature[9])
        threshold=ESI*(0.7*no_Of_Downloads+0.3*user_ratings*no_Of_Votes)
        fileThreshold["file_"+fileName]=threshold
    
        
    for k in fileThreshold.keys():
        out.write(k+","+str(fileThreshold[k])+"\n")    
            
   
    listOfFiles = os.listdir(path=basePath);
    
    for file_name in listOfFiles:
        file=(file_name.split(".")[0]) 
        if file in fileThreshold:
            if fileThreshold[file]>threshold:
                os.rename(basePath+file_name, basePath+genre+"-1-"+file_name)    
            elif fileThreshold[file]<=threshold:
                os.rename(basePath+file_name, basePath+genre+"-0-"+file_name)    
        

if __name__ == '__main__':
    file=sys.argv[1]
    genre=sys.argv[2]
    basePath=sys.argv[3]
    threshold=sys.argv[4]
    thresholdGenerator(file,genre,basePath,threshold)