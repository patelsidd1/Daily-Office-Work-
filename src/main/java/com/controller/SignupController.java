package com.controller;

import com.dao.UserDao;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;

import java.util.Random;

@Controller
@RequestMapping("/otp")
public class SignupController {

    @Autowired
    private UserDao userDao;

    @PostMapping("/generate")
    public String generateOTP(@RequestParam("mobile") String mobile, HttpSession session, Model m) {
        if (!mobile.matches("\\d{10}")) {
            m.addAttribute("error", "Invalid mobile number! Please enter a 10-digit number.");
            return "signup";
        }

        int generatedOTP = new Random().nextInt(900000) + 100000;
        // OTP is valid for 5 minutes
        long expiryTime = System.currentTimeMillis() + (30);

        session.setAttribute("otp", generatedOTP);
        session.setAttribute("otpExpiry", expiryTime);
        session.setAttribute("mobile", mobile);

        System.out.println("Generated OTP: " + generatedOTP);

        m.addAttribute("message", "OTP sent successfully! Valid for 30 sec.");

        return "otp";
    }

    @PostMapping("/verifyotp")
    public String verifyOtp(@RequestParam("otp") String enteredOtp, Model m, HttpSession session) {
        // Validate OTP format
        if (!enteredOtp.matches("\\d{6}")) {
            m.addAttribute("error", "Invalid OTP. Please enter a 6-digit number.");
            return "otp";
        }

        // Check if OTP has expired
        long currentTime = System.currentTimeMillis() + (5*1000);
        Long expiryTime = (Long) session.getAttribute("otpExpiry");

        if (expiryTime == null || currentTime > expiryTime) {
            m.addAttribute("error", "OTP has expired. Please generate a new one.");
            return "signup"; // Redirect to signup page
        }

        // Verify OTP
        int otp = Integer.parseInt(enteredOtp);
        String mobile = (String) session.getAttribute("mobile");

        if (mobile == null) {
            m.addAttribute("error", "Session expired. Please generate OTP again.");
            return "signup";
        }

        int storedOtp = (int) session.getAttribute("otp");

        if (otp == storedOtp) {
            User existingUser = userDao.getUserByMobile(mobile);

            if (existingUser != null) {
                m.addAttribute("error", "User already exists. Please login.");
                return "otp";
            }

            m.addAttribute("mobile", mobile);
            return "registrationform";
        } else {
            m.addAttribute("error", "Invalid OTP. Try again!");
            return "otp";
        }
    }

    @PostMapping("/registerUser")
    public RedirectView handleUserRegistration(@ModelAttribute User user,
                                               @RequestParam("pImage") CommonsMultipartFile file,
                                               HttpSession session,
                                               HttpServletRequest request, Model model) {

        System.out.println("File Upload Handler ");
        System.out.println(file.getName());

        byte[] data = file.getBytes();
        //we have to save this file to server
        String path = session.getServletContext().getRealPath("/") + "images" + File.separator + file.getOriginalFilename();

        try {
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(data);
            fos.close();
            System.out.println("File Uploaded Sucessfully ");

            model.addAttribute("filename", file.getOriginalFilename());
            model.addAttribute("msg", "Uploaded Sucessfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Uploading Error ");
            model.addAttribute("msg", "Uploading error ");
        }
        System.out.println(path);


        String mobile = (String) session.getAttribute("mobile");

        if (mobile != null) {
            user.setMobile(mobile);

            user.setProfileImage(file.getOriginalFilename());

            userDao.createDetails(user);
            System.out.println("User Registered: " + user);
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(request.getContextPath() + "/otp/regsucess");
        return redirectView;

    }
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model m) {
        String mobile = (String) session.getAttribute("mobile");


        if (mobile == null) {
            return "redirect:/otp";
        }

        User user = userDao.getUserByMobile(mobile);
        m.addAttribute("user", user);

        return "regsucess";
    }

    @GetMapping("/regsucess")
    public String regSuccess() {
        return "regsucess";
    }
}
