package house_calc_library.additional_classes;

public class CalculatorResult {
    private ReferenceCity nearestReferenceCity;
    private Double distanceFromFlatToNearestReferenceCity, basicPricePerMeter, housePrice, finalPricePerMeter;

    public CalculatorResult() {
    }

    public CalculatorResult(ReferenceCity nearestReferenceCity, Double distanceFromFlatToNearestReferenceCity) {
        this.nearestReferenceCity = nearestReferenceCity;
        this.distanceFromFlatToNearestReferenceCity = distanceFromFlatToNearestReferenceCity;
    }

    public ReferenceCity getNearestReferenceCity() {
        return nearestReferenceCity;
    }

    public void setNearestReferenceCity(ReferenceCity nearestReferenceCity) {
        this.nearestReferenceCity = nearestReferenceCity;
    }

    public Double getDistanceFromFlatToNearestReferenceCity() {
        return distanceFromFlatToNearestReferenceCity;
    }

    public void setDistanceFromFlatToNearestReferenceCity(Double distanceFromFlatToNearestReferenceCity) {
        this.distanceFromFlatToNearestReferenceCity = distanceFromFlatToNearestReferenceCity;
    }

    public Double getBasicPricePerMeter() {
        return basicPricePerMeter;
    }

    public void setBasicPricePerMeter(Double basicPricePerMeter) {
        this.basicPricePerMeter = basicPricePerMeter;
    }

    public Double getHousePrice() {
        return housePrice;
    }

    public void setHousePrice(Double housePrice) {
        this.housePrice = housePrice;
    }

    public Double getFinalPricePerMeter() {
        return finalPricePerMeter;
    }

    public void setFinalPricePerMeter(Double finalPricePerMeter) {
        this.finalPricePerMeter = finalPricePerMeter;
    }
}
