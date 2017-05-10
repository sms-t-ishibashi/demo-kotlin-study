package kotolin.study.service

import kotolin.study.model.Memo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert.assertEquals

/**
 * @author tomoharu-ishibashi
 */
class MemoServiceSpec : Spek({

    describe("joinに引数を渡す") {
        val memoService = MemoService()

        val paramMemo = "testMemo"
        val paramAuthor = "testAuthor"

        val expected = Memo(paramMemo, paramAuthor, null)
        val actual = memoService.join(paramMemo, paramAuthor)

        it("joinの戻り値が期待値通りであること") {
            assertEquals(actual.memo, expected.memo)
            assertEquals(actual.author, expected.author)
        }

    }
})