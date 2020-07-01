package com.example.barbershopstaff.Model;

import com.google.firebase.Timestamp;

public class BookingInformation {

    String customer_name,customer_phone,time,barber_id,barber_name,salon_id,salon_name,salon_address,city_id;
    Long slot;
    Timestamp timestamp;
    boolean done;

    public BookingInformation() {
    }

    public BookingInformation(String customer_name, String customer_phone, String time, String barber_id, String barber_name, String salon_id, String salon_name, String salon_address, String city_id, Long slot, Timestamp timestamp, boolean done) {
        this.customer_name = customer_name;
        this.customer_phone = customer_phone;
        this.time = time;
        this.barber_id = barber_id;
        this.barber_name = barber_name;
        this.salon_id = salon_id;
        this.salon_name = salon_name;
        this.salon_address = salon_address;
        this.city_id = city_id;
        this.slot = slot;
        this.timestamp = timestamp;
        this.done = done;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBarber_id() {
        return barber_id;
    }

    public void setBarber_id(String barber_id) {
        this.barber_id = barber_id;
    }

    public String getBarber_name() {
        return barber_name;
    }

    public void setBarber_name(String barber_name) {
        this.barber_name = barber_name;
    }

    public String getSalon_id() {
        return salon_id;
    }

    public void setSalon_id(String salon_id) {
        this.salon_id = salon_id;
    }

    public String getSalon_name() {
        return salon_name;
    }

    public void setSalon_name(String salon_name) {
        this.salon_name = salon_name;
    }

    public String getSalon_address() {
        return salon_address;
    }

    public void setSalon_address(String salon_address) {
        this.salon_address = salon_address;
    }

    public Long getSlot() {
        return slot;
    }

    public void setSlot(Long slot) {
        this.slot = slot;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
