package br.com.casadocodigo.loja.conf;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controller.HomeController;
import br.com.casadocodigo.loja.controller.ProdutosController;
import br.com.casadocodigo.loja.data.ProdutoDAO;
import br.com.casadocodigo.loja.infra.CarrinhoCompras;
import br.com.casadocodigo.loja.infra.FileRecorder;

/**
 * A classe AppWebConfiguration será usada como classe de configuração do
 * servlet do SpringMVC
 */
@EnableWebMvc
//Todo componente e controller deve ser mapeado aqui
@ComponentScan(basePackageClasses = { HomeController.class, ProdutosController.class, ProdutoDAO.class, FileRecorder.class, CarrinhoCompras.class })
public class AppWebConfiguration extends WebMvcConfigurerAdapter {

	private static final String LOCATION_LANGUAGE_PT_BR = "pt_BR";
	private static final String LOCATION_LANGUAGE_EN_US = "en_US";
	private static String LOCATION_LANGUAGE = "pt_BR";

	/**
	 * Configuração para q o SpringMVC consiga encontrar as views
	 */
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setExposedContextBeanNames("carrinhoCompras");
		return resolver;
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setBasename("WEB-INF/resources/properties/"+LOCATION_LANGUAGE_PT_BR+"/messages");
		messageSource.setCacheSeconds(1);
		return messageSource;
	}

	@Bean
	public FormattingConversionService mvcConversionService(){
	    DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
	    DateFormatterRegistrar formatterRegistrar = new DateFormatterRegistrar();
	    formatterRegistrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
	    formatterRegistrar.registerFormatters(conversionService);

	    return conversionService;
	}

	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations(
                "/resources/");
    }

	public static String getLOCATION_LANGUAGE() {
		return LOCATION_LANGUAGE;
	}

	public static void setLOCATION_LANGUAGE(String lOCATION_LANGUAGE) {
		LOCATION_LANGUAGE = lOCATION_LANGUAGE;
	}

	public static String getLocationLanguagePtBr() {
		return LOCATION_LANGUAGE_PT_BR;
	}

	public static String getLocationLanguageEnUs() {
		return LOCATION_LANGUAGE_EN_US;
	}
	
}
