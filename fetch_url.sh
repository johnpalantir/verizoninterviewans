#!/bin/bash
clear
echo "---------> Pls pass parameters url NoOfParallelThread and NoOfTimeEachThreadFetches <---------"

javac FetchURL.java

java FetchURL  

echo "Ended Running Fetch URL Java program succesfully"