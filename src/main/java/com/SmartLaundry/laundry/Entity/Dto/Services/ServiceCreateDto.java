package com.SmartLaundry.laundry.Entity.Dto.Services;

public class ServiceCreateDto {
    private String title;
    private String category;
    private String price;

    @Override
    public String toString() {
        return "ServiceCreateDto{" +
                "title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
