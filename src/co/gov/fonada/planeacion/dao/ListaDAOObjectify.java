package co.gov.fonada.planeacion.dao;

import co.gov.fonada.planeacion.model.Lista;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;


import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by esalazar on 23/04/2015.
 */
public class ListaDAOObjectify implements Serializable, ListaDAO {

    /**
     *
     */
    private static final long serialVersionUID = 8337700736834254734L;

    @Override
    public Long save(Lista lista) {
        ofy().save().entity(lista);
        return lista.getId();
    }

    @Override
    public List<Lista> getAll() {

        return ofy().load().type(Lista.class).list();
    }

    @Override
    public List<Lista> getAllOrdered() {
        try {
            return ofy().load().type(Lista.class).orderKey(true).list();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Boolean remove(Lista lista) {
        ofy().delete().entity(lista).now();
        return true;
    }

    @Override
    public Lista findById(Long id) {
        Key<Lista> k = Key.create(Lista.class, id);
        return ofy().load().key(k).now();
    }

    @Override
    public void deleteAllLista() {
        ofy().clear();
        List<Key<Lista>> keys = ofy().load().type(Lista.class).keys().list();
        System.out
                .println("ListaDAOObjectify deleteAllLista keys after delete "
                        + keys.size());
        if (keys.size() > 0) {
            ofy().delete().keys(keys).now();
            try {
                Thread.sleep(666);
            } catch (Exception e) {
                e.printStackTrace();
            }
            deleteAllLista();
        }
    }

    @Override
    public void deleteSomeLista(String valor) {
        ofy().clear();
        List<Key<Lista>> keys = ofy().load().type(Lista.class).filter("list", valor).keys().list();
        ofy().delete().keys(keys);
    }


    @Override
    public List<Lista> getSome(String menu) {
        List<Lista> filtered;
        filtered = ofy().load().type(Lista.class).filter("list", menu).list();

        System.out.println("ListaDAOObjectify getSome filtered "
                + filtered.size());
        return filtered;
    }

    @Override
    public Boolean isInFilter(String lista, String filter, String value) {
        return ofy().load().type(Lista.class).filter("list",lista).filter(filter, value).count() != 0;
    }

    @Override
    public Lista getItem(String lista, String filter, String value, String project) {
        return ofy().load().type(Lista.class).filter("list",lista).filter(filter, value).project(project).first().now();
    }

    @Override
    public void bulkSave(List<Lista> lista) {
        try {
            System.out.println("ListaDAOObjectify save success "+ofy().save().entities(lista).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public SelectItem[] getSectorOption() {
        //ofy().load().type(Entity.class).project("field1").distinct(true);
        Query<Lista> sectorQuery = ofy().load().type(Lista.class).filter("list","sector").project("valor").distinct(Boolean.TRUE);
        List<Lista> sectorList = sectorQuery.list();
        SelectItem[] sectorOption = new SelectItem[sectorList.size() + 1];
        sectorOption[0] = new SelectItem("", "");
        for (int i = 0; i < sectorList.size(); i++) {
            sectorOption[i + 1] = new SelectItem(sectorList.get(i).getValor(), sectorList.get(i).getValor());
        }
        return sectorOption;
    }

    @Override
    public SelectItem[] getProductoOption() {
        //ofy().load().type(Entity.class).project("field1").distinct(true);
        Query<Lista> productoQuery = ofy().load().type(Lista.class).filter("list","producto").project("productoPND").distinct(Boolean.TRUE);
        List<Lista> productoList = productoQuery.list();
        SelectItem[] productoOption = new SelectItem[productoList.size() + 1];
        productoOption[0] = new SelectItem("", "");
        for (int i = 0; i < productoList.size(); i++) {
            productoOption[i + 1] = new SelectItem(productoList.get(i).getProductoPND(), productoList.get(i).getProductoPND());
        }
        return productoOption;
    }
    @Override
    public List<String> getProductoStringLista() {
        Query<Lista> productoQuery = ofy().load().type(Lista.class).filter("list","producto").project("productoPND").distinct(Boolean.TRUE);
        List<Lista> productoList = productoQuery.list();
        List<String> productoStringLista = new ArrayList<>();
        for (Lista a:productoList) productoStringLista.add(a.getProductoPND());
        return productoStringLista;
    }
   
    @Override
    public List<String> getSectorStringLista() {
        Query<Lista> sectorQuery = ofy().load().type(Lista.class).filter("list","sector").project("valor").distinct(Boolean.TRUE);
        List<Lista> sectorList = sectorQuery.list();
        List<String> sectorStringLista = new ArrayList<>();
        for (Lista a:sectorList) sectorStringLista.add(a.getValor());
        return sectorStringLista;
    }
   
    @Override
    public List<String> getPostulacionStringLista() {
        Query<Lista> postulacionQuery = ofy().load().type(Lista.class).filter("list","postulacion").project("postulacion").distinct(Boolean.TRUE);
        List<Lista> postulacionList = postulacionQuery.list();
        List<String> postulacionStringLista = new ArrayList<>();
        for (Lista a:postulacionList) postulacionStringLista.add(a.getPostulacion());
        return postulacionStringLista;
    }
   
    @Override
    public List<String> getIntervencionStringLista(String postulacion) {
        Query<Lista> intervencionQuery = ofy().load().type(Lista.class).filter("list","postulacion").filter("postulacion",postulacion).project("intervencion").distinct(Boolean.TRUE);
        List<Lista> intervencionList = intervencionQuery.list();
        List<String> intervencionStringLista = new ArrayList<>();
        for (Lista a:intervencionList) intervencionStringLista.add(a.getIntervencion());
        return intervencionStringLista;
    }
    @Override
    public SelectItem[] getPostulacionOption() {
        //ofy().load().type(Entity.class).project("field1").distinct(true);
        Query<Lista> postulacionQuery = ofy().load().type(Lista.class).filter("list","postulacion").project("postulacion").distinct(Boolean.TRUE);
        List<Lista> postulacionList = postulacionQuery.list();
        SelectItem[] postulacionOption = new SelectItem[postulacionList.size() + 1];
        postulacionOption[0] = new SelectItem("", "");
        for (int i = 0; i < postulacionList.size(); i++) {
            postulacionOption[i + 1] = new SelectItem(postulacionList.get(i).getPostulacion(), postulacionList.get(i).getPostulacion());
        }
        return postulacionOption;
    }
    @Override
    public SelectItem[] getIntervencionOption() {
        //ofy().load().type(Entity.class).project("field1").distinct(true);
        Query<Lista> intervencionQuery = ofy().load().type(Lista.class).filter("list","postulacion").project("intervencion").distinct(Boolean.TRUE);
        List<Lista> intervencionList = intervencionQuery.list();
        SelectItem[] intervencionOption = new SelectItem[intervencionList.size() + 1];
        intervencionOption[0] = new SelectItem("", "");
        for (int i = 0; i < intervencionList.size(); i++) {
            intervencionOption[i + 1] = new SelectItem(intervencionList.get(i).getIntervencion(), intervencionList.get(i).getIntervencion());
        }
        return intervencionOption;
    }

    @Override
    public Integer getListaCount(String lista) {
        System.out.println("ListaDAOOBjectify getAllCount()");
        return ofy().load().type(Lista.class).filter("list",lista).count();
    }


    @Override
    public List<Lista> getListaRange(int startingAt, int maxPerPage, String lista, Map<String, Object> filters) {
        System.out.println("ListaDAOOBjectify getRange()");
        Query<Lista> query = ofy().load().type(Lista.class);
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            query = query.filter(entry.getKey(), entry.getValue());
        }
        return query.filter("list", lista).limit(maxPerPage).offset(startingAt).orderKey(true).list();
    }
}
