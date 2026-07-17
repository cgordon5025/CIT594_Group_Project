package edu.upenn.cit5940.ui;

class HelpCommand implements Command {
    @Override
    public void execute(String[] args) {
        System.out.println("Available Commands:");
        System.out.println("  search <keyword(s)>        - Search articles case-insensitively (AND match).");
        System.out.println("  autocomplete <prefix>      - Suggest up to 10 matching words.");
        System.out.println("  topics <period>            - Top 10 trending words for a month (YYYY-MM).");
        System.out.println("  trends <topic> <st> <end>  - Monthly counts for a topic across period.");
        System.out.println("  articles <start> <end>     - List articles in range (YYYY-MM-DD) chronologically.");
        System.out.println("  article <id>               - Display article details by unique ID.");
        System.out.println("  stats                      - Show total article metrics.");
        System.out.println("  menu                       - Exit back to main dashboard.");
    }
}
