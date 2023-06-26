package com.example.filestorage.filestorageapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @io.swagger.v3.oas.annotations.info.Info(
				title = "File Storage API",
				version = "1.0",
				description = "File Storage API",
				contact = @Contact(
						name = "File Storage API",
						email = "ertugrulkuyucu@gmail.com"
				),
				license = @License(
						name = "ertugrulkuyucu",
						url = "https://github.com/ertugrulkuyucu"
				)
		)
)
public class FileStorageApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileStorageApiApplication.class, args);
	}

}
