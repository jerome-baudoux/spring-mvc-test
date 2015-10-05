package com.mvc.services;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.mvc.categories.UnitTest;
import com.mvc.services.VersionService;
import com.mvc.services.VersionService.Version;
import com.mvc.services.impl.VersionServiceImpl;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit test for the version service
 * 
 * @author Jerome
 */
@Category(UnitTest.class)
@RunWith(MockitoJUnitRunner.class)
public class VersionServiceImplTest {

	@InjectMocks
	private VersionService versionService = new VersionServiceImpl();

	@Test
	public void getVersionShouldReturnTheCurrentVersion() {

		Version version = this.versionService.getVersion();

		assertThat(version).isNotNull();
		assertThat(version.getMajor()).isEqualTo(0);
		assertThat(version.getMinor()).isEqualTo(1);
	}
}
