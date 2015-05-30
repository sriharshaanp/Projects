'''
Created on Apr 22, 2015

@author: RAJ
'''
import os
import sys
from perceplearn import percepLearn

def snGrams(dependencyFile,basePath):
    training="training"
    tr=open(training,"w+")
    listOfFiles = os.listdir(path=basePath)
    
    
    for file in listOfFiles:
        
        sucess=file.split("-")[1]
        
        file=open(basePath+file,"r")
        
        sentenceDict=[]
        dict={}
        for line in file:
            line=line.rstrip()
            line=line.split(",;;")
            line=line[0:len(line)-1]
            for tuple in line:
                tuple=tuple[tuple.find("(")+1:tuple.find(")")]
                tuple=tuple.split(",")
                head=tuple[0].split("-")[0]
                determinant=tuple[1].split("-")[0].lstrip()
                dict.setdefault(head,[])
                if head in dict:
                    dict[head].append(determinant)
                else:
                    dict[head]=determinant
            else:
                sentenceDict.append(dict)
                dict={}
            
        nGrams=[]
        for sentenceStructure in sentenceDict:
            generateNgrams(sentenceStructure,"ROOT",nGrams)
            nGrams=" ".join(nGrams)
            tr.write(sucess+" "+nGrams+"\n")
            nGrams=[]
            
    tr.close()        
    modelFile="model"            
    percepLearn(training,modelFile)
        
def generateNgrams(sentenceStructure,start,nGrams):
    if start in sentenceStructure:
        dependentList=(sentenceStructure[start])
        for dependent in dependentList:
            if start+dependent not in nGrams:
                nGrams.append(start+dependent)
                generateNgrams(sentenceStructure, dependent,nGrams)            
                        
if __name__ == '__main__':
    
    basePath=sys.argv[1]
    dependencyFile=sys.argv[2]
    snGrams(dependencyFile,basePath)