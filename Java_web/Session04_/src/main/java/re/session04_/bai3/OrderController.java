package re.session04_.bai3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {

    @GetMapping("/bai3/orders/{id}")
    @ResponseBody
    public String getOrderDetail(@PathVariable("id") int id) {
        return "Chi tiết đơn hàng số " + id;
    }
}
