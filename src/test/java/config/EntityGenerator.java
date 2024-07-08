package config;

import enums.Role;
import lombok.experimental.UtilityClass;
import model.User;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class EntityGenerator {

    public static User getUser() {
        return User.builder()
                .intro("Test intro")
                .userName("test")
                .profile("test_profile")
                .firstName("test_fn")
                .middleName("test_mn")
                .lastName("test_ln")
                .role(Role.JUNIOR_SPECIALIST)
                .passwordHash("pass")
                .phone("phone")
                .build();
    }

    public static List<User> getUserList(int userCount) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < userCount; i++) {
            userList.add(User.builder()
                    .intro(String.format("Test intro-%d", i))
                    .userName(String.format("test-%d", i))
                    .profile(String.format("test_profile-%d", i))
                    .firstName(String.format("test_fn-%d", i))
                    .middleName(String.format("test_mn-%d", i))
                    .lastName(String.format("test_ln-%d", i))
                    .role(Role.ADMINISTRATOR)
                    .passwordHash(String.format("pass-%d", i))
                    .phone(String.format("phone-%d", i))
                    .build());
        }
        return userList;
    }
}
