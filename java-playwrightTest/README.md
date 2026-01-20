# Running tests

Fix the URL in Config.java.

Run the test once, it should download and install the Playwright browser for you.

# How to launch it 
mvn test

# Example how to test one features 
mvn test -Dtest=AutomaticEmployeeManagementTest#addingUserTest

# Launch test with debug mode
$env:PWDEBUG=1
mvn test
