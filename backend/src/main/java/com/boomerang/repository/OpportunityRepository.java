package com.boomerang.repository;

import com.boomerang.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "opportunities", path = "opportunities")
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {

}
