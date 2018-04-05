package house_calc_library.additional_classes;

public class ReferenceCity {
    private String name;
    private Double pricePerMeterOnPrimaryMarket, pricePerMeterOnAftermarket;
    private String latitude, longitude;

    public ReferenceCity(String name, Double pricePerMeterOnPrimaryMarket, Double pricePerMeterOnAftermarket, String latitude, String longitude) {
        this.name = name;
        this.pricePerMeterOnPrimaryMarket = pricePerMeterOnPrimaryMarket;
        this.pricePerMeterOnAftermarket = pricePerMeterOnAftermarket;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPricePerMeterOnPrimaryMarket() {
        return pricePerMeterOnPrimaryMarket;
    }

    public void setPricePerMeterOnPrimaryMarket(Double pricePerMeterOnPrimaryMarket) {
        this.pricePerMeterOnPrimaryMarket = pricePerMeterOnPrimaryMarket;
    }

    public Double getPricePerMeterOnAftermarket() {
        return pricePerMeterOnAftermarket;
    }

    public void setPricePerMeterOnAftermarket(Double pricePerMeterOnAftermarket) {
        this.pricePerMeterOnAftermarket = pricePerMeterOnAftermarket;
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
}
