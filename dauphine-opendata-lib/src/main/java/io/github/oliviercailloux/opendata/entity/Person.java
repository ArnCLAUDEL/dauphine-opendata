package io.github.oliviercailloux.opendata.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person extends AbstractEntity {

	private static final long serialVersionUID = -5949388193512611670L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	private long id;

	@Override
	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

}
