'''
Created on Apr 19, 2015

@author: Sri Harsha
'''
import math

allTerms = [];
termsDocsArray = [];
def getDocumentWordsList(f):
    global termsDocsArray;
    for line in f:
        termsDocsArray = line.replace("[\\W&&[^\\s]]", "").split(".");
    print(len(termsDocsArray));
    
def parseDocuments(file_name):
    global allTerms;
    # Here we need to take the Term Frequency and Inverse Term Frequency of the first 1000 words that we encounter
    with open(file_name,encoding='utf-8', errors='ignore') as f:
        getDocumentWordsList(f);
        for words in termsDocsArray[0:1000]:
            words1 = words.replace("[\\W&&[^\\s]]", "").split();
            for word in words1:
                if(word not in allTerms):
                    allTerms.append(word); 

def tfCalculator(totalTerms,term):
    count = 0;
    for s in totalTerms:
        if(s.lower() == term.lower()):
            count+=1;
    return count/len(totalTerms);

def idfCalculator(allTerms1 , termToCheck):
    count = 1;
    for ss in allTerms1:
        m = ss.split();
        for s in m:
            if(s.lower() == termToCheck.lower()):
                count+=1;
                break;
    return math.log(len(allTerms1)/count);
    
def tfIdfCalculator():
    finalTfIdfDoc = {};
    for docTermsArray in termsDocsArray:
        for term in allTerms:
            tf = tfCalculator(docTermsArray.split(),term);
            idf = idfCalculator(termsDocsArray,term);
            tfidf = tf * idf;
            try:
                finalTfIdfDoc[term]=tfidf;
            except KeyError:
                finalTfIdfDoc[term]=tfidf;
            print(term+":"+str(len(finalTfIdfDoc)))
    return finalTfIdfDoc;

if __name__ == '__main__':
    parseDocuments("/home/sri/workspace/file_addison3159231592-8.txt");
    tfIdfCalculator();
