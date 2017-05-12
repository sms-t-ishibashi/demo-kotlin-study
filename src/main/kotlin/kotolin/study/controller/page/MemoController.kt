package kotolin.study.controller.page

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import kotolin.study.model.Memo
import kotolin.study.service.MemoService
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping


/**
 * @author tomoharu-ishibashi
 */
@Controller
@RequestMapping("memo")
class MemoController(val memoService: MemoService) {

    @GetMapping
    fun get(model: Model): String {
        model.addAttribute("items", memoService.readAll())
        return "memo"
    }

    @GetMapping("author/{author}")
    operator fun get(@PathVariable author: String,
                     model: Model): String {
        model.addAttribute("items", memoService.readByAuthor(author))
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
        memoService.write(item.memo as String, item.author as String)
        return "redirect:/memo"
    }
}