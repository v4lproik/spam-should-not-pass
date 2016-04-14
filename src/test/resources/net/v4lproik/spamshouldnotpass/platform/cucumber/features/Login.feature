Feature: Login

  Scenario: User login scenarios;

    Given A new user
      | email          | password | firstname | lastname | corporation |
      | abcde@abcde.fr | 1234     | spider    | cochon   | google      |
    When He submits its credentials
    Then He is logged in