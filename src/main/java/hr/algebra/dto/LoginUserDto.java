package hr.algebra.dto;

import com.sun.istack.NotNull;

public class LoginUserDto {

    @NotNull
    private String password;

    @NotNull
    private String username;

    public LoginUserDto() {
    }

    public LoginUserDto(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
