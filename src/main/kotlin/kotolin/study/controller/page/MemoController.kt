package kotolin.study.controller.page

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import kotolin.study.model.Memo
import kotolin.study.service.MemoService
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*


/**
 * @author tomoharu-ishibashi
 */
@Controller
@RequestMapping("memo")
class MemoController(val memoService: MemoService) {

    @GetMapping
    fun get(model: Model): String {
        val items = mutableListOf<Memo>()
        items += memoService.join("Empty Memo", "Empty Author")
        items += memoService.join("memo memo", "abcde fghid")
        items += memoService.join("memo memo memo", "issy bassy gakky")
        model.addAttribute("items", items)
        return "memo"
    }

    @GetMapping("param/{memo:[a-zA-Z0-9]+}")
    fun getParams(@PathVariable memo: String,
                  @RequestParam(required = false, defaultValue = "Default Author") author: String,
                  model: Model): String {
        model.addAttribute("items", listOf(memoService.join(memo, author)))
        return "memo"
    }

    @PostMapping
    fun post(@ModelAttribute item: Memo, model: Model): String {
        model.addAttribute("items", listOf(item))
        return "memo"
    }
}