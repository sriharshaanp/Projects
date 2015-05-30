'''
Created on Apr 16, 2015

@author: RAJ
'''
import sys
import os
from perceplearn import percepLearn

def ngramGenerator(n,basePath):
    
    listOfFiles = os.listdir(path=basePath);
    training="test"
    trainingFile=open(training,"w+")
    for file in listOfFiles:
        
        
        fHandle=open(basePath+file,"r")
    
        for line in fHandle:
            trainingFile.write(file.split("-")[1]+" ")
            words=line.split()
            
            index=0
            for word in words:
            
                if (index-n)>=0:
                    nGram=words[index:(index-n):-1]
                    index+=1
                else:            
                    nGram=words[index::-1]
                    index+=1
                nGram=nGram[::-1]
            
                appendString=""
                if len(nGram)<n:
                    appendLength=n-len(nGram)
                    for i in range(0,appendLength):
                        appendString+="BOS"
                
                finalNGram=appendString+"".join(nGram)                          
                #print(appendString+"".join(nGram))
                trainingFile.write(finalNGram+" ")
            trainingFile.write("\n")       
        fHandle.close()
    trainingFile.close()
    modelFile="model"
    percepLearn(training,modelFile)
            
if __name__ == '__main__':
    n=sys.argv[1]
    basePath=sys.argv[2]
    ngramGenerator(n,basePath)