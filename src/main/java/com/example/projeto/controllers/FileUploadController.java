package com.example.projeto.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class FileUploadController {
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/uploads";
	
	@PostMapping("/upload")
	public String upload(@RequestParam("files") MultipartFile[] files, RedirectAttributes redirAttrs) {
		StringBuilder fileNames = new StringBuilder();
		for (MultipartFile file : files) {
			Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
			fileNames.append(file.getOriginalFilename());
			try {
				Files.write(fileNameAndPath, file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		redirAttrs.addFlashAttribute("success", "Arquivo upado com sucesso "+fileNames.toString()+" ");
		//return "paginas/uploadstatusview";
		return "redirect:/portal";
	}	

}
