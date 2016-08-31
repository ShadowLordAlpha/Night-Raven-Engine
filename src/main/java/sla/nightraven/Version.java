/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 ShadowLordAlpha
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package sla.nightraven;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Version class provides a common immutable version system compatible with the Vulkan API.
 * 
 * @author Josh "ShadowLordAlpha"
 */
public class Version {

	/**
	 * Major version number, should be incremented whenever the program is changed in a non-backwards compatible way.
	 */
	@JsonProperty("major")
	private int major;

	/**
	 * Minor version number, should be incremented whenever the program is changed to add new features but remains
	 * backwards compatible.
	 */
	@JsonProperty("minor")
	private int minor;

	/**
	 * Patch version number, should be incremented whenever the program has a fix applied that does not contain any
	 * program changes.
	 */
	@JsonProperty("patch")
	private int patch;
	
	protected Version() {
		
	}

	/**
	 * @param major
	 * @param minor
	 * @param patch
	 */
	public Version(final int major, final int minor, final int patch) {
		this.major = major;
		this.minor = minor;
		this.patch = patch;
	}

	/**
	 * @param version
	 */
	public Version(int version) {
		this((version >> 22), ((version >> 12) & 0x3FF), (version & 0xFFF));
	}

	/**
	 * @return
	 */
	public int getMajor() {
		return major;
	}

	/**
	 * @return
	 */
	public int getMinor() {
		return minor;
	}

	/**
	 * @return
	 */
	public int getPatch() {
		return patch;
	}

	/**
	 * @return a Vulkan Friendly integer representing the version
	 */
	public int getVersion() {
		return (major << 22) | (minor << 12) | patch;
	}
	
	public String getVersionString() {
		return major + "." + minor + "." + patch;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + major;
		result = prime * result + minor;
		result = prime * result + patch;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Version other = (Version) obj;
		if (major != other.major) return false;
		if (minor != other.minor) return false;
		if (patch != other.patch) return false;
		return true;
	}

	@Override
	public String toString() {
		return "Version [major=" + major + ", minor=" + minor + ", patch=" + patch + "]";
	}
}
