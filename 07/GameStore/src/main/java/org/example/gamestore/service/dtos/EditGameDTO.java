package org.example.gamestore.service.dtos;

import jakarta.validation.constraints.Positive;
import org.example.gamestore.data.entities.BaseEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EditGameDTO extends BaseEntity {
    @Length(min = 3, max = 100)
    private String title;

    @Length(min = 11, max = 11)
    private String trailer;

    @Length(min = 20)
    private String description;

    @Positive
    private BigDecimal price;

    @Positive
    private Double size;

    private String imageThumbnail;

    private LocalDate releaseDate;

    public EditGameDTO() {
    }

    public EditGameDTO(String title, String trailer, String description, BigDecimal price, Double size, String imageThumbnail, LocalDate releaseDate) {
        this.title = title;
        this.trailer = trailer;
        this.description = description;
        this.price = price;
        this.size = size;
        this.imageThumbnail = imageThumbnail;
        this.releaseDate = releaseDate;
    }

    public @Length(min = 3, max = 100) String getTitle() {
        return title;
    }

    public void setTitle(@Length(min = 3, max = 100) String title) {
        this.title = title;
    }

    public @Length(min = 11, max = 11) String getTrailer() {
        return trailer;
    }

    public void setTrailer(@Length(min = 11, max = 11) String trailer) {
        this.trailer = trailer;
    }

    public @Length(min = 20) String getDescription() {
        return description;
    }

    public void setDescription(@Length(min = 20) String description) {
        this.description = description;
    }

    public @Positive BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@Positive BigDecimal price) {
        this.price = price;
    }

    @Positive
    public Double getSize() {
        return size;
    }

    public void setSize(@Positive Double size) {
        this.size = size;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}

