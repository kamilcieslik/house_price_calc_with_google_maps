package house_calc_library;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import house_calc_library.additional_classes.Address;
import house_calc_library.additional_classes.CalculatorResult;
import house_calc_library.additional_classes.ReferenceCity;
import house_calc_library.exception.ConstructionYearViolationException;

import java.io.IOException;
import java.util.*;

public class PricesCalculator {
    private GeoApiContext geoApiContext;
    private List<ReferenceCity> referenceCities;
    private List<Address> autocompleteAddresses;
    private Address selectedAddress;
    private Map<String, Double> buildingTypes;
    private List<String> marketTypes;
    private Map<String, Double> buildingMaterials;
    private Map<String, Double> additionalAttributes;
    private CalculatorResult calculatorResult;

    public PricesCalculator(String googleApiKey) {
        initReferenceCities();
        initReferenceCities();
        initMarketTypes();
        initBuildingTypes();
        initBuildingMaterials();
        initAdditionalAttributes();
        geoApiContext = new GeoApiContext.Builder().apiKey(googleApiKey).build();
    }

    public void autocompleteAddresses(String address) throws InterruptedException, ApiException, IOException {
        GeocodingResult[] geocodingResults;
        geocodingResults = GeocodingApi.geocode(geoApiContext, address).language("pl").await();

        if (geocodingResults.length >= 1) {
            if (autocompleteAddresses == null)
                autocompleteAddresses = new ArrayList<>();
            else
                autocompleteAddresses.clear();

            Address autocompleteAddress;
            for (GeocodingResult geocodingResult : geocodingResults) {
                autocompleteAddress = new Address();

                String addressType;
                for (int i = 0; i < geocodingResult.addressComponents.length; i++) {
                    addressType = geocodingResult.addressComponents[i].types[0].toCanonicalLiteral();

                    switch (addressType) {
                        case "street_number":
                            autocompleteAddress.setStreetNumber(geocodingResult.addressComponents[i].longName);
                            break;

                        case "route":
                            String route = geocodingResult.addressComponents[i].longName;

                            if (route.contains("Ulica") || route.contains("ulica")) {
                                route = route.substring(6);
                                route = "ul. " + route;
                            }

                            autocompleteAddress.setRoute(route);
                            break;

                        case "locality":
                            autocompleteAddress.setCity(geocodingResult.addressComponents[i].longName);
                            break;

                        case "country":
                            autocompleteAddress.setCountry(geocodingResult.addressComponents[i].longName);
                            break;

                        case "administrative_area_level_1":
                            String province = geocodingResult.addressComponents[i].shortName;

                            if (province.contains("Województwo") || province.contains("województwo"))
                                province = province.substring(12);

                            autocompleteAddress.setProvince(province);
                            break;

                        case "postal_code":
                            autocompleteAddress.setPostalCode(geocodingResult.addressComponents[i].longName);
                            break;
                    }
                }
                autocompleteAddress.setLatitude(String.valueOf(geocodingResult.geometry.location.lat));
                autocompleteAddress.setLongitude(String.valueOf(geocodingResult.geometry.location.lng));

                if (autocompleteAddress.getCountry().equals("Polska"))
                    autocompleteAddresses.add(autocompleteAddress);
            }
        }
    }

