package com.eventmanager.organizationmanagement.domain.valueobjects;

import com.eventmanager.sharedkernel.domain.base.ValueObject;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address implements ValueObject
{
    private final String street;
    private final String number;
    private final String city;
    private final String country;

    protected Address()
    {
        street = number = city = country = null;
    }

    public Address(@NonNull String street, @NonNull String number, @NonNull String city, @NonNull String country)
    {
        this.street = street;
        this.number = number;
        this.city = city;
        this.country = country;
    }

    public static Address addressOf(String street, String number, String city, String country)
    {
        return new Address(street, number, city, country);
    }
}
