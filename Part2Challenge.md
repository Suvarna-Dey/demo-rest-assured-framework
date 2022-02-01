#Part 2 Challenge

###Improvements in Quality for Team A:

1. While Kanban retrospectives are not mandatory, I feel it is important to practice it to get feedback for continuous improvement. 
2. Unit test coverage should be increased as it is base for test pyramid. The higher number of unit tests, better is the code quality. Also, there should be a tool to track the coverage like SonarQube.
3. There is no test coverage for Rest API which can be improved by adding some component/contract level tests.
4. There should be a defined test coverage for web apps on Safari Browser, tablets and mobile devices.
5. There should also be test coverage for Performance, security or accessibility testing.
6. E2E tests should be run after every deployment to a shared environment.

###Improvements in Quality for Team B:

1. There should be backlog grooming to prioritise and plan the next sprints.
2. As the team seems to work on backend APIs mostly, there could be changes in contracts that could break the consumers. It would be good to have contract testing in place to avoid the non communicated hiccups.
3. There should be Non-functional test coverage or security test coverage.
4. The engineers should be able to test the feature on their own laptop without depending on the shared environment using tools like Docker. This promotes the shift left approach and also removes the dependency on a shared environment.
5. Instead of going heavy on Integration tests, it would be good to have some component test that mock the services. This should be done in the CI phase.