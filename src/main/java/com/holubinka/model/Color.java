package com.holubinka.model;

public enum Color {
    GREEN("GREEN"),
    BLUE("BLUE"),
    BLACK("BLACK"),
    WHITE("WHITE"),
    RED("RED"),
    YELLOW("YELLOW");

    private String color;
    Color(String color) {
        this.color = color;
    }
}
