/**
 * 
 */
package com.ServerProject.employee.datamodel.audit;

/**
 * @author Vyanky
 *
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date; 

@MappedSuperclass 
public abstract class DateAudit implements Serializable {

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at",updatable=false)
	private Date createdAt = new Date();

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatedAt = new Date();

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
