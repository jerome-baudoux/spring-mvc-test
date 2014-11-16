package main.services;

/**
 * Version service
 * @author Jerome
 */
public interface VersionService {
	
	/**
	 * @return version
	 */
	Version getVersion();
    
	/**
	 * Version Holder
	 * @author Jerome
	 */
    public static class Version {
    	private final int minor;
    	private final int major;
    	
		public Version(int minor, int major) {
			super();
			this.minor = minor;
			this.major = major;
		}

		/**
		 * @return the minor
		 */
		public int getMinor() {
			return minor;
		}

		/**
		 * @return the major
		 */
		public int getMajor() {
			return major;
		}
    }
}
