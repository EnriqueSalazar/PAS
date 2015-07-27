package co.gov.fonada.planeacion.model;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.io.Serializable;

/**
 * Created by esalazar on 23/04/2015.
 */
@Cache
@Entity
public class Lista implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5521987295840853784L;

    @Id
    private Long id;
    @Index
    private String list;
    @Index
    private String valor;
    @Index
    private String productoPND;
    @Index
    private String productoPSA;
    @Index
    private String sector;
    @Index
    private String postulacion;
    @Index
    private String intervencion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getIntervencion() {
        return intervencion;
    }

    public void setIntervencion(String intervencion) {
        this.intervencion = intervencion;
    }

    public String getPostulacion() {
        return postulacion;
    }

    public void setPostulacion(String postulacion) {
        this.postulacion = postulacion;
    }



    public String getProductoPND() {
        return productoPND;
    }

    public void setProductoPND(String productoPND) {
        this.productoPND = productoPND;
    }

    public String getProductoPSA() {
        return productoPSA;
    }

    public void setProductoPSA(String productoPSA) {
        this.productoPSA = productoPSA;
    }

    public String getList() {
        return list;
    }

    public void setList(String lista) {
        this.list = lista;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }


    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}


