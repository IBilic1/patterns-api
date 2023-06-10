package hr.algebra.service.impl;

import hr.algebra.model.UserConsumption;
import hr.algebra.model.UserContent;
import hr.algebra.model.UserPackage;
import hr.algebra.repository.UserConsumptionRepository;
import hr.algebra.repository.UserContentRepository;
import hr.algebra.repository.UserPackageRepository;
import hr.algebra.repository.UserRepository;
import hr.algebra.service.UserContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
    public UserContent addUsersContent(UserContent userContent) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPackage usersPackage = userPackageRepository.findFirstByUserUsernameOrderByDateTimeAsc(principal.getUsername());

        if (usersPackage != null) {
            List<UserConsumption> consumptions = userConsumptionRepository.findByDateTime(userContent.getUser().getId(), userContent.getContent().getDateTime().minusDays(1), userContent.getContent().getDateTime());

            if (!consumptions.isEmpty()) {

                UserConsumption userConsumption = consumptions.get(0);
                int dailyUploadLimit = userConsumption.getDailyUploadLimit() + 1;
                double dailyUploadSize = userConsumption.getUploadSize() + userContent.getContent().getSize();

                if (dailyUploadSize < usersPackage.getCustomPackage().getUploadSize() &&
                        dailyUploadLimit < usersPackage.getCustomPackage().getDailyUploadLimit()) {
                    userContent.setUser(userRepository.findById(userContent.getUser().getId()).orElse(userContent.getUser()));
                    userConsumption.setDailyUploadLimit(dailyUploadLimit);
                    userConsumption.setUploadSize(dailyUploadSize);
                    userConsumptionRepository.save(userConsumption);
                    userContent.setUser(userRepository.findById(userContent.getUser().getId()).orElse(userContent.getUser()));
                    return userContentRepository.save(userContent);
                }
            } else {
                userConsumptionRepository.save(new UserConsumption(userContent.getContent().getDateTime(),
                        userContent.getUser(),
                        usersPackage.getCustomPackage(),
                        userContent.getContent().getSize(), 1));

                userContent.setUser(userRepository.findById(userContent.getUser().getId()).orElse(userContent.getUser()));
                return userContentRepository.save(userContent);
            }
        }
        return null;
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
}
