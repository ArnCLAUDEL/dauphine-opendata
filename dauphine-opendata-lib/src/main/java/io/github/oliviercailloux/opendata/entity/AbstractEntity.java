package io.github.oliviercailloux.opendata.entity;

/**
 * Almost empty implementation of {@link Entity} which provides base
 * implementation of the methods {@link #toString()}, {@link #equals(Object)}.
 * and {@link #hashCode()}. <br />
 * This mostly provides a nice flexibility for any future common features for
 * entities.
 *
 * @author Dauphine - CLAUDEL Arnaud
 *
 */
public abstract class AbstractEntity implements Entity {

	private static final long serialVersionUID = 1919019794294704718L;

	@Override
	public String toString() {
		return Long.toString(getId());
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Entity)) {
			return false;
		}

		final Entity e = (Entity) obj;

		if (getId() == null || e.getId() == null) {
			return super.equals(obj);
		}

		return getId().equals(e.getId());
	}

	@Override
	public int hashCode() {
		return 0;
	}

}
