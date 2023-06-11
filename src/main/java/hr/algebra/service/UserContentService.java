package hr.algebra.service;

import hr.algebra.model.UserConsumption;
import hr.algebra.model.UserContent;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface UserContentService {

    List<UserContent> getAllUsersContents();

    List<UserContent> getUserContents(Long idUser);

    void addUsersContent(UserContent userContent, String username);

    List<UserConsumption> getConsumptions();

    UserConsumption getConsumptionsByUser();

}
