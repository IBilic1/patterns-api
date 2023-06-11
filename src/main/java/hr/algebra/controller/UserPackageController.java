package hr.algebra.controller;

import hr.algebra.dto.SuccessfullyUpdatedPackageDto;
import hr.algebra.dto.UserDto;
import hr.algebra.dto.UserPackageDto;
import hr.algebra.mapper.UserMapper;
import hr.algebra.mapper.UserPackageMapper;
import hr.algebra.model.User;
import hr.algebra.model.UserPackage;
import hr.algebra.service.UserPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user-package")
@Secured({"USER", "ADMIN"})
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class UserPackageController {

    private final UserPackageService userPackageService;

    private final UserPackageMapper userPackageMapper;

    private final UserMapper userMapper;

    @Autowired
    public UserPackageController(UserPackageService userPackageService, UserPackageMapper userPackageMapper, UserMapper userMapper) {
        this.userPackageService = userPackageService;
        this.userPackageMapper = userPackageMapper;
        this.userMapper = userMapper;
    }

    @PutMapping()
    public ResponseEntity<SuccessfullyUpdatedPackageDto> updateUserPackage(@RequestBody UserPackageDto userPackageDto) {
        UserPackage userPackage = userPackageMapper.to(userPackageDto);
        return new ResponseEntity<>(new SuccessfullyUpdatedPackageDto(userPackageService.updatePackage(userPackage)), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserPackageDto>> getAllUsersPackages() {
        return new ResponseEntity<>(userPackageMapper.mapToDto(userPackageService.getAllUsers()), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser() {
        Optional<User> user = userPackageService.getUser();
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(userMapper.from(user.get()));
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserPackageDto> get() {
        return new ResponseEntity<>(userPackageMapper.from(userPackageService.getUserPackage()), HttpStatus.OK);
    }

}
