package io.github.ljun51.auditing;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @author lijun (ljun51@outlook.com)
 * @date 2020-08-10
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return Optional.of("john");
        } else {
            if (authentication == null) {
                return Optional.of("john");
            } else {
                User user = (User) authentication.getPrincipal();
                return Optional.of(user.getUsername());
            }
        }
    }
}
