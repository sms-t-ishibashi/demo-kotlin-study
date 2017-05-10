package kotolin.study.controller.page

import kotolin.study.model.Memo
import kotolin.study.service.MemoService
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.data_driven.data
import org.jetbrains.spek.data_driven.on
import org.junit.Assert.assertEquals
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

/**
 * @author tomoharu-ishibashi
 */
@SpringBootTest
class MemoControllerSpec : Spek({

    // TODO AutoWiredとモック作成
    val mvc = MockMvcBuilders.standaloneSetup(MemoController(MemoService())).build()

    given("/memoにGETリクエストした場合") {
        val result = mvc.perform(MockMvcRequestBuilders.get("/memo/")) // FIXME 何故か末尾のスラが要る

        on("レスポンスを確認する") {
            it("ステータス200である") {
                result.andExpect(MockMvcResultMatchers.status().isOk)
            }
            it("memoのviewが返却される") {
                result.andExpect(MockMvcResultMatchers.view().name("memo"))
            }
            it("modelにitemsが存在すること") {
                result.andExpect(MockMvcResultMatchers.model().attributeExists("items"))

                var items = result.andReturn().modelAndView.modelMap["items"] as List<Memo>
                assertEquals(items.size, 3)
                assertEquals(items[0].memo, "Empty Memo")
                assertEquals(items[0].author, "Empty Author")
                assertEquals(items[1].memo, "memo memo")
                assertEquals(items[1].author, "abcde fghid")
                assertEquals(items[2].memo, "memo memo memo")
                assertEquals(items[2].author, "issy bassy gakky")
            }
        }
    }

    describe("/memoにPOSTリクエストした場合のレスポンスを確認する") {
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params["memo"] = "submitted memo"
        params["author"] = "submitted author"

        val result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/memo/") // FIXME 何故か末尾のスラが要る
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .characterEncoding("UTF-8")
                        .params(params))

        it("ステータスは200である") {
            result.andExpect(MockMvcResultMatchers.status().isOk)
        }
        it("memoのviewが返却される") {
            result.andExpect(MockMvcResultMatchers.view().name("memo"))
        }
        it("modelにitemsが存在すること") {
            result.andExpect(MockMvcResultMatchers.model().attributeExists("items"))

            var items = result.andReturn().modelAndView.modelMap["items"] as List<Memo>
            assertEquals(items.size, 1)
            assertEquals(items[0].memo, "submitted memo")
            assertEquals(items[0].author, "submitted author")
        }
    }

    /*
        パラメータのパターン
        memo            | author      | patern
        半角英語小文字のみ | 無し        | memoは正規表現にマッチする
        半角英語大文字のみ | 無し        | memoは正規表現にマッチする
        半角数字のみ      | 無し        | memoは正規表現にマッチする
        半角英数混在      | 無し        | memoは正規表現にマッチする
        任意の半角英数字   | 任意の文字列 | memoは正規表現にマッチする
        半角英数字以外    | 任意の文字列  | memoは正規表現にマッチしない
        無し             | 無し        | memoは正規表現にマッチしない
        無し             | 任意の文字列 | memoは正規表現にマッチしない
     */
    given("/memo/param/{memo:[a-zA-Z0-9]+}にGETリクエストした場合") {

        val successParams = arrayOf(
                // memo, author, expected memo and expected author
                data("abc", "", expected = ("abc" to "Default Author")),
                data("XYZ", "", expected = ("XYZ" to "Default Author")),
                data("777", "", expected = ("777" to "Default Author")),
                data("abCDef99", "", expected = ("abCDef99" to "Default Author")),
                data("abCDef99", "Query Author", expected = ("abCDef99" to "Query Author"))
        )

        val failureParams = arrayOf(
                data("ａｂｃ", "", HttpStatus.NOT_FOUND),
                data("ＸＹＺ", "", HttpStatus.NOT_FOUND),
                data("７７７", "", HttpStatus.NOT_FOUND),
                data("$%^&*あいうえお", "", HttpStatus.NOT_FOUND),
                data("", "", HttpStatus.NOT_FOUND),
                data("", "Query Author", HttpStatus.NOT_FOUND)
        )

        on("パラメータ memo= %s , author= %s の場合のレスポンスを確認する", with = *successParams) {
            paramMemo, paramAuthor, expected ->

            val result = mvc.perform(
                    MockMvcRequestBuilders.get("/memo/param/$paramMemo").param("author", paramAuthor))

            it("ステータスは200である") {
                result.andExpect(MockMvcResultMatchers.status().isOk)
            }
            it("memoのviewが返却される") {
                result.andExpect(MockMvcResultMatchers.view().name("memo"))
            }
            it("modelにitemsが存在すること") {
                result.andExpect(MockMvcResultMatchers.model().attributeExists("items"))

                var items = result.andReturn().modelAndView.modelMap["items"] as List<Memo>
                assertEquals(items.size, 1)
                assertEquals(items[0].memo, expected.first)
                assertEquals(items[0].author, expected.second)
            }
        }

        on("パラメータ memo= %s , author= %s の場合のレスポンスを確認する", with = *failureParams) {
            paramMemo, paramAuthor, expected ->

            val result = mvc.perform(
                    MockMvcRequestBuilders.get("/memo/param/$paramMemo").param("author", paramAuthor))

            it("ステータスは $expected である") {
                result.andExpect(MockMvcResultMatchers.status().isNotFound)
            }
        }
    }
})