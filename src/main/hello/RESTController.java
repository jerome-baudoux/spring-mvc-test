package main.hello;

import main.services.VersionService;
import main.services.VersionService.Version;

import java.util.Date;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A simple REST controller showing the current version
 * @author Jerome
 */
@RestController
public class RESTController {
	
	@Autowired
	private VersionService versionService;

	@RequestMapping("/version")
	public Version version() {
		return versionService.getVersion();
	}

	@RequestMapping("/wait")
	public String doWait() {
		
		System.out.println("Started at: " + new Date());
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Ended at: " + new Date());
		
		return "ok";
	}

	@Async
	@RequestMapping("/async-version")
	public Callable<Version> async() {
		return new Callable<Version>() {
			@Override
			public Version call() throws Exception {
				Thread.sleep(1000);
				return versionService.getVersion();
			}
		};
	}
}
