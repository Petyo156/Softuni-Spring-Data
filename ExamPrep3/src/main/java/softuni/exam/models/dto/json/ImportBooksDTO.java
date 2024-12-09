package softuni.exam.models.dto.json;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;
import softuni.exam.models.entity.Genre;

import javax.persistence.Lob;
import javax.validation.constraints.Positive;

public class ImportBooksDTO {
    @Expose
    @Length(min = 3, max = 40)
    private String author;
    @Expose
    private boolean available;
    @Expose
    @Length(min = 5)
    private String description;
    @Expose
    private Genre genre;
    @Expose
    @Length(min = 3, max = 40)
    private String title;
    @Expose
    @Positive
    private double rating;

    public ImportBooksDTO() {
    }

    public @Length(min = 3, max = 40) String getAuthor() {
        return author;
    }

    public void setAuthor(@Length(min = 3, max = 40) String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public @Length(min = 5) String getDescription() {
        return description;
    }

    public void setDescription(@Length(min = 5) String description) {
        this.description = description;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public @Length(min = 3, max = 40) String getTitle() {
        return title;
    }

    public void setTitle(@Length(min = 3, max = 40) String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
/*
{
    "author": "F. Scott Fitzgerald",
    "available": true,
    "description": "A classic novel set in the roaring 20s",
    "genre": "CLASSIC_LITERATURE",
    "title": "The Great Gatsby",
    "rating": 9.1
  },
 */