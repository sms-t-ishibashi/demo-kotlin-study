package kotlin.study.repository.jdbc

import kotolin.study.model.Memo
import kotolin.study.repository.MemoRepository
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.on
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * @author tomoharu-ishibashi
 */
class JdbcMemoRepositorySpec : Spek({

    doFlywayMigration()

    val jdbcTemplate = createJdbcTemplate()
    val repository : MemoRepository = JdbcMemoRepository(jdbcTemplate)

    describe("find実行時") {
        val result = repository.find()

        on("件数は4件である") {
            assertEquals(result.size, 4)
        }

        on("取得したデータを確認する") {
            assertEquals(result[0].memo, "Springを学ぶ")
            assertEquals(result[0].author, "金次郎")
            assertEquals(result[1].memo, "Thymeleafを学ぶ")
            assertEquals(result[1].author, "金次郎")
            assertEquals(result[2].memo, "Flywayを学ぶ")
            assertEquals(result[2].author, "金次郎")
            assertEquals(result[3].memo, "AspectJを生麩")
            assertEquals(result[3].author, "金字塔")
        }
    }

    describe("findByAuthor実行時") {
        val paramAuthor = "金字塔"
        val result = repository.findByAuthor(paramAuthor)

        on("取得件数は1件である") {
            assertEquals(result.size, 1)
        }

        on("$paramAuthor のデータが取得できる") {
            assertEquals(result[0].memo, "AspectJを生麩")
            assertEquals(result[0].author, "金字塔")
        }
    }

    describe("save実行時") {
        val paramMemo = "ジロウラーメン"
        val paramAuthor = "ラーメン二郎"

        repository.save(Memo(paramMemo, paramAuthor, Date()))

        on("メモを1件保存できる") {
            val result = repository.findByAuthor(paramAuthor)

            assertEquals(result[0].memo, paramMemo)
            assertEquals(result[0].author, paramAuthor)
        }
    }
})