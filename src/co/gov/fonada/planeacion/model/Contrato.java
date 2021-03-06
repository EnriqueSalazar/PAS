package co.gov.fonada.planeacion.model;

import java.io.Serializable;


import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

@Cache
@Entity
public class Contrato implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -5521987295840853767L;

    @Id
    private Long id;
    @Index
    private Boolean isVersion = Boolean.FALSE;
    @Index
    private Long versionOf;
    private Integer item;

    @Index
    private String sector;
    @Index
    private String postulacion;
    @Index
    private String descripcion;
    private String codigoContrato;
    private Double duracion;
    private String modalidad;
    private String referencia;
    private Date fechaRef;
    private Integer cerrado;
    private Integer empleos;
    private Integer usuario;
    private Integer noProgramado;
    private Date timeStamp;
    private String controlCambio;
    private Integer indicador;
    private Integer productos;
    private Integer beneficiarios;
    private Integer caso;
    private Double valor;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the item
     */
    public Integer getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(Integer item) {
        this.item = item;
    }

    /**
     * @return the sector
     */
    public String getSector() {
//		out.println("Dentro de getSector");
        return sector;
    }

    /**
     * @param sector the sector to set
     */
    public void setSector(String sector) {
//		out.println("Dentro de setSector");

        this.sector = sector;
    }

    /**
     * @return the codigoContrato
     */
    public String getCodigoContrato() {
        return codigoContrato;
    }

    /**
     * @param codigoContrato the codigoContrato to set
     */
    public void setCodigoContrato(String codigoContrato) {
        this.codigoContrato = codigoContrato;
    }

    /**
     * @return the duracion
     */
    public Double getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    /**
     * @return the modalidad
     */
    public String getModalidad() {
        return modalidad;
    }

    /**
     * @param modalidad the modalidad to set
     */
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * @return the referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * @param referencia the referencia to set
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * @return the fechaRef
     */
    public Date getFechaRef() {
        return fechaRef;
    }

    /**
     * @param fechaRef the fechaRef to set
     */
    public void setFechaRef(Date fechaRef) {
        this.fechaRef = fechaRef;
    }

    /**
     * @return the cerrado
     */
    public Integer getCerrado() {
        return cerrado;
    }

    /**
     * @param cerrado the cerrado to set
     */
    public void setCerrado(Integer cerrado) {
        this.cerrado = cerrado;
    }

    /**
     * @return the empleos
     */
    public Integer getEmpleos() {
        return empleos;
    }

    /**
     * @param empleos the empleos to set
     */
    public void setEmpleos(Integer empleos) {
        this.empleos = empleos;
    }

    /**
     * @return the usuario
     */
    public Integer getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the noProgramado
     */
    public Integer getNoProgramado() {
        return noProgramado;
    }

    /**
     * @param noProgramado the noProgramado to set
     */
    public void setNoProgramado(Integer noProgramado) {
        this.noProgramado = noProgramado;
    }

    /**
     * @return the timeStamp
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * @return the controlCambio
     */
    public String getControlCambio() {
        return controlCambio;
    }

    /**
     * @param controlCambio the controlCambio to set
     */
    public void setControlCambio(String controlCambio) {
        this.controlCambio = controlCambio;
    }


    /**
     * @return the caso
     */
    public Integer getCaso() {
        return caso;
    }

    /**
     * @param caso the caso to set
     */
    public void setCaso(Integer caso) {
        this.caso = caso;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the postulacion
     */
    public String getPostulacion() {
        return postulacion;
    }

    /**
     * @param postulacion the postulacion to set
     */
    public void setPostulacion(String postulacion) {
        this.postulacion = postulacion;
    }


    public Long getVersionOf() {
        return versionOf;
    }

    public void setVersionOf(Long versionOf) {
        this.versionOf = versionOf;
    }

    public Integer getIndicador() {
        return indicador;
    }

    public void setIndicador(Integer indicador) {
        this.indicador = indicador;
    }

    public Integer getProductos() {
        return productos;
    }

    public void setProductos(Integer productos) {
        this.productos = productos;
    }

    public Integer getBeneficiarios() {
        return beneficiarios;
    }

    public void setBeneficiarios(Integer beneficiarios) {
        this.beneficiarios = beneficiarios;
    }

    public Boolean getIsVersion() {
        return isVersion;
    }

    public void setIsVersion(Boolean isVersion) {
        this.isVersion = isVersion;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
