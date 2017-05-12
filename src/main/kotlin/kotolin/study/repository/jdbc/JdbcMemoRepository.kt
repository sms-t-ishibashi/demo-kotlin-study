package kotolin.study.repository.jdbc

import kotolin.study.model.Memo
import kotolin.study.repository.MemoRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository


/**
 * @author tomoharu-ishibashi
 */
@Repository
class JdbcMemoRepository(val jdbcTemplate: JdbcTemplate) : MemoRepository {
    override fun find(): List<Memo> {
        return jdbcTemplate.query(
                """
                  SELECT MEMO, AUTHOR 
                  FROM MEMO 
                  ORDER BY CREATED ASC
                """,
                { resultSet, i ->
                    Memo(resultSet.getString("MEMO"), resultSet.getString("AUTHOR"), null)
                })
    }

    override fun findByAuthor(author: String): List<Memo> {
        return jdbcTemplate.query(
                "SELECT MEMO, AUTHOR FROM MEMO WHERE AUTHOR = ? ORDER BY CREATED ASC",
                { resultSet, i -> Memo(resultSet.getString("MEMO"), resultSet.getString("AUTHOR"), null) },
                arrayOf(author))
    }

    override fun save(item: Memo) {
        jdbcTemplate.update(
                "INSERT INTO MEMO (MEMO, AUTHOR, CREATED) VALUES (?, ?, CURRENT_TIMESTAMP)",
                item.memo, item.author);
    }
}