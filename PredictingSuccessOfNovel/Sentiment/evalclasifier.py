__author__ = 'mohit'
import sys


actual_file = sys.argv[1]

predicted_file = sys.argv[2]

myActualDict = {}
myPredictedDict = {}
myCorrectlyClass = {}
correct_count = 0
total_count = 0
with open(actual_file,"r") as actual_file_hdl,open(predicted_file,"r") as predicted_file_hdl:
    for actual_line,predicted_line in zip(actual_file_hdl,predicted_file_hdl):

        total_count += 1

        actual_line = actual_line.strip()

        predicted_line = predicted_line.strip()

        myActualDict[actual_line] = myActualDict[actual_line] + 1 if actual_line in myActualDict else 1

        myPredictedDict[predicted_line] = myPredictedDict[predicted_line] + 1 if predicted_line in myPredictedDict else 1

        if actual_line == predicted_line:
            myCorrectlyClass[predicted_line] = myCorrectlyClass[predicted_line] + 1 if predicted_line in myCorrectlyClass else 1
            correct_count += 1

for label,count in myActualDict.items():

    precision = myCorrectlyClass[label]/myPredictedDict[label] if (label in myPredictedDict and
                                                                   label in myCorrectlyClass) else 0

    recall = myCorrectlyClass[label]/myActualDict[label] if (label in myCorrectlyClass and
                                                             label in myActualDict) else 0

    f = (2 * precision * recall)/(precision + recall) if (recall+precision) > 0 else 0
    print(label + ":\n Fscore:" + str(f) + "\ncd prec:" + str(precision) + "\nRecall:" + str(recall))

print("Accuracy:" + str(correct_count/total_count))
