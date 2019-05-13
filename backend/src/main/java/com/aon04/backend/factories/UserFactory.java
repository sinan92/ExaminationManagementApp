package com.aon04.backend.factories;

import com.aon04.backend.models.Role;
import com.aon04.backend.models.User;
import com.aon04.backend.models.input.UserInput;

public class UserFactory {
    public static User createFromUserInput(UserInput input) {
        User user = new User();

        Role studentRole = new Role();
        studentRole.setName("Student");
        studentRole.setId(1);

        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setStudentNumber(input.getStudentNumber());
        user.setRole(studentRole);

        return user;

    }
}
