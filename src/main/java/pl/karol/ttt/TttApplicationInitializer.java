package pl.karol.ttt;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import pl.karol.ttt.config.WebConfig;

public class TttApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {


	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}


	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}


	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
