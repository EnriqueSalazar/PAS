package co.gov.fonada.planeacion.dao;

import co.gov.fonada.planeacion.model.Hito;
import co.gov.fonada.planeacion.model.Seguimiento;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class HitoDAOObjectify implements Serializable, HitoDAO {

    /**
     *
     */
    private static final long serialVersionUID = 8337700736834254734L;

    @Override
    public Long save(Hito hito) {
        ofy().save().entity(hito);
        return hito.getId();
    }

    @Override
    public List<Hito> getAll() {

        return ofy().load().type(Hito.class).list();
    }

    @Override
    public List<Hito> getAllOrdered() {
        return ofy().load().type(Hito.class).orderKey(true).list();
    }

    @Override
    public Boolean remove(Hito hito) {
        ofy().delete().entity(hito).now();
        return true;
    }

    @Override
    public Hito findById(Long id) {
        Key<Hito> k = Key.create(Hito.class, id);
        return ofy().load().key(k).now();
    }

    @Override
    public void deleteAllHito() {
        ofy().clear();
        List<Key<Hito>> keys = ofy().load().type(Hito.class).keys().list();
        System.out
                .println("HitoDAOObjectify deleteAllHito keys after delete "
                        + keys.size());
        if (keys.size() > 0) {
            ofy().delete().keys(keys).now();
            try {
                Thread.sleep(666);
            } catch (Exception e) {
                e.printStackTrace();
            }
            deleteAllHito();

        }
    }

    @Override
    public List<Hito> getSome(String opc, String mod) {
        List<Hito> toReturn = new ArrayList<Hito>();
        List<Hito> toReturn2 = new ArrayList<Hito>();

        toReturn = ofy().load().type(Hito.class).filter("opcion", opc).filter("modalidad", mod).project("hitos", "duracion").list();

/*		System.out.println("HitoDAOObjectify getSome toReturn "
                + toReturn.size());*/
        return toReturn;
    }

    @Override
    public Boolean isInFilter(String filter, String value) {
        return ofy().load().type(Hito.class).filter(filter, value).count() != 0;
    }

    @Override
    public void bulkSave(List<Hito> hito) {
        try {
            System.out.println("HitoDAOObjectify save success " + ofy().save().entities(hito).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Integer getAllCount() {
        System.out.println("HitoDAOOBjectify getAllCount()");
        return ofy().load().type(Hito.class).count();
    }

    /*   @Override
       public List<Hito> getRange(int startingAt, int maxPerPage, Map<String, Object> filters) {
           System.out.println("HitoDAOOBjectify getRange()");
           Query<Hito> query = ofy().load().type(Hito.class);
           for (Map.Entry<String, Object> entry : filters.entrySet()) {
               query = query.filter(entry.getKey(), entry.getValue());
           }
           List<Hito> range = query.limit(maxPerPage).offset(startingAt).orderKey(true).list();
           return range;
       }*/
    @Override
    public List<Hito> getRange(int startingAt, int maxPerPage, Long parentId) {
        System.out.println("HitoDAOOBjectify getRange()");
        List<Hito> range = ofy().load().type(Hito.class).ancestor(Key.create(Seguimiento.class, parentId)).limit(maxPerPage).offset(startingAt).orderKey(true).list();
        return range;
    }


}
