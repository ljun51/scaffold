package io.github.ljun51.auditing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author lijun (ljun51@outlook.com)
 * @date 2020-08-10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void create() {
        User data = new User();
        data.setUsername("john");
        user = userRepository.save(data);

        assertThat(user.getCreatedDate())
                .isNotNull();

        assertThat(user.getLastModifiedDate())
                .isNotNull();

        assertThat(user.getCreatedBy())
                .isEqualTo("john");

        assertThat(user.getLastModifiedBy())
                .isEqualTo("john");
    }

    @Test
    public void update() {
        LocalDateTime created = user.getCreatedDate();
        LocalDateTime modified = user.getLastModifiedDate();
        user.setUsername("john li");
        userRepository.save(user);

        userRepository.findById(user.getId())
                .ifPresent(updatedUser -> {

                    assertThat(updatedUser.getUsername())
                            .isEqualTo("john li");

                    assertThat(updatedUser.getCreatedDate())
                            .isEqualTo(created);

                    assertThat(updatedUser.getLastModifiedDate())
                            .isAfter(modified);
                });
    }
}
