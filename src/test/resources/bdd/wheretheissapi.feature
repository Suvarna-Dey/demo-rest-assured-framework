Feature: Get satellite position and TLE data
#  This feature tests the wheretheiss public APIs
# Tests coverage for below APIs
# Get satellite position by id : /satellites/[id]/positions
# Get two line element set for a given satellite using id: /satellites/[id]/tles

  Scenario Outline: Get satellite position for valid satellite id
    When I get the satellite position for satellite "<satellite_id>" for below
      | timestamps | <timestamps> |
      | units      | <unit>       |
    Then the response status code should be <response_code>
    And the response body should be have satellite position

    Examples:
      | satellite_id | timestamps            | unit  | response_code |
      | 25544        | 1436029892            | miles | 200           |
      | 25544        | 1436029892,1436029902 | miles | 200           |
      | 25544        | 1436029892            |       | 200           |
      | 25544        | 1436029892,1436029892 |       | 200           |

  Scenario Outline: Validations for get satellite position by Id
    When I get the satellite position for satellite "<satellite_id>" for below
      | timestamps | <timestamps> |
      | units      | <unit>       |
    Then the response status code should be <response_code>
    And the error message should be "<error>"

    Examples:
      | satellite_id | timestamps            | unit  | response_code | error                      |
      | 123          | 1436029892            |       | 404           | satellite not found        |
      | 25544        |                       | miles | 400           | invalid timestamp in list: |
#    any other unit than miles or kilometers = displays in kilometers

  Scenario: Validate response for get satellite positions with 10 timestamps
    When I get the satellite position for satellite id "25544" with 10 timestamps
    Then the response status code should be 200
    And the response body should be have satellite position

  Scenario: Validate response for get satellite positions with 11 timestamps
    When I get the satellite position for satellite id "25544" with 11 timestamps
    Then the response status code should be 403

  Scenario Outline: Get TLE data for a satellite in JSON and Text format
    When I get TLE data in "<format>" format for a given satellite "<satellite_id>"
    Then the response status code should be 200
    And the response body should have TLE data for given satellite

    Examples:
      | satellite_id | format |
      | 25544        |        |
      | 25544        | json   |
      | 25544        | text   |
      | 25544        | xml    |
#    get TLE data for satellite that does not exist = 404
#    get TLE data in format other than JSON or text like xml = returns JSON

  @nft
  Scenario: Load test create quote request
    When I get the satellite position for satellite "25544" for below 20 times
      | timestamps | 1436029892 |
      | units      | miles      |
    Then log the response when service is unavailable