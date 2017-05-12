package kotlin.study.repository

import kotolin.study.model.Memo
import org.springframework.stereotype.Repository

/**
 * @author tomoharu-ishibashi
 */
interface MemoRepository {
    fun find(): List<Memo>
    fun findByAuthor(author: String): List<Memo>
    fun save(item: Memo)
}