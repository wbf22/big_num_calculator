# Calculator

A simple big number calculator to use in the terminal. It can handle arbitrarily large numbers and supports addition, subtraction, multiplication, division, powers, expressions with parentheses, and more.


# Installation

You'll first need to have java 17 and maven installed on your machine. Another version of java might work, but it hasn't been tested.

Clone the project and run this in the project root
```bash
mvn package
```

This should make a jar file in a new directory there called target at this path 'target/big_num_calculator-1.0.jar'. You can run the program with this command
```bash
java -jar target/big_num_calculator-1.0.jar
```

You then might make an alias if you're on windows or mac like this one:
```bash
alias calc='java -jar {path_to_project_root}/target/big_num_calculator-1.0.jar'
```

You can then run that program and start entering numbers and expressions like a normal calculator.