package zhou.hardcat.communtiy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublishController {
@RequestMapping("/publish")
public String toPublic(){
 return "publish" ;
}
}
