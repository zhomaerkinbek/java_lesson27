import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println();
        FileService movies = readMovies();
        System.out.println("-----------------------------Не отсортированная коллекция---------------------------------");

        printMovies(movies);
        sortByDirector(movies);
        sortByName(movies);
        sortByYear(movies);
        searchMovieByDirector(movies);
        searchMovieByName(movies);
    }

    public static void printMovies(FileService movies){
        String fmt = "| %-41s  |  %-11s  |  %-9s  |  %-17s  |  %-16s";
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(String.format(fmt, "Название", "Год выпуска", "Описание", "Режиссер", "Актерский состав      |  Роль"));
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Movie movie: movies.getMovies()) {
            System.out.print(movie);
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");

        }
    }

    public static void searchMovieByDirector(FileService movies){
        Scanner sc = new Scanner(System.in);
        Map<Director, Movie> directorMovieMap =
                movies.getMovies().stream().collect(Collectors.toMap(Movie::getDirectors, item -> item));
        System.out.printf("Введите частичное или полное ФИО режиссера: ");
        String text1 = sc.nextLine();
        boolean check = false;
        for(Map.Entry<Director, Movie> kv: directorMovieMap.entrySet()){
            if(kv.getKey().getFullName().toLowerCase().contains(text1.toLowerCase(Locale.ROOT))){
                System.out.println("Фильмы - " + kv.getValue().getName());
                check = true;
            }
        }
        if(!check) System.out.println("Фильмов с таким режиссером нету!");
    }

    public static void searchMovieByName(FileService movies){
        Scanner sc = new Scanner(System.in);
        System.out.printf("Введите частичное или полное название фильма: ");
        String text = sc.nextLine();
        boolean check = false;
        for(var movie : movies.getMovies()){
            if(movie.getName().toLowerCase(Locale.ROOT).strip().contains(text.toLowerCase(Locale.ROOT))){
                System.out.println("Ваш фильм - " + movie.getName());
                check = true;
            }
        }
        if(!check) System.out.println("Фильма с таким названием нету!");

    }

    public static void sortByName(FileService movies){
        System.out.println("-----------------------------Отсортирован по названию по возрастанию---------------------------------");
        Comparator sortName = Comparator.comparing(Movie::getName);
        movies.getMovies().sort(sortName);
        printMovies(movies);
        System.out.println("-----------------------------Отсортирован по названию по убыванию---------------------------------");
        movies.getMovies().sort(sortName.reversed());
        printMovies(movies);
    }

    public static void sortByDirector(FileService movies){
        System.out.println("-----------------------------Отсортирован по фамилии директороа по возрастанию---------------------------------");
        Collections.sort(movies.getMovies());
        printMovies(movies);
        System.out.println("-----------------------------Отсортирован по фамилии директороа по убыванию---------------------------------");
        Collections.sort(movies.getMovies(), Comparator.reverseOrder());
        printMovies(movies);
    }

    public static void sortByYear(FileService movies){
        System.out.println("-----------------------------Отсортирован по году выпуска по возрастанию---------------------------------");
        Comparator sortYear = Comparator.comparing(Movie::getYear);
        movies.getMovies().sort(sortYear);
        printMovies(movies);
        System.out.println("-----------------------------Отсортирован по году выпуска по убыванию---------------------------------");
        movies.getMovies().sort(sortYear.reversed());
        printMovies(movies);
    }


    public static FileService readMovies(){
        Gson gson = new Gson();
        String json = getJson("./movies.json");
        return gson.fromJson(json, FileService.class);
    }

    static String getJson(String fileName){
        String json = "";
        Path path = Paths.get(fileName);
        try {
            json = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }


}
