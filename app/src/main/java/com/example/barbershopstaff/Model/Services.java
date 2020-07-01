package com.example.barbershopstaff.Model;

public class Services {

    String service_id,service_name;
    Long service_price;

    public Services(String service_id, String service_name, Long service_price) {
        this.service_id = service_id;
        this.service_name = service_name;
        this.service_price = service_price;
    }

    public Services() {
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public Long getService_price() {
        return service_price;
    }

    public void setService_price(Long service_price) {
        this.service_price = service_price;
    }
}
