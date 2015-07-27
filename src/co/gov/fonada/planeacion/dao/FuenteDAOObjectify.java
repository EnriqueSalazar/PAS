package co.gov.fonada.planeacion.dao;

import co.gov.fonada.planeacion.model.Fuente;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class FuenteDAOObjectify implements Serializable, FuenteDAO {

    /**
     *
     */
    private static final long serialVersionUID = 8337700736834254734L;

    @Override
    public Long save(Fuente fuente) {
        ofy().save().entity(fuente);
        return fuente.getId();
    }

    @Override
    public List<Fuente> getAll() {

        return ofy().load().type(Fuente.class).list();
    }

    @Override
    public List<Fuente> getAllOrdered() {
        return ofy().load().type(Fuente.class).orderKey(true).list();
    }

    @Override
    public Boolean remove(Fuente fuente) {
        ofy().delete().entity(fuente).now();
        return true;
    }

    @Override
    public Fuente findById(Long id) {
        Key<Fuente> k = Key.create(Fuente.class, id);
        return ofy().load().key(k).now();
    }

    @Override
    public void deleteAllFuente() {
        ofy().clear();
        List<Key<Fuente>> keys = ofy().load().type(Fuente.class).keys().list();
        System.out
                .println("FuenteDAOObjectify deleteAllFuente keys after delete "
                        + keys.size());
        if (keys.size() > 0) {
            ofy().delete().keys(keys).now();
            try {
                Thread.sleep(666);
            } catch (Exception e) {
                e.printStackTrace();
            }
            deleteAllFuente();

        }
    }

    @Override
    public List<Fuente> getSome(String opc, String mod) {
        List<Fuente> toReturn = new ArrayList<Fuente>();
        List<Fuente> toReturn2 = new ArrayList<Fuente>();

        toReturn = ofy().load().type(Fuente.class).filter("fuente", opc).filter("modalidad", mod).project("fuentes", "duracion").list();

/*		System.out.println("FuenteDAOObjectify getSome toReturn "
                + toReturn.size());*/
        return toReturn;
    }

    @Override
    public Boolean isInFilter(String filter, String value) {
        return ofy().load().type(Fuente.class).filter(filter, value).count() != 0;
    }

    @Override
    public void bulkSave(List<Fuente> fuente) {
        try {
            System.out.println("FuenteDAOObjectify save success " + ofy().save().entities(fuente).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Integer getAllCount() {
        System.out.println("FuenteDAOOBjectify getAllCount()");
        return ofy().load().type(Fuente.class).count();
    }

    @Override
    public List<Fuente> getRange(int startingAt, int maxPerPage, Map<String, Object> filters) {
        System.out.println("FuenteDAOOBjectify getRange()");
        Query<Fuente> query = ofy().load().type(Fuente.class);
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            query = query.filter(entry.getKey(), entry.getValue());
        }
        List<Fuente> range = query.limit(maxPerPage).offset(startingAt).orderKey(true).list();
        return range;
    }





    @Override
    public List<String> getFuenteLista() {
        Query<Fuente> fuenteQuery = ofy().load().type(Fuente.class).project("fuente").distinct(Boolean.TRUE);
        List<Fuente> fuenteList = fuenteQuery.list();
        List<String> fuenteStringFuente = new ArrayList<>();
        for (Fuente a : fuenteList) fuenteStringFuente.add(a.getFuente());
        return fuenteStringFuente;
    }



    @Override
    public SelectItem[] getFuenteOption() {
        //ofy().load().type(Entity.class).project("field1").distinct(true);
        System.out.println("FuenteDAOOBjectify getFuenteOption()");

        Query<Fuente> fuenteQuery = ofy().load().type(Fuente.class).project("fuente").distinct(Boolean.TRUE);
        List<Fuente> fuenteList = fuenteQuery.list();
        SelectItem[] fuenteOption = new SelectItem[fuenteList.size() + 1];
        fuenteOption[0] = new SelectItem("", "");
        for (int i = 0; i < fuenteList.size(); i++) {
            fuenteOption[i + 1] = new SelectItem(fuenteList.get(i).getFuente(), fuenteList.get(i).getFuente());

        }
        return fuenteOption;
    }

}
