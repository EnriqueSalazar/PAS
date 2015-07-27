package co.gov.fonada.planeacion.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.Serializable;
import java.util.List;

import co.gov.fonada.planeacion.model.Accion;
import co.gov.fonada.planeacion.model.Contrato;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

public class AccionDAOObjectify implements Serializable, AccionDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8337700736834254734L;

	@Override
	public Long save(Accion accion) {
		System.out.println("AccionDAOOBjectify save");
		ofy().save().entity(accion).now();
		return accion.getId();
	}
	@Override
	public void bulkSave(List<Accion> accion) {

		try {

			System.out.println("AccionDAOObjectify save success "+ofy().save().entities(accion));

		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}
	@Override
	public Integer getAllCount() {
		System.out.println("AccionDAOOBjectify getAllCount()");
		return ofy().load().type(Accion.class).filter("isVersion", Boolean.FALSE).count();
	}
	@Override
	public List<Accion> getRange(int startingAt, int maxPerPage) {
		System.out.println("AccionDAOOBjectify getRange()");
		List<Accion> range = ofy().load().type(Accion.class).filter("isVersion", Boolean.FALSE).limit(maxPerPage).offset(startingAt).orderKey(true).list();
		return range;
	}
	@Override
	public Integer getParentCount(Long parentId) {
		System.out.println("AccionDAOOBjectify getAllCount()");
		return ofy().load().type(Accion.class).ancestor(Key.create(Contrato.class, parentId)).filter("isVersion", Boolean.FALSE).count();
	}
	@Override
	public List<Accion> getParentRange(int startingAt, int maxPerPage, Long parentId) {
		System.out.println("AccionDAOOBjectify getRange()");
		List<Accion> range = ofy().load().type(Accion.class).ancestor(Key.create(Contrato.class, parentId)).filter("isVersion", Boolean.FALSE).limit(maxPerPage).offset(startingAt).orderKey(true).list();
		return range;
	}
	@Override
	public List<Accion> getAll() {
		System.out.println("AccionDAOOBjectify getAll");

		return ofy().load().type(Accion.class).filter("isVersion", Boolean.FALSE).list();
	}
	
	@Override
	public List<Accion> getAllOrdered() {

		return ofy().load().type(Accion.class).filter("isVersion", Boolean.FALSE).orderKey(true).list();
	}

	@Override
	public List<Accion> getByParent(Long parent) {
		List<Accion> list = null;
		try {
			System.out.println("AccionDAOOBjectify getByParent [" + parent);
			Query<Accion> query = ofy().load().type(Accion.class)
					.ancestor(Key.create(Contrato.class, parent));
			list = query.list();
			System.out.println("AccionDAOOBjectify getByParent [" + parent
					+ "] full load size: " + list.size());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}

	@Override
	public Boolean remove(Accion accion) {
		System.out.println("AccionDAOOBjectify remove");
		try {
			ofy().delete().entity(accion).now();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;

		}

	}

	@Override
	public Accion findById(Long id) {
		System.out.println("AccionDAOOBjectify findById");
		Key<Accion> k = Key.create(Accion.class, id);
		return ofy().load().key(k).now();
	}

	@Override
	public void deleteAllAccion() {
		ofy().clear();
		List<Key<Accion>> keys = ofy().load().type(Accion.class).filter("isVersion", Boolean.FALSE).keys().list();

			ofy().delete().keys(keys);


	}


	
	/*	@Override
	public void deleteAllAccion() {
		ofy().clear();
		List<Key<Accion>> keys = ofy().load().type(Accion.class).keys().list();
		System.out
				.println("AccionDAOObjectify deleteAllAccion keys after delete "
						+ keys.size());
		if (keys.size()>0){
			ofy().delete().keys(keys).now();
			try{
				Thread.sleep(666);
			}catch (Exception e){
				e.printStackTrace();
			}
			deleteAllAccion();
		}
	}*/

	
}
