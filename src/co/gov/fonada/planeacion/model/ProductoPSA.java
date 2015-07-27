package co.gov.fonada.planeacion.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.*;

import java.io.Serializable;
import java.util.Date;

@Cache
@Entity
public class ProductoPSA implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5521987295840853784L;

    @Id
    private Long id;
    @Parent
    Key<ProductosPSA> parent;
    private Long parentId;
    @Index
    private String producto;
    private String productoPAS;
    @Ignore
    private String departamento;
    @Ignore
    private String municipio;
    @Index
    private Integer divipola;
    @Index
    private String intervencion;
    private Integer vigencia;
    @Index
    private Long contrato;

    private Float enero;
    private Float febrero;
    private Float marzo;
    private Float abril;
    private Float mayo;
    private Float junio;
    private Float julio;
    private Float agosto;
    private Float septiembre;
    private Float octubre;
    private Float noviembre;
    private Float diciembre;

    @Ignore
    private Float enero0;
    @Ignore
    private Float febrero0;
    @Ignore
    private Float marzo0;
    @Ignore
    private Float abril0;
    @Ignore
    private Float mayo0;
    @Ignore
    private Float junio0;
    @Ignore
    private Float julio0;
    @Ignore
    private Float agosto0;
    @Ignore
    private Float septiembre0;
    @Ignore
    private Float octubre0;
    @Ignore
    private Float noviembre0;
    @Ignore
    private Float diciembre0;

    @Ignore
    private Float eneroDelta;
    @Ignore
    private Float febreroDelta;
    @Ignore
    private Float marzoDelta;
    @Ignore
    private Float abrilDelta;
    @Ignore
    private Float mayoDelta;
    @Ignore
    private Float junioDelta;
    @Ignore
    private Float julioDelta;
    @Ignore
    private Float agostoDelta;
    @Ignore
    private Float septiembreDelta;
    @Ignore
    private Float octubreDelta;
    @Ignore
    private Float noviembreDelta;
    @Ignore
    private Float diciembreDelta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Key<ProductosPSA> getParent() {
        return parent;
    }

    public void setParent(Key<ProductosPSA> parent) {
        this.parent = parent;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Integer getDivipola() {
        return divipola;
    }

    public void setDivipola(Integer divipola) {
        this.divipola = divipola;
    }

    public String getIntervencion() {
        return intervencion;
    }

    public void setIntervencion(String intervencion) {
        this.intervencion = intervencion;
    }

    public Integer getVigencia() {
        return vigencia;
    }

    public void setVigencia(Integer vigencia) {
        this.vigencia = vigencia;
    }

    public Long getContrato() {
        return contrato;
    }

    public void setContrato(Long contrato) {
        this.contrato = contrato;
    }

    public Float getEnero() {
        return enero;
    }

    public void setEnero(Float enero) {
        this.enero = enero;
    }

    public Float getFebrero() {
        return febrero;
    }

    public void setFebrero(Float febrero) {
        this.febrero = febrero;
    }

    public Float getMarzo() {
        return marzo;
    }

    public void setMarzo(Float marzo) {
        this.marzo = marzo;
    }

    public Float getAbril() {
        return abril;
    }

    public void setAbril(Float abril) {
        this.abril = abril;
    }

    public Float getMayo() {
        return mayo;
    }

    public void setMayo(Float mayo) {
        this.mayo = mayo;
    }

    public Float getJunio() {
        return junio;
    }

    public void setJunio(Float junio) {
        this.junio = junio;
    }

    public Float getJulio() {
        return julio;
    }

    public void setJulio(Float julio) {
        this.julio = julio;
    }

    public Float getAgosto() {
        return agosto;
    }

    public void setAgosto(Float agosto) {
        this.agosto = agosto;
    }

    public Float getSeptiembre() {
        return septiembre;
    }

    public void setSeptiembre(Float septiembre) {
        this.septiembre = septiembre;
    }

    public Float getOctubre() {
        return octubre;
    }

    public void setOctubre(Float octubre) {
        this.octubre = octubre;
    }

    public Float getNoviembre() {
        return noviembre;
    }

    public void setNoviembre(Float noviembre) {
        this.noviembre = noviembre;
    }

    public Float getDiciembre() {
        return diciembre;
    }

    public void setDiciembre(Float diciembre) {
        this.diciembre = diciembre;
    }

    public Float getEnero0() {
        return enero0;
    }

    public void setEnero0(Float enero0) {
        this.enero0 = enero0;
    }

    public Float getFebrero0() {
        return febrero0;
    }

    public void setFebrero0(Float febrero0) {
        this.febrero0 = febrero0;
    }

    public Float getMarzo0() {
        return marzo0;
    }

    public void setMarzo0(Float marzo0) {
        this.marzo0 = marzo0;
    }

    public Float getAbril0() {
        return abril0;
    }

    public void setAbril0(Float abril0) {
        this.abril0 = abril0;
    }

    public Float getMayo0() {
        return mayo0;
    }

    public void setMayo0(Float mayo0) {
        this.mayo0 = mayo0;
    }

    public Float getJunio0() {
        return junio0;
    }

    public void setJunio0(Float junio0) {
        this.junio0 = junio0;
    }

    public Float getJulio0() {
        return julio0;
    }

    public void setJulio0(Float julio0) {
        this.julio0 = julio0;
    }

    public Float getAgosto0() {
        return agosto0;
    }

    public void setAgosto0(Float agosto0) {
        this.agosto0 = agosto0;
    }

    public Float getSeptiembre0() {
        return septiembre0;
    }

    public void setSeptiembre0(Float septiembre0) {
        this.septiembre0 = septiembre0;
    }

    public Float getOctubre0() {
        return octubre0;
    }

    public void setOctubre0(Float octubre0) {
        this.octubre0 = octubre0;
    }

    public Float getNoviembre0() {
        return noviembre0;
    }

    public void setNoviembre0(Float noviembre0) {
        this.noviembre0 = noviembre0;
    }

    public Float getDiciembre0() {
        return diciembre0;
    }

    public void setDiciembre0(Float diciembre0) {
        this.diciembre0 = diciembre0;
    }

    public Float getEneroDelta() {
        return eneroDelta;
    }

    public void setEneroDelta(Float eneroDelta) {
        this.eneroDelta = eneroDelta;
    }

    public Float getFebreroDelta() {
        return febreroDelta;
    }

    public void setFebreroDelta(Float febreroDelta) {
        this.febreroDelta = febreroDelta;
    }

    public Float getMarzoDelta() {
        return marzoDelta;
    }

    public void setMarzoDelta(Float marzoDelta) {
        this.marzoDelta = marzoDelta;
    }

    public Float getAbrilDelta() {
        return abrilDelta;
    }

    public void setAbrilDelta(Float abrilDelta) {
        this.abrilDelta = abrilDelta;
    }

    public Float getMayoDelta() {
        return mayoDelta;
    }

    public void setMayoDelta(Float mayoDelta) {
        this.mayoDelta = mayoDelta;
    }

    public Float getJunioDelta() {
        return junioDelta;
    }

    public void setJunioDelta(Float junioDelta) {
        this.junioDelta = junioDelta;
    }

    public Float getJulioDelta() {
        return julioDelta;
    }

    public void setJulioDelta(Float julioDelta) {
        this.julioDelta = julioDelta;
    }

    public Float getAgostoDelta() {
        return agostoDelta;
    }

    public void setAgostoDelta(Float agostoDelta) {
        this.agostoDelta = agostoDelta;
    }

    public Float getSeptiembreDelta() {
        return septiembreDelta;
    }

    public void setSeptiembreDelta(Float septiembreDelta) {
        this.septiembreDelta = septiembreDelta;
    }

    public Float getOctubreDelta() {
        return octubreDelta;
    }

    public void setOctubreDelta(Float octubreDelta) {
        this.octubreDelta = octubreDelta;
    }

    public Float getNoviembreDelta() {
        return noviembreDelta;
    }

    public void setNoviembreDelta(Float noviembreDelta) {
        this.noviembreDelta = noviembreDelta;
    }

    public Float getDiciembreDelta() {
        return diciembreDelta;
    }

    public void setDiciembreDelta(Float diciembreDelta) {
        this.diciembreDelta = diciembreDelta;
    }


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }


    public String getProductoPAS() {
        return productoPAS;
    }

    public void setProductoPAS(String productoPAS) {
        this.productoPAS = productoPAS;
    }
}
