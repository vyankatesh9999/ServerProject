/**
 * 
 */
package com.ServerProject.employee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ServerProject.employee.payload.CheckHealthResponse;

/**
 * @author Vyanky
 *
 */
@RestController
@RequestMapping("/api/health")
public class HealthCheckController {

	@GetMapping("/user/checkHealth")
	public CheckHealthResponse checkHealth() {
		CheckHealthResponse checkHealth = new CheckHealthResponse();
		checkHealth.setAvailable(true);
		return checkHealth;
	}
}
