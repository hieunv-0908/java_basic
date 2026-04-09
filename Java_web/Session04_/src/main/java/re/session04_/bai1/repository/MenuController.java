package re.session04_.bai1.repository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// code gây lỗi 400
//@Controller
//public class MenuController {
//
//    @GetMapping("/bai2/menu")
//    @ResponseBody
//    public String getMenu(@RequestParam("category") String category) {
//        return "Bạn đang xem menu loại: " + category;
//    }
//}
// Khi chạy:
//
//Truy cập:
//
//http://localhost:8080/bai2/menu
//
//Kết quả:
//
//Lỗi 400 Bad Request
//Vì category là bắt buộc (required = true mặc định)

// code đã sửa
// @Controller
@Controller
public class MenuController {

    @GetMapping("/bai2/menu")
    @ResponseBody
    public String getMenu(
            @RequestParam(value = "category", required = false, defaultValue = "chay") String category
    ) {
        return "Bạn đang xem menu loại: " + category;
    }
}