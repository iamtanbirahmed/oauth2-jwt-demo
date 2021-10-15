package com.medium.acl.authservice.repository;

import com.medium.acl.authservice.model.Participant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParticipantRepository extends CrudRepository<Participant, UUID> {
    Participant findByUsername(String s);
}
