package io.ess.projections;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

import io.ess.model.Document;

@Projection(name="summary", types=Document.class)
public interface DocumentSummaryProjection {
	
	public String getName();
	public Date getLastUpdatedOn();
	public Date getCreatedOn();

}
