package dev.mhung.ltmobile.petapplication.request;

public class ProductRequest {
    private int id;
    private String name;
    private int age;
    private int price;
    private int quantity;
    private String img;
    private String description;
    private String gender;

    public ProductRequest(int id, String name, int age, int price, int quantity, String img, String description, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.price = price;
        this.quantity = quantity;
        this.img = img;
        this.description = description;
        this.gender = gender;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
