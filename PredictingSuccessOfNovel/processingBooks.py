__author__ = 'Ajinkya'
import sys
import os


dir=sorted(os.listdir("Books/"))

for fname in dir:
    fname1="Books/"+fname

    fo=open("Processed/processed_"+fname, "a")

    fo1=open("preprocessed/preprocessed_"+fname, "a")

    clean = open(fname1).read().replace('. ', '.\n')
    fo1.write(clean)
    fo1.close()

    i=1
    j=1;
    str=''
    for line in open("preprocessed/preprocessed_"+fname, "r"):
        if j==int(sys.argv[1]):
            break;
        line=line.rstrip('\n')
        if i%(int(sys.argv[2]))==0:
            fo.write(str+"\n")
            str=''
            i=1
        else:
            str=str+line+" "
            i=i+1
        j=j+1
    fo.close()
