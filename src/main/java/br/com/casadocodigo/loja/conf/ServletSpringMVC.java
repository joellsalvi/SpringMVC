package br.com.casadocodigo.loja.conf;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Existem duas formas de configurarmos o servidor Tomcat para que ele passe as requisições para o SpringMVC: 
 * 1. Atravéz de Servets 
 * 2. Através de Filtros;
 * Usaremos a primeira opção, pois o SpringMVC já tem com um servlet pronto para utilizarmos como servlet de configuração. 
 * Podemos configura-lo através de XML ou código Java e mais uma vez usaremos código java!
 * @author User
 *
 */
public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * A classe AppWebConfiguration será usada como classe de configuração do servlet do SpringMVC
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {AppWebConfiguration.class, JPAConfiguration.class};
	}

	/**
	 * Com estas configurações estamos definindo que o servlet do SpringMVC atenderá as requisições 
	 * a partir da raiz do nosso projeto (/)
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		return new Filter[] {characterEncodingFilter};
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}

}
