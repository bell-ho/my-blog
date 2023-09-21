package com.lasvegas;

import lombok.Data;

@Data
public class Dice {
    private int number;
    private String owner;

    public Dice(String owner) {
        this.number = (int) (Math.random() * 6) + 1;
        this.owner = owner;
    }

}
