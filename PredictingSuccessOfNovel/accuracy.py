import os
basePath="C:/Users/RAJ/Desktop/Gutenberg/BooksScrapper/BooksScrapper/Processed_Adventure/Test/"
listOfFiles = os.listdir(path=basePath);

truth=open("truth","w+")
for file in listOfFiles:
    f=open(basePath+file,"r")
    for line in f:
        truth.write(file.split("-")[1]+"\n")
total=0
counter=0
classified0=0
correctlyclassified0=0
belongsin0=0
classified1=0
correctlyclassified1=0
belongsin1=0
truth.close()
with open("output","r") as a, open("truth","r") as b:
    for(x,y)in zip(a,b):
        x=int(x.strip())
        y=int(y.strip())
        
        total+=1
        if x==y:
            counter+=1
        if x==0:
            classified0+=1
            if y==0:
                correctlyclassified0+=1
        if x==1:
            classified1+=1
            if y==1:
                correctlyclassified1+=1
        if y==0:
            belongsin0+=1                
        if y==1:
            belongsin1+=1
            
            
    precision=correctlyclassified0/classified0
    recall=correctlyclassified0/belongsin0
    fScore=(2*precision*recall)/(precision+recall)
    print(precision)
    print(recall)
    print(fScore)        
    
    print()
    
    precision=correctlyclassified1/classified1
    recall=correctlyclassified1/belongsin1
    fScore=(2*precision*recall)/(precision+recall)
    print(precision)
    print(recall)
    print(fScore)        
                    