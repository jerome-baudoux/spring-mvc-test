package main.services.impl;

import main.services.VersionService;

/**
 * Implementation of VersionService
 * @author Jerome
 */
public class VersionServiceImpl implements VersionService {
	
	/**
	 * @return Hardcoded version
	 */
	@Override
	public Version getVersion() {
		return new Version(1,0);
	}
}
