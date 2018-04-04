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

    public Double getPricePerMeterOnPrimaryMarket() {
        return pricePerMeterOnPrimaryMarket;
    }

    public Double getPricePerMeterOnAftermarket() {
        return pricePerMeterOnAftermarket;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
