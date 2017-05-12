package kotlin.study.service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import kotolin.study.model.Memo
import kotolin.study.repository.MemoRepository
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert.assertEquals
import org.mockito.Mockito

/**
 * @author tomoharu-ishibashi
 */
class MemoServiceSpec : Spek({

    // repository mocking
    val memoRepository: MemoRepository = mock {
        on { find() }.doReturn(listOf<Memo>(
                Memo("memo", "author", null)
        ))

        on { findByAuthor(any()) }.doReturn(listOf<Memo>(
                Memo("memo", "author", null)
        ))
    }

    val memoService = MemoService(memoRepository)

    describe("joinに引数を渡す") {

        val paramMemo = "testMemo"
        val paramAuthor = "testAuthor"

        val expected = Memo(paramMemo, paramAuthor, null)
        val actual = memoService.join(paramMemo, paramAuthor)

        it("joinの戻り値が期待値通りであること") {
            assertEquals(actual.memo, expected.memo)
            assertEquals(actual.author, expected.author)
        }
    }

    describe("readAllを呼び出す") {
        memoService.readAll()

        it("リポジトリのfindを呼び出すこと") {
            verify(memoRepository, Mockito.times(1)).find()
        }
    }

    describe("readByAuthorを呼び出す") {
        memoService.readByAuthor("author")

        it("リポジトリのfindByAuthorを呼び出すこと") {
            verify(memoRepository, Mockito.times(1)).findByAuthor(any())
        }
    }

    describe("writeを呼び出す") {
        memoService.write("memo", "author")

        it("リポジトリのsaveを呼び出すこと") {
            verify(memoRepository, Mockito.times(1)).save(any())
        }
    }
})