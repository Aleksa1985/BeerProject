package com.lexsoft.project.beer.database.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	protected T id;

	protected Date created;
	protected Date updated;

	public T getId() {
		return id;
	}
	public void setId(T id) {
		this.id = id;
	}
	public boolean isPersistent() {
		return (id != null) ? true : false;
	}

	@PrePersist
	protected void onCreate() {
		created = new Date();
		updated = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		updated = new Date();
	}
	public Date getCreated() {
		return created;
	}
	public Date getUpdated() {
		return updated;
	}

}
