package hr.algebra.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Content {

    @Id
    @Column(name = "id_content")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String pictureContent;

    private double size;

    private LocalDateTime dateTime;

    private String format;

    public Content() {
    }

    public Content(Long id, String pictureContent, double size, LocalDateTime dateTime, String format) {
        this.id = id;
        this.pictureContent = pictureContent;
        this.size = size;
        this.dateTime = dateTime;
        this.format = format;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPictureContent() {
        return pictureContent;
    }

    public void setPictureContent(String pictureContent) {
        this.pictureContent = pictureContent;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
