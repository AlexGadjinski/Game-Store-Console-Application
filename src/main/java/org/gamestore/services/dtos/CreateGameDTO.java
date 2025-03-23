package org.gamestore.services.dtos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateGameDTO {
    @Length(min = 3, max = 100, message = "Title must be between 3 and 100 symbols")
    @Pattern(regexp = "^[A-Z][a-zA-Z'\\d@$!%*#?& ]+$", message = "Invalid title!")
    private String title;
    @Positive(message = "Price must be a positive number")
    private BigDecimal price;
    @Positive(message = "Size must be a positive number")
    private Double size;
    @Length(min = 11, max = 11, message = "Trailer must be 11 symbols long")
    private String url;
    @Pattern(regexp = "^https*://.+", message = "Invalid thumbnail url!")
    private String imageThumbnail;
    @Length(min = 20, message = "Description must be at least 20 symbols")
    private String description;
    private LocalDate releaseDate;

    public CreateGameDTO() {}

    public CreateGameDTO(String title, BigDecimal price, Double size, String url,
                         String imageThumbnail, String description, LocalDate releaseDate) {
        this.title = title;
        this.price = price;
        this.size = size;
        this.url = url;
        this.imageThumbnail = imageThumbnail;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
