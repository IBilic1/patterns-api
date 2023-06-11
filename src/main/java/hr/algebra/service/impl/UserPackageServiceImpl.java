package hr.algebra.service.impl;

import hr.algebra.exception.NotFoundException;
import hr.algebra.model.User;
import hr.algebra.model.UserConsumption;
import hr.algebra.model.UserPackage;
import hr.algebra.repository.PackageRepository;
import hr.algebra.repository.UserConsumptionRepository;
import hr.algebra.repository.UserPackageRepository;
import hr.algebra.repository.UserRepository;
import hr.algebra.service.UserPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserPackageServiceImpl implements UserPackageService {

    private final PackageRepository packageRepository;

    private final UserRepository userRepository;

    private final UserPackageRepository userPackageRepository;

    private final UserConsumptionRepository userConsumptionRepository;

    @Autowired
    public UserPackageServiceImpl(PackageRepository packageRepository, UserRepository userRepository, UserPackageRepository userPackageRepository, UserConsumptionRepository userConsumptionRepository) {
        this.packageRepository = packageRepository;
        this.userPackageRepository = userPackageRepository;
        this.userRepository = userRepository;
        this.userConsumptionRepository = userConsumptionRepository;
    }

    @Override
    public User signIn(UserPackage userPackage) {
        userPackage.setCustomPackage(packageRepository.findById(userPackage.getCustomPackage().getId()).orElse(userPackage.getCustomPackage()));
        if (!userRepository.existsByEmail(userPackage.getUser().getEmail()).isPresent()) {
            UserPackage savedUser = userPackageRepository.saveAndFlush(userPackage);
            return savedUser.getUser();
        }
        return null;
    }

    @Override
    public Optional<User> getUser() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername());
    }

    @Override
    public boolean updatePackage(UserPackage userPackage) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserPackage> byDateTime = userPackageRepository.findByDateTime(principal.getUsername(), userPackage.getDateTime().minusDays(1), userPackage.getDateTime());
        if (byDateTime.size() == 1) {
            userPackage.setCustomPackage(packageRepository.findById(userPackage.getCustomPackage().getId()).orElse(userPackage.getCustomPackage()));
            userPackage.setUser(userRepository.findById(userPackage.getUser().getId()).orElse(userPackage.getUser()));
            userPackageRepository.save(userPackage);
            userConsumptionRepository.save(new UserConsumption(userPackage.getDateTime(),
                    userPackage.getUser(),
                    userPackage.getCustomPackage(),
                    0, 0));
            return true;
        }

        return false;
    }

    @Override
    public List<UserPackage> getAllUsers() {
        return userPackageRepository.findAll();
    }

    @Override
    public UserPackage getUserPackage() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPackageRepository.findFirstByUserUsernameOrderByDateTimeAsc(principal.getUsername()).orElseThrow(() -> new NotFoundException("User with username not found"));
    }
}
