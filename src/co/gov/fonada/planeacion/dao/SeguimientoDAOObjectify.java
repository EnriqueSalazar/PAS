package co.gov.fonada.planeacion.dao;

import co.gov.fonada.planeacion.model.Seguimiento;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class SeguimientoDAOObjectify implements Serializable, SeguimientoDAO {

    /**
     *
     */
    private static final long serialVersionUID = 8337700736834254734L;

    @Override
    public Long save(Seguimiento seguimiento) {
        ofy().save().entity(seguimiento);
        return seguimiento.getId();
    }

    @Override
    public List<Seguimiento> getAll() {

        return ofy().load().type(Seguimiento.class).list();
    }

    @Override
    public List<Seguimiento> getAllOrdered() {
        return ofy().load().type(Seguimiento.class).orderKey(true).list();
    }

    @Override
    public Boolean remove(Seguimiento seguimiento) {
        ofy().delete().entity(seguimiento).now();
        return true;
    }

    @Override
    public Seguimiento findById(Long id) {
        Key<Seguimiento> k = Key.create(Seguimiento.class, id);
        return ofy().load().key(k).now();
    }

    @Override
    public void deleteAllSeguimiento() {
        ofy().clear();
        List<Key<Seguimiento>> keys = ofy().load().type(Seguimiento.class).keys().list();
        System.out
                .println("SeguimientoDAOObjectify deleteAllSeguimiento keys after delete "
                        + keys.size());
        if (keys.size() > 0) {
            ofy().delete().keys(keys).now();
            try {
                Thread.sleep(666);
            } catch (Exception e) {
                e.printStackTrace();
            }
            deleteAllSeguimiento();

        }
    }

    @Override
    public List<Seguimiento> getSome(String opc, String mod) {
        List<Seguimiento> toReturn = new ArrayList<Seguimiento>();
        List<Seguimiento> toReturn2 = new ArrayList<Seguimiento>();

        toReturn = ofy().load().type(Seguimiento.class).filter("opcion", opc).filter("modalidad", mod).project("seguimientos", "duracion").list();

/*		System.out.println("SeguimientoDAOObjectify getSome toReturn "
                + toReturn.size());*/
        return toReturn;
    }

    @Override
    public Boolean isInFilter(String filter, String value) {
        return ofy().load().type(Seguimiento.class).filter(filter, value).count() != 0;
    }

    @Override
    public void bulkSave(List<Seguimiento> seguimiento) {
        try {
            System.out.println("SeguimientoDAOObjectify save success " + ofy().save().entities(seguimiento).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Integer getAllCount() {
        System.out.println("SeguimientoDAOOBjectify getAllCount()");
        return ofy().load().type(Seguimiento.class).count();
    }

    @Override
    public List<Seguimiento> getRange(int startingAt, int maxPerPage, Map<String, Object> filters) {
        System.out.println("SeguimientoDAOOBjectify getRange()");
        Query<Seguimiento> query = ofy().load().type(Seguimiento.class);
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            query = query.filter(entry.getKey(), entry.getValue());
        }
        List<Seguimiento> range = query.limit(maxPerPage).offset(startingAt).orderKey(true).list();
        return range;
    }


}
