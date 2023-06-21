package hr.algebra.utils;

import hr.algebra.model.Package;
import hr.algebra.model.User;
import hr.algebra.model.UserConsumption;

import java.time.LocalDateTime;

public class UserConsumptionBuilder implements IUserConsumptionBuilder {

    private UserConsumption userConsumption;

    @Override
    public IUserConsumptionBuilder setId(int id) {
        userConsumption.setId(id);
        return this;
    }

    @Override
    public IUserConsumptionBuilder setDateTime(LocalDateTime dateTime) {
        userConsumption.setDateTime(dateTime);
        return this;
    }

    @Override
    public IUserConsumptionBuilder setUser(User user) {
        userConsumption.setUser(user);
        return this;
    }

    @Override
    public IUserConsumptionBuilder setPackage(Package igPackage) {
        userConsumption.setIgPackage(igPackage);
        return this;
    }

    @Override
    public IUserConsumptionBuilder setUploadSize(double uploadSize) {
        userConsumption.setUploadSize(uploadSize);
        return this;
    }

    @Override
    public IUserConsumptionBuilder setDailyUploadLimit(int dailyUploadLimit) {
        userConsumption.setDailyUploadLimit(dailyUploadLimit);
        return this;
    }

    public UserConsumption build() {
        return userConsumption;
    }
}
