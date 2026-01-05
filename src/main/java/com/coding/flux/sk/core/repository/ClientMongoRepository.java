package com.coding.flux.sk.core.repository;

import com.coding.flux.sk.core.entity.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ClientMongoRepository extends MongoRepository<Client, String> {

    List<Client> findAllByEnabledTrueOrderByCreatedAtDesc();

    boolean existsByEnabledTrueAndEmailIgnoreCase(String email);

    Optional<Client> findByEnabledTrueAndIdClient(String id);

    boolean existsByEnabledTrueAndEmailIgnoreCaseAndIdClientNot(String email, String id);

}
