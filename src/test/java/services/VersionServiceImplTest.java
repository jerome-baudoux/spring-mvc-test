package services;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import services.impl.VersionServiceImpl;
import categories.UnitTest;

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

		VersionService.Version version = this.versionService.getVersion();

		assertThat(version).isNotNull();
		assertThat(version.getMajor()).isEqualTo(0);
		assertThat(version.getMinor()).isEqualTo(1);
	}
}
