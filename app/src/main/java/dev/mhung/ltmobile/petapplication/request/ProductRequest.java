package dev.mhung.ltmobile.petapplication.request;

public class ProductRequest {
    private int id;
    private String name;
    private int age;
    private long price;
    private int quantity;
    private String size;
    private String color;
    private String img;
    private String description;
    private String breed;




    public ProductRequest(int id, String name, int age, long price, int quantity, String size, String color, String img, String description, String breed) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.img = img;
        this.description = description;
        this.breed = breed;
    }

    public ProductRequest(String name, int age, long price, int quantity, String size, String color, String description, String img, String breed) {
        this.name = name;
        this.age = age;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.description = description;
        this.img = img;
        this.breed = breed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
