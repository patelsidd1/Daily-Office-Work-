package MvcOrm.Controller;
import MvcOrm.Model.User;
import MvcOrm.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FormController {
    @Autowired
    private UserService userService;


    @RequestMapping("/form")
    public String showForm(Model m) {
        m.addAttribute("Header", "Learn Code With Sid ");
        m.addAttribute("Desc", "Home for Programmer ");
        System.out.println("This is form page ");
        return "form";
    }

    @RequestMapping(path = "/processform", method = RequestMethod.POST)
    public String handleForm(@ModelAttribute("user") User user, Model model) {

        System.out.println(user);
        int  createdUser = this.userService.createUser(user);



        model.addAttribute("user", user);
        model.addAttribute("Header", "Learn Code With Sid ");
        model.addAttribute("Desc", "Home for Programmer ");
        model.addAttribute("msg","User created with Id " +createdUser);

        model.addAttribute("email", user.getEmail());
        model.addAttribute("name", user.getUserName());
        model.addAttribute("password",user.getPassword());

        return "sucess";

    }

    // 1st Method:--
//    @RequestMapping(path = "/processform", method = RequestMethod.POST )
//    public String handleForm(HttpServletRequest request){
//        String email = request.getParameter("email");
//        String userName = request.getParameter("userName");
//        String password = request.getParameter("password");
//        System.out.println("User email:  " +email);
//        System.out.println("User Name:  " +userName);
//        System.out.println("Password:  " +password);
//
//        return "";
//    }

    // 2nd Method:--
//    @RequestMapping(path = "/processform", method = RequestMethod.POST)
//    public String handle(@RequestParam("email") String userEmail,
//                         @RequestParam("userName") String userName,
//                         @RequestParam("password") String password, MvcOrm.Service.Model model) {
//        User user = new User();
//        user.setEmail(userEmail);
//        user.setUserName(userName);
//        user.setPassword(password);
//
//
//        System.out.println(userEmail);
//        System.out.println(userName);
//        System.out.println(password);
//
//        model.addAttribute("name", userName);
//        model.addAttribute("email", userEmail);
//        model.addAttribute("password", password);
//        return "sucess";
//    }
}

