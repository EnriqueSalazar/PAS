package co.gov.fonada.planeacion.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.Serializable;
import java.util.List;

import co.gov.fonada.planeacion.model.Contrato;
import co.gov.fonada.planeacion.model.Pago;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.googlecode.objectify.util.ResultTranslator;

public class PagoDAOObjectify implements Serializable, PagoDAO {

    /**
     *
     */
    private static final long serialVersionUID = 8337700736834254734L;

    @Override
    public Long save(Pago pago) {

        try {
            ofy().save().entity(pago).now();
            System.out.println("PagoDAOObjectify save success");

            return pago.getId();

        } catch (Exception ex) {
            ex.printStackTrace();
            return pago.getId();

        }

    }

    @Override
    public void bulkSave(List<Pago> pago) {
        try {
            System.out.println("PagoDAOObjectify save success " + ofy().save().entities(pago).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Integer getAllCount() {
        System.out.println("PagoDAOOBjectify getAllCount()");
        return ofy().load().type(Pago.class).filter("isVersion", Boolean.FALSE).count();
    }

    @Override
    public List<Pago> getRange(int startingAt, int maxPerPage) {
        System.out.println("PagoDAOOBjectify getRange()");
        List<Pago> range = ofy().load().type(Pago.class).filter("isVersion", Boolean.FALSE).limit(maxPerPage).offset(startingAt).orderKey(true).list();
        return range;
    }

    @Override
    public Integer getParentCount(Long parentId) {
        System.out.println("PagoDAOOBjectify getAll()");
        return ofy().load().type(Pago.class).ancestor(Key.create(Contrato.class, parentId)).filter("isVersion", Boolean.FALSE).count();
    }

    @Override
    public List<Pago> getParentRange(int startingAt, int maxPerPage, Long parentId) {
        System.out.println("PagoDAOOBjectify getRange()");
        List<Pago> range = ofy().load().type(Pago.class).ancestor(Key.create(Contrato.class, parentId)).filter("isVersion", Boolean.FALSE).limit(maxPerPage).offset(startingAt).orderKey(true).list();
        return range;
    }

    @Override
    public List<Pago> getAll() {
        System.out.println("PagoDAOOBjectify getAll()");
        return ofy().load().type(Pago.class).filter("isVersion", Boolean.FALSE).list();
    }


    @Override
    public List<Pago> getAllOrdered() {
        System.out.println("PagoDAOOBjectify getAll()");
        return ofy().load().type(Pago.class).filter("isVersion", Boolean.FALSE).orderKey(true).list();
    }


    @Override
    public List<Pago> getByParent(Long parent) {
        // System.out.println("PagoDAOOBjectify getByParent ["+parent+"] ");
        List<Pago> list = null;
        try {
            System.out.println("PagoDAOOBjectify getByParent [" + parent);
            Query<Pago> query = ofy().load().type(Pago.class)
                    .ancestor(Key.create(Contrato.class, parent));
            list = query.list();
            System.out.println("PagoDAOOBjectify getByParent [" + parent
                    + "] full load size: " + list.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // query= query.filter("parent ==", parent);
        // System.out.println("PagoDAOOBjectify getByParent ["+parent+"] filtered size: "+query.list().size());
        return list;
    }


    @Override
    public Double getSumAportes(Long parent) {
        // System.out.println("PagoDAOOBjectify getByParent ["+parent+"] ");
        List<Pago> list = null;
        Double sumAportes = 0.0;
        try {
            System.out.println("PagoDAOOBjectify getByParent [" + parent);
            Query<Pago> query = ofy().load().type(Pago.class)
                    .ancestor(Key.create(Contrato.class, parent)).project("aporte");
            list = query.list();
            System.out.println("PagoDAOOBjectify getByParent [" + parent
                    + "] full load size: " + list.size());
            for (Pago a : list) sumAportes = sumAportes + a.getAporte();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // query= query.filter("parent ==", parent);
        // System.out.println("PagoDAOOBjectify getByParent ["+parent+"] filtered size: "+query.list().size());
        return sumAportes;
    }

    @Override
    public Boolean remove(Pago pago) {
        System.out.println("PagoDAOOBjectify remove");
        ofy().delete().entity(pago).now();
        return true;
    }

    @Override
    public Pago findById(Long id) {
        System.out.println("PagoDAOOBjectify findById");

        Key<Pago> k = Key.create(Pago.class, id);
        return ofy().load().key(k).now();
    }

    @Override
    public void deleteAllPago() {
        ofy().clear();
        List<Key<Pago>> keys = ofy().load().type(Pago.class).filter("isVersion", Boolean.FALSE).keys().list();
        ofy().delete().keys(keys);
        /*System.out
				.println("PagoDAOObjectify deleteAllPago keys after delete "
						+ keys.size());
		if (keys.size()>0){
			ofy().delete().keys(keys);

			try{
				Thread.sleep(666);
			}catch (Exception e){
				e.printStackTrace();
			}
//			deleteAllPago();
		}*/
    }
}
