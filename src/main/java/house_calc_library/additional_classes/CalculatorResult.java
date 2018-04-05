package house_calc_library.additional_classes;

public class CalculatorResult {
    private ReferenceCity nearestReferenceCity;
    private Double distanceFromFlatToNearestReferenceCity;

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
}
