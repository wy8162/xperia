Feature: a custom mapper

  Scenario: mapping a matrix to list of list
    Given matrix
      | 1 | 2 | 3 |
      | 2 | 2 | 2 |
      | 3 | 3 | 3 |
    When I parse the data
    Then I'll get a matrix

  Scenario: mapping a sales object
    Given sales data {"Id":1, "deptNo":"dept1", "country":"China", "partNo":"part1", "amount":0.1}
    When the data is read
    Then I'll get a sakes object

  Scenario: mapping a list of sales object with custom transformer
    Given list of sales objects in a table as below
      | Id  |  deptNo | country | partNo | amount |
      | 1   |  dept1  | China   | p1     | 1.11   |
      | 2   |  dept2  | USA     | p2     | 2.22   |

  Scenario: mapping sales object with custom cell transformer
    Given I have a list of sales items below
      | Id  |  deptNo | country | partNo | amount |
      | 1   |  dept1  | China   | p1     | 3.33   |
      | 2   |  dept2  | USA     | p2     | 4.44   |

  Scenario: mapping a student
    Given map single student from string {"id":1, "name":"Jack", "courses":[{"id":1, "courseName":"Math", "students":[], "records":[]}], "records":[]}

  Scenario: now I want to map a student in another way
    Given map a student directly {"id":1, "name":"Jack", "courses":[{"id":1, "courseName":"Math", "students":[], "records":[]}], "records":[]}

  Scenario: Process persons registered with TypeRegistry
    Given persons as table entries (header as key and data in rows) of a table below
      | firstName | lastName | birthDate  |
      | Jack      | Wang     | 2019-01-01 |
      | Jane      | Wan      | 2019-02-01 |
      | Tom       | Li       | 2019-03-01 |
