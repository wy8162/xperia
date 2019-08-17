Feature: another lambda Java 8
  Scenario Outline: another just test Java 8
    Given if today is <day>
    When probably I ask whether it's Friday yet
    Then I might be told <answer>

    Examples:
      | day            | answer |
      | Friday         | TGIF   |
      | Sunday         | Nope   |
      | anythingElse   | Nope   |