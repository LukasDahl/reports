Feature: Getting reports
    
  Scenario: A manager asks for a report
    Given a customer with id "01"
    And a merchant with id "10"
    And a manager with id "20"
    And an empty transaction database
    And the customer has 2 transactions date "2021-01-03" to id "11"
    And the merchant has 2 transactions date "2021-01-03" from id "02"
    And they all ask to see their transactions between "2021-01-02" and "2021-01-04"
    When they request the transaction report
    Then the customer receives a list of 2 transactions
    And the merchant receives a list of 2 transactions
    And the manager receives a list of 4 transactions
    And the client ids are anonymous to the merchant

  Scenario: Invalid dates
    Given a customer with id "01"
    And an empty transaction database
    And the customer has 2 transactions date "2021-01-03" to id "10"
    And the customer asks to see his transactions between "2021-01-01" and "2021-01-02"
    When the customer requests the transaction report
    Then the customer receives a list of 0 transactions
    
  Scenario: Receiving a message
  	Given an empty transaction database
    And we are listening
    When a message from payments is received
    Then a transaction is recorded
    
  