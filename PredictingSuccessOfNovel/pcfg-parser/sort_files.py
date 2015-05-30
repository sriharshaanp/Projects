import glob
import sys
import random


TRAIN_PER = 0.80


if __name__ == '__main__':

	# Main program takes 2 command line arguments -
	# <ratings-filename> <user-id> <N-number of nearest neighbours> <k-Top K results to show>
	if len(sys.argv) != 2:
		print("Invalid Arguments!")
		exit(1)

	folder_name = sys.argv[1]
	
	success_filename = folder_name +'_succ'
	succ_file_train = open(success_filename+'_train','w',errors='ignore')
	
	nonsuccess_filename = folder_name + '_nonsucc'
	nonsucc_file_train = open(nonsuccess_filename+'_train', 'w',errors='ignore')

	allfiles = glob.glob(folder_name+'/*.txt')

	random.shuffle(allfiles)

	train_size = int(len(allfiles)*TRAIN_PER)

	for index in range(0,train_size):
		#base_filename = ntpath.basename(filename)
		filename = allfiles[index]

		if '-1-' in filename:
			with open(filename,'r', errors='ignore') as input_file:
				succ_file_train.write(input_file.read())
				succ_file_train.write('\n')
		else:
			with open(filename,'r', errors='ignore') as input_file:
				nonsucc_file_train.write(input_file.read())
				nonsucc_file_train.write('\n')

	succ_file_train.close()
	nonsucc_file_train.close()
	
	succ_file_test = open(success_filename+'_test','w',errors='ignore')
	nonsucc_file_test = open(nonsuccess_filename+'_test', 'w',errors='ignore')

	for index in range(train_size,len(allfiles)):
		#base_filename = ntpath.basename(filename)
		filename = allfiles[index]

		if '-1-' in filename:
			with open(filename,'r', errors='ignore') as input_file:
				succ_file_test.write(input_file.read())
				succ_file_test.write('\n')
		else:
			with open(filename,'r', errors='ignore') as input_file:
				nonsucc_file_test.write(input_file.read())
				nonsucc_file_test.write('\n')

	succ_file_test.close()
	nonsucc_file_test.close()