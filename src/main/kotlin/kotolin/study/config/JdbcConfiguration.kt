package kotolin.study.config

import org.springframework.context.annotation.Configuration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource


/**
 * @author tomoharu-ishibashi
 */
@Configuration
class JdbcConfiguration (val dataSource: DataSource) {
    @Bean
    fun jdbcTemplate(): JdbcTemplate = JdbcTemplate(dataSource)
}