package hr.algebra.service.impl;

import hr.algebra.exception.NotFoundException;
import hr.algebra.exception.OffLimitException;
import hr.algebra.model.UserConsumption;
import hr.algebra.model.UserContent;
import hr.algebra.model.UserPackage;
import hr.algebra.repository.UserConsumptionRepository;
import hr.algebra.repository.UserContentRepository;
import hr.algebra.repository.UserPackageRepository;
import hr.algebra.repository.UserRepository;
import hr.algebra.service.UserContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserContentServiceImpl implements UserContentService {

    private final UserContentRepository userContentRepository;

    private final UserPackageRepository userPackageRepository;

    private final UserConsumptionRepository userConsumptionRepository;

    private final UserRepository userRepository;

    @Autowired
    public UserContentServiceImpl(UserContentRepository userContentRepository, UserRepository userRepository, UserPackageRepository userPackageRepository, UserConsumptionRepository userConsumptionRepository) {
        this.userContentRepository = userContentRepository;
        this.userRepository = userRepository;
        this.userPackageRepository = userPackageRepository;
        this.userConsumptionRepository = userConsumptionRepository;
    }

    @Override
    public List<UserContent> getAllUsersContents() {
        return userContentRepository.findAll();
    }

    @Override
    public List<UserContent> getUserContents(Long idUser) {
        Stream<UserContent> userContentStream = getAllUsersContents().stream().filter((userContent -> userContent.getUser().getId().equals(idUser)));
        return userContentStream.collect(Collectors.toList());
    }

    @Override
    public void addUsersContent(UserContent userContent, String username) {
        UserPackage userPackage = userPackageRepository.findFirstByUserUsernameOrderByDateTimeAsc(username)
                .orElseThrow(() -> new NotFoundException("User with username not found"));

        userContent.setUser(userRepository.findById(userContent.getUser().getId()).orElse(userContent.getUser()));

        Optional<UserConsumption> userConsumption = userConsumptionRepository.findByDateTime(
                userContent.getUser().getId(),
                userContent.getContent().getDateTime().minusDays(1),
                userContent.getContent().getDateTime()).stream().findFirst();

        userConsumption.ifPresentOrElse(
                userConsumption1 -> ifConsumptionsExists(userContent, userPackage, userConsumption1),
                () -> ifConsumptionsNotExists(userContent, userPackage));
    }

    private UserContent ifConsumptionsExists(UserContent userContent, UserPackage userPackage, UserConsumption userConsumption1) {
        double dailyUploadSize = userConsumption1.getUploadSize() + userContent.getContent().getSize();

        if (userPackage.checkIfLimit(dailyUploadSize, userConsumption1.getDailyUploadLimit() + 1)) {
            userConsumption1.setDailyUploadLimit(userConsumption1.getDailyUploadLimit() + 1);
            userConsumption1.setUploadSize(dailyUploadSize);
            userConsumptionRepository.save(userConsumption1);
            return userContentRepository.save(userContent);
        }
        throw new OffLimitException();
    }

    private UserContent ifConsumptionsNotExists(UserContent userContent, UserPackage userPackage) {
        if (userContent.getContent().getSize() < userPackage.getCustomPackage().getUploadSize()) {
            userConsumptionRepository.save(new UserConsumption(userContent.getContent().getDateTime(),
                    userContent.getUser(),
                    userPackage.getCustomPackage(),
                    userContent.getContent().getSize(), 1));

            return userContentRepository.save(userContent);
        }
        throw new OffLimitException();
    }

    @Override
    public List<UserConsumption> getConsumptions() {
        return userConsumptionRepository.findAllByDateTime(LocalDateTime.now().minusDays(1), LocalDateTime.now());
    }

    @Override
    public UserConsumption getConsumptionsByUser() {
        List<UserConsumption> allByDateTime = userConsumptionRepository.findAllByDateTime(LocalDateTime.now().minusDays(1), LocalDateTime.now());
        if (!allByDateTime.isEmpty()) {
            return allByDateTime.get(0);
        }
        return null;
    }

    public static final Function<List<UserConsumption>, Boolean> CALCULATE_COSUMPTION = List::isEmpty;
}
