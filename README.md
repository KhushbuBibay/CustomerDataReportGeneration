# CustomerDataReportGeneration

**PROBLEM STATEMENT:**
A supermarket owner has decided to understand the visiting pattern of his customers. To do this, he assigned a unique id to each customer. Set the visit count of each customer to 0. Now every time a customer visits the shop, he increments the visit count of the customer by 1. He performed this job for a quarter of the year and the collected the data.
Now, the owner needs your help to group the customers with respect to their visit count. You are provided with the data in CSV format with first row as the header, and the following rows indicates the customer's id and their visit count. You have to read the data from the file and group the customers into buckets of 
0 - 1000, 
1001 - 5000, 
5001 - 10000, 
10001 - 25000, 
25001 - 50000, 
50001 - 75000, 
75001 - 100000, 
100000 +

1- Take value as per given range
2- Take range dynamically

**IMPLEMETION:**

This project is implemented by using core java concepts. Below are the Implementation steps:

Parsed the given file by splitting each line into customer id and it's visit count, and created a Hashmap<String, ArrayList<String>> which contains range as key and list of customer id's as value.
After that user provided with two options to get the output file either in csv or text format.

1- Take value as per given range: ReadReport.java
2- Take range dynamically: DynamicRangeReports.java


