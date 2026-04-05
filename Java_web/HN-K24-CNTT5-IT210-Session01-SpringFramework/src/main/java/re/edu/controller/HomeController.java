package re.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import re.edu.utils.InputMethod;

@Controller
public class HomeController {
//    DI
//    @Autowired
    private InputMethod inputMethod;
//    @Autowired
//    public void setInputMethod(InputMethod inputMethod) {
//        this.inputMethod = inputMethod;
//    }
        public HomeController(InputMethod inputMethod) {
        this.inputMethod = inputMethod;
    }

    @GetMapping // xử lý theo phương thức GET
    public String homePage(){
        System.out.println("input : "+inputMethod.getString());
        return "home";
    }
}
