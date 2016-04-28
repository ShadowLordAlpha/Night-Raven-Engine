package sla.nightraven;

/**
 * Internal Pointer class nearly identical to LWJGL 3's Default pointer implementation.
 * 
 * @author Josh "ShadowLordAlpha"
 */
public abstract class Pointer {

	protected long address;
	
	/**
	 * Returns the address of the underlying object.
	 * 
	 * @return the address of the underlying object.
	 */
	public long address() {
		return this.address;
	}
	
	@Override
	public int hashCode() {
		return (31 + (int) (address ^ (address >>> 32)));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pointer other = (Pointer) obj;
		if (address != other.address)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s [ address = 0x%016X ]", this.getClass().getSimpleName(), address);
	}
}
