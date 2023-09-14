package com.example.demo.entity;

public class SupplierBuilder {
    private long id;
    private String name;
    private String address;
    private String contactDetails;
    private String specialties;

    public SupplierBuilder id(long id) {
        this.id = id;
        return this;
    }

    public SupplierBuilder name(String name) {
        this.name = name;
        return this;
    }

    public SupplierBuilder address(String address) {
        this.address = address;
        return this;
    }

    public SupplierBuilder contactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
        return this;
    }

    public SupplierBuilder specialties(String specialties) {
        this.specialties = specialties;
        return this;
    }
    public Supplier build() {
        Supplier supplier = new Supplier();
        supplier.setId(this.id);
        supplier.setName(this.name);
        supplier.setAddress(this.address);
        supplier.setContactDetails(this.contactDetails);
        supplier.setSpecialties(this.specialties);
        return supplier;
    }
}

