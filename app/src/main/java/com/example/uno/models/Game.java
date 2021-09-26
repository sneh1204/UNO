package com.example.uno.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Game implements Serializable {

    String id;

    ArrayList<String> deck = null, discard = new ArrayList<>();

    ArrayList<String> player1hand = new ArrayList<>();

    ArrayList<String> player2hand = new ArrayList<>();

    User player1;

    Date created_at = new Date();

    User player2;

    String winner = null;

    boolean dealing = true, active = true, draw4 = false, skip = false;

    String playerTurn = "p1", center = null;

    public Game() {
    }

    public Date getCreated_at() {
        return created_at;
    }

    public boolean isMyTurn(User user){
        return user.getId().equals(this.getUserTurn().getId());
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<String> deck) {
        this.deck = deck;
    }

    public ArrayList<String> getPlayer1hand() {
        return player1hand;
    }

    public void setPlayer1hand(ArrayList<String> player1hand) {
        this.player1hand = player1hand;
    }

    public ArrayList<String> getPlayer2hand() {
        return player2hand;
    }

    public void setPlayer2hand(ArrayList<String> player2hand) {
        this.player2hand = player2hand;
    }

    public User getPlayer1() {
        return player1;
    }

    public void setPlayer1(User player1) {
        this.player1 = player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public void setPlayer2(User player2) {
        this.player2 = player2;
    }

    public boolean isDealing() {
        return dealing;
    }

    public void setDealing(boolean dealing) {
        this.dealing = dealing;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDraw4() {
        return draw4;
    }

    public void setDraw4(boolean draw4) {
        this.draw4 = draw4;
    }

    public ArrayList<String> getDiscard() {
        return discard;
    }

    public void setDiscard(ArrayList<String> discard) {
        this.discard = discard;
    }

    public boolean isSkip() {
        return skip;
    }

    public Game(User player1, User player2, ArrayList<String> deck, ArrayList<String> player1hand, ArrayList<String> player2hand) {
        this.deck = deck;
        this.player1 = player1;
        this.player2 = player2;
        this.player1hand = player1hand;
        this.player2hand = player2hand;
    }

    public String getTopCard(){
        return (this.deck.size() >= 1) ? this.deck.get(0) : null;
    }

    public String getDrawCard(){
        return (this.deck.size() >= 2) ? this.deck.get(1) : null;
    }

    public boolean isPlayer1(User user){
        return user.getId().equals(this.player1.getId());
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String getPlayerTurn() {
        return playerTurn;
    }

    public boolean isPlayer1Turn(){
        return this.playerTurn.equals("p1");
    }

    public User getUserTurn(){
        if(this.playerTurn.equals("p1"))   return player1;
        else return player2;
    }

    public void setPlayerTurn(String playerTurn) {
        this.playerTurn = playerTurn;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", deck=" + deck +
                ", discard=" + discard +
                ", player1hand=" + player1hand +
                ", player2hand=" + player2hand +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", winner='" + winner + '\'' +
                ", dealing=" + dealing +
                ", active=" + active +
                ", draw4=" + draw4 +
                ", skip=" + skip +
                ", playerTurn='" + playerTurn + '\'' +
                ", center='" + center + '\'' +
                '}';
    }

}
