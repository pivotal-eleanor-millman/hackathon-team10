package com.boomerang.repository;

import com.boomerang.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "opportunities", path = "opportunities")
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {

    List<Opportunity> findByRequester(@Param("requester") String requester);

}
