import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a tital search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.println("Enter a cast: ");
        String searchTerm = scanner.nextLine();
        ArrayList<String> results = new ArrayList<String>();
        for (int i = 0; i < movies.size(); i++) {
            String cast = movies.get(i).getCast();
            if (cast.indexOf(searchTerm) != -1) {
                String temp = movies.get(i).getCast();
                while (temp.indexOf("|") != -1) { //need to parse here
                    String name = temp.substring(0, temp.indexOf("|"));
                    if (name.indexOf(searchTerm) != -1) {
                        results.add(temp.substring(0, temp.indexOf("|")));
                    }
                    temp = temp.substring(temp.indexOf("|") + 1);
                }
            }
        }
        for (int i = 1; i < results.size(); i++) {
            int j = i;
            while (j > 0 && results.get(j - 1).compareTo(results.get(j)) > 0) {
                String swap = results.set(j - 1, results.get(j));
                results.set(j, swap);
                j--;
            }
        }
        for (int i=0; i<results.size()-1;i++){
            if (results.get(i+1).equals(results.get(i))){
                results.remove(i);
                i--;
            }
        }
        for (int i=0;i<results.size();i++){
            String cast = results.get(i);
            int choiceNum=i+1;
            System.out.println(""+choiceNum+". "+cast);
        }
        System.out.println("Which cast would you like to learn more about?");
        System.out.println("Enter number: ");
        int choice = scanner.nextInt();
        String selectedCast = results.get(choice-1);
        ArrayList<Movie>Movie1 = new ArrayList<Movie>();
        for (int i=0; i<movies.size();i++){
            String casts = movies.get(i).getCast();
            if (casts.indexOf(selectedCast)!=-1){
                Movie1.add(movies.get(i));
            }
        }
        sortResults(Movie1);
        for (int i=0;i<Movie1.size();i++){
            String title = Movie1.get(i).getTitle();
            int choiceNum = i+1;
            System.out.println(""+choiceNum+". "+title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.println("Enter number: ");
        int choices = scanner.nextInt();
        Movie selectedMovie = Movie1.get(choices-1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }


    private void searchKeywords()
    {
        System.out.println("Enter a keywords:");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();
        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int i =0; i<movies.size();i++) {
            String keyword = movies.get(i).getKeywords();
            keyword = keyword.toLowerCase();
            if (keyword.indexOf(searchTerm)!=-1){
                results.add(movies.get(i));
            }
        }
        sortResults(results);
        for (int i=0;i<results.size();i++){
            String title = results.get(i).getTitle();
            int choiceNum = i+1;
            System.out.println(""+choiceNum+". "+title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.println("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = results.get(choice-1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        ArrayList<String>results= new ArrayList<String>();
        for (int i=0;i<movies.size();i++){
            String temp = movies.get(i).getGenres();
            while (temp.indexOf("|")!=-1){
                String genre = temp.substring(0,temp.indexOf("|"));
                results.add(genre);
                temp= temp.substring(temp.indexOf("|")+1);
            }
        }
        for (int i = 1; i < results.size(); i++) {
            int j = i;
            while (j > 0 && results.get(j - 1).compareTo(results.get(j)) > 0) {
                String swap = results.set(j - 1, results.get(j));
                results.set(j, swap);
                j--;
            }
        }
        for (int i=0; i<results.size()-1;i++){
            if (results.get(i+1).equals(results.get(i))){
                results.remove(i);
                i--;
            }
        }
        for (int i=0;i<results.size();i++){
            String genre = results.get(i);
            int choiceNum=i+1;
            System.out.println(""+choiceNum+". "+genre);
        }
        System.out.println("Which genre would you like to learn more about?");
        System.out.println("Enter number: ");
        int choice = scanner.nextInt();
        String selectedGenre = results.get(choice-1);
        ArrayList<Movie>Movie1 = new ArrayList<Movie>();
        for (int i=0; i<movies.size();i++){
            String genre = movies.get(i).getGenres();
            if (genre.indexOf(selectedGenre)!=-1){
                Movie1.add(movies.get(i));
            }
        }
        sortResults(Movie1);
        for (int i=0;i<Movie1.size();i++){
            String title = Movie1.get(i).getTitle();
            int choiceNum = i+1;
            System.out.println(""+choiceNum+". "+title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.println("Enter number: ");
        int choices = scanner.nextInt();
        Movie selectedMovie = Movie1.get(choices-1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }


    private void listHighestRated()
    {
        ArrayList<Double>rating = new ArrayList<Double>();
        Movie[] rating50 = new Movie[50];
        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int i = 0; i< movies.size();i++){
            rating.add(movies.get(i).getUserRating());
            results.add(movies.get(i));
        }
        for (int i = 1; i < results.size(); i++) {
            int j = i;
            while (j > 0 && results.get(j - 1).getUserRating() < (results.get(j).getUserRating())) {
                Movie swap = results.set(j - 1, results.get(j));
                results.set(j, swap);
                j--;
            }
        }
        for (int i = 0; i< rating50.length; i++){
            rating50[i]=results.get(i);
        }
        for (int i=0;i< rating50.length;i++){
            String title = rating50[i].getTitle();
            Double stars = rating50[i].getUserRating();
            int choiceNum = i+1;
            System.out.println(""+choiceNum+". "+title+" "+stars);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.println("Enter number: ");
        int choices = scanner.nextInt();
        Movie selectedMovie = results.get(choices-1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        ArrayList<Integer>money = new ArrayList<Integer>();
        Movie[] money50 = new Movie[50];
        ArrayList<Movie> results = new ArrayList<Movie>();
        for (int i = 0; i< movies.size();i++){
            money.add(movies.get(i).getRevenue());
            results.add(movies.get(i));
        }
        for (int i = 1; i < results.size(); i++) {
            int j = i;
            while (j > 0 && results.get(j - 1).getRevenue() < (results.get(j).getRevenue())) {
                Movie swap = results.set(j - 1, results.get(j));
                results.set(j, swap);
                j--;
            }
        }
        for (int i = 0; i< money50.length; i++){
            money50[i]=results.get(i);
        }
        for (int i=0;i< money50.length;i++){
            String title = money50[i].getTitle();
            int rev = money50[i].getRevenue();
            int choiceNum = i+1;
            System.out.println(""+choiceNum+". "+title+" "+rev);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.println("Enter number: ");
        int choices = scanner.nextInt();
        Movie selectedMovie = results.get(choices-1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}