import sys
import os
import errno

MULTIPLIER = 600
if __name__ == '__main__':

	# Main program takes 2 command line arguments -
	# <ratings-filename> <user-id> <N-number of nearest neighbours> <k-Top K results to show>
	if len(sys.argv) != 3:
		print("Invalid Arguments!")
		exit(1)

	filename = sys.argv[1]

	folder_name = sys.argv[2]
	try:
		os.mkdir(folder_name)
	except OSError as exception:
		if exception.errno != errno.EEXIST:
			raise

	with open(filename,'r') as input_file:
		lines = input_file.readlines()

		counter = int(len(lines)/MULTIPLIER)

		counter = counter + 1 if len(lines) % MULTIPLIER > 0 else counter

		for index in range(counter):

			somelines = lines[(index*MULTIPLIER):((index+1)*MULTIPLIER)]

			with open(os.path.join(folder_name,folder_name+"_"+str(index)), 'w') as write_file:
				write_file.write("".join(somelines)) 


	

