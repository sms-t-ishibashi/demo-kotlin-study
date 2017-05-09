package kotolin.study.controller.page

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

/**
 * @author tomoharu-ishibashi
 */
@Controller
@RequestMapping("memo")
class MemoController {

    @RequestMapping("")
    fun get(model: Model): String {
//        List<Map<String, Object>> items = new ArrayList<>();
        val items = mutableListOf<MutableMap<String, Any>>()
//        val items2 = ArrayList<Map<String, Any>>()
//        val items3 = mutableListOf<Map<String, Any>>()

//        Map<String, Object> item = new HashMap<>();
        val item = LinkedHashMap<String, Any>()
        val item2 = hashMapOf<String, Any>()
        val item3 = mutableMapOf<String, Any>()

//        item.put("memo", "Empty Memo");
//        item.put("author", "Empty Author");
//        items.add(item);

        // Javaの記述
        item.put("memo", "Empty Memo")
        item.put("author", "Empty Author")
        items.add(item)

        // スクリプト言語っぽい記述
        item2["memo"] = "memo memo"
        item2["author"] = "Author bassy"
        items += item2

        // Kotlinの(?)記述
        item3 += ("memo" to "memo memo memo")
        item3 += ("author" to "issy bassy gakky")
        items += item3

//        model.addAttribute("items", items);
        model.addAttribute("items", items)
        return "memo"
    }
}