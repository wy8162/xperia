Feature: Various way to process data table

  # Data table without header row
  Scenario: Create new account in Facebook
    Given fill up the new account form with the following data without header
      | Test FN | Test LN | 0123123123 | Pass1234 | 01 | Jan | 1990 | Male |

  Scenario: Create new account in Facebook
    Given fill up the new account form with the following data with header
      | First Name | Last Name | Phone No   | Password | DOB Day  | DOB Month  | DOB Year  | Gender |
      | Test FN    | Test LN   | 0123123123 | Pass1234 | 01       | Jan        | 1990      | Male   |

  # Data table with header row. Can be converted to List<Map<String, String>> : DataTable.asMap(String.class, String.class)
  Scenario: Create multiple new accounts in Facebook
    Given I open Facebook URL and create new accounts with below data
      | First Name | Last Name | Phone No   | Password | DOB Day  | DOB Month  | DOB Year  | Gender |
      | Abc FN     | Abc LN    | 0123123123 | Pass1234 | 01       | Jan        | 1990      | Male   |
      | Def FN     | Def LN    | 0456456456 | Abcd1234 | 01       | Feb        | 1990      | Female |
      | Xyz FN     | Xyz LN    | 0789789789 | Pass2018 | 01       | Mar        | 1990      | Female |

  # Data table as List<List<String>>
  Scenario: Case 1 - Input data as List<List<String>>
    Given Case 1 - Input data as List<List<String>>
      | firstName   | lastName | birthDate  |
      | Annie M. G. | Schmidt  | 1911-03-20 |
      | Roald       | Dahl     | 1916-09-13 |
      | Astrid      | Lindgren | 1907-11-14 |

  # Data table as List<List<String>>
  Scenario: Case 2 - Input data as List<Map<String, String>>
    Given Case 2 - Input data as List<Map<String, String>>
      | firstName   | lastName | birthDate  |
      | Annie M. G. | Schmidt  | 1911-03-20 |
      | Roald       | Dahl     | 1916-09-13 |
      | Astrid      | Lindgren | 1907-11-14 |

  # Data table converted to a single map with first column as keys
  Scenario: Case 3 - Two-column Table as a map with first column as keys
    Given Case 3 - Table to Map<String, String>
      | KMSY | Louis Armstrong New Orleans International Airport |
      | KSFO | San Francisco International Airport               |
      | KSEA | Seattle–Tacoma International Airport              |
      | KJFK | John F. Kennedy International Airport             |

  # Data table converted to a single map with first column as keys
  Scenario: Case 4 - Table as a map of lists with first column as keys
    Given Case 4 - Table to Map<String, List<String>>
      | KMSY | 29.993333 |  -90.258056 |
      | KSFO | 37.618889 | -122.375000 |
      | KSEA | 47.448889 | -122.309444 |
      | KJFK | 40.639722 |  -73.778889 |

  # Data table converted to a single map with first column as keys
  Scenario: Case 5 - Table as a map of map with first column as keys
    Given Case 5 - Table to Map<String, Map<String, String>>
      |      |       lat |         lon |
      | KMSY | 29.993333 |  -90.258056 |
      | KSFO | 37.618889 | -122.375000 |
      | KSEA | 47.448889 | -122.309444 |
      | KJFK | 40.639722 |  -73.778889 |
