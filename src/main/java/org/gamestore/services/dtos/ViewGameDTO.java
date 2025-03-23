package org.gamestore.services.dtos;

import java.math.BigDecimal;

public class ViewGameDTO {
    private String title;
    private BigDecimal price;

    public ViewGameDTO() {}

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

    @Override
    public String toString() {
        return String.format("%s %s", title, price);
    }
}
