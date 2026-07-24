# Project Report
## Charlee Tarr & Edward Gu UPenn CIT5940

## Usage Instructions
### Compilation
This application uses Maven for compilation.

__Step 1:__
Ensure you have Maven installed on your device. From the command line, type ```mvn -v```

This will display information about the Apache Maven versiona nd your Java environment. If this command is unrecognized, download Maven from the [Official Apache Maven Download Page](https://maven.apache.org/download.cgi) before proceeding.

__Step 2:__
Download __CIT594_Group_Project.zip__ and unzip it. Save it to a folder, and ensure that there is a __pom.xml__ file in the folder.

__Step 3:__
Navigate to the application’s root from the command line by typing in
```cd```
Then, click and drag your folder to the terminal window to pull in the file path automatically. Press enter.

__Step 4:__ We will now compile the program. Type the following command into the command line

```mvn clean compile```

You will see a **BUILD SUCCESS** message to indicate that the program has compiled successfully.

### Execution
First ensure that it has been compiled successfully and that you are in the application’s root. Navigate to the application’s root from the command line by typing in
```cd <path_to_root```. Alternatively, on Mac you can click and drag your folder to the terminal window to pull in the file path automatically, or windows copy the path to the root file and paste it in the terminal. Press enter.

The Tech News Application takes up to **two optional** arguments that specify the source of the articles and the output file for the event logging. There are three options to launch the application.

__Option 1__: Use the default article list and logging file
In your command line, enter the following:

```mvn exec:java -Dexec.mainClass="edu.upenn.cit5940.Main"```

This will run the application using the default article list (articles.csv) and the default logging file (tech_news_search.log).

__Option 2__: Specify the article list and use the default logging file
In your command line, enter the following:

```mvn exec:java -Dexec.mainClass="edu.upenn.cit5940.Main" -Dexec.args="data/sample_articles.csv"```

Replace the data/sample_articles.csv with the filepath to a valid .csv or .json file. This will run the application with your specified article list and the default logging file (tech_news_search.log).

__Option 3__: Specify the article list and the logging file
In your command line, enter the following:

```mvn exec:java -Dexec.mainClass="edu.upenn.cit5940.Main" -Dexec.args="data/sample_articles.csv documents/sample_logging.log"```

Replace the data/sample_articles.csv with the filepath to a valid .csv or .json file. Additionally, replace the documents/sample_logging.log with another filepath. This will run the application with your specified article list and output the log to the specified logging file.


## System Design
### System Architecture
Our Tech News Application uses a strict 3-tier architecture where the dependencies flowed exclusively downward. Higher floors make calls to the services of the floor directly below them, and the lower floors remain unaware of the floors above.

__Tier 1:__ Data Management - edu.upenn.cit5940.datamanagement

Our lowest tier handles file operations, data transformation, and storage. For file operations, we have classes such as ParserStrategyFactory to determine which parsing strategy should be used to read the input file. The CSVParserStrategy and JSONParserStrategy then process the files, keeping in mind text normalization and STOPWORD cleaning. The ArticlesParsed and KeywordMap classes use the cleaned Article data to instantiate the key data structures that underlie the functions that the Tech News Application supports.

__Tier 2:__ Application/Logic  - edu.upenn.cit5940.processor

Our middle tier handles the core computations and operations that are called by the UI layer. These functions include the various commands available in the Interactive and Command modes of the application. Information is passed down from the UI tier, and this layer uses that information to search the data structures of the Data Management tier to return results back to the UI tier. This tier contains no references to Scanner objects, parsing logic, or terminal output printing, so it is blind to the layer above it (and does not care about the inner workings of the tier below it).

__Tier 3:__ Presentation/User Interface - edu.upenn.cit5940.ui

Our top layer handles the user interface functions and features. It manages the main application loop through the AppState interface (InterfaceModeState, CommandModeState, HelpModeState, MainMenuState). We also use a command design pattern through the Command interface to handle individual commands in the Interface and Command modes. Each command has its own class where the input is handled and validated, a call is made to the middle processor layer, and then the output is formatted for display.

### Data Strucutres & Refactoring

### Design Patterns

## Challenges Faced