    public CalculatorResult calculateHousePrice(String buildingType, String marketType, String buildingMaterial,
                                                Integer constructionYear, Integer numberOfMeters, Boolean balcony, Boolean cellar,
                                                Boolean garden, Boolean terrace, Boolean elevator, Boolean separateKitchen,
                                                Boolean guardedEstate) throws ConstructionYearViolationException {
        Double meterPriceMultiplier = 1.0;
        meterPriceMultiplier *= calculateMultiplierForConstructionYear(constructionYear);
        Double multiplierForDistanceToNearestReferenceCity = calculateMultiplierForDistanceToNearestReferenceCity(calculateTheNearestReferenceCity());

        Double housePrice;
        if (marketType.equals("pierwotny"))
            housePrice = calculatorResult.getNearestReferenceCity().getPricePerMeterOnPrimaryMarket();
        else
            housePrice = calculatorResult.getNearestReferenceCity().getPricePerMeterOnAftermarket();

        calculatorResult.setBasicPricePerMeter(meterPriceMultiplier * housePrice);

        meterPriceMultiplier *= multiplierForDistanceToNearestReferenceCity;
        meterPriceMultiplier *= buildingTypes.get(buildingType);
        meterPriceMultiplier *= buildingMaterials.get(buildingMaterial);

        housePrice *= meterPriceMultiplier;
        housePrice *= numberOfMeters;

        if (balcony)
            housePrice += additionalAttributes.get("balkon");

        if (cellar)
            housePrice += additionalAttributes.get("piwnica");

        if (garden)
            housePrice += additionalAttributes.get("ogródek");

        if (terrace)
            housePrice += additionalAttributes.get("taras");

        if (elevator)
            housePrice += additionalAttributes.get("winda");

        if (separateKitchen)
            housePrice += additionalAttributes.get("oddzielna kuchnia");

        if (guardedEstate)
            housePrice += additionalAttributes.get("strzeżone osiedle");

        calculatorResult.setHousePrice(housePrice);
        calculatorResult.setFinalPricePerMeter(housePrice / Double.valueOf(numberOfMeters));

        return calculatorResult;
    }

    private Double calculateMultiplierForDistanceToNearestReferenceCity(Double distance) {
        if (distance < 1000)
            return 1.1;
        else if (distance < 4000)
            return 1.0;
        else if (distance < 10000)
            return 0.95;
        else if (distance < 20000)
            return 0.90;
        else if (distance < 35000)
            return 0.85;
        else if (distance < 47000)
            return 0.80;
        else if (distance < 60000)
            return 0.77;
        else if (distance < 75000)
            return 0.74;
        else if (distance < 90000)
            return 0.72;
        else
            return 0.65;
    }

    private Double calculateTheNearestReferenceCity() {
        Map<ReferenceCity, Double> distancesFromFlatToReferenceCities = new HashMap<>();
        final int EARTH_RADIUS = 6371;
        referenceCities.forEach(city -> {
            Double latDistance =
                    Math.toRadians(Double.valueOf(city.getLatitude()) - Double.valueOf(selectedAddress.getLatitude()));
            Double lonDistance =
                    Math.toRadians(Double.valueOf(city.getLongitude()) - Double.valueOf(selectedAddress.getLongitude()));
            Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(Double.valueOf(selectedAddress.getLatitude())))
                    * Math.cos(Math.toRadians(Double.valueOf(city.getLatitude())))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            Double distanceInMeters = EARTH_RADIUS * c * 1000;

            distancesFromFlatToReferenceCities.put(city, distanceInMeters);
        });

        Map.Entry<ReferenceCity, Double> min = null;
        for (Map.Entry<ReferenceCity, Double> entry : distancesFromFlatToReferenceCities.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }

        assert min != null;
        calculatorResult = new CalculatorResult(min.getKey(), min.getValue());

