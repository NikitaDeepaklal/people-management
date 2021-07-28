# People Directory

# Project description:
Provide help on people directory

1.	Add Person (id, firstName, lastName)
2.	Edit Person (firstName, lastName)
3.	Delete Person (id)
4.	Add Address to person [multiple required] (id, street, city, state, postalCode)
5.	Edit Address (street, city, state, postalCode)
6.	Delete Address (id)
7.	Count Number of Persons
8.	List Persons



# How to Run the project:

## Pre-requisities

#### Java SE Development Kit 8 (1.8.0) or newer

Run this command in your terminal to see what version you have setup

```
javac -version
```



#### Maven 3.0 or newer

Run this command in your terminal to see what version you have setup

```
mvn -version
```

## Project Download

You can either

* Download the .zip file and extract it
* Clone the project using git

## Build the project

* After you have unzipped, using your terminal navigate to its root folder
* Now run this command:  `mvn clean install`
* This will use Maven to download the required dependencies, and build an executable jar file in the newly created "
  target" folder

## Run the project

You can now execute the project by navigating inside the target folder and running this command

`mvn spring-boot:run`

Access the url: http://localhost:9091/swagger-ui.html#/ to see the APIs and to try it out.

### <u>Person API:</u>

Helps you do the following:

Add Person
Delete Person
Update Person
Count
List persons

Sample input/output data:
POST:
http://localhost:9091/persons

Sample input data:
 
{"firstName": "Nikita",
"lastName": "Deepaklal"}

Sample output data:

```
{
	{
		"id": 1,
		"firstName":"Nikita",
		"lastName":"Deepaklal",
		"Address": null

	}
}

```


### <u>Address API:</u>

Helps you do the following:
Add address 
Update address
Delete address

Sample input/output

POST:
http://localhost:9091/persons/1/address

Sample input data:
{
"city":"city name",
"state":"state name",
"country":"country name",
"postalcode": 12345
}

Sample output Data:
{
	{
		"id":1,
		"city":"city name",
		"state":"state name",
		"country":"country name",
		"postalcode": 12345
	}
	
}


---
### Code includes:

#### Database:
H2 - in memory database

#### Basic validations:
checks if the input data is valid for person
checks if the person exists before updating or deleting
checks if the person exists before adding address
checks if input data is valid for address.

#### TestCases: UNIT Tests with mockito
Positive/negative:
checks if each of the above mentioned operations are working for a valid input
checks if each of the above mentioned operations fails when input is invalid
checks if the operations fails when the given user is not found.

#### Exceptions:
InvalidInputException - is thrown when the input is not valid.
NotFoundException - is thrown when the person or address is not found.
ServiceException - is thrown when we see a server error (error while talking to DB)


# What can be improved here:
Add front end for better experience
Add Authorization and authentication for restapis.
Validations can be improved and error responses can be made better.

Note:
* This project uses 'com.exercise.peoplemanagement' as project name.


##Author:
Nikita Deepaklal nikita.achpal@gmail.com


 




