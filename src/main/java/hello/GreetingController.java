package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.ArrayList;

@Controller
public class GreetingController {
    private final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    public GreetingController(){
        logger.debug("Greeting controller created.");
    }

    @RequestMapping("/greeting")
    // http://localhost:8080/greeting?name=nnnn 
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        logger.info("Greeting endpoint called.");
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping(value="/dummy",produces="application/json")
    @ResponseBody
    // http://localhost:8080/dummy
    public List<Dummy> dummy(){
      List<Dummy> list= new java.util.ArrayList<Dummy>();
      Dummy dummy = new Dummy();
      dummy.setFieldA("AAA");
      dummy.setFieldB("CCC");
      list.add(dummy);

      Dummy dummy2 = new Dummy();
      dummy2.setFieldA("AAA2");
      dummy2.setFieldB("CCC2");
      list.add(dummy2);

      return list;
    }

}