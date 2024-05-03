package org.dalesbred.internal.jdbc

import org.dalesbred.Database
import org.dalesbred.DatabaseException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ResultSetUtilsTest {

    private lateinit var db: Database

    companion object {
        @JvmStatic
        @Container
        private val mariaDb = MariaDBContainer("mariadb:lts")
    }

    @BeforeAll
    fun init() {
        db = Database.forUrlAndCredentials(mariaDb.jdbcUrl, mariaDb.username, mariaDb.password)
        db.update("drop table if exists binary_test")
        db.update("create table binary_test (id binary(16) primary key)")
        db.update("insert into binary_test (id) values (X'9D7F88947A1849F8862C0B3143C2E914')")
    }

    @Test
    fun `ResultSetUtils cannot handle BINARY type columns with MariaDB ConnectorJ 3`() {
        System.setProperty("byteArrayWorkaroundEnabled", "false")
        assertThrows<DatabaseException> {
            db.findUnique(ByteArray::class.java, "SELECT id FROM binary_test")
        }
    }

    @Test
    fun `Modified ResultSetUtils can handle BINARY type columns with MariaDB ConnectorJ 3`() {
        System.setProperty("byteArrayWorkaroundEnabled", "true")
        Assertions.assertTrue(db.findUnique(ByteArray::class.java, "SELECT id FROM binary_test").isNotEmpty())
    }

}
