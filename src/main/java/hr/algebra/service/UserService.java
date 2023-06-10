package hr.algebra.service;

import hr.algebra.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User login(String email, String password);
}
