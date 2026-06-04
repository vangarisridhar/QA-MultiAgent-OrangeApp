@logout
Feature: Logout - ecommerce Application
  As a logged-in user
  I want to log out of the application
  So that my session is securely terminated

  # ── Positive Scenarios ──────────────────────────────────────────────────

  @positive @smoke @TC-KAN1-P-004
  Scenario: TC-KAN1-P-004 Logged-in user can successfully log out and session is terminated
    Given the user is already logged in
    When the user clicks the logout button
    Then the session should be terminated
    And the user should be redirected to the login page

  @positive @security @TC-KAN1-P-005
  Scenario: TC-KAN1-P-005 Back button after logout does not grant access to protected pages
    Given the user is already logged in
    And the user has navigated to the dashboard
    When the user clicks the logout button
    And the user presses the browser back button
    Then the user should not be able to access the dashboard
    And the user should be redirected to the login page

  # ── Negative Scenarios ──────────────────────────────────────────────────

  @negative @security @TC-KAN1-N-007
  Scenario: TC-KAN1-N-007 Accessing a protected page URL directly after logout is blocked
    Given the user is already logged in
    And the user notes the dashboard URL
    When the user clicks the logout button
    And the user navigates directly to the dashboard URL
    Then the user should be redirected to the login page
    And the dashboard content should not be visible