        return min.getValue();
    }

    private Double calculateMultiplierForConstructionYear(Integer constructionYear) throws ConstructionYearViolationException {
        Calendar calendarDateNow = Calendar.getInstance();
        int dateNowYear = calendarDateNow.get(Calendar.YEAR);
        if (constructionYear > dateNowYear) {
            Throwable exceptionCause;
            exceptionCause = new Throwable("rok budowy nie może być większy niż obecny.");
            throw new ConstructionYearViolationException("Błąd roku budowy", exceptionCause);
        }

        if (constructionYear < 1900) {
            Throwable exceptionCause;
            exceptionCause = new Throwable("rok budowy nie może być mniejszy niż 1900.");
            throw new ConstructionYearViolationException("Błąd roku budowy", exceptionCause);
        }

        if (constructionYear == dateNowYear)
            return 1.20;
        else if (constructionYear < dateNowYear - 40)
            return 0.80;
        else
            return 1.20 - ((dateNowYear - constructionYear) / 100.0);
    }

    private void initReferenceCities() {
        if (referenceCities == null)
            referenceCities = new ArrayList<>();

        referenceCities.add(new ReferenceCity("Gdańsk", 6596.0,
                8082.0, "54.372158", "18.638306"));

        referenceCities.add(new ReferenceCity("Katowice", 5421.0,
                4664.0, "50.270908", "19.039993"));

        referenceCities.add(new ReferenceCity("Kraków", 7401.0,
                8013.0, "50.049683", "19.944544"));

        referenceCities.add(new ReferenceCity("Lublin", 5378.0,
                5254.0, "51.246452", "22.568445"));

        referenceCities.add(new ReferenceCity("Łódź", 4814.0,
                4290.0, "51.759445", "19.457216"));

        referenceCities.add(new ReferenceCity("Poznań", 6437.0,
                6344.0, "52.409538", "16.931992"));

        referenceCities.add(new ReferenceCity("Szczecin", 5102.0,
                4691.0, "53.428543", "14.552812"));

        referenceCities.add(new ReferenceCity("Warszawa", 8080.0,
                9457.0, "52.237049", "21.017532"));

        referenceCities.add(new ReferenceCity("Wrocław", 6297.0,
                6495.0, "51.107883", "17.038538"));
    }

    private void initMarketTypes() {
        if (marketTypes == null)
            marketTypes = new ArrayList<>();

        marketTypes.add("pierwotny");
        marketTypes.add("wtórny");
    }

    private void initBuildingTypes() {
        if (buildingTypes == null)
            buildingTypes = new HashMap<>();

        buildingTypes.put("blok", 1.0);
        buildingTypes.put("kamienica", 0.85);
        buildingTypes.put("dom wolnostojący", 1.05);
        buildingTypes.put("apartamentowiec", 1.15);
    }

    private void initBuildingMaterials() {
        if (buildingMaterials == null)
            buildingMaterials = new HashMap<>();

        buildingMaterials.put("wielka płyta", 0.9);
        buildingMaterials.put("pustak", 0.95);
        buildingMaterials.put("cegła", 1.0);
        buildingMaterials.put("drewno", 0.6);
        buildingMaterials.put("żelbeton", 1.15);
    }

    private void initAdditionalAttributes() {
        if (additionalAttributes == null)
            additionalAttributes = new HashMap<>();

        additionalAttributes.put("balkon", 20000.0);
        additionalAttributes.put("piwnica", 10000.0);
        additionalAttributes.put("ogródek", 50000.0);
        additionalAttributes.put("taras", 40000.0);
        additionalAttributes.put("winda", 30000.0);
        additionalAttributes.put("oddzielna kuchnia", 30000.0);
        additionalAttributes.put("strzeżone osiedle", 50000.0);
    }

    public GeoApiContext getGeoApiContext() {
        return geoApiContext;
    }

    public void setGeoApiContext(GeoApiContext geoApiContext) {
        this.geoApiContext = geoApiContext;
    }

    public List<ReferenceCity> getReferenceCities() {
        return referenceCities;
    }

    public void setReferenceCities(List<ReferenceCity> referenceCities) {
        this.referenceCities = referenceCities;
    }

    public List<Address> getAutocompleteAddresses() {
        return autocompleteAddresses;
    }

    public void setAutocompleteAddresses(List<Address> autocompleteAddresses) {
        this.autocompleteAddresses = autocompleteAddresses;
    }

    public Address getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public Map<String, Double> getBuildingTypes() {
        return buildingTypes;
    }

    public void setBuildingTypes(Map<String, Double> buildingTypes) {
        this.buildingTypes = buildingTypes;
    }

    public List<String> getMarketTypes() {
        return marketTypes;
    }

    public void setMarketTypes(List<String> marketTypes) {
        this.marketTypes = marketTypes;
    }

    public Map<String, Double> getBuildingMaterials() {
        return buildingMaterials;
    }

    public void setBuildingMaterials(Map<String, Double> buildingMaterials) {
        this.buildingMaterials = buildingMaterials;
    }

    public Map<String, Double> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setAdditionalAttributes(Map<String, Double> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public CalculatorResult getCalculatorResult() {
        return calculatorResult;
    }

    public void setCalculatorResult(CalculatorResult calculatorResult) {
        this.calculatorResult = calculatorResult;
    }
}
