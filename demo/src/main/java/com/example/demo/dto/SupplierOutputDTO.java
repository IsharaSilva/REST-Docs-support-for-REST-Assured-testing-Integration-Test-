package com.example.demo.dto;

import lombok.Getter;

@Getter
public class SupplierOutputDTO extends SupplierInputDTO {
    private final long id;

    public SupplierOutputDTO(final long id, final String name, final String address, final String contactDetails, final String specialties){
        super(name, address, contactDetails, specialties);
        this.id = id;
    } 
}