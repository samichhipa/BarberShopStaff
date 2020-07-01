package com.example.barbershopstaff.Model;

public class Token {
    String TokenID,BarberID;


    public Token() {
    }


    public Token(String tokenID, String barberID) {
        TokenID = tokenID;
        BarberID = barberID;
    }

    public String getTokenID() {
        return TokenID;
    }

    public void setTokenID(String tokenID) {
        TokenID = tokenID;
    }

    public String getBarberID() {
        return BarberID;
    }

    public void setBarberID(String barberID) {
        BarberID = barberID;
    }
}
