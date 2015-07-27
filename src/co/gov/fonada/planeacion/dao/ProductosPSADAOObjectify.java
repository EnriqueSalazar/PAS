package co.gov.fonada.planeacion.dao;

import co.gov.fonada.planeacion.model.ProductosPSA;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ProductosPSADAOObjectify implements Serializable, ProductosPSADAO {

    /**
     *
     */
    private static final long serialVersionUID = 8337700736834254734L;

    @Override
    public Long save(ProductosPSA productosPSA) {
        ofy().save().entity(productosPSA);
        return productosPSA.getId();
    }

    @Override
    public List<ProductosPSA> getAll() {

        return ofy().load().type(ProductosPSA.class).list();
    }

    @Override
    public List<ProductosPSA> getAllOrdered() {
        return ofy().load().type(ProductosPSA.class).orderKey(true).list();
    }

    @Override
    public Boolean remove(ProductosPSA productosPSA) {
        ofy().delete().entity(productosPSA).now();
        return true;
    }

    @Override
    public ProductosPSA findById(Long id) {
        Key<ProductosPSA> k = Key.create(ProductosPSA.class, id);
        return ofy().load().key(k).now();
    }

    @Override
    public void deleteAllProductosPSA() {
        ofy().clear();
        List<Key<ProductosPSA>> keys = ofy().load().type(ProductosPSA.class).keys().list();
        System.out
                .println("ProductosPSADAOObjectify deleteAllProductosPSA keys after delete "
                        + keys.size());
        if (keys.size() > 0) {
            ofy().delete().keys(keys).now();
            try {
                Thread.sleep(666);
            } catch (Exception e) {
                e.printStackTrace();
            }
            deleteAllProductosPSA();

        }
    }

    @Override
    public List<ProductosPSA> getSome(String opc, String mod) {
        List<ProductosPSA> toReturn = new ArrayList<ProductosPSA>();
        List<ProductosPSA> toReturn2 = new ArrayList<ProductosPSA>();

        toReturn = ofy().load().type(ProductosPSA.class).filter("opcion", opc).filter("modalidad", mod).project("productosPSAs", "duracion").list();

/*		System.out.println("ProductosPSADAOObjectify getSome toReturn "
                + toReturn.size());*/
        return toReturn;
    }

    @Override
    public Boolean isInFilter(String filter, String value) {
        return ofy().load().type(ProductosPSA.class).filter(filter, value).count() != 0;
    }

    @Override
    public void bulkSave(List<ProductosPSA> productosPSA) {
        try {
            System.out.println("ProductosPSADAOObjectify save success " + ofy().save().entities(productosPSA).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Integer getAllCount() {
        System.out.println("ProductosPSADAOOBjectify getAllCount()");
        return ofy().load().type(ProductosPSA.class).count();
    }

    @Override
    public List<ProductosPSA> getRange(int startingAt, int maxPerPage, Map<String, Object> filters) {
        System.out.println("ProductosPSADAOOBjectify getRange()");
        Query<ProductosPSA> query = ofy().load().type(ProductosPSA.class);
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            query = query.filter(entry.getKey(), entry.getValue());
        }
        List<ProductosPSA> range = query.limit(maxPerPage).offset(startingAt).orderKey(true).list();
        return range;
    }


}
