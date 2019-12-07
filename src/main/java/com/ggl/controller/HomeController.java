package com.ggl.controller;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import com.ggl.bo.GglBo;
//import com.ggl.dao.GglDao;

//import org.apache.commons.io.FilenameUtils;

//import java.io.IOException;


@Controller
@CrossOrigin
public class HomeController {
	
	@RequestMapping(value = {"/","/home"})
		public String home() {
		return "/index.html";
	}
}