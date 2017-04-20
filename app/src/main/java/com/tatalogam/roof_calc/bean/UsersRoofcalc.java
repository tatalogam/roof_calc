package com.tatalogam.roof_calc.bean;

/**
 * Created by stephanuskoeswandi on 4/5/17.
 */

public class UsersRoofcalc {
    private String name;
    private String email;
    private String phone;
    private String sim_country;
    private String location;
    private String order;

    public UsersRoofcalc(String name,String email,String phone,String sim_country,String location,String order){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.sim_country = sim_country;
        this.location = location;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSim_country() {
        return sim_country;
    }

    public void setSim_country(String sim_country) {
        this.sim_country = sim_country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
