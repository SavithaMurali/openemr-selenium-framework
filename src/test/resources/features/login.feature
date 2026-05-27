Feature: Login Authentication
  As a user of OpenEMR
  I want to be able to login with valid credentials
  So that I can access the system

@Smoke
  Scenario Outline: Valid login with different roles
    Given I am on the OpenEMR login page
    When I enter username "<username>" and password "<password>"
    And I click the login button
    Then I should be redirected to the dashboard

    Examples:
      | testCaseId | username     | password     |
      | AUTH-01    | admin        | pass         |
      | AUTH-02    | physician    | physician    |
      | AUTH-02b   | clinician    | clinician    |
      | AUTH-02c   | receptionist | receptionist |
      | AUTH-02d   | accountant   | accountant   |
      
 
@AUTH-03 @Negative
  Scenario Outline: Invalid credentials shows error message
    Given I am on the OpenEMR login page
    When I enter username "<username>" and password "<password>"
    And I click the login button
    Then I should see the error message "Invalid username or password"

    Examples:
      | testCaseId | username      | password      |
      | AUTH-03a   | wronguser     | wrongpass     |
      | AUTH-03b   | admin         | wrongpass     |
      | AUTH-03c   | wronguser     | pass          |
      | AUTH-03d   | ' OR '1'='1   | ' OR '1'='1   |
      
@AUTH-04 @Negative
  Scenario Outline: Empty fields shows validation message
    Given I am on the OpenEMR login page
    When I enter username "<username>" and password "<password>"
    And I click the login button
    Then I should remain on the login page

    Examples:
      | testCaseId | username | password |
      | AUTH-04a   |          |          |
      | AUTH-04b   | admin    |          |
      | AUTH-04c   |          | pass     |
      
      
@AUTH-05 @Regression
  Scenario: Logout redirects to login page
    Given I am logged in as "admin" with password "pass"
    When I navigate to the logout page
    Then I should be redirected to the login page
    
@AUTH-06 @Regression
  Scenario: Password field is masked by default
    Given I am on the OpenEMR login page
    When I enter password "testpassword"
    Then the password field should be masked

@AUTH-07 @Regression
  Scenario: Eye icon toggles password visibility
    Given I am on the OpenEMR login page
    When I enter password "testpassword"
    And I click the eye icon
    Then the password should be visible
    When I click the eye icon again
    Then the password field should be masked
    
    
      
      
         