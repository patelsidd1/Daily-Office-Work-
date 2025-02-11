package MvcOrm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @RequestMapping("/")
    public String home(){
        System.out.println("This is home page ");
        return "index";
    }
    @RequestMapping("/about")
    public ModelAndView about(){
        System.out.println("This is about section ");

        // Creating object of ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        //Setting the Values
        modelAndView.addObject("name","Kishan");
        modelAndView.addObject("id",214);
        LocalDateTime now = LocalDateTime.now();
        modelAndView.addObject("time",now );

        //Setting the View name
        modelAndView.setViewName("about");

        return modelAndView;

    }
    @RequestMapping("/help")
    public  String help(Model model){

        System.out.println("This is help Section ");
        model.addAttribute("name","Siddhant patel");
        model.addAttribute("id",25);

        List<String > friends = new ArrayList<>();
        friends.add("Ravi ");
        friends.add("Kishan");
        friends.add("Raj ");
        friends.add("Ashwani");

        model.addAttribute("fr", friends);

        return "help";
    }


}
