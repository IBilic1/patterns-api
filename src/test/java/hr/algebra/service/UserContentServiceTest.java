package hr.algebra.service;

import hr.algebra.exception.OffLimitException;
import hr.algebra.model.Package;
import hr.algebra.model.*;
import hr.algebra.repository.UserConsumptionRepository;
import hr.algebra.repository.UserContentRepository;
import hr.algebra.repository.UserPackageRepository;
import hr.algebra.repository.UserRepository;
import hr.algebra.service.impl.UserContentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    private Content content;
    private UserPackage userPackage;
    private User user;
    private UserContent userContent;
    private Package package_ ;
    private UserConsumption userConsumption;
    private List<UserConsumption> userConsumptions;

    private

    @BeforeEach
    void setUp() {
        content = new Content();
        content.setDateTime(LocalDateTime.now());
        content.setSize(120);

        user = new User();
        user.setId(1L);
        user.setPassword("abcd123");
        user.setUsername("eva123");
        user.setEmail("eva123@gmail.com");
        user.setRoleId(Roles.USER.getId());

        userContent = new UserContent();
        userContent.setId(1);
        userContent.setContent(content);
        userContent.setUser(user);

        package_ = new Package();
        package_.setId(15L);
        package_.setDailyUploadLimit(10);
        package_.setUploadSize(10000);

        userPackage = new UserPackage();
        userPackage.setDateTime(LocalDateTime.now());
        userPackage.setUser(user);
        userPackage.setCustomPackage(package_);

        userConsumptions = new ArrayList<>();
        userConsumption = new UserConsumption(LocalDateTime.now(), user, package_, 1, 2);
        userConsumptions.add(userConsumption);
    }

    @Test
    void when_add_users_content_should_return_saved_content() {
        when(userPackageRepository.findFirstByUserUsernameOrderByDateTimeAsc(any())).thenReturn(Optional.ofNullable(userPackage));
        when(userConsumptionRepository.findByDateTime(any(), any(), any())).thenReturn(userConsumptions);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(userContentRepository.save(any())).thenReturn(new UserContent());

        userContentService.addUsersContent(userContent, "eva123");
        verify(userContentRepository, times(1)).save(any());
    }

    @Test
    void when_add_users_content_should_return_not_content() {
        userConsumption.setDailyUploadLimit(10);
        userConsumption.setUploadSize(9999);

        when(userPackageRepository.findFirstByUserUsernameOrderByDateTimeAsc(any())).thenReturn(Optional.ofNullable(userPackage));
        when(userConsumptionRepository.findByDateTime(any(), any(), any())).thenReturn(userConsumptions);
        verify(userRepository, never()).findById(any());

        assertThrows(
                OffLimitException.class,
                () -> userContentService.addUsersContent(userContent, "eva123"));
    }
}
