package org.midheaven.application.contact;

import org.midheaven.culture.CountryCode;
import org.midheaven.culture.CountryDivisionCode;

public class Address {
    
    private CountryCode countryCode;
    private PostalCode postalCode;
    private CountryDivisionCode countryDivisionCode;
    private String streetName;
    private String number;
    private String complement;
    private String neighbourhood;
    private String cityName;
    
    public CountryCode countryCode() {
        return countryCode;
    }
    
    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }
    
    public PostalCode postalCode() {
        return postalCode;
    }
    
    public void setPostalCode(PostalCode postalCode) {
        this.postalCode = postalCode;
    }
    
    public CountryDivisionCode countryDivisionCode() {
        return countryDivisionCode;
    }
    
    public void setCountryDivisionCode(CountryDivisionCode countryDivisionCode) {
        this.countryDivisionCode = countryDivisionCode;
    }
    
    public String streetName() {
        return streetName;
    }
    
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    
    public String number() {
        return number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }
    
    public String complement() {
        return complement;
    }
    
    public void setComplement(String complement) {
        this.complement = complement;
    }
    
    public String neighbourhood() {
        return neighbourhood;
    }
    
    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }
    
    public String cityName() {
        return cityName;
    }
    
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
