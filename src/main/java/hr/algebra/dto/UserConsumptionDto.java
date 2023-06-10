package hr.algebra.dto;

import hr.algebra.model.Package;
import hr.algebra.model.User;

import java.time.LocalDateTime;

public class UserConsumptionDto {

    private int id;

    private LocalDateTime dateTime;

    private User user;

    private Package customPackage;

    private double uploadSize;

    private int dailyUploadLimit;

    public UserConsumptionDto() {
    }

    public UserConsumptionDto(int id, LocalDateTime dateTime, User user, Package customPackage, double uploadSize, int dailyUploadLimit) {
        this.id = id;
        this.dateTime = dateTime;
        this.user = user;
        this.customPackage = customPackage;
        this.uploadSize = uploadSize;
        this.dailyUploadLimit = dailyUploadLimit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Package getCustomPackage() {
        return customPackage;
    }

    public void setCustomPackage(Package customPackage) {
        this.customPackage = customPackage;
    }

    public double getUploadSize() {
        return uploadSize;
    }

    public void setUploadSize(double uploadSize) {
        this.uploadSize = uploadSize;
    }

    public int getDailyUploadLimit() {
        return dailyUploadLimit;
    }

    public void setDailyUploadLimit(int dailyUploadLimit) {
        this.dailyUploadLimit = dailyUploadLimit;
    }
}
