package com.example.uno.models;

import java.util.ArrayList;

public class Deck {
    ArrayList<String> cards;

    public Deck() {
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public void setCards(ArrayList<String> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "cards=" + cards +
                '}';
    }
}
