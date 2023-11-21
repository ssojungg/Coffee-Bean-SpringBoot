package jpabook1.jpashop1.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HelloContorlloer {


    @RequestMapping("/")
    public String hone() {
        log.info("home controller");
        return "home";

    }
}
