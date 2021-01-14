Feature: Getting reports

  Scenario: A client asks for a report
    Given a client with id "01"
    And the client has 2 transactions date "20210103" to id "10"
    And he asks to see his transactions between "20210102" and "20210104"
    When we request the transaction report
    Then he receives a hashmap of 2 transactions
    
  Scenario: A merchant asks for a report
    Given a merchant with id "10"
    And the merchant has 2 transactions date "20210103" from id "01"
    And the merchant has 1 transactions date "20210103" to id "01" 
    And he asks to see his transactions between "20210102" and "20210104"
    When we request the transaction report
    Then he receives a hashmap of 3 transactions
    And the client ids are anonymous

  Scenario: Invalid dates
    Given a client with id "01"
    And the client has 2 transactions date "20210103" to id "10"
    And he asks to see his transactions between "20210101" and "20210102"
    When we request the transaction report
    Then he receives a hashmap of 0 transactions