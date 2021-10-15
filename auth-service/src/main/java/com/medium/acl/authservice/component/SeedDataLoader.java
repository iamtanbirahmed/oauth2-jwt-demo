package com.medium.acl.authservice.component;

import com.medium.acl.authservice.model.Participant;
import com.medium.acl.authservice.repository.ParticipantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedDataLoader implements CommandLineRunner {

    private ParticipantRepository participantRepository;

    public SeedDataLoader(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadParticipantData();
    }

    private void loadParticipantData() {
        if (participantRepository.count() == 0) {
            Participant participant1 = new Participant("user", "user", "ROLE_USER");
            Participant participant2 = new Participant("admin", "admin", "ROLE_ADMIN");
            participantRepository.save(participant1);
            participantRepository.save(participant2);
        }
        System.out.println("Current participant count: " + participantRepository.count());

    }


}
