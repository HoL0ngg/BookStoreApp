package model;

public class Book {
    private String title;
    private String[] author;
    private int soluong;

    public Book(String title, String[] author, int soluong) {
        this.title = title;
        this.author = author;
        this.soluong = soluong;
    }

    public Book(String title, String[] author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getAuthor() {
        return author;
    }

    public void setAuthor(String[] author) {
        this.author = author;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

}