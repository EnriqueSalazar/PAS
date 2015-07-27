package co.gov.fonada.planeacion.model;

import java.io.Serializable;


import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.*;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.*;

public class LazyPagoSorter implements Comparator<Pago> {

    private String sortField;

    private SortOrder sortOrder;

    public LazyPagoSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(Pago pago1, Pago pago2) {
        try {
            Object value1 = Pago.class.getField(this.sortField).get(pago1);
            Object value2 = Pago.class.getField(this.sortField).get(pago2);

            int value = ((Comparable)value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}