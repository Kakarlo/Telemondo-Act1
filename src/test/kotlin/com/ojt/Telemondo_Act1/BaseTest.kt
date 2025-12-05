package com.ojt.Telemondo_Act1

import com.github.database.rider.core.api.configuration.DBUnit
import com.github.database.rider.spring.api.DBRider
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
@DBRider
@DBUnit(
    schema = "testdb",
    caseSensitiveTableNames = true,
    alwaysCleanBefore = true,
    alwaysCleanAfter = true,
)
class BaseTest {
    companion object {
        @Container
        @JvmStatic
        val mysql: MySQLContainer<*> =
            MySQLContainer("mysql:9.2")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test")

        @JvmStatic
        @DynamicPropertySource
        fun registerProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", mysql::getJdbcUrl)
            registry.add("spring.datasource.username", mysql::getUsername)
            registry.add("spring.datasource.password", mysql::getPassword)

            // Ensure Flyway points to the Testcontainers DB
            registry.add("spring.flyway.url", mysql::getJdbcUrl)
            registry.add("spring.flyway.user", mysql::getUsername)
            registry.add("spring.flyway.password", mysql::getPassword)
        }
    }
}
