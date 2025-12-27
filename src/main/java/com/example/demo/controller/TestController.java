package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Tag(name = "Test Controller", description = "Test endpoints for Swagger verification")
public class TestController {

    @GetMapping("/hello")
    @Operation(summary = "Get hello message", description = "Returns a simple hello message")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello from Demo API!");
    }

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Returns application health status")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Application is running!");
    }
}