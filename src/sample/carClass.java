package sample;

import javafx.scene.image.ImageView;


public class carClass {
    private String registration;
    private String model;
    private String price;
    private String available;
    private String color1;
    private String color2;
    private String color3;
    private String year;
    private String manufacturer;
    //private String image;
    private ImageView image;

    carClass() {

    }

    carClass(String re, String mod, String pr, String av, String col1, String col2, String col3, String yr, String man, ImageView img) {
        registration = re;
        model = mod;
        price = pr;
        available = av;
        color1 = col1;
        color2 = col2;
        color3 = col3;
        year = yr;
        manufacturer = man;
        image = img;

    }

    public String getRegistration() {
        return registration;
    }

    public String getModel() {
        return model;
    }

    public String getPrice() {
        return price;
    }

    public String getAvailable() {
        return available;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getColor1() {
        return color1;
    }

    public String getColor2() {
        return color2;
    }

    public String getColor3() {
        return color3;
    }

    public String getYear() {
        return year;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public void setColor3(String color3) {
        this.color3 = color3;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

}
