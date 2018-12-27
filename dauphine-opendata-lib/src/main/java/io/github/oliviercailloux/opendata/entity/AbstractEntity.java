package io.github.oliviercailloux.opendata.entity;

public abstract class AbstractEntity implements Entity {

	private static final long serialVersionUID = 1919019794294704718L;

	@Override
	public String toString() {
		return Long.toString(getId());
	}

}
