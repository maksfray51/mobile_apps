package ru.mirea.poltavets.httpurlconnection;

public class UserData {
    private String ip;
    private String city;
    private String country;
    private String region;

    public UserData(String ip, String city, String country, String region) {
        this.ip = ip;
        this.city = city;
        this.country = country;
        this.region = region;
    }

    public String getIp() {
        return ip;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }
}
