import sys
import os
import itertools

def call_stanford_coreNLP(folder_name):
	pass

def add_predictions(filename_to_predctns_dict, curr_filename, predictions):
	lists = filename_to_predctns_dict[curr_filename]
	new_list = []
	for prediction in predictions.split():
		new_list.append(float(prediction))
	lists.append(new_list)

def get_max_index(my_list):
	max_index = -1
	max_val = 0.0

	for i in range(my_list):
		if my_list[i] > max_val:
			max_index = i
			max_val = my_list

	return max_index

def getPredictionStr(index):
	return {
		0:"Neg",
		1:"Neg",
		2:"Neu",
		3:"Pos",
		4:"Pos"
	}.get(index)


def write_predictions(filename_to_predctns_dict, curr_filename, n_input):
	print_str = curr_filename

	prediction_sentences = filename_to_predctns_dict[curr_filename]

	prediction_sublists = [prediction_sentences[i:i+n_input] for i in range(0,len(prediction_sentences),3)]

	for my_sublist in prediction_sublists:
		sum_n_predictions = [sum(sublist) for sublist in itertools.izip(*my_sublist)]

		max_index = get_max_index(sum_n_predictions)

		print_str += " "+getPredictionStr(max_index)

	# Printing to STDOUT
	print(print_str)


def create_train_sent(folder_name, n_input):

	call_stanford_coreNLP(folder_name)

	temp_filename = folder_name + "_temp"
	curr_filename = ""

	regex_split_tokens = re.compile(r"([^:]+):(.+)")

	filename_to_predctns_dict = {}

	# Assume temp file starts with filename: sentence1
	with open(temp_filename,'r') as input_file:
		while True:
			line = input_file.readline()
			if line == '' or line == None:
				break
			line = line.strip()

			filename,my_sentence = regex_split_tokens.match(line).groups(1,2)

			predictions = input_file.readline().strip()

			if curr_filename != filename:
				if curr_filename != "":
					write_predictions(filename_to_predctns_dict,curr_filename, n_input)
				curr_filename = filename
				filename_to_predctns_dict[curr_filename] = []

			add_predictions(filename_to_predctns_dict, curr_filename, predictions)

		if curr_filename != "":
			write_predictions(filename_to_predctns_dict,curr_filename,n_input)






if __name__ == '__main__':

	# Main program takes 2 command line arguments -
	# <ratings-filename> <user-id> <N-number of nearest neighbours> <k-Top K results to show>
	if len(sys.argv) != 2:
		print("Invalid Arguments!")
		exit(1)

	folder_name = sys.argv[1]
	n_input = int(sys.argv[2])

	create_train_sent(folder_name, n_input)
