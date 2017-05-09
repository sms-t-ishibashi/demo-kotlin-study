package controller.page

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
        val items = arrayListOf<Map<String, Any>>()
//        val items2 = ArrayList<Map<String, Any>>()
//        val items3 = mutableListOf<Map<String, Any>>()

//        Map<String, Object> item = new HashMap<>();
        val item = HashMap<String, Any>()
//        val item2 = hashMapOf<String, Any>()
//        val item3 = mutableMapOf<String, Any>()

//        item.put("memo", "Empty Memo");
//        item.put("author", "Empty Author");
//        items.add(item);
        item.put("memo", "Empty")
        item.put("author", "Empty Author")
        items.add(item)

//        model.addAttribute("items", items);
        model.addAttribute(items)
        return "memo"
    }
}