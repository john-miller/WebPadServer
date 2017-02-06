package io.ess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import io.ess.model.Document;
import io.ess.projections.DocumentSummaryProjection;

@RepositoryRestResource(excerptProjection=DocumentSummaryProjection.class)
public interface DocumentRepository extends JpaRepository<Document, Long> {

}
