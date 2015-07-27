package co.gov.fonada.planeacion.dao;

import co.gov.fonada.planeacion.model.Divipola;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.cmd.Query;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by esalazar on 29/04/2015.
 */
public class DivipolaDAOObjectify implements Serializable, DivipolaDAO {

    /**
     *
     */
    private static final long serialVersionUID = 8337700736834254734L;

    @Override
    public Long save(Divipola divipola) {
        ofy().save().entity(divipola);
        return divipola.getId();
    }

    @Override
    public List<Divipola> getAll() {

        return ofy().load().type(Divipola.class).list();
    }

    @Override
    public List<Divipola> getAllOrdered() {
        try {
            return ofy().load().type(Divipola.class).orderKey(false).list();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public Boolean remove(Divipola divipola) {
        ofy().delete().entity(divipola).now();
        return true;
    }

    @Override
    public Divipola findById(Long id) {
        Key<Divipola> k = Key.create(Divipola.class, id);
        return ofy().load().key(k).now();
    }

    @Override
    public void deleteAllDivipola() {
        ofy().clear();
        List<Key<Divipola>> keys = ofy().load().type(Divipola.class).keys().list();
        System.out
                .println("DivipolaDAOObjectify deleteAllDivipola keys after delete "
                        + keys.size());
        if (keys.size() > 0) {
            ofy().delete().keys(keys).now();
            try {
                Thread.sleep(666);
            } catch (Exception e) {
                e.printStackTrace();
            }
            deleteAllDivipola();
        }
    }

    @Override
    public void deleteSomeDivipola(String valor) {
        ofy().clear();
        List<Key<Divipola>> keys = ofy().load().type(Divipola.class).filter("divipola", valor).keys().list();
        System.out
                .println("DivipolaDAOObjectify deleteAllDivipola keys after delete "
                        + keys.size());
        if (keys.size() > 0) {
            ofy().delete().keys(keys).now();
            try {
                Thread.sleep(666);
            } catch (Exception e) {
                e.printStackTrace();
            }
            deleteSomeDivipola(valor);
        }
    }

    @Override
    public void bulkSave(List<Divipola> divipola) {

        try {

            System.out.println("DivipolaDAOObjectify save success " + ofy().save().entities(divipola));

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    @Override
    public Integer getAllCount() {
        System.out.println("DivipolaDAOOBjectify getAllCount()");
        return ofy().load().type(Divipola.class).count();
    }

    @Override
    public List<Divipola> getRange(int startingAt, int maxPerPage, Map<String, Object> filters) {
        System.out.println("DivipolaDAOOBjectify getRange()");
        Query<Divipola> query = ofy().load().type(Divipola.class);
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            query = query.filter(entry.getKey(), entry.getValue());
        }
        return query.limit(maxPerPage).offset(startingAt).orderKey(true).list();
    }

    @Override
    public List<Divipola> getSome(String entidad) {
        List<Divipola> filtered = ofy().load().type(Divipola.class).filter("entidad", entidad).list();
        System.out.println("DivipolaDAOObjectify getSome toReturn "
                + filtered.size());
        return filtered;
    }

    @Override
    public Divipola getByDivipola(Integer divipola) {
        System.out.println("DivipolaDAOObjectify getByDivipola " + divipola);
//        Integer divipolaInt = Integer.parseInt(divipola);
        Divipola filtered = ofy().load().type(Divipola.class).filter("divipola ==", divipola).first().now();
        return filtered;
    }

    @Override
    public SelectItem[] getEntidadOption() {
        //ofy().load().type(Entity.class).project("field1").distinct(true);
        Query<Divipola> entidadQuery = ofy().load().type(Divipola.class).project("entidad").distinct(Boolean.TRUE);
        List<Divipola> entidadList = entidadQuery.list();
        SelectItem[] entidadOption = new SelectItem[entidadList.size() + 1];
        entidadOption[0] = new SelectItem("", "");
        for (int i = 0; i < entidadList.size(); i++) {
            entidadOption[i + 1] = new SelectItem(entidadList.get(i).getEntidad(), entidadList.get(i).getEntidad());

        }
        return entidadOption;
    }
    @Override
    public SelectItem[] getDepartamentoOption() {
        //ofy().load().type(Entity.class).project("field1").distinct(true);
        Query<Divipola> departamentoQuery = ofy().load().type(Divipola.class).project("departamento").distinct(Boolean.TRUE);
        List<Divipola> departamentoList = departamentoQuery.list();
        SelectItem[] departamentoOption = new SelectItem[departamentoList.size() + 1];
        departamentoOption[0] = new SelectItem("", "");
        for (int i = 0; i < departamentoList.size(); i++) {
            departamentoOption[i + 1] = new SelectItem(departamentoList.get(i).getDepartamento(), departamentoList.get(i).getDepartamento());

        }
        return departamentoOption;
    }
    @Override
    public SelectItem[] getMunicipioOption() {
        //ofy().load().type(Entity.class).project("field1").distinct(true);
        Query<Divipola> municipioQuery = ofy().load().type(Divipola.class).project("municipio").distinct(Boolean.TRUE);
        List<Divipola> municipioList = municipioQuery.list();
        SelectItem[] municipioOption = new SelectItem[municipioList.size() + 1];
        municipioOption[0] = new SelectItem("", "");
        for (int i = 0; i < municipioList.size(); i++) {
            municipioOption[i + 1] = new SelectItem(municipioList.get(i).getMunicipio(), municipioList.get(i).getMunicipio());
        }
        return municipioOption;
    }

    @Override
    public Boolean isDivipola(int value) {
        return ofy().load().type(Divipola.class).filter("divipola",value).count() != 0;
    }

    @Override
    public Map<String,String> getDepartamentoMap() {
        Query<Divipola> departamentoQuery = ofy().load().type(Divipola.class).project("departamento").distinct(Boolean.TRUE);
        List<Divipola> departamentoList = departamentoQuery.list();
        Map<String,String> departamentoMap  = new HashMap<String, String>();
        for (Divipola i : departamentoList) departamentoMap.put(i.getDepartamento(),i.getDepartamento());
        return departamentoMap;
    }
    @Override
    public Map<String,Integer> getMunicipioMap(String departamento) {
        Query<Divipola> municipioQuery = ofy().load().type(Divipola.class).filter("departamento", departamento);
        List<Divipola> municipioList = municipioQuery.list();
        Map<String,Integer
                > municipioMap  = new HashMap<String, Integer>();
        for (Divipola i : municipioList) municipioMap.put(i.getMunicipio(),i.getDivipola());
        return municipioMap;
    }
}