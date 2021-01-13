Feature: Getting reports

  Scenario: A client asks for a report
    Given a client with id "cid"
    And he has 2 transactions date "20210102"
    And he asks to see his transactions between "20210102" and "20210103"
    When we request the transaction report
    Then he receives a hashmap of 2 transactions

  Scenario: A client asks for a report
    Given a client with id "cid"
    And he has 2 transactions date "20210102"
    And he asks to see his transactions between "20210101" and "20210102"
    When we request the transaction report
    Then he receives a hashmap of 0 transactions
