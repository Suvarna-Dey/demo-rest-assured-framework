# Truelayer Tech Challenge

### Description:

This Java based framework tests the WTIA REST APIs using RestAssured library. The wheretheiss API lets you get the current, past or future position of the ISS, get timezone information about a set of cordinates, and also get the TLE data on the ISS.

### Prerequisites:
* Java 8 or more
* Maven
* Any browser for viewing HTML test report

### Execution:
Tests can be run by following the below

* Clone the Github repository
* cd into the project folder from  terminal
* Run the command:

```bash
 mvn clean install
 ```

### Reports:
Reports can be viewed in folder:
* Cucumber HTML Reports: `target/cucumber-reports/cucumber-html-reports/overview-features.html`

### Logging:
The api logs for negative scenarios can be viewed in folder: `log/api.log`

###Observations/Issues
1. The exact error codes and messages are not specified for negative path. So the tests have been written by some assumption of error codes and scenarios.
2. The API spec does not talk about the data types of the response or request data members.
3. `/satellites/[id]/positions` does not limits the timestamps to 10 which contracdicts the API spec.
4. If a same timestamp is provided to the `/satellites/[id]/positions` as query param, the same position is returned twice.

### Author
Suvarna Narayan Dey  