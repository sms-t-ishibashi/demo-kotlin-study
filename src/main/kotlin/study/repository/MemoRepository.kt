package study.repository

import study.model.Memo

/**
 * @author tomoharu-ishibashi
 */
interface MemoRepository {
    fun find(): List<Memo>
    fun findByAuthor(author: String): List<Memo>
    fun save(item: Memo)
}