package hr.algebra.service;

import hr.algebra.model.User;
import hr.algebra.model.UserPackage;

import java.util.List;
import java.util.Optional;

public interface UserPackageService {

    User signIn(UserPackage userPackage);

    Optional<User> getUser();

    boolean updatePackage(UserPackage userPackage);

    List<UserPackage> getAllUsers();

    UserPackage getUserPackage();

}
