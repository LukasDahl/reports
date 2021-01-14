Feature: Getting reports
    
  Scenario: A manager asks for a report
    Given a customer with id "01"
    And a merchant with id "10"
    And a manager with id "20"
    And an empty transaction database
    And the customer has 2 transactions date "20210103" to id "11"
    And the merchant has 2 transactions date "20210103" from id "02"
    And they all ask to see their transactions between "20210102" and "20210104"
    When they request the transaction report
    Then the customer receives a hashmap of 2 transactions
    And the merchant receives a hashmap of 2 transactions
    And the manager receives a hashmap of 4 transactions
    And the client ids are anonymous to the merchant

  Scenario: Invalid dates
    Given a customer with id "01"
    And an empty transaction database
    And the customer has 2 transactions date "20210103" to id "10"
    And the customer asks to see his transactions between "20210101" and "20210102"
    When the customer requests the transaction report
    Then the customer receives a hashmap of 0 transactions