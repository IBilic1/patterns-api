package hr.algebra.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UserPackage {

    @Id
    @Column(name = "id_user_package")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "package_id")
    private Package igPackage;

    private LocalDateTime dateTime;

    public UserPackage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Package getIgPackage() {
        return igPackage;
    }

    public void setIgPackage(Package customPackage) {
        this.igPackage = customPackage;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public UserPackage(int id, User user, Package igPackage, LocalDateTime dateTime) {
        this.id = id;
        this.user = user;
        this.igPackage = igPackage;
        this.dateTime = dateTime;
    }

    public boolean checkIfLimit(double dailyUploadSize, int dailyUploadLimit) {
        return dailyUploadSize < this.getIgPackage().getUploadSize() &&
                dailyUploadLimit < this.getIgPackage().getDailyUploadLimit();
    }
}
