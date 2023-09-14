package com.example.demo.entity;

import com.example.demo.dto.SupplierOutputDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Supplier {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplierId")
    private long id;

    @Column(name = "supplierName")
    private String name;
    
    @Column(name = "address")
    private String address;

    @Column(name = "contactDetails")
    private String contactDetails;

    @Column(name = "specialties")
    private String specialties;
    
    public SupplierOutputDTO viewAsOutputDto(){
        return new SupplierOutputDTO(id, name, address, contactDetails, specialties);
    }
}