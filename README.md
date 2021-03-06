# API Test Framework

### Description:

This Java based framework tests the WTIA(What The ISS at) REST APIs using RestAssured library. 
The API lets you get the current, past or future position of the ISS, get timezone information about a set of coordinates, and also get the TLE data on the ISS.

### API Spec:
https://wheretheiss.at/w/developer

### Scope

Tests are covered for below APIs
* Get satellite position by id : `/satellites/[id]/positions`
* Get two line element set for a given satellite using id: `/satellites/[id]/tles`

### Prerequisites:
* Java 8 and above
* Maven
* Git
* Any terminal to run the bash command
* Any browser for viewing HTML test report

### Execution:
Tests can be run by following the below steps

* Clone the GitHub repository
* cd into the project folder from the terminal
* Run the below command:

```bash
 mvn clean install
 ```

### Reports:
Reports can be viewed in folder:
* Cucumber HTML Reports: `target/cucumber-reports/cucumber-html-reports/overview-features.html`

### Logging:
The api logs for error scenarios can be viewed in folder: `log/api.log`

### Observations/Issues
1. The exact error codes and messages are not specified for negative path. So the tests have been written by some assumption of error codes and messages.
2. The API spec does not talk about the data types of rthe response or request data members.
3. `/satellites/[id]/positions` does not limits the timestamps to 10 which contradicts the API spec.
4. If a same timestamp is provided to the endpoint `/satellites/[id]/positions` twice as query param, the same position is returned twice.

### Author
Suvarna Narayan Dey