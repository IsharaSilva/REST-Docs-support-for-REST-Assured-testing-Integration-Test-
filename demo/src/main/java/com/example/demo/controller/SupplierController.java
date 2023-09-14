package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.SupplierInputDTO;
import com.example.demo.dto.SupplierOutputDTO;
import com.example.demo.entity.Supplier;
import com.example.demo.service.SupplierService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/suppliers")
@Validated
public class SupplierController {
	@Autowired
	private SupplierService supplierService;

	@Operation(summary = "Get All Suppliers", description = "Get a list of suppliers", tags = "Get suppliers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "found suppliers", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Supplier.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = Supplier.class)) }),
			@ApiResponse(responseCode = "404", description = "not found suppliers", content = @Content) })

	@GetMapping("/")
	public ResponseEntity<List<SupplierOutputDTO>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }


	@Operation(summary = "Get a Supplier by ID", description = "Get a supplier by ID", tags = "Get a supplier")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "found a supplier", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Supplier.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = Supplier.class)) }),
			@ApiResponse(responseCode = "404", description = "not found the supplier", content = @Content) })

	@GetMapping("/{id}")
	public ResponseEntity<SupplierOutputDTO> getSupplierById(@PathVariable long id) {
		 SupplierOutputDTO supplier = supplierService.getSupplierById(id);
		    if (supplier == null) {
		        return ResponseEntity.notFound().build();
		    }
		    return ResponseEntity.ok(supplier);
    }

	@Operation(summary = "Create a new Supplier ", description = "Create a new Supplier", tags = "Create sup[pliers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Created a new supplier", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Supplier.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = Supplier.class)) })})

	@PostMapping
	public ResponseEntity<SupplierOutputDTO> createSupplier(@Valid @RequestBody SupplierInputDTO supplierDto) {
        return new ResponseEntity<>(supplierService.createSupplier(supplierDto), HttpStatus.CREATED);
    }

	@Operation(summary = "Update a Supplier by ID", description = "Update a supplier by ID", tags = "Update suppliers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Updated a supplier", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Supplier.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = Supplier.class)) }),
			@ApiResponse(responseCode = "404", description = "not found the supplier to update", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})

	@PutMapping("/{id}")
	public ResponseEntity<SupplierOutputDTO> updateSupplier(@PathVariable long id, @RequestBody SupplierInputDTO supplierDto) {
	        SupplierOutputDTO updatedSupplier = supplierService.updateSupplier(id, supplierDto);
	        return ResponseEntity.ok(updatedSupplier);

    }

	@Operation(summary = "Delete a Supplier by ID", description = "Delete a supplier by ID", tags = "Delete suppliers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Deleted a supplier", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Supplier.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = Supplier.class)) }),
			@ApiResponse(responseCode = "204", description = "no content of the supplier to delete", content = @Content) })

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSupplier(@PathVariable long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}