package study.service

import study.model.Memo
import study.repository.MemoRepository
import org.springframework.stereotype.Service
import java.util.*


/**
 * @author tomoharu-ishibashi
 */
@Service
class MemoService(val memoRepository: MemoRepository) {
    fun join(memo: String, author: String): Memo = Memo(memo, author, Date())

    fun readAll(): List<Memo> = memoRepository.find()

    fun readByAuthor(author: String): List<Memo> = memoRepository.findByAuthor(author)

    fun write(memo: String, author: String) = memoRepository.save(Memo(memo, author, Date()))
}