package co.gov.fonada.planeacion.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.Serializable;
import java.util.*;
//import java.lang.instrument.Instrumentation;

import co.gov.fonada.planeacion.model.Contrato;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

public class ContratoDAOObjectify implements Serializable, ContratoDAO {

    /**
     *
     */
    private static final long serialVersionUID = 8337700736834254734L;

    @Override
    public Long save(Contrato contrato) {
        try {
            ofy().save().entity(contrato).now();
            System.out.println("ContratoDAOObjectify save");
            return contrato.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
            return contrato.getId();
        }
    }

    @Override
    public void bulkSave(List<Contrato> contrato) {

        try {

            System.out.println("ContratoDAOObjectify save success " + ofy().save().entities(contrato));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Integer getAllCount() {
        System.out.println("ContratoDAOOBjectify getAllCount()");
        return ofy().load().type(Contrato.class).filter("isVersion", Boolean.FALSE).count();
    }

    @Override
    public Map<Integer, List<Contrato>> getRange(int startingAt, int maxPerPage, Map<String, Object> filters) {
        System.out.println("ContratoDAOOBjectify getRange()");
        Query<Contrato> query = ofy().load().type(Contrato.class).filter("isVersion", Boolean.FALSE);
        Boolean isTextSearch = Boolean.FALSE;
        String descripcionString = null;
        String contratoString = null;
        Integer rowCount = 0;
        List<Contrato> contratos = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase("descripcion") && !entry.getKey().equalsIgnoreCase("id"))
                query = query.filter(entry.getKey(), entry.getValue());
            else if (entry.getKey().equalsIgnoreCase("descripcion")) {
                isTextSearch = Boolean.TRUE;
                descripcionString = entry.getValue().toString();
            } else {
                isTextSearch = Boolean.TRUE;
                contratoString = entry.getValue().toString();
            }
        }
        if (isTextSearch.booleanValue()) {
            List<Key<Contrato>> cKey = new ArrayList<>();
            if (contratoString != null) {
                List<Key<Contrato>> cTemp = query.keys().list();
                System.out.println("ContratoDAOOBjectify getRange() textSearch " + cTemp.size());
                for (Key<Contrato> c : cTemp) {
//                System.out.println("ContratoDAOObjectify getRange "+c.getId());
                    if (Objects.toString(c.getId()).contains(contratoString)) {
                        cKey.add(Key.create(Contrato.class, c.getId()));
                    }
                }
                if (cKey != null) query = query.filterKey("in", cKey);
                cKey.clear();
            }
            if (descripcionString != null) {
                List<Contrato> cTemp = query.project("descripcion").list();
                System.out.println("ContratoDAOOBjectify getRange() textSearch " + cTemp.size());
                for (Contrato c : cTemp) {
//                System.out.println("ContratoDAOObjectify getRange "+c.getId());
                    if (c.getDescripcion().contains(descripcionString)) {
                        cKey.add(Key.create(Contrato.class, c.getId()));
                    }
                }
            }
            System.out.println("ContratoDAOOBjectify getRange() textSearch " + cKey.size());
            try {
                query = query.filterKey("in", cKey);
                rowCount=query.count();
                query=query.limit(maxPerPage).offset(startingAt).orderKey(true);
            } catch (Exception e) {
                rowCount=query.count();
                query = query.limit(maxPerPage).offset(startingAt).orderKey(true);
            }
        } else {
            rowCount=query.count();
            query = query.limit(maxPerPage).offset(startingAt).orderKey(true);
        }
        contratos = query.list();
        Map<Integer, List<Contrato>> toReturn = new HashMap<Integer, List<Contrato>>();
        toReturn.put(rowCount,contratos);
        return toReturn;
    }

    @Override
    public List<Contrato> getAll() {
        System.out.println("ContratoDAOObjectify getAll");
        // ofy().clear();
        try {
            List<Contrato> listContrato = ofy().load().type(Contrato.class)
                    .filter("isVersion", Boolean.FALSE).list();
            // System.out.println(Instrumentation.getObjectSize(listContrato));
            return listContrato;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Contrato> getAllVersionsOf(Long versionOf) {
        System.out.println("ContratoDAOObjectify getAll");
        // ofy().clear();
        try {
            List<Contrato> listContrato = ofy().load().type(Contrato.class).filter("isVersion", Boolean.TRUE)
                    .filter("versionOf", versionOf).list();
            return listContrato;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Contrato> getAllOrdered() {
        System.out.println("ContratoDAOObjectify getAll");
        // ofy().clear();
        try {
            List<Contrato> listContrato = ofy().load().type(Contrato.class).filter("isVersion", Boolean.FALSE)
                    .orderKey(true).list();
            // System.out.println(Instrumentation.getObjectSize(listContrato));
            return listContrato;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }


    @Override
    public Boolean remove(Contrato contrato) {
        System.out.println("ContratoDAOObjectify remove");
        ofy().delete().keys(
                ofy().load()
                        .ancestor(Key.create(Contrato.class, contrato.getId()))
                        .keys().list());
        ofy().delete().entity(contrato).now();
        return true;
    }

    @Override
    public Contrato findById(Long id) {
        System.out.println("ContratoDAOObjectify findById");

        Key<Contrato> k = Key.create(Contrato.class, id);
        return ofy().load().key(k).now();
    }

    @Override
    public Boolean isContrato(Long id) {
        Key<Contrato> k = Key.create(Contrato.class, id);
        try{
            Contrato temp=ofy().load().key(k).now();
            if (temp!=null) return true; else return false;
        }catch (Exception ex){
            return false;
        }
//        return ofy().load().key(k).now() != null;
    }

    @Override
    public void deleteAllContrato() {
        ofy().clear();

        List<Key<Contrato>> keys = ofy().load().type(Contrato.class).keys().list();
        ofy().delete().keys(keys);
//
//        int count = keys.size();
//        while (count > 0) {
//            count = ofy().load().type(Contrato.class).count();
//            System.out
//                    .println("ContratoDAOObjectify deleteAllContrato keys after delete "
//                            + count);
//        }
    }

}
