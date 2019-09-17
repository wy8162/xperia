Practicing Test Driven Development and JMock, EasyMock and Spock.

TDD:
http://www.theserverside.com/news/1365050/Using-JMock-in-Test-Driven-Development

1. Write Test Code
2. Test Fails
3. Write Code
4. Tests Pass
5. Refactor the codes
6. Repeat 1 through 6

JMock Case:

1. Write test code to test TimedCache
2. Ends up with dependency ObjectLoader
3. Mock ObjectLoader
4. Test passes
5. Refactor to get cached object without loading
6. Test cases will reflect the sequence, the times, the arguments and returned values
   of method calls
7. Refactor and ends up with new dependencies: Clock and ReloadPolicy
8. Refactor and retests

This is a typical TDD process.
