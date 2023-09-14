package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.SupplierInputDTO;
import com.example.demo.dto.SupplierOutputDTO;
import com.example.demo.entity.Supplier;
import com.example.demo.entity.SupplierBuilder;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SupplierRepository;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(final SupplierRepository supplierRepository){
        this.supplierRepository = supplierRepository;
    }

    public List<SupplierOutputDTO> getAllSuppliers() {
        return supplierRepository.findAll().stream().map(Supplier::viewAsOutputDto).toList();
    }

    public SupplierOutputDTO getSupplierById(long id) {
        return supplierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("No Supplier entity with id %s exists!", id))).viewAsOutputDto();
    }

    public SupplierOutputDTO createSupplier(SupplierInputDTO supplierDto) {
        Supplier supplier = new SupplierBuilder().name(supplierDto.getName()).contactDetails(supplierDto.getContactDetails()).address(supplierDto.getAddress()).specialties(supplierDto.getSpecialties()).build();
        return supplierRepository.save(supplier).viewAsOutputDto();
    }

    public SupplierOutputDTO updateSupplier(long id, SupplierInputDTO supplierDto) {
        SupplierOutputDTO existingSupplier = getSupplierById(id);
        Supplier supplier = new SupplierBuilder().name(supplierDto.getName()).contactDetails(supplierDto.getContactDetails()).address(supplierDto.getAddress()).specialties(supplierDto.getSpecialties()).id(existingSupplier.getId()).build();
        return supplierRepository.save(supplier).viewAsOutputDto();
    }
    
    public void deleteSupplier(long id) {
        supplierRepository.deleteById(id);
    }
    
    public boolean existsSupplierWithId(long supplierId) {
        return supplierRepository.existsById(supplierId);
    }
}
