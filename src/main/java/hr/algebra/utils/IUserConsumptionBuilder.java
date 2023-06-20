package hr.algebra.utils;

import hr.algebra.model.Package;
import hr.algebra.model.User;

import java.time.LocalDateTime;

public interface IUserConsumptionBuilder {


    IUserConsumptionBuilder setId(int id);

    IUserConsumptionBuilder setDateTime(LocalDateTime dateTime);

    IUserConsumptionBuilder setUser(User user);

    IUserConsumptionBuilder setPackage(Package igPackage);

    IUserConsumptionBuilder setUploadSize(double uploadSize);

    IUserConsumptionBuilder setDailyUploadLimit(int dailyUploadLimit);
}
