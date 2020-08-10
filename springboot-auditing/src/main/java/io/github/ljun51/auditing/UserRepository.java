package io.github.ljun51.auditing;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lijun (ljun51@outlook.com)
 * @date 2020-08-10
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
