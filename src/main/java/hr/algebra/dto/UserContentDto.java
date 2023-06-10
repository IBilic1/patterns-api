package hr.algebra.dto;

import hr.algebra.model.Content;

public class UserContentDto {

    private int id;

    private UserDto user;

    private Content content;

    public UserContentDto() {
    }

    public UserContentDto(int id, UserDto user, Content content) {
        this.id = id;
        this.user = user;
        this.content = content;
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

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
