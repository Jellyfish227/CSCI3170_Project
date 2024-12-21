# Sales System

## CSCI3170 @CUHK (Fall 2024) Instructed by [Dr. YU Michael Ruisi]

## Disclaimer

This repository contains academic work and is published for record and reference purposes only. Do not copy or reuse the code as it may constitute academic misconduct. The code is specific to our course project and should be used solely for learning and understanding the concepts. For your own good, please do your project with your own understanding and knowledge. I am not responsible for any academic misconduct caused by the code in this repository.

All project specifications and related course materials are the intellectual property of The Chinese University of Hong Kong. I do not own or claim any copyright over these materials. They are provided for reference only.

### Group Information
**Group Number**: 4

## Project Structure

### File Tree
    
    CSCI3170_Introduction_to_DBS
    │
    ├── .idea
    │   ├── dictionaries
    │   │   └── jellyfish.xml
    │   ├── inspectionProfiles
    │   │   └── Project_Default.xml
    │   ├── .gitignore
    │   ├── dataSources.xml
    │   ├── discord.xml
    │   ├── material_theme_project_new.xml
    │   ├── misc.xml
    │   ├── modules.xml
    │   ├── sqldialects.xml
    │   └── vcs.xml
    │
    ├── docs
    │   ├── project_spec.pdf
    │   └── suggested-ER.pdf
    │
    ├── lib
    │   └── ojdbc8.jar
    │
    ├── out
    │   └── production
    │       └── CSCI3170_Introduction_to_DBS
    │           ├── category.txt
    │           ├── manufacturer.txt
    │           ├── part.txt
    │           ├── salesperson.txt
    │           ├── transaction.txt
    │           ├── run.bat
    │           └── sample_data
    │               ├── category.txt
    │               ├── manufacturer.txt
    │               ├── part.txt
    │               ├── salesperson.txt
    │               └── transaction.txt
    │
    ├── src
    │   ├── sample_data
    │   │   ├── category.txt
    │   │   ├── manufacturer.txt
    │   │   ├── part.txt
    │   │   ├── salesperson.txt
    │   │   └── transaction.txt
    │   ├── cuhk.com.SQL_SoonQuitLife.csci3170.cse.Administrator.java
    │   ├── cuhk.com.SQL_SoonQuitLife.csci3170.cse.CategoryTable.java
    │   ├── cuhk.com.SQL_SoonQuitLife.csci3170.cse.Connectable.java
    │   ├── cuhk.com.SQL_SoonQuitLife.csci3170.cse.Console.java
    │   ├── cuhk.com.SQL_SoonQuitLife.csci3170.cse.Main.java
    │   ├── cuhk.com.SQL_SoonQuitLife.csci3170.cse.Manager.java
    │   ├── cuhk.com.SQL_SoonQuitLife.csci3170.cse.ManufacturerTable.java
    │   ├── cuhk.com.SQL_SoonQuitLife.csci3170.cse.OracalDBConnector.java
    │   ├── cuhk.com.SQL_SoonQuitLife.csci3170.cse.PartTable.java
    │   ├── run.bat
    │   ├── cuhk.com.SQL_SoonQuitLife.csci3170.cse.Salesperson.java
    │   ├── cuhk.com.SQL_SoonQuitLife.csci3170.cse.SalespersonTable.java
    │   ├── cuhk.com.SQL_SoonQuitLife.csci3170.cse.TransactionTable.java
    │   └── cuhk.com.SQL_SoonQuitLife.csci3170.cse.User.java
    │
    ├── CSCI3170_Introduction_to_DBS.iml
    │
    ├── LICENSE
    │
    └── README.md
    
### Documentation
- [Project Specification](docs/project_spec.pdf)
- [Suggested ER Diagram](docs/suggested-ER.pdf)

### Source Code
- [cuhk.com.SQL_SoonQuitLife.csci3170.cse.Administrator](src/Administrator.java)
- [cuhk.com.SQL_SoonQuitLife.csci3170.cse.CategoryTable](src/CategoryTable.java)
- [cuhk.com.SQL_SoonQuitLife.csci3170.cse.Connectable](src/Connectable.java)
- [cuhk.com.SQL_SoonQuitLife.csci3170.cse.Console](src/Console.java)
- [cuhk.com.SQL_SoonQuitLife.csci3170.cse.Main](src/Main.java)
- [cuhk.com.SQL_SoonQuitLife.csci3170.cse.Manager](src/Manager.java)
- [cuhk.com.SQL_SoonQuitLife.csci3170.cse.ManufacturerTable](src/ManufacturerTable.java)
- [cuhk.com.SQL_SoonQuitLife.csci3170.cse.OracalDBConnector](src/OracalDBConnector.java)
- [cuhk.com.SQL_SoonQuitLife.csci3170.cse.PartTable](src/PartTable.java)
- [cuhk.com.SQL_SoonQuitLife.csci3170.cse.Salesperson](src/Salesperson.java)
- [cuhk.com.SQL_SoonQuitLife.csci3170.cse.SalespersonTable](src/SalespersonTable.java)
- [cuhk.com.SQL_SoonQuitLife.csci3170.cse.Table](src/Table.java)
- [cuhk.com.SQL_SoonQuitLife.csci3170.cse.TransactionTable](src/TransactionTable.java)
- [cuhk.com.SQL_SoonQuitLife.csci3170.cse.User](src/User.java)
- [run.bat](src/run.bat)

