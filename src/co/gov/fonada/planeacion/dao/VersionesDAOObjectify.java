package co.gov.fonada.planeacion.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.Serializable;
import java.util.List;

import co.gov.fonada.planeacion.model.Versiones;

import com.googlecode.objectify.Key;

public class VersionesDAOObjectify implements Serializable, VersionesDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8337700736834254734L;

	@Override
	public Long save(Versiones contrato) {
		ofy().save().entity(contrato).now();
		return contrato.getId();
	}

	@Override
	public List<Versiones> getAll() {
		return ofy().load().type(Versiones.class).list();
	}
	
	@Override
	public List<Versiones> getAllOrdered() {
		return ofy().load().type(Versiones.class).order("id").list();
	}

	@Override
	public Boolean remove(Versiones contrato) {
		ofy().delete().entity(contrato).now();
		return true;
	}

	@Override
	public Versiones findById(Long id) {
		Key<Versiones> k = Key.create(Versiones.class, id);
		return ofy().load().key(k).now();
	}

}
