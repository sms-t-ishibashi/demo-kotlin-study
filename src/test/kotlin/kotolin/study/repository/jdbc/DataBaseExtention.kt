package kotolin.study.repository.jdbc

import org.flywaydb.core.Flyway
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

/**
 * @author tomoharu-ishibashi
 */
fun createDatasource(driverClassName: String = "org.hsqldb.jdbc.JDBCDriver",
                     url: String = "jdbc:hsqldb:mem:memo",
                     username: String = "sa",
                     password: String = "")
        : DataSource {
    val dataSource = DriverManagerDataSource(url, username, password)
    dataSource.setDriverClassName(driverClassName)
    return dataSource
}

fun createJdbcTemplate(dataSource: DataSource = createDatasource()) = JdbcTemplate(dataSource)

// flyway
fun doFlywayMigration(dataSource: DataSource = createDatasource(),
                      schema: String = "PUBLIC",
                      location: String = "db/migration") {
    val flyway = Flyway()
    flyway.dataSource = dataSource
    flyway.setSchemas(schema)
    flyway.setLocations(location)

    flyway.migrate()
}
