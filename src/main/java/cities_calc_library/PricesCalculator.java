package cities_calc_library;

import cities_calc_library.additional_classes.Address;
import cities_calc_library.additional_classes.ReferenceCity;
import cities_calc_library.exception.ConstructionYearViolationException;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;
import java.util.*;

public class PricesCalculator {
    private GeoApiContext geoApiContext;
    private List<ReferenceCity> referenceCitiesMap;
    private List<Address> autocompleteAddresses;
    private Address selectedAddress;
    private Map<String, Double> buildingTypes;
    private List<String> marketTypes;
    private Map<String, Double> buildingMaterials;
    private Map<String, Double> additionalAttributes;

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

    public void calculateTheNearestReferenceCity() {

    }

    private void initReferenceCities() {
        if (referenceCitiesMap == null)
            referenceCitiesMap = new ArrayList<>();

        referenceCitiesMap.add(new ReferenceCity("Gdańsk", 6596.0,
                8082.0, "54.372158", "18.638306"));

        referenceCitiesMap.add(new ReferenceCity("Katowice", 5421.0,
                4664.0, "50.270908", "19.039993"));

        referenceCitiesMap.add(new ReferenceCity("Kraków", 7401.0,
                8013.0, "50.049683", "19.944544"));

        referenceCitiesMap.add(new ReferenceCity("Lublin", 5378.0,
                5254.0, "51.246452", "22.568445"));

        referenceCitiesMap.add(new ReferenceCity("Łódź", 4814.0,
                4290.0, "51.759445", "19.457216"));

        referenceCitiesMap.add(new ReferenceCity("Poznań", 6437.0,
                6344.0, "52.409538", "16.931992"));

        referenceCitiesMap.add(new ReferenceCity("Szczecin", 5102.0,
                4691.0, "53.428543", "14.552812"));

        referenceCitiesMap.add(new ReferenceCity("Warszawa", 8080.0,
                9457.0, "52.237049", "21.017532"));

        referenceCitiesMap.add(new ReferenceCity("Wrocław", 6297.0,
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

    public List<ReferenceCity> getReferenceCitiesMap() {
        return referenceCitiesMap;
    }

    public void setReferenceCitiesMap(List<ReferenceCity> referenceCitiesMap) {
        this.referenceCitiesMap = referenceCitiesMap;
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
}
