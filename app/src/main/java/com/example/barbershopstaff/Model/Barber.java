package com.example.barbershopstaff.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Barber implements Parcelable {

    String barber_id, barber_name, barber_email, barber_password;
    Long barber_rating;

    public Barber() {
    }

    public Barber(String barber_id, String barber_name, String barber_email, String barber_password, Long barber_rating) {
        this.barber_id = barber_id;
        this.barber_name = barber_name;
        this.barber_email = barber_email;
        this.barber_password = barber_password;
        this.barber_rating = barber_rating;
    }


    protected Barber(Parcel in) {
        barber_id = in.readString();
        barber_name = in.readString();
        barber_email = in.readString();
        barber_password = in.readString();
        if (in.readByte() == 0) {
            barber_rating = null;
        } else {
            barber_rating = in.readLong();
        }
    }

    public static final Creator<Barber> CREATOR = new Creator<Barber>() {
        @Override
        public Barber createFromParcel(Parcel in) {
            return new Barber(in);
        }

        @Override
        public Barber[] newArray(int size) {
            return new Barber[size];
        }
    };

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

    public String getBarber_email() {
        return barber_email;
    }

    public void setBarber_email(String barber_email) {
        this.barber_email = barber_email;
    }

    public String getBarber_password() {
        return barber_password;
    }

    public void setBarber_password(String barber_password) {
        this.barber_password = barber_password;
    }

    public Long getBarber_rating() {
        return barber_rating;
    }

    public void setBarber_rating(Long barber_rating) {
        this.barber_rating = barber_rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(barber_id);
        dest.writeString(barber_name);
        dest.writeString(barber_email);
        dest.writeString(barber_password);
        if (barber_rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(barber_rating);
        }
    }
}
