package com.orderService.orderService;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)		//random port assign
class OrderServiceApplicationTests {

	private WireMockServer wireMockServer;


	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.0");
	@LocalServerPort
	private Integer port;

//	@BeforeEach
//	void setup() {
////		RestAssured
//	}


@BeforeEach
public void setup() {
	wireMockServer = new WireMockServer(); // Start on default port 8080
	wireMockServer.start();
	configureFor("localhost", wireMockServer.port());
}

	@AfterEach
	public void teardown() {
		wireMockServer.stop();
	}

	@Test
	public void testInventoryService() {
		String skuCode = "iphone_15";
		int quantity = 1;

		stubFor(get(urlEqualTo("/api/inventory?skuCode=" + skuCode + "&quantity=" + quantity))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("true")));

		// Call your service method that hits this stubbed endpoint
		// Assert the response as needed
	}
	@Test
	void contextLoads() {
	}
}
