package co.gov.fonada.planeacion.model;

import co.gov.fonada.planeacion.dao.ProductosPSADAO;
import co.gov.fonada.planeacion.dao.ProductosPSADAOObjectify;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

public class ProductosPSALazyList extends LazyDataModel<ProductosPSA> {

    private static final long serialVersionUID = 1L;
    private List<ProductosPSA> productosPSAs;
    private ProductosPSADAO dao;

    @Override
    public List<ProductosPSA> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        dao = new ProductosPSADAOObjectify();
        try {
            System.out.println("ProductosPSALazyList startingAt " + startingAt);
            System.out.println("ProductosPSALazyList maxPerPage " + maxPerPage);
            productosPSAs = dao.getRange(startingAt, maxPerPage, filters);

            System.out.println("ProductosPSALazyList getRange " + productosPSAs.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setRowCount(dao.getAllCount());
        setPageSize(maxPerPage);
        return productosPSAs;
    }

    @Override
    public Object getRowKey(ProductosPSA productosPSA) {
        return productosPSA.getId();
    }

    @Override
    public ProductosPSA getRowData(String productosPSAId) {
        Integer id = Integer.valueOf(productosPSAId);
        for (ProductosPSA productosPSA : productosPSAs) {
            if (id.equals(productosPSA.getId())) {
                return productosPSA;
            }
        }
        return null;
    }
}