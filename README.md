# Portfolio project IDATT1003

STUDENT NAME = Aksel Kirkhorn  
STUDENT ID = "161357"

## Project description

This project is a personal **Diary Application** developed as a portfolio assignment for the course IDATT1003 - Programmering 1.

**Key Features:**
*   **Write Entries:** Create new diary entries with a title, description, and an associated author.
*   **Manage Authors:** Create and maintain a registry of authors.
*   **Search:** Find entries by keyword, specific date, date range, or author ID.
*   **Statistics:** View the number of entries per author.
*   **Persistence:** All data is handled in-memory for fast execution during the session.

## Project structure

The project follows a standard Maven directory structure and organizes source code into packages based on the Model-View-Controller (MVC) design pattern.

The detailed project file structure is as follows:

```
.
├── java
│   └── edu
│       └── ntnu
│           └── idi
│               └── idatt
│                   ├── App.java                 
│                   ├── controller
│                   │   └── DiaryController.java
│                   ├── model
│                   │   ├── entity
│                   │   │   ├── Author.java      
│                   │   │   └── DiaryEntry.java 
│                   │   └── register
│                   │       ├── AuthorRegister.java 
│                   │       └── DiaryRegister.java  
│                   ├── util
│                   │   └── Validators.java      
│                   └── view
│                       └── UserInterface.java    
└── resources
```

## Link to repository

[https://github.com/NTNU-IDI/mappe-2025-akseltk](https://github.com/NTNU-IDI/mappe-2025-akseltk)


## How to run the project

You need **Java 21** (or a compatible JDK) and **Maven** installed on your machine.

1.  **Navigate to the project root directory** (where `pom.xml` is located).
2.  **Build the project:**
    ```bash
    mvn clean install
    ```
3.  **Run the application:**
    ```bash
    mvn exec:java
    ```

**Interaction:**
The application is menu-driven. Follow the on-screen instructions to navigate menus by entering numbers (e.g., `1` to write an entry) and providing text input when prompted.

## How to run the tests

The project includes a suite of JUnit 5 tests to verify the correctness of the model and registers.

1.  **Run all tests using Maven:**
    ```bash
    mvn test
    ```
2.  **View Results:**
    The test results will be displayed in the terminal. Detailed reports can be found in `target/surefire-reports/`.

## References

*   **Course Material:** Principles of Object-Oriented Programming and MVC architecture as taught in IDATT1003.
*   **Java Documentation:** [Official Java 21 Documentation](https://docs.oracle.com/en/java/javase/21/)
*   **Maven:** [Apache Maven Project](https://maven.apache.org/)
*   GeeksforGeek, «MVC Architecture - System Design,» [Internett]. Available: https://www.geeksforgeeks.org/system-design/mvc-architecture-system-design/. [Funnet 29 11 2025].
*   I. Polat, «Coupling Concept in Software Engineering,» [Internett]. Available: https://medium.com/@ilkaypolatnl/coupling-concept-in-software-engineering-916fc07f5c33. [Funnet 9 11 2025].
*   R. Patel, «Understanding Cohesion in Software Engineering,» [Internett]. Available: https://medium.com/@ravipatel.it/understanding-cohesion-in-software-engineering-8fefc017de5c. [Funnet 29 11 2025].
*   Automation Panda, «Arrange-Act-Assert: A Pattern for Writing Good Tests,» [Internett]. Available: https://automationpanda.com/2020/07/07/arrange-act-assert-a-pattern-for-writing-good-tests/.
*   N. Muguda, «Responsibility-Driven Design: Designing with Stories and Roles,» [Internett]. Available: https://naveenkumarmuguda.medium.com/responsibility-driven-design-designing-with-stories-and-roles-acc2e903c69f.
*   C. S. Horstmann, Core Java Volume I--Fundamentals, Oracle Press, 2018.
*   S. Dizon, «Defensive Coding Approach,» [Internett]. Available: https://medium.com/@sophiadizon/defensive-coding-approach-43bf3f3c007d. [Funnet 29 11 2025].