package ru.nsu.fit.crocodile;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.nsu.fit.crocodile.dto.UserDto;
import ru.nsu.fit.crocodile.model.UserData;

import java.util.LinkedList;
import java.util.List;

public class Utils {
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    public static UserDto userdataToDto(UserData user) {
        UserDto dto = new UserDto();
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        return dto;
    }
    public static List<UserDto> userdataToDto(List<UserData> users) {
        List<UserDto> dtoList = new LinkedList<>();
        for (UserData user: users) {
            dtoList.add(userdataToDto(user));
        }
        return dtoList;
    }

}
