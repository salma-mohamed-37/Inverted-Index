# Inverted-Index
This  repository contains Java implementaion of building inverted index for 10 text files, takes a query of word or more and prints the ids of the documents contains the query based on the retrieval model the user chooses. 

# The retrieval models
This project contains 2 models : 
1-Boolean model : checks if a document contains at least one word from the words in the query and return it  
2-Ranking model : which calculates TF-IDF and cosine similarity and return all the 10 files in descending order with respect to the value of cosine similarity between the query and the document

# Running this project
To use this project you will clone this repository and run the project on any Java IDE but if you want to run it on different files you have to put the files in the project beside src folder and put the name and the extension of the file in the following line in collection class

'''
String filesnames [] = {"software engineering.txt", "software engineering2.txt","operating systems.txt","operating systems2.txt","cloud computing.txt","cloud computing2.txt","data structures.txt","data structures2.txt", "google assistant.txt" , "google assistant2.txt"};
'''
Notes : some parts of the code assumes that always the number of the files is 10. 
The id of the file is its index in filesnames array +1.

	
