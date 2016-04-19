Feature: Login

  Background:
    Given A new user
      | email          | password | firstname | lastname | corporation |
      | abcde@abcde.fr | 1234     | spider    | cochon   | google      |

  @database
  Scenario: A user submits good credentials
    When He submits its credentials
    Then He is logged in

   @database
   Scenario: A user submits bad credentials
     When He submits its wrong credentials
     Then He receives an error

   @database
   Scenario: Email is already taken
     Given A new user
       | email          | password | firstname | lastname | corporation |
       | abcde@abcde.fr | 12345    | spider5   | cochon5  | google5     |
     Then He receives an error

  @database
  Scenario: A user creates an API key
    When He creates an API key
    Then He receives an API key

  @database
  Scenario: Delete a user
    When He tries to delete the user
    Then The user is deleted

  @database
  Scenario: A user logs out
    When He submits its credentials
    And He tries to log out
    Then He is logged out