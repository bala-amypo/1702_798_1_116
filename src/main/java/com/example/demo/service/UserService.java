package com.example.demo.service;



import java.util.List;

public interface UserService {

    User registerUser(String email, String password);

    User getUserByEmail(String email);
}
