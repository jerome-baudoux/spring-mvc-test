package com.mvc.services.impl;

import org.springframework.stereotype.Service;

import com.mvc.services.VersionService;

/**
 * Implementation of VersionService
 * @author Jerome
 */
@Service
public class VersionServiceImpl implements VersionService {
	
	/**
	 * @return Hardcoded version
	 */
	@Override
	public Version getVersion() {
		return new Version(1,0);
	}
}
