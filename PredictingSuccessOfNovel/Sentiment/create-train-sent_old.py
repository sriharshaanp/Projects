import sys
import re

def print_processed(sentence_dict):

	for key,line_text in sentence_dict.items():
		print(line_text)

def read_process_input(file_name):

	regex_split_tokens = re.compile(r"([0-9]+)\s+([0-9]+)\s+(.+)\s+([0-9]+)$")
	with open(file_name,'r') as file_hdl:

		# skip first line
		file_hdl.readline()
		
		sentence_dict = {}
		for line in file_hdl:
			line = line.strip()

			phraseid,sentenceid,sent_text,sentiment_score = regex_split_tokens.match(line).group(1,2,3,4)

			if sentenceid not in sentence_dict:
				sentence_dict[sentenceid] = sentiment_score + " " + sent_text

	print_processed(sentence_dict)

			
if __name__ == '__main__':

	# Main program takes 5 command line arguments -
	# <ratings-filename> <user-id> <N-number of nearest neighbours> <k-Top K results to show>
	#if len(sys.argv) != 2:
	#	print("Invalid Arguments!")
	#	exit(1)
	folder_name = sys.argv[1]

	allfiles = glob.glob(folder_name+"/*.out")

	print(allfiles)
	# file_name = sys.argv[1]

	#read_process_input(file_name)

