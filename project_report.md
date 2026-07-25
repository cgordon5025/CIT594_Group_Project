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
```cd <path_to_root>```. Alternatively, after typing ```cd``` on Mac you can click and drag your folder to the terminal window to pull in the file path automatically, or windows copy the path to the root file and paste it in the terminal. Press enter.

__Step 4:__ We will now compile the program. Type the following command into the command line

```mvn clean compile```

You will see a **BUILD SUCCESS** message to indicate that the program has compiled successfully.

### Execution
First ensure that it has been compiled successfully and that you are in the application’s root. Navigate to the application’s root from the command line by typing in
```cd <path_to_root>```. Alternatively, after typing ```cd``` on Mac you can click and drag your folder to the terminal window to pull in the file path automatically, or windows copy the path to the root file and paste it in the terminal. Press enter.

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
__Refactor of InvertedIndex to HashMap:__ As our ArticleParser maps the Articles to a HashMap, it would take both much more time and memory to create an InvertedIndex tree from the title, in order to perform both the Search and Autocomplete functions, we wanted to leverage what already existed.
Additionally, refactoring the InvertedIndex from a Tree to a HashMap, allows us to use .contains() instead of traversing the tree, which brings the average time complexity from O(logT) to O(1), and worst case remains the same.
Original implementation of Searching an invertedIndex for a word
```
for (String word : normalizedText) {
           if (STOP_WORDS.contains(word) || word.isEmpty()) {
               continue;
           }
           var existingNode = searchTreeForNode(root, word);
           if (existingNode != null&& existingNode.keyWord.equals(word)) {
                if (lookingForIntersection) {
                   intersectingNodes.add(existingNode);
              }
                   _docIds.addAll(existingNode.documentIDs);
           }else{
               _docIds = new HashSet<>();
               break; //there is a non-existent word, definitely there will be no match
           }
       }
```
New Implementation
```
if (KeywordMap.STOP_WORDS.contains(word) || word.isEmpty()) {
   continue;
}
if (KeywordMap.allMappedKeywords.containsKey(word)) {
   intersectingArticles.put(word, KeywordMap.allMappedKeywords.get(word));
   articleIds.addAll(KeywordMap.allMappedKeywords.get(word));
}
```

__Data Structure Decisions and Refactoring Continued:__ Our functions for Search and AutoComplete (recommendations)  we refactored from Assignments 6 and 8, respectively. Additionally, a shared tool, normalizeText, from Assignment 6, was adapted to be used across the application to provide consistent normalization. During the refactor process, the original InvertedIndex tree was changed to match the ArticleMap (HashMap) structure. In doing so, we remove the need to traverse the tree, in any order, and utilize HashMap .contains().

Tries were utilized as they are the most efficient when building a tree of characters that form words.

For our trends <topic> <start> <end> function, we refactored from Assignment 8 to use a TreeMap within the KeywordMap class with dates as the keys and a map of keywords to a count of the keyword occurrences within the month as the value. This allows for lightning fast retrieval of information as the TreeMap naturally sorts the dates in ascending order. This also provides a way to organize articles by month. Once the month is identified, accessing the topic’s count for each month is as simple as getting the value associated with the topic as the key.

For the articles <start> <end> function, we used a TreeSet of articles (and implemented a natural ordering to the Article class based on publication date, and then used the Article title as the tiebreaker) that allows a tree-based search of the set of Articles.

### Design Patterns
__Parsing Strategy Design__
We used a Strategy design pattern for the parsing of the articles. This was an appropriate choice because of the need for handling multiple potential data sources. We did not want to handle this complexity through a switch statement or a series of if/else statements. The Strategy design pattern allows us to implement different parsing strategies without modifying the classes that call the parser and to instead handle it through a factory class. This is also sustainable if this application were to grow to support more file types in the future (say, .xml files).

Interface for ArticleParserStrategy to define what each strategy must be able to support. This enforces a similar function for each strategy.
```
public interface ArticleParserStrategy {
   /**
    * Parses the given file into a Map of Articles.
    *
    * @param file The input data file (CSV, JSON, etc.)
    * @throws Exception if parsing fails due to format errors or I/O issues.
    */
   void parse(File file) throws Exception;
}
```

__Commands: Command design__

Our commands used the Command design pattern which treats each request as an object. Importantly, this allows us to decouple the execution of the commands from the program loop. Without this implementation, we would have likely had to use a monolithic CommandModeState class with a large switch block to handle the 9 operations available to the user in Command Mode and Interactive Mode.

The Command design pattern is implemented again with an interface class, Command. This interface class defines that each Command must have an execute() method. The arguments from the user input are passed as arguments into the execute() method.
```
/**
* The Command interface defines any methods that Commands must implement, including:
* -execute(): carries out the main function of the command
*/
public interface Command {
   void execute(String[] args);
}
```
Within the CommandModeState class, we use a HashMap of the different commands to enable fast lookup of the correct object.
```
private final Map<String, Command> commands = new HashMap<>();

commands.put("search", new SearchCommand(processor));
commands.put("autocomplete", new AutocompleteCommand(processor));
commands.put("topics", new TopicsCommand(processor));
commands.put("trends", new TrendsCommand(processor));
commands.put("articles", new ArticlesCommand(processor));
commands.put("article", new ArticleDetailsCommand(processor));
commands.put("stats", new StatsCommand(processor));
commands.put("help", new HelpCommand());
```

Further in the class, we obtain the command keyword from the user’s input and match that to the correct key in the map and return the corresponding Command object.
```
String[] parts = line.split("\\s+");
String cmdKeyword = parts[0].toLowerCase();

if (cmdKeyword.equals("menu")) {
   System.out.println("Returning to Main Menu...\n");
   app.changeState(new MainMenuState());
   return;
}

Command command = commands.get(cmdKeyword);
```

After some further parsing, we pass an array of strings as the argument into the execute() command and simply call the execute() method of the correct Command object type. That Command object’s execute() method makes the call to the Application/Logic layer and later formats the returned data to display to the CLI.

```
command.execute(args);
```

__Logger: Singleton design__

Our logger was created as a Singleton instance, as we had to initiate the path_to_write once, and then every instance of the logger would write to the same location, as it would persist through the run of the program. Additionally, it ensures we have a single FileWriter Open, instead of opening and closing a writer repeatedly
Initiation of Logger
```
Logger logger = Logger.getInstance();
logger.initLogger(logFilePath);
logger.LogInformation("Application Starting", Logger.LogStatus.INFO);
```
Later use in main
```
logger.LogInformation(String.format("Loaded %d articles from %s",ArticlesParsed.parsedArticles.size(),dataFilePath), Logger.LogStatus.INFO);
```
Later use in main menu State
logger.LogInformation("Invalid Menu option provided", Logger.LogStatus.ERROR);

__Logger Singleton class__
```
private FileWriter out;
   public enum LogStatus{
       INFO, ERROR
   }
   private static final Logger logger = new Logger();
   //preventing external init
    private Logger(){  }
   public void initLogger(String filePath){
       try{
           out = new FileWriter(filePath,true);
       }catch(Exception e){
           System.out.println("Error int logger");
       }
   }
   public static Logger getInstance(){
       return logger;
   }
   public void LogInformation(String action, LogStatus logStatus){
       DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
       var time = sdf.format(System.currentTimeMillis());
       try{
           out.write("["+time+"] "+logStatus+" "+action +"\n");
           out.flush();
       }catch(Exception e){
           System.out.println("error");
       }
   }
   public void CloseLogger(){
       try{
       out.close();
       }catch(Exception e){
           System.out.println();
       }
   }
```


## Challenges Faced