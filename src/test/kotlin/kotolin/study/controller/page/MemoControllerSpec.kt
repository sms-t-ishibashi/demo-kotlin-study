package kotolin.study.controller.page

import kotolin.study.model.Memo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Test

import org.junit.Assert.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.ModelResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

/**
 * @author tomoharu-ishibashi
 */
class MemoControllerSpec : Spek({

    val mvc = MockMvcBuilders.standaloneSetup(MemoController()).build()

    describe("/memoにGETアクセスする") {
        val result = mvc.perform(MockMvcRequestBuilders.get("/memo/")) // FIXME 何故か末尾のスラが要る

        it("ステータス200である") {
            result.andExpect(MockMvcResultMatchers.status().isOk)
        }
        it("memoのviewが返却される") {
            result.andExpect(MockMvcResultMatchers.view().name("memo"))
        }
        it("modelにitemsが存在すること") {
            result.andExpect(MockMvcResultMatchers.model().attributeExists("items"))
            var items = result.andReturn().modelAndView.modelMap["items"] as List<MutableMap<String, Any>>

            assertEquals(items.size, 3)
            assertEquals(items[0]["memo"], "Empty Memo")
            assertEquals(items[0]["author"], "Empty Author")
            assertEquals(items[1]["memo"], "memo memo")
            assertEquals(items[1]["author"], "abcde fghid")
            assertEquals(items[2]["memo"], "memo memo memo")
            assertEquals(items[2]["author"], "issy bassy gakky")
        }
    }
})