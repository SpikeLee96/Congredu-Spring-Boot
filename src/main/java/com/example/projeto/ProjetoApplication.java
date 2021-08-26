package com.example.projeto;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.example.projeto.controllers.FileUploadController;

@SpringBootApplication
@ComponentScan({"com.example.projeto", "controller"})
public class ProjetoApplication {

	public static void main(String[] args) {
		new File(FileUploadController.uploadDirectory).mkdir();
		SpringApplication.run(ProjetoApplication.class, args);
	}

}
