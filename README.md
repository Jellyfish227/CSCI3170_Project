# CSCI3170 @CUHK (Fall 2024) Final Project - Sales System


## Disclaimer

This repository contains academic work and is published for record and reference purposes only. Do not copy or reuse the code as it may constitute academic misconduct. The code is specific to our course project and should be used solely for learning and understanding the concepts. For your own good, please do your project with your own understanding and knowledge. I am not responsible for any academic misconduct caused by the code in this repository.

All project specifications and related course materials are the intellectual property of The Chinese University of Hong Kong. I do not own or claim any copyright over these materials. They are provided for reference only.

--------

## Project Overview

The project is to implement a comprehensive sales management application for a computer parts store. This project involves developing a Java command-line application that utilizes the Java JDBC API for interactions with an Oracle Database. The system will manage transactions, computer parts, and salespersons' information while supporting interactive user queries. The main job of this project is to create an application to bridge the user and database without needing the user to design the query themselves. 

--------

## Group Information

**Group Name**: `SQL=SoonQuitLife`

**Group Number**: 4

### Group Members

- **Yu Ching Hei**  
  Student ID: 1155193237  
  Email: <chyu2@cse.cuhk.edu.hk>

- **Tam Yiu Hei**  
  Student ID: 1155223226  
  Email: <1155223226@link.cuhk.edu.hk>

- **Leung Chung Wang**  
  Student ID: 1155194650  
  Email: <1155194650@link.cuhk.edu.hk>

--------

## Project Structure

### File Tree

```plaintext
    CSCI3170_Introduction_to_DBS
    │
    ├── .mvn/       <-----maven framework environment
    │   └── wrapper/
    │       └── maven-wrapper.properties
    │
    ├── docs/       <-----specifications
    │   ├── project_spec.pdf
    │   └── suggested-ER.pdf
    │
    ├── lib/        <-----directory for dependencies
    │   └── ojdbc10-19.21.0.0.jar   <-----Will be overwritten when recompile
    │
    ├── sample_data/
    │   ├── category.txt
    │   ├── manufacturer.txt
    │   ├── part.txt
    │   ├── salesperson.txt
    │   └── transaction.txt
    │
    │── src/        <-----source code directory
    │   └── main/
    │       └─── java/
    │         ├── Administrator.java
    │         ├── CategoryTable.java
    │         ├── Connectable.java
    │         ├── Console.java
    │         ├── Main.java
    │         ├── Manager.java
    │         ├── ManufacturerTable.java
    │         ├── OracalDBConnector.java
    │         ├── PartTable.java
    │         ├── Salesperson.java
    │         ├── SalespersonTable.java
    │         ├── Table.java
    │         ├── TransactionTable.java
    │         └── User.java
    .
    . (Other files)
    .
    ├── DemoVid.mp4 <-----demo video
    │
    ├── mvnw        <-----portable maven for Unix
    │
    └── mvnw.cmd    <-----portable maven for Windows
```

### Documentation

- [Project Specification](docs/project_spec.pdf)
- [Suggested ER Diagram](docs/suggested-ER.pdf)

### Source Code

- [Source Code Directory](src/main/java)
  - [Main](src/main/java/Main.java)
  - [Console](src/main/java/Console.java)
  - [Connectable](src/main/java/Connectable.java)
    - [OracalDBConnector](src/main/java/OracalDBConnector.java)
  - [User](src/main/java/User.java)
    - [Administrator](src/main/java/Administrator.java)
    - [Salesperson](src/main/java/Salesperson.java)
    - [Manager](src/main/java/Manager.java)
  - [Table](src/main/java/Table.java)
    - [CategoryTable](src/main/java/CategoryTable.java)
    - [ManufacturerTable](src/main/java/ManufacturerTable.java)
    - [PartTable](src/main/java/PartTable.java)
    - [SalespersonTable](src/main/java/SalespersonTable.java)
    - [TransactionTable](src/main/java/TransactionTable.java)

### File Descriptions

- `src/main/java/Main.java`: The main program entry point, responsible for initializing the database connection and displaying the main menu to manage user operations.

- `src/main/java/Console.java`: Provides convenient console input methods for reading various types of user input.

- `src/main/java/Connectable.java`: Defines an interface for database connections. It decouples the main program from the specific implementation of database connections, and in case of future changes, it is easy to implement a new database connection implementation without rewriting the main program logic.

- `src/main/java/OracalDBConnector.java`: Implements the `Connectable` interface, handling the specific implementation for establishing a connection to CUHK CSE Oracle database.

- `src/main/java/User.java`: Defines the abstract class `User`, representing a user and providing an abstract method for executing menus and utilise polymorphism to execute different menus for different users in the main program.

- `src/main/java/Administrator.java`: Inherits from `User` and implements the administrator's operations menu, including creating and deleting tables, loading data, and showing table content.

