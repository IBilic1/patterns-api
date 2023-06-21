package hr.algebra.controller;

import hr.algebra.config.jwt.JwtUtils;
import hr.algebra.config.services.RefreshTokenService;
import hr.algebra.dto.LoginUserDto;
import hr.algebra.dto.TokensDto;
import hr.algebra.dto.UserPackageDto;
import hr.algebra.mapper.UserPackageMapper;
import hr.algebra.model.RefreshToken;
import hr.algebra.model.Roles;
import hr.algebra.model.User;
import hr.algebra.model.UserPackage;
import hr.algebra.repository.UserRepository;
import hr.algebra.service.UserPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    AuthenticationManager authenticationManager;

    UserRepository userRepository;

    UserPackageService userPackageService;

    private final UserPackageMapper userPackageMapper;

    PasswordEncoder encoder;

    JwtUtils jwtUtils;

    RefreshTokenService refreshTokenService;

    @Value("${patterns.app.jwtRefreshCookieName}")
    private String jwtRefreshCookie;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JwtUtils jwtUtils, RefreshTokenService refreshTokenService, UserPackageService userPackageService, UserPackageMapper userPackageMapper) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.userPackageService = userPackageService;
        this.userPackageMapper = userPackageMapper;
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<TokensDto> authenticateUser(@Valid @RequestBody LoginUserDto loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User userDetails = (User) authentication.getPrincipal();


        String jwt = jwtUtils.generateTokenFromUsername(userDetails.getUsername(), Arrays.asList(Roles.get(userDetails.getRoleId())));
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok().body(new TokensDto(jwt, refreshToken.getToken()));
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> registerUser(@RequestBody UserPackageDto userPackageDto) {
        userPackageDto.getUser().setPassword(encoder.encode(userPackageDto.getUser().getPassword()));
        UserPackage userPackage = userPackageMapper.to(userPackageDto);
        if (userRepository.findByUsername(userPackage.getUser().getUsername()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        userPackageService.signIn(userPackage);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/signout")
    public ResponseEntity<Void> logoutUser() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!principle.toString().equals("anonymousUser")) {
            Long userId = ((User) principle).getId();
            refreshTokenService.deleteByUserId(userId);
        }

        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<TokensDto> refreshtoken(HttpServletRequest request) {
        String refreshToken = jwtUtils.getToken(request, jwtRefreshCookie);

        if ((refreshToken != null) && (refreshToken.length() > 0)) {
            Optional<RefreshToken> refreshTokenFromDatabase = refreshTokenService.findByToken(refreshToken);

            Optional<User> user = refreshTokenFromDatabase.map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUser);

            if (user.isPresent()) {
                String jwt = jwtUtils.generateTokenFromUsername(user.get().getUsername(), Arrays.asList(Roles.get(user.get().getRoleId())));

                return ResponseEntity.ok().body(new TokensDto(jwt, refreshToken));
            }
        }

        return ResponseEntity.badRequest().build();
    }
}
