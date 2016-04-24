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
     When He submits wrong credentials
     Then He receives an error

   @database
   Scenario: Email is already taken
     Given A new user
       | email          | password | firstname | lastname | corporation |
       | abcde@abcde.fr | 12345    | spider5   | cochon5  | google5     |
     Then He receives an error

  @database
  Scenario: A user creates an API key
    When He submits its credentials
    Then He is logged in
    When He creates an API key
    Then He receives an API key

  @database
  Scenario: A user decides to delete its own account
    When He submits its credentials
    Then He is logged in
    When He deletes his account
    When He submits its credentials
    Then He is not logged in

  @database
  Scenario: A user logs out
    When He submits its credentials
    When He logged out
    Then He is logged out