package ac2_project.example.ac2_ca.domain.port;

import ac2_project.example.ac2_ca.domain.event.GamificationEvent;

public interface DomainEventPublisher {

    void publish(GamificationEvent event);
}
