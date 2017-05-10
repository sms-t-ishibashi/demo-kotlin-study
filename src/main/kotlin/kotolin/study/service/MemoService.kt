package kotolin.study.service

import kotolin.study.model.Memo
import org.springframework.stereotype.Service
import java.util.*


/**
 * @author tomoharu-ishibashi
 */
@Service
class MemoService {
    fun join(memo: String, author: String): Memo = Memo(memo, author, Date())
}