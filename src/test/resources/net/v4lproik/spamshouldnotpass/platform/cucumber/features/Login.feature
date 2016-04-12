Feature: Login

  Scenario Outline: Should return null when a non-registered user tries to login

    Given my login is "<Login>"
    Given my password is "<Password>"
    When I provide my credentials
    Then I should receive the following error "<Result>"

  Examples:
    | Login          | Password | Result |
    | abcde@abcde.fr | 1234     | null   |
    | abcde@abcde.fr |          | null   |
    | abcde@abcde.fr | null     | null   |