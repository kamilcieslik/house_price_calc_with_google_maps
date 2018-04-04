package cities_calc_library.additional_classes;

public class Address {
    private String streetNumber, route, city, country, province, postalCode;

    private String latitude;
    private String longitude;

    public Address() {

    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        String fullAddress = "";

        if (country != null)
            fullAddress += country + ", ";

        if (province != null)
            fullAddress += province + ", ";

        if (postalCode != null)
            fullAddress += postalCode + " ";

        if (city != null)
            fullAddress += city + ", ";

        if (route != null) {
            fullAddress += route + " ";
            if (streetNumber != null)
                fullAddress += streetNumber;
        }

        if (fullAddress.endsWith(" "))
            fullAddress = fullAddress.substring(0, fullAddress.length() - 1);
        if (fullAddress.endsWith(","))
            fullAddress = fullAddress.substring(0, fullAddress.length() - 1);

        return fullAddress;
    }
}
