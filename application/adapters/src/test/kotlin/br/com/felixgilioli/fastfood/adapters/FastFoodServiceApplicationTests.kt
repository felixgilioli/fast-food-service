package br.com.felixgilioli.fastfood.adapters

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FastFoodServiceApplicationTests(
    @Autowired val restTemplate: TestRestTemplate
) {

    @LocalServerPort
    private var port: Int = 0

    @Test
    fun contextLoadsSuccessfully() {
        assertNotNull(restTemplate)
    }

    @Test
    fun healthEndpointReturnsOk() {
        val response = restTemplate.getForEntity("http://localhost:$port/actuator/health", String::class.java)
        assertNotNull(response.body)
        assert(response.statusCode.is2xxSuccessful)
        assert(response.body!!.contains("\"status\":\"UP\""))
    }
}