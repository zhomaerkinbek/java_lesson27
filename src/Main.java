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
        searchMovieByActor(movies);
        searchMovieByYear(movies);
        searchMovieAndRoleByActor(movies);
        printAllActors(movies);
    }


    static void printAllActors(FileService movies){
        Map<String, List<String[]>> actor = new HashMap<>();

        for(var movie: movies.getMovies()){
            for(var cast : movie.getCasts()){
                actor.put(cast.getFullName(), getMovieNameAndRoleByActor(cast.getFullName(), movies.getMovies()));
            }
        }
        System.out.println("----------------Список всех актеров----------------");
        System.out.println("---------------------------------------------------");
        for(Map.Entry<String, List<String[]>> kv: actor.entrySet()){
            System.out.println(kv.getKey() + " cнимаeтся в фильмах:");
            for(var movie: kv.getValue()){
                System.out.println("Фильм: " + movie[0] + "\nРоль: " + movie[1]);

            }
            System.out.println("---------------------------------------------------");
        }
    }
    public static List<String> getMovieName(String name, List<Movie> movies){
        List<String> old_movies = new ArrayList<>();
        for(var movie: movies){
            if(movie.getDirectors().getFullName().contains(name)){
                old_movies.add(movie.getName());
            }
        }
        return old_movies;
    }

    public static List<String> getMovieNameByActor(String name, List<Movie> movies){
        List<String> old_movies = new ArrayList<>();
        for(var movie: movies){
            for(var cast : movie.getCasts()){
                if(cast.getFullName().contains(name)){
                    old_movies.add(movie.getName());
                }
            }
        }
        return old_movies;
    }

    public static List<String[]> getMovieNameAndRoleByActor(String name, List<Movie> movies){
        List<String[]> old_movies = new ArrayList<>();
        for(var movie: movies){
            for(var cast : movie.getCasts()){
                if(cast.getFullName().contains(name)){
                    old_movies.add(new String[]{movie.getName(), cast.getRole()});
                }
            }
        }
        return old_movies;
    }

    public static List<String> getMovieNameByYear(Integer year, List<Movie> movies){
        List<String> old_movies = new ArrayList<>();
        for(var movie: movies){
            if(movie.getYear() == year){
                old_movies.add(movie.getName());
            }
        }
        return old_movies;
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
        boolean check = false;
        Map<String, List<String>> director = new HashMap<>();

        for(var movie: movies.getMovies()){
            director.put(movie.getDirectors().getFullName(), getMovieName(movie.getDirectors().getFullName(), movies.getMovies()));
        }
        System.out.print("Введите частичное или полное имя режиссера: ");
        String text = sc.nextLine();
        for(Map.Entry<String, List<String>> kv: director.entrySet()){
            if(kv.getKey().toLowerCase().contains(text.toLowerCase(Locale.ROOT))){
                System.out.println(kv.getKey() + " фильмы:");
                for(var movie: kv.getValue()){
                    System.out.println("Фильм: " + movie);

                }
                check = true;
            }
        }
        if(!check) System.out.println("Фильмов с таким режиссером нету!");
    }

    public static void searchMovieByActor(FileService movies){
        Scanner sc = new Scanner(System.in);
        boolean check = false;
        Map<String, List<String>> actor = new HashMap<>();

        for(var movie: movies.getMovies()){
            for(var cast : movie.getCasts()){
                actor.put(cast.getFullName(), getMovieNameByActor(cast.getFullName(), movies.getMovies()));
            }
        }
        System.out.print("Введите частичное или полное имя актера: ");
        String text = sc.nextLine();
        for(Map.Entry<String, List<String>> kv: actor.entrySet()){
            if(kv.getKey().toLowerCase().contains(text.toLowerCase(Locale.ROOT))){
                System.out.println(kv.getKey() + " cнимаeтся в фильмах:");
                for(var movie: kv.getValue()){
                    System.out.println("Фильм: " + movie);

                }
                check = true;
            }
        }
        if(!check) System.out.println("Фильмов с таким актером нету!");
    }

    public static void searchMovieAndRoleByActor(FileService movies){
        Scanner sc = new Scanner(System.in);
        boolean check = false;
        Map<String, List<String[]>> actor = new HashMap<>();

        for(var movie: movies.getMovies()){
            for(var cast : movie.getCasts()){
                actor.put(cast.getFullName(), getMovieNameAndRoleByActor(cast.getFullName(), movies.getMovies()));
            }
        }
        System.out.print("Введите частичное или полное имя актера: ");
        String text = sc.nextLine();
        for(Map.Entry<String, List<String[]>> kv: actor.entrySet()){
            if(kv.getKey().toLowerCase().contains(text.toLowerCase(Locale.ROOT))){
                System.out.println(kv.getKey() + " cнимаeтся в фильмах:");
                for(var movie: kv.getValue()){
                    System.out.println("Фильм: " + movie[0] + "\nРоль: " + movie[1]);
                }
                check = true;
            }
        }
        if(!check) System.out.println("Фильмов с таким актером нету!");

    }

    public static void searchMovieByYear(FileService movies){
        Scanner sc = new Scanner(System.in);
        boolean check = false;
        int year_number;
        Map<Integer, List<String>> year = new HashMap<>();

        for(var movie: movies.getMovies()){
            year.put(movie.getYear(), getMovieNameByYear(movie.getYear(), movies.getMovies()));
        }

        while (true){
            System.out.print("Введите год выпуска фильма: ");
            try {
                year_number = tryParse(sc.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Expected integer");
                continue;
            }
            break;
        }

        for(Map.Entry<Integer, List<String>> kv: year.entrySet()){
            if(kv.getKey() == year_number){
                System.out.println("Фильмы в заданном году:");
                for(var movie: kv.getValue()){
                    System.out.println("Фильм: " + movie);

                }
                check = true;
            }
        }
        if(!check) System.out.println("Таких фильмов не было в заданном году!");
    }

    static int tryParse(String str) throws NumberFormatException{
        int number = Integer.parseInt(str);
        return number;
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
        System.out.println("-----------------------------Отсортирован по названию в порядке возрастания---------------------------------");
        Comparator sortName = Comparator.comparing(Movie::getName);
        movies.getMovies().sort(sortName);
        printMovies(movies);
        System.out.println("-----------------------------Отсортирован по названию в порядке убывания---------------------------------");
        movies.getMovies().sort(sortName.reversed());
        printMovies(movies);
    }

    public static void sortByDirector(FileService movies){
        System.out.println("-----------------------------Отсортирован по фамилии директороа в порядке возрастания---------------------------------");
        Collections.sort(movies.getMovies());
        printMovies(movies);
        System.out.println("-----------------------------Отсортирован по фамилии директороа в порядке убывания---------------------------------");
        Collections.sort(movies.getMovies(), Comparator.reverseOrder());
        printMovies(movies);
    }

    public static void sortByYear(FileService movies){
        System.out.println("-----------------------------Отсортирован по году выпуска в порядке возрастания---------------------------------");
        Comparator sortYear = Comparator.comparing(Movie::getYear);
        movies.getMovies().sort(sortYear);
        printMovies(movies);
        System.out.println("-----------------------------Отсортирован по году выпуска в порядке убывания---------------------------------");
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
