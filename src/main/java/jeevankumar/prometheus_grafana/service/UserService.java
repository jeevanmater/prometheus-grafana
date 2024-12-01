package jeevankumar.prometheus_grafana.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jeevankumar.prometheus_grafana.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final AtomicLong userIdCounter = new AtomicLong(0);

    private final Counter userCreationCounter;
    private final Counter userDeletionCounter;

    public UserService(MeterRegistry meterRegistry) {
        // Custom metrics
        userCreationCounter = Counter.builder("app_user_creation_total")
                .description("Total number of users created")
                .register(meterRegistry);

        userDeletionCounter = Counter.builder("app_user_deletion_total")
                .description("Total number of users deleted")
                .register(meterRegistry);
    }

    public User createUser(User user) {
        user.setId(userIdCounter.incrementAndGet());
        users.add(user);

        // Increment user creation metric
        userCreationCounter.increment();

        return user;
    }

    public boolean deleteUser(Long id) {
        boolean removed = users.removeIf(user -> user.getId().equals(id));

        if (removed) {
            // Increment user deletion metric
            userDeletionCounter.increment();
        }

        return removed;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}
