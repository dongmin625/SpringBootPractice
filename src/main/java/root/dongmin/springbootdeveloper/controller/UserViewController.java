package root.dongmin.springbootdeveloper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    @GetMapping("/login")
    public String login(){
        return "login"; // 로그인 페이지 이동(login.html)
    }

    @GetMapping("/signup")
    public String signup(){
        return "signup"; // 회원가입 페이지 이동(signup.html)
    }
}
