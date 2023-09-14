package com.example.demo.ControllerRestConfig;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.restassured.RestAssuredRestDocumentation;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.entity.Supplier;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestDocs(outputDir = "target/generated-restSnippets")
@TestPropertySource(locations = "classpath:application-test.properties")
public class SupplierControllerIntegrationTest {
	
	private static final String SUPPLIER_PATH = "/api/suppliers";
	private static final String SUPPLIER_WITH_ID_PATH = "/api/suppliers/{id}";
	
	public static final long NON_EXISTING_SUPPLIER_ID = 999;
	public static final long SUPPLIER_ID1 = 1;
	public static final long SUPPLIER_ID2 = 3;
	public static final String NAME = "name";
	public static final String ADDRESS = "address";
	public static final String CONTACT = "contact";
	public static final String SPECIALTIES = "specialities";
	public static final String SUPPLIER_NAME1 = "NewSupplier";
	public static final String SUPPLIER_ADDRESS1 = "SupplierAddress";
	public static final String SUPPLIER_CONTACT1 = "SupplierContact";
	public static final String SUPPLIER_SPECIALTIES1 = "SupplierSpecialties";
	public static final String SUPPLIER_NAME2 = "UpdatedSupplier";
	public static final String SUPPLIER_ADDRESS2 = "456";
	public static final Long SUPPLIER_ID = 1L;
	public static final int OK_STATUS_CODE = 200;
	public static final int CREATED_STATUS_CODE = 201;
	public static final int NOTFOUND_STATUS_CODE = 404;
	public static final int NOCONTENT_STATUS_CODE = 204;
	
	@LocalServerPort
	private int port;

	@Autowired
	private RestDocumentationContextProvider restDocumentationContextProvider;

	private RequestSpecification documentationSpec;

	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
		documentationSpec = new RequestSpecBuilder()
				.addFilter(RestAssuredRestDocumentation.documentationConfiguration(restDocumentationContextProvider))
				.addFilter(RestAssuredRestDocumentation.document("{method-name}",
						Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
						Preprocessors.preprocessResponse(Preprocessors.prettyPrint())))
				.build();
	}

	@Test
	public void testGetAllSuppliers() {
		given(documentationSpec).contentType(ContentType.JSON).when().get(SUPPLIER_PATH+ "/").then().statusCode(OK_STATUS_CODE)
				.contentType(ContentType.JSON).body("size()", greaterThan(0));
	}

	@Test
	public void testGetSupplierByIdExisting() {

		given(documentationSpec).contentType(ContentType.JSON).when().get(SUPPLIER_WITH_ID_PATH, SUPPLIER_ID)
				.then().statusCode(OK_STATUS_CODE).contentType(ContentType.JSON)
				.body("id", equalTo(SUPPLIER_ID.intValue()))
				 .body(NAME, equalTo(SUPPLIER_NAME2)) 
			        .body(ADDRESS, equalTo(SUPPLIER_ADDRESS2));
	}

	@Test
	public void testGetSupplierByIdNonExisting() {

		given(documentationSpec).contentType(ContentType.JSON).when().get(SUPPLIER_WITH_ID_PATH, NON_EXISTING_SUPPLIER_ID)
				.then().statusCode(NOTFOUND_STATUS_CODE);
	}

	@Test
	public void testCreateSupplier() {
		String requestBody = "{\"name\":\"NewSupplier\",\"address\":\"SupplierAddress\",\"contactDetails\":\"1234\",\"specialties\":\"software\"}";

		given(documentationSpec).contentType(ContentType.JSON).body(requestBody).when().post(SUPPLIER_PATH).then()
				.statusCode(CREATED_STATUS_CODE).contentType(ContentType.JSON)
				.body(NAME, equalTo(SUPPLIER_NAME1))
				.body(ADDRESS, equalTo(SUPPLIER_ADDRESS1));
	}
	
	

	@Test
	public void testUpdateSupplier() {

		String requestBody = "{\"name\":\"UpdatedSupplier\",\"address\":\"456\",\"contactDetails\":\"1234\",\"specialties\":\"hardware\"}";

		given(documentationSpec).contentType(ContentType.JSON).body(requestBody).when()
				.put(SUPPLIER_WITH_ID_PATH, SUPPLIER_ID1).then().statusCode(OK_STATUS_CODE).contentType(ContentType.JSON)
				.body(NAME, equalTo(SUPPLIER_NAME2))
				.body(ADDRESS, equalTo(SUPPLIER_ADDRESS2));
	}

	@Test
	public void testDeleteSupplier() {

		given(documentationSpec).contentType(ContentType.JSON).when().delete(SUPPLIER_WITH_ID_PATH, SUPPLIER_ID2).then()
				.statusCode(NOCONTENT_STATUS_CODE);
	}

	@Test
	public void testUpdateNonExistingSupplier() {

		Supplier updatedSupplier = new Supplier();
		updatedSupplier.setAddress(SUPPLIER_ADDRESS2);

		given(documentationSpec).contentType(ContentType.JSON).body(updatedSupplier).when()
				.put("http://localhost:" + port + SUPPLIER_WITH_ID_PATH, NON_EXISTING_SUPPLIER_ID).then().statusCode(NOTFOUND_STATUS_CODE);
	}
}
