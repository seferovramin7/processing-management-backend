package az.processing.client.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("first")
public class FirstController {
	@GetMapping("test")
	public ResponseEntity<String> readFirst(Authentication authentication) {

		Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication1.getName();

		System.out.println("-----------------------------------");
		System.out.println(email);
		System.out.println("-----------------------------------");

		return ResponseEntity.ok("{\"status\":\"access granted\"}");
	}
}