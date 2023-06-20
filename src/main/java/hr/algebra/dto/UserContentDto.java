package hr.algebra.dto;

public class UserContentDto {

    private int id;

    private UserDto user;

    private ContentDto content;

    public UserContentDto() {
    }

    public UserContentDto(int id, UserDto user, ContentDto content) {
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

    public ContentDto getContent() {
        return content;
    }

    public void setContent(ContentDto content) {
        this.content = content;
    }
}
