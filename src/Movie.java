import java.util.List;

public class Movie implements Comparable<Movie>{
    private String name;
    private int year;
    private String description;
    private Director director;
    private List<Cast> cast;

    public Movie(String name, int year, String description, Director director, List<Cast> cast) {
        this.name = name;
        this.year = year;
        this.description = description;
        this.director = director;
        this.cast = cast;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Director getDirectors() {
        return director;
    }

    public void setDirectors(Director director) {
        this.director = director;
    }

    public List<Cast> getCasts() {
        return cast;
    }

    public void setCasts(List<Cast> casts) {
        this.cast = casts;
    }

    @Override
    public String toString() {
        String fmt = "| %-41s  |  %-11s  |  %-9s  |  %-17s  |  %-16s";
        String casts = "";
        for(int i = 0; i < cast.size(); i++){
            if(i == 0){
                casts += String.format("%-20s", cast.get(i).getFullName()) + "  |  " + cast.get(i).getRole() + "\n";
            }
            else {
                casts += "| " + " ".repeat(41) + "  |               |  " + " ".repeat(9) + "  |  " + " ".repeat(17) + "  |  " + String.format("%-20s", cast.get(i).getFullName()) + "  |  " + cast.get(i).getRole() + "\n";
            }

        }

        String text = String.format(fmt, name, year, description, director, casts);
        return text;
    }

    @Override
    public int compareTo(Movie o) {
        return this.getDirectors().getFullName().compareTo(o.getDirectors().getFullName());
    }
}
