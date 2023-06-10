package hr.algebra.dto;

import hr.algebra.model.Package;

import java.time.LocalDateTime;

public class UserPackageDto {

    private int id;

    private UserDto user;

    private Package package_;

    private LocalDateTime dateTime;

    private int roleId;

    public UserPackageDto() {
    }

    public UserPackageDto(int id, UserDto user, Package package_, LocalDateTime dateTime, int roleId) {
        this.id = id;
        this.user = user;
        this.package_ = package_;
        this.dateTime = dateTime;
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Package getPackage_() {
        return package_;
    }

    public void setPackage_(Package package_) {
        this.package_ = package_;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