### File Descriptions
- **src/cuhk.com.SQL_SoonQuitLife.csci3170.cse.Table.java**  
  Defines the abstract class `cuhk.com.SQL_SoonQuitLife.csci3170.cse.Table`, providing basic methods for data table operations such as creating, querying, deleting, and loading tables.

- **src/cuhk.com.SQL_SoonQuitLife.csci3170.cse.TransactionTable.java**  
  Inherits from `cuhk.com.SQL_SoonQuitLife.csci3170.cse.Table` and implements specific operations for the transaction table, including table creation and loading transaction data.

- **src/cuhk.com.SQL_SoonQuitLife.csci3170.cse.User.java**  
  Defines the abstract class `cuhk.com.SQL_SoonQuitLife.csci3170.cse.User`, representing a user and providing an abstract method for executing menus.

- **src/cuhk.com.SQL_SoonQuitLife.csci3170.cse.Administrator.java**  
  Inherits from `cuhk.com.SQL_SoonQuitLife.csci3170.cse.User` and implements the administrator's operations menu, including creating and deleting tables and loading data.

- **src/cuhk.com.SQL_SoonQuitLife.csci3170.cse.CategoryTable.java**  
  Inherits from `cuhk.com.SQL_SoonQuitLife.csci3170.cse.Table` and implements specific operations for the category table, including table creation and loading category data.

- **src/cuhk.com.SQL_SoonQuitLife.csci3170.cse.Connectable.java**  
  Defines an interface `cuhk.com.SQL_SoonQuitLife.csci3170.cse.Connectable` for setting and retrieving database connections.

- **src/cuhk.com.SQL_SoonQuitLife.csci3170.cse.Console.java**  
  Provides convenient console input methods for reading various types of user input.

- **src/cuhk.com.SQL_SoonQuitLife.csci3170.cse.Main.java**  
  The main program entry point, responsible for initializing the database connection and displaying the main menu to manage user operations.

- **src/cuhk.com.SQL_SoonQuitLife.csci3170.cse.Manager.java**  
  Inherits from `cuhk.com.SQL_SoonQuitLife.csci3170.cse.User` and implements the manager's operations menu, including querying salespersons and manufacturers' sales data.

- **src/cuhk.com.SQL_SoonQuitLife.csci3170.cse.ManufacturerTable.java**  
  Inherits from `cuhk.com.SQL_SoonQuitLife.csci3170.cse.Table` and implements specific operations for the manufacturer table, including table creation and loading manufacturer data.

- **src/cuhk.com.SQL_SoonQuitLife.csci3170.cse.OracalDBConnector.java**  
  Implements the `cuhk.com.SQL_SoonQuitLife.csci3170.cse.Connectable` interface, handling the specific implementation for establishing a connection to an Oracle database.

- **src/cuhk.com.SQL_SoonQuitLife.csci3170.cse.PartTable.java**  
  Inherits from `cuhk.com.SQL_SoonQuitLife.csci3170.cse.Table` and implements specific operations for the part table, including table creation and loading part data.

- **src/cuhk.com.SQL_SoonQuitLife.csci3170.cse.Salesperson.java**  
  Inherits from `cuhk.com.SQL_SoonQuitLife.csci3170.cse.User` and implements the salesperson's operations menu, including searching for parts and selling parts.

- **src/cuhk.com.SQL_SoonQuitLife.csci3170.cse.SalespersonTable.java**  
  Inherits from `cuhk.com.SQL_SoonQuitLife.csci3170.cse.Table` and implements specific operations for the salesperson table, including table creation and loading salesperson data.

- **src/run.bat**  
  A batch file that automates the compilation and execution of the Java application. It compiles all Java source files in the `src` directory and then runs the `cuhk.com.SQL_SoonQuitLife.csci3170.cse.Main` class, providing a simple way to start the application without manually entering commands.

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

To compile and run the Sales System project, you can use the provided `run.bat` batch file, which automates the compilation and execution process. Follow these steps:

1. **Prerequisites**: Ensure you have the Java Development Kit (JDK) installed on your machine. You can download it from the [Oracle website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or use OpenJDK.

2. **Clone the Repository**: Clone this repository to your local machine using:
   ```bash
   git clone https://github.com/Jellyfish227/CSCI3170_Introduction_to_DBS.git
   cd CSCI3170_Introduction_to_DBS
