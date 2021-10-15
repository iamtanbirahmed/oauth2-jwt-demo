package com.medium.acl.authservice.service;

import com.medium.acl.authservice.model.Participant;
import com.medium.acl.authservice.model.ParticipantDetails;
import com.medium.acl.authservice.repository.ParticipantRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService implements UserDetailsService {
    private ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Participant participant = participantRepository.findByUsername(s);
        if (participant == null) throw new UsernameNotFoundException("username not found");
        return new ParticipantDetails(participant);
    }
}