- `src/main/java/Salesperson.java`: Inherits from `User` and implements the salesperson's operations menu, including searching for parts and selling parts.

- `src/main/java/Manager.java`: Inherits from `User` and implements the manager's operations menu, including querying salespersons and manufacturers' sales data.

- `src/main/java/Table.java`: Defines the abstract class `Table`, providing basic common methods for data table operations such as creating, querying, deleting, and loading tables, utilising polymorphism to help simplify the Administrator's code by reducing code duplication. This class and its subclasses are designed to be used as a query generator that abstract the query logic from the main program.

- `src/main/java/CategoryTable.java`: Inherits from `Table` and implements table-specific operations for the category table, including table creation and loading category data.

- `src/main/java/ManufacturerTable.java`: Inherits from `Table` and implements table-specific operations for the manufacturer table, including table creation and loading manufacturer data. Other than inheriting the generic `queryTable()` method, it also implements `queryManufacturerSales()` method that specifically query the manufacturer sales data.

- `src/main/java/PartTable.java`: Inherits from `Table` and implements table-specific operations for the part table, including table creation and loading part data. Other than inheriting the generic `queryTable()` method, it also implements `queryPartTable()`, `queryPopularParts()`, and `queryPartSales()` methods that specifically query the part by specific criteria, the popular parts data and part sales data.

- `src/main/java/SalespersonTable.java`: Inherits from `Table` and implements table-specific operations for the salesperson table, including table creation and loading salesperson data. Other than inheriting the generic `queryTable()` method, it also implements `querySalespersonByExp()` and `querySalespersonTransaction()` method that specifically query the salesperson by experience and transaction data.

- `src/main/java/TransactionTable.java`: Inherits from `Table` and implements table-specific operations for the transaction table, including table creation and loading transaction data. It also includes table-specific `recordTransaction()` method that record the transaction data.

--------

## Acknowledgments

- **Instructor**: Dr. YU, Michael Ruisi
- **Teaching Assistant**:
  - DING Wenlong
  - GAO Jialin
  - LIU Yuqi
  - LU Haodong
  - ZHANG Yiyi

--------

## Methods of Compilation and Execution

### Prerequisites and Dependencies

- **Project Directory**: If you are seeing this in the GitHub repository, you are suggested to clone the repository to your local machine and navigate to the project directory to run the project.

- **Running environment**: Since our project is a command-line application, you can run it on any operating system that supports Java.
  - For Unix / MacOS, you are suggested to use the `bash` or `zsh` to run the project.
  - For Windows, you are suggested to use the `powershell` or `cmd` to run the project.

- **Java Environment**: Please ensure you have the Java Development Kit (JDK) or Java Runtime Environment (JRE) of version 17 or newer installed on your machine and added to the system environment variables. You can download it from the [Oracle website](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html). 
  - To check if you have the Java environment, you can use the following command:
    ```sh
    java -version
    ```

- **Maven**: To ensure the portability of the project, we use [Maven](https://maven.apache.org/) to manage dependencies and compile the project so we do not need to rely on platform-dependent batch files or scripts to compile the project and we don't need to manually manage the dependencies. For the ease of use, we also provide a portable Maven wrapper for both Unix and Windows.
  - To check if the portable Maven wrapper is working, you can use the following command:
    For Unix / MacOS:
    ```sh
    ./mvnw -version
    ```
    For Windows:
    ```sh
    ./mvnw.cmd -version
    ```

- **Network Connection**: Please ensure you have connected to the CUHK CSE VPN before running the project.

### Compile and Run

1. Navigate to the project directory

2. To just simply (fresh-)build and run the project, you can use the following command:

    For Unix / MacOS:

    ```sh
    ./mvnw package && java -jar ./Project-1.0-RELEASE.jar
    ```

    For Windows:

    ```sh
    ./mvnw.cmd package && java -jar Project-1.0-RELEASE.jar
    ```

#### If you want to do the steps separately, you can use the following commands:
- To clean the project built artifacts, you can use the following command:

  For Unix / MacOS:

  ```sh
  ./mvnw clean
  ```

  For Windows:

  ```sh
  ./mvnw.cmd clean
  ```

- To just build the project into a jar file `Project-1.0-RELEASE.jar`, you can use the following command:

  For Unix / MacOS:

  ```sh
  ./mvnw package
  ```

  For Windows:

  ```sh
  ./mvnw.cmd package
  ```
  PS: Maven actually cleans the project built artifacts when building the project, so you don't need to explicitly clean the project built artifacts before building the project.

- To run the project, you can use the following command: (Only after building the project)

  For All OS:

  ```sh
  java -jar ./Project-1.0-RELEASE.jar
  ```
-------

## Project History

Full source code and development history available on [GitHub](https://github.com/Jellyfish227/CSCI3170_Introduction_to_DBS.git).
