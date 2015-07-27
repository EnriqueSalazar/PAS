package co.gov.fonada.planeacion.model;

import co.gov.fonada.planeacion.dao.ListaDAO;
import co.gov.fonada.planeacion.dao.ListaDAOObjectify;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

public class ListaLazyList extends LazyDataModel<Lista> {

    private static final long serialVersionUID = 1L;
    private List<Lista> listas;
    private ListaDAO dao;

    private String lista;

    public ListaLazyList(String lista){
        this.lista=lista;
    }

    @Override
    public List<Lista> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        dao = new ListaDAOObjectify();
        try {
            System.out.println("ListaLazyList startingAt " + startingAt);
            System.out.println("ListaLazyList maxPerPage " + maxPerPage);
            System.out.println("ListaLazyList lista " + lista);

            listas = dao.getListaRange(startingAt, maxPerPage, lista, filters);
            System.out.println("ListaLazyList getRange " + listas.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setRowCount(dao.getListaCount(lista));
        setPageSize(maxPerPage);
        return listas;
    }

    @Override
    public Object getRowKey(Lista lista) {
        return lista.getId();
    }

    @Override
    public Lista getRowData(String listaId) {
        Integer id = Integer.valueOf(listaId);
        for (Lista lista : listas) {
            if (id.equals(lista.getId())) {
                return lista;
            }
        }
        return null;
    }
}