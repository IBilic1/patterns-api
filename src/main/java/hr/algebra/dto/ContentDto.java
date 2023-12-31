package hr.algebra.dto;

import java.time.LocalDateTime;

public class ContentDto {

    private Long id;

    private String content;

    private double size;

    private LocalDateTime dateTime;

    private String format;

    public ContentDto() {
    }

    public ContentDto(Long id, String content, double size, LocalDateTime dateTime, String format) {
        this.id = id;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
