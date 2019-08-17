Feature: Withdraw money
  Demo for how to use tables

  Scenario Outline:
    Given I have <Balance> in my account
    When I choose to withdraw the fixed amount of <Withdrawal>
    Then I should receive <Withdrawal> and the balance of my account should be <Remaining> and the <Outcome>

    Examples:
      | Balance | Withdrawal | Remaining | Outcome              |
      | 500     | 50         | 450       | Receive 50 in cash  |
      | 500     | 100        | 400       | Receive 100 in cash |
      | 500     | 200        | 300       | Receive 200 in cash |