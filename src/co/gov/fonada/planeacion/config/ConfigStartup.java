package co.gov.fonada.planeacion.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import co.gov.fonada.planeacion.model.*;


import com.googlecode.objectify.ObjectifyService;

public class ConfigStartup implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ObjectifyService.register(Etapa.class);
		ObjectifyService.register(Contrato.class);
		ObjectifyService.register(Pago.class);
		ObjectifyService.register(Accion.class);
		ObjectifyService.register(Regionalizacion.class);
		ObjectifyService.register(Lista.class);
		ObjectifyService.register(Divipola.class);
		ObjectifyService.register(Fuente.class);
		ObjectifyService.register(Hito.class);
		ObjectifyService.register(Seguimiento.class);
		ObjectifyService.register(ProductoPSA.class);
		ObjectifyService.register(ProductosPSA.class);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}
	
}
