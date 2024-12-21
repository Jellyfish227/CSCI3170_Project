# CSCI3170 @CUHK (Fall 2024) Final Project - Sales System

## Disclaimer
This repository contains academic work and is published for record and reference purposes only. Do not copy or reuse the code as it may constitute academic misconduct. The code is specific to our course project and should be used solely for learning and understanding the concepts. For your own good, please do your project with your own understanding and knowledge. I am not responsible for any academic misconduct caused by the code in this repository.

All project specifications and related course materials are the intellectual property of The Chinese University of Hong Kong. I do not own or claim any copyright over these materials. They are provided for reference only.

## Project Overview
The project is designed to implement a comprehensive sales management application for a computer parts store. This project requires the development of a Java command-line application that utilizes the Java JDBC API for database interactions with an Oracle Database (version 19c). The system will handle transactions, manage computer parts, and maintain salespersons' information while supporting interactive user queries. The project involves designing a relational database schema based on a provided ER diagram, loading data from text files, and implementing various functionalities for administrators, salespersons, and managers.

## Group Information
**Group Number**: 4

## Project Structure

### File Tree
    
    CSCI3170_Introduction_to_DBS
    │
    ├── .mvn/
    │   └── wrapper/
    │       └── maven-wrapper.properties
    │
    ├── .idea/
    │   ├── dictionaries/
    │   │   └── jellyfish.xml
    │   ├── inspectionProfiles/
    |   |   └── Project_Default.xml
    │   ├── .gitignore
    │   ├── compiler.xml
    │   ├── dataSources.xml
    │   ├── discord.xml
    │   ├── jarRepositories.xml
    │   ├── material_theme_project_new.xml
    │   ├── misc.xml
    │   ├── modules.xml
    │   ├── sqldialects.xml
    │   └── vcs.xml
    │
    ├── docs/
    │   ├── project_spec.pdf
    │   └── suggested-ER.pdf
    │
    ├── lib/
    │
    ├── out/
    │   └── production/
    │       └── CSCI3170_Introduction_to_DBS/
    │           ├── category.txt
    │           ├── manufacturer.txt
    │           ├── part.txt
    │           ├── salesperson.txt
    │           ├── transaction.txt
    │           └── run.bat
    │
    ├── sample_data/
    │   ├── category.txt
    │   ├── manufacturer.txt
    │   ├── part.txt
    │   ├── salesperson.txt
    │   └── transaction.txt
    │
    ├── src/
        ├── main/
            └─── java/
              ├── Administrator.java
              ├── CategoryTable.java
              ├── Connectable.java
              ├── Console.java
              ├── Main.java
              ├── Manager.java
              ├── ManufacturerTable.java
              ├── OracalDBConnector.java
              ├── PartTable.java
              ├── Salesperson.java
              ├── SalespersonTable.java
              ├── Table.java
              ├── TransactionTable.java
              └── User.java

    
### Documentation
- [Project Specification](docs/project_spec.pdf)
- [Suggested ER Diagram](docs/suggested-ER.pdf)

### Source Code
- [Administrator](src/main/java/Administrator.java)
- [CategoryTable](src/main/java/CategoryTable.java)
- [Connectable](src/main/java/Connectable.java)
- [Console](src/main/java/Console.java)
- [Main](src/main/java/Main.java)
- [Manager](src/main/java/Manager.java)
- [ManufacturerTable](src/main/java/ManufacturerTable.java)
- [OracalDBConnector](src/main/java/OracalDBConnector.java)
- [PartTable](src/main/java/PartTable.java)
- [Salesperson](src/main/java/Salesperson.java)
- [SalespersonTable](src/main/java/SalespersonTable.java)
- [Table](src/main/java/Table.java)
- [TransactionTable](src/main/java/TransactionTable.java)
- [User](src/main/java/User.java)

### File Descriptions
- **src/main/java/Table.java**  
  Defines the abstract class `Table`, providing basic methods for data table operations such as creating, querying, deleting, and loading tables.

- **src/main/java/TransactionTable.java**  
  Inherits from `Table` and implements specific operations for the transaction table, including table creation and loading transaction data.

- **src/main/java/User.java**  
  Defines the abstract class `User`, representing a user and providing an abstract method for executing menus.

- **src/main/java/Administrator.java**  
  Inherits from `User` and implements the administrator's operations menu, including creating and deleting tables and loading data.

- **src/main/java/CategoryTable.java**  
  Inherits from `Table` and implements specific operations for the category table, including table creation and loading category data.

- **src/main/java/Connectable.java**  
  Defines an interface for setting and retrieving database connections.

- **src/main/java/Console.java**  
  Provides convenient console input methods for reading various types of user input.

- **src/main/java/Main.java**  
  The main program entry point, responsible for initializing the database connection and displaying the main menu to manage user operations.

- **src/main/java/Manager.java**  
  Inherits from `User` and implements the manager's operations menu, including querying salespersons and manufacturers' sales data.

- **src/main/java/ManufacturerTable.java**  
  Inherits from `Table` and implements specific operations for the manufacturer table, including table creation and loading manufacturer data.

- **src/main/java/OracalDBConnector.java**  
  Implements the `Connectable` interface, handling the specific implementation for establishing a connection to an Oracle database.

- **src/main/java/PartTable.java**  
  Inherits from `Table` and implements specific operations for the part table, including table creation and loading part data.

- **src/main/java/Salesperson.java**  
  Inherits from `User` and implements the salesperson's operations menu, including searching for parts and selling parts.

- **src/main/java/SalespersonTable.java**  
  Inherits from `Table` and implements specific operations for the salesperson table, including table creation and loading salesperson data.

## Group Members
- **Tam Yiu Hei**  
  Student ID: 1155223226  
  Email: 1155223226@link.cuhk.edu.hk

- **Yu Ching Hei**  
  Student ID: 1155193237  
  Email: chyu2@cse.cuhk.edu.hk

- **Leung Chung Wang**  
  Student ID: 1155194650  
  Email: 1155194650@link.cuhk.edu.hk

## Acknowledgments
- **Instructor**: Dr. YU, Michael Ruisi
- **TA**:
  - DING Wenlong
  - GAO Jialin
  - LIU Yuqi
  - LU Haodong
  - ZHANG Yiyi 

## Methods of Compilation and Execution

1. **Prerequisites**: Ensure you have the Java Development Kit (JDK) not earlier than java 17 installed on your machine. You can download it from the [Oracle website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or use OpenJDK.
