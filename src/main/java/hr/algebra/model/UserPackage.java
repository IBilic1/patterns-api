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
    private Package customPackage;

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

    public Package getCustomPackage() {
        return customPackage;
    }

    public void setCustomPackage(Package customPackage) {
        this.customPackage = customPackage;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public UserPackage(int id, User user, Package customPackage, LocalDateTime dateTime) {
        this.id = id;
        this.user = user;
        this.customPackage = customPackage;
        this.dateTime = dateTime;
    }
}
