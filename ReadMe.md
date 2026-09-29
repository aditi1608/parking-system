 # Overview 
This project is a Spring Boot application that provides a REST API for a simple car park management system. It supports:

It comprises of 3 endpoints 
1. get the available spaces and occupied spaces
2. park the specific vehicle 
3. bill the parked vehicle


# Prerequisites
Java 17+
Maven 3.6+
Git version control 
A terminal / command line

# How to Run Locally
1. Clone the repo
bash
git clone <your-repo-url>
cd carpark

2. Build the project
bash
./mvnw clean install

3. Run the application
bash
./mvnw spring-boot:run

Application will start on po

# API Endpoints
Method	Endpoint	     Description
GET	    /parking	     Get available and occupied number of spaces
POST	/parking	     Park a vehicle in the first available space
POST	/parking/bill	 Free up a vehicle's space and return its final charge

# I have created .http files to call the endpoints direclty from the project but 
PostMan can be used as well to call the endpoints


