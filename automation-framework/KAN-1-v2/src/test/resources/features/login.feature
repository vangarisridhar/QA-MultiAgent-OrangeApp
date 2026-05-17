@login
Feature: Login - ecommerce Application
  As a registered user
  I want to log in to the application
  So that I can access protected features

  Background:
    Given the user is on the login page

  # ── Positive Scenarios ──────────────────────────────────────────────────

  @positive @smoke @TC-KAN1-P-001
  Scenario: TC-KAN1-P-001 Successful login with valid credentials redirects to dashboard
    When the user enters username "Admin"
    And the user enters password "admin123"
    And the user clicks the login button
    Then the user should be redirected to the dashboard
    And no error message should be displayed

  @positive @TC-KAN1-P-002
  Scenario: TC-KAN1-P-002 Login succeeds with username entered in uppercase
    When the user enters username "ADMIN"
    And the user enters password "admin123"
    And the user clicks the login button
    Then the user should be redirected to the dashboard

  @positive @TC-KAN1-P-003
  Scenario: TC-KAN1-P-003 User can log in again after a previous logout
    Given the user is already logged in
    And the user has logged out
    And the user is on the login page
    When the user enters username "Admin"
    And the user enters password "admin123"
    And the user clicks the login button
    Then the user should be redirected to the dashboard

  # ── Negative Scenarios ──────────────────────────────────────────────────

  @negative @TC-KAN1-N-001
  Scenario: TC-KAN1-N-001 Login fails with invalid username and valid password
    When the user enters username "nonexistent@example.com"
    And the user enters password "Valid@123"
    And the user clicks the login button
    Then an error message should be displayed
    And the user should remain on the login page
    And no session should be created

  @negative @TC-KAN1-N-002
  Scenario: TC-KAN1-N-002 Login fails with valid username and incorrect password
    When the user enters username "Admin"
    And the user enters password "WrongPass!"
    And the user clicks the login button
    Then an error message should be displayed
    And the user should remain on the login page

  @negative @TC-KAN1-N-003
  Scenario: TC-KAN1-N-003 Login fails with both username and password invalid
    When the user enters username "fake@fake.com"
    And the user enters password "badpass"
    And the user clicks the login button
    Then an error message should be displayed
    And the user should remain on the login page

  @negative @TC-KAN1-N-004
  Scenario: TC-KAN1-N-004 Login fails when username field is empty
    When the user enters username ""
    And the user enters password "Valid@123"
    And the user clicks the login button
    Then a validation error should be shown for the username field

  @negative @TC-KAN1-N-005
  Scenario: TC-KAN1-N-005 Login fails when password field is empty
    When the user enters username "testuser@example.com"
    And the user enters password ""
    And the user clicks the login button
    Then a validation error should be shown for the password field

  @negative @TC-KAN1-N-006
  Scenario: TC-KAN1-N-006 Login fails when both username and password fields are empty
    When the user clicks the login button without entering credentials
    Then a validation error should be shown for the username field
    And a validation error should be shown for the password field

  # ── Edge Cases ──────────────────────────────────────────────────────────

  @edge @TC-KAN1-E-001
  Scenario: TC-KAN1-E-001 Login with username at maximum character limit
    When the user enters a username of maximum allowed length
    And the user enters password "Valid@123"
    And the user clicks the login button
    Then the application should handle the max-length input without crashing

  @edge @TC-KAN1-E-002
  Scenario: TC-KAN1-E-002 Login with password containing special characters
    When the user enters username "Admin"
    And the user enters password "P@$$w0rd!#%"
    And the user clicks the login button
    Then an error message should be displayed

  @edge @security @TC-KAN1-E-003
  Scenario: TC-KAN1-E-003 SQL injection attempt in username field is rejected
    When the user enters username "' OR '1'='1"
    And the user enters password "anything"
    And the user clicks the login button
    Then an error message should be displayed
    And the user should remain on the login page
    And no SQL error should be exposed

  @edge @security @TC-KAN1-E-004
  Scenario: TC-KAN1-E-004 XSS payload in username field is sanitized
    When the user enters username "<script>alert('xss')</script>"
    And the user enters password "anything"
    And the user clicks the login button
    Then the XSS payload should not be executed
    And the user should remain on the login page

  # ── Validation Scenarios ─────────────────────────────────────────────────

  @validation @TC-KAN1-V-001
  Scenario: TC-KAN1-V-001 Username field validates correct email format
    When the user enters username "userexample.com"
    And the user clicks the login button
    Then a validation error should be shown for the username field

  @validation @TC-KAN1-V-002
  Scenario: TC-KAN1-V-002 Password field masks characters as user types
    When the user enters password "TestPassword123"
    Then the password field should mask the input

  @validation @security @TC-KAN1-V-003
  Scenario: TC-KAN1-V-003 Error message on failed login does not reveal which field is wrong
    When the user enters username "Admin"
    And the user enters password "WrongPass!"
    And the user clicks the login button
    Then the error message should be generic and not field-specific

  @validation @security @TC-KAN1-V-004
  Scenario: TC-KAN1-V-004 Session cookie is cleared after logout
    Given the user is already logged in
    When the user logs out
    Then the session cookie should be cleared

  @validation @security @TC-KAN1-V-005
  Scenario: TC-KAN1-V-005 Auth token is invalidated after logout
    Given the user is already logged in
    When the user logs out
    Then the auth token should be invalidated
