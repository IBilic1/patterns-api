package hr.algebra.service;

import hr.algebra.model.Package;
import hr.algebra.model.*;
import hr.algebra.repository.UserConsumptionRepository;
import hr.algebra.repository.UserContentRepository;
import hr.algebra.repository.UserPackageRepository;
import hr.algebra.repository.UserRepository;
import hr.algebra.service.impl.UserContentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserContentServiceTest {

    @Mock
    private UserContentRepository userContentRepository;

    @Mock
    private UserPackageRepository userPackageRepository;

    @Mock
    private UserConsumptionRepository userConsumptionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserContentServiceImpl userContentService;

    @Test
    void addUsersContent() {
        List<Package> packages = new ArrayList<>();
        packages.add(new Package(15L, "", 15000, 10, 10));
        UserPackage userPackageDto = new UserPackage();
        User userDto = new User();
        userDto.setPassword("abcd123");
        userDto.setUsername("eva123");
        userDto.setEmail("eva123@gmail.com");
        userDto.setRoleId(Roles.USER.getId());

        Package package_ = new Package();
        package_.setId(15L);
        userPackageDto.setDateTime(LocalDateTime.now());

        userPackageDto.setUser(userDto);
        userPackageDto.setCustomPackage(package_);

        List<UserConsumption> userConsumptions = new ArrayList<>();
        userConsumptions.add(new UserConsumption(LocalDateTime.now(), userDto, package_, 100000, 10));
    }

    @Test
    void getAllUsersContents(){

    }

    @Test
    void getUserContents(){

    }
}
