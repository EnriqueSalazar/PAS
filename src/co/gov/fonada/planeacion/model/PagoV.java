package co.gov.fonada.planeacion.model;

import java.io.Serializable;













import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

import java.util.Date;

@Cache
@Entity
public class PagoV implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5521987295840853768L;

	@Id Long id;
	@Parent Key<Contrato> parent;

	/**
	 * @return the parent
	 */
	public Key<Contrato> getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Key<Contrato> parent) {
		this.parent = parent;
	}


	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	private Long parentId;
	private String fuente;
	private Float aporte;
	private Float vigencia2015;
	private Float vigencia2016;
	private Float vigencia2017;
	private Float vigencia2018;
	private Float vigencia2019;
	private Float vigencia2020;

	private Float enero2015;
	private Float febrero2015;
	private Float marzo2015;
	private Float abril2015;
	private Float mayo2015;
	private Float junio2015;
	private Float julio2015;
	private Float agosto2015;
	private Float septiembre2015;
	private Float octubre2015;
	private Float noviembre2015;
	private Float diciembre2015;
	private Float enero2016;
	private Float febrero2016;
	private Float marzo2016;
	private Float abril2016;
	private Float mayo2016;
	private Float junio2016;
	private Float julio2016;
	private Float agosto2016;
	private Float septiembre2016;
	private Float octubre2016;
	private Float noviembre2016;
	private Float diciembre2016;
	private Float enero2017;
	private Float febrero2017;
	private Float marzo2017;
	private Float abril2017;
	private Float mayo2017;
	private Float junio2017;
	private Float julio2017;
	private Float agosto2017;
	private Float septiembre2017;
	private Float octubre2017;
	private Float noviembre2017;
	private Float diciembre2017;
	private Float enero2018;
	private Float febrero2018;
	private Float marzo2018;
	private Float abril2018;
	private Float mayo2018;
	private Float junio2018;
	private Float julio2018;
	private Float agosto2018;
	private Float septiembre2018;
	private Float octubre2018;
	private Float noviembre2018;
	private Float diciembre2018;
	private Float enero2019;
	private Float febrero2019;
	private Float marzo2019;
	private Float abril2019;
	private Float mayo2019;
	private Float junio2019;
	private Float julio2019;
	private Float agosto2019;
	private Float septiembre2019;
	private Float octubre2019;
	private Float noviembre2019;
	private Float diciembre2019;
	private Float enero2020;
	private Float febrero2020;
	private Float marzo2020;
	private Float abril2020;
	private Float mayo2020;
	private Float junio2020;
	private Float julio2020;
	private Float agosto2020;
	private Float septiembre2020;
	private Float octubre2020;
	private Float noviembre2020;
	private Float diciembre2020;

	
	private Integer usuario;
	private Integer noProgramado;
	private Date timeStamp;
	private Integer controlCambio;

	
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
	 * @return the fuente
	 */
	public String getFuente() {
		return fuente;
	}
	/**
	 * @param fuente the fuente to set
	 */
	public void setFuente(String fuente) {
		this.fuente = fuente;
	}
	/**
	 * @return the aporte
	 */
	public Float getAporte() {
		return aporte;
	}
	/**
	 * @param aporte the aporte to set
	 */
	public void setAporte(Float aporte) {
		this.aporte = aporte;
	}
	/**
	 * @return the vigencia2015
	 */
	public Float getVigencia2015() {
		return vigencia2015;
	}
	/**
	 * @param vigencia2015 the vigencia2015 to set
	 */
	public void setVigencia2015(Float vigencia2015) {
		this.vigencia2015 = vigencia2015;
	}
	/**
	 * @return the vigencia2016
	 */
	public Float getVigencia2016() {
		return vigencia2016;
	}
	/**
	 * @param vigencia2016 the vigencia2016 to set
	 */
	public void setVigencia2016(Float vigencia2016) {
		this.vigencia2016 = vigencia2016;
	}
	/**
	 * @return the vigencia2017
	 */
	public Float getVigencia2017() {
		return vigencia2017;
	}
	/**
	 * @param vigencia2017 the vigencia2017 to set
	 */
	public void setVigencia2017(Float vigencia2017) {
		this.vigencia2017 = vigencia2017;
	}
	/**
	 * @return the vigencia2018
	 */
	public Float getVigencia2018() {
		return vigencia2018;
	}
	/**
	 * @param vigencia2018 the vigencia2018 to set
	 */
	public void setVigencia2018(Float vigencia2018) {
		this.vigencia2018 = vigencia2018;
	}
	/**
	 * @return the vigencia2019
	 */
	public Float getVigencia2019() {
		return vigencia2019;
	}
	/**
	 * @param vigencia2019 the vigencia2019 to set
	 */
	public void setVigencia2019(Float vigencia2019) {
		this.vigencia2019 = vigencia2019;
	}
	/**
	 * @return the vigencia2020
	 */
	public Float getVigencia2020() {
		return vigencia2020;
	}
	/**
	 * @param vigencia2020 the vigencia2020 to set
	 */
	public void setVigencia2020(Float vigencia2020) {
		this.vigencia2020 = vigencia2020;
	}
	/**
	 * @return the enero2015
	 */
	public Float getEnero2015() {
		return enero2015;
	}
	/**
	 * @param enero2015 the enero2015 to set
	 */
	public void setEnero2015(Float enero2015) {
		this.enero2015 = enero2015;
	}
	/**
	 * @return the febrero2015
	 */
	public Float getFebrero2015() {
		return febrero2015;
	}
	/**
	 * @param febrero2015 the febrero2015 to set
	 */
	public void setFebrero2015(Float febrero2015) {
		this.febrero2015 = febrero2015;
	}
	/**
	 * @return the marzo2015
	 */
	public Float getMarzo2015() {
		return marzo2015;
	}
	/**
	 * @param marzo2015 the marzo2015 to set
	 */
	public void setMarzo2015(Float marzo2015) {
		this.marzo2015 = marzo2015;
	}
	/**
	 * @return the abril2015
	 */
	public Float getAbril2015() {
		return abril2015;
	}
	/**
	 * @param abril2015 the abril2015 to set
	 */
	public void setAbril2015(Float abril2015) {
		this.abril2015 = abril2015;
	}
	/**
	 * @return the mayo2015
	 */
	public Float getMayo2015() {
		return mayo2015;
	}
	/**
	 * @param mayo2015 the mayo2015 to set
	 */
	public void setMayo2015(Float mayo2015) {
		this.mayo2015 = mayo2015;
	}
	/**
	 * @return the junio2015
	 */
	public Float getJunio2015() {
		return junio2015;
	}
	/**
	 * @param junio2015 the junio2015 to set
	 */
	public void setJunio2015(Float junio2015) {
		this.junio2015 = junio2015;
	}
	/**
	 * @return the julio2015
	 */
	public Float getJulio2015() {
		return julio2015;
	}
	/**
	 * @param julio2015 the julio2015 to set
	 */
	public void setJulio2015(Float julio2015) {
		this.julio2015 = julio2015;
	}
	/**
	 * @return the agosto2015
	 */
	public Float getAgosto2015() {
		return agosto2015;
	}
	/**
	 * @param agosto2015 the agosto2015 to set
	 */
	public void setAgosto2015(Float agosto2015) {
		this.agosto2015 = agosto2015;
	}
	/**
	 * @return the septiembre2015
	 */
	public Float getSeptiembre2015() {
		return septiembre2015;
	}
	/**
	 * @param septiembre2015 the septiembre2015 to set
	 */
	public void setSeptiembre2015(Float septiembre2015) {
		this.septiembre2015 = septiembre2015;
	}
	/**
	 * @return the octubre2015
	 */
	public Float getOctubre2015() {
		return octubre2015;
	}
	/**
	 * @param octubre2015 the octubre2015 to set
	 */
	public void setOctubre2015(Float octubre2015) {
		this.octubre2015 = octubre2015;
	}
	/**
	 * @return the noviembre2015
	 */
	public Float getNoviembre2015() {
		return noviembre2015;
	}
	/**
	 * @param noviembre2015 the noviembre2015 to set
	 */
	public void setNoviembre2015(Float noviembre2015) {
		this.noviembre2015 = noviembre2015;
	}
	/**
	 * @return the diciembre2015
	 */
	public Float getDiciembre2015() {
		return diciembre2015;
	}
	/**
	 * @param diciembre2015 the diciembre2015 to set
	 */
	public void setDiciembre2015(Float diciembre2015) {
		this.diciembre2015 = diciembre2015;
	}
	/**
	 * @return the enero2016
	 */
	public Float getEnero2016() {
		return enero2016;
	}
	/**
	 * @param enero2016 the enero2016 to set
	 */
	public void setEnero2016(Float enero2016) {
		this.enero2016 = enero2016;
	}
	/**
	 * @return the febrero2016
	 */
	public Float getFebrero2016() {
		return febrero2016;
	}
	/**
	 * @param febrero2016 the febrero2016 to set
	 */
	public void setFebrero2016(Float febrero2016) {
		this.febrero2016 = febrero2016;
	}
	/**
	 * @return the marzo2016
	 */
	public Float getMarzo2016() {
		return marzo2016;
	}
	/**
	 * @param marzo2016 the marzo2016 to set
	 */
	public void setMarzo2016(Float marzo2016) {
		this.marzo2016 = marzo2016;
	}
	/**
	 * @return the abril2016
	 */
	public Float getAbril2016() {
		return abril2016;
	}
	/**
	 * @param abril2016 the abril2016 to set
	 */
	public void setAbril2016(Float abril2016) {
		this.abril2016 = abril2016;
	}
	/**
	 * @return the mayo2016
	 */
	public Float getMayo2016() {
		return mayo2016;
	}
	/**
	 * @param mayo2016 the mayo2016 to set
	 */
	public void setMayo2016(Float mayo2016) {
		this.mayo2016 = mayo2016;
	}
	/**
	 * @return the junio2016
	 */
	public Float getJunio2016() {
		return junio2016;
	}
	/**
	 * @param junio2016 the junio2016 to set
	 */
	public void setJunio2016(Float junio2016) {
		this.junio2016 = junio2016;
	}
	/**
	 * @return the julio2016
	 */
	public Float getJulio2016() {
		return julio2016;
	}
	/**
	 * @param julio2016 the julio2016 to set
	 */
	public void setJulio2016(Float julio2016) {
		this.julio2016 = julio2016;
	}
	/**
	 * @return the agosto2016
	 */
	public Float getAgosto2016() {
		return agosto2016;
	}
	/**
	 * @param agosto2016 the agosto2016 to set
	 */
	public void setAgosto2016(Float agosto2016) {
		this.agosto2016 = agosto2016;
	}
	/**
	 * @return the septiembre2016
	 */
	public Float getSeptiembre2016() {
		return septiembre2016;
	}
	/**
	 * @param septiembre2016 the septiembre2016 to set
	 */
	public void setSeptiembre2016(Float septiembre2016) {
		this.septiembre2016 = septiembre2016;
	}
	/**
	 * @return the octubre2016
	 */
	public Float getOctubre2016() {
		return octubre2016;
	}
	/**
	 * @param octubre2016 the octubre2016 to set
	 */
	public void setOctubre2016(Float octubre2016) {
		this.octubre2016 = octubre2016;
	}
	/**
	 * @return the noviembre2016
	 */
	public Float getNoviembre2016() {
		return noviembre2016;
	}
	/**
	 * @param noviembre2016 the noviembre2016 to set
	 */
	public void setNoviembre2016(Float noviembre2016) {
		this.noviembre2016 = noviembre2016;
	}
	/**
	 * @return the diciembre2016
	 */
	public Float getDiciembre2016() {
		return diciembre2016;
	}
	/**
	 * @param diciembre2016 the diciembre2016 to set
	 */
	public void setDiciembre2016(Float diciembre2016) {
		this.diciembre2016 = diciembre2016;
	}
	/**
	 * @return the enero2017
	 */
	public Float getEnero2017() {
		return enero2017;
	}
	/**
	 * @param enero2017 the enero2017 to set
	 */
	public void setEnero2017(Float enero2017) {
		this.enero2017 = enero2017;
	}
	/**
	 * @return the febrero2017
	 */
	public Float getFebrero2017() {
		return febrero2017;
	}
	/**
	 * @param febrero2017 the febrero2017 to set
	 */
	public void setFebrero2017(Float febrero2017) {
		this.febrero2017 = febrero2017;
	}
	/**
	 * @return the marzo2017
	 */
	public Float getMarzo2017() {
		return marzo2017;
	}
	/**
	 * @param marzo2017 the marzo2017 to set
	 */
	public void setMarzo2017(Float marzo2017) {
		this.marzo2017 = marzo2017;
	}
	/**
	 * @return the abril2017
	 */
	public Float getAbril2017() {
		return abril2017;
	}
	/**
	 * @param abril2017 the abril2017 to set
	 */
	public void setAbril2017(Float abril2017) {
		this.abril2017 = abril2017;
	}
	/**
	 * @return the mayo2017
	 */
	public Float getMayo2017() {
		return mayo2017;
	}
	/**
	 * @param mayo2017 the mayo2017 to set
	 */
	public void setMayo2017(Float mayo2017) {
		this.mayo2017 = mayo2017;
	}
	/**
	 * @return the junio2017
	 */
	public Float getJunio2017() {
		return junio2017;
	}
	/**
	 * @param junio2017 the junio2017 to set
	 */
	public void setJunio2017(Float junio2017) {
		this.junio2017 = junio2017;
	}
	/**
	 * @return the julio2017
	 */
	public Float getJulio2017() {
		return julio2017;
	}
	/**
	 * @param julio2017 the julio2017 to set
	 */
	public void setJulio2017(Float julio2017) {
		this.julio2017 = julio2017;
	}
	/**
	 * @return the agosto2017
	 */
	public Float getAgosto2017() {
		return agosto2017;
	}
	/**
	 * @param agosto2017 the agosto2017 to set
	 */
	public void setAgosto2017(Float agosto2017) {
		this.agosto2017 = agosto2017;
	}
	/**
	 * @return the septiembre2017
	 */
	public Float getSeptiembre2017() {
		return septiembre2017;
	}
	/**
	 * @param septiembre2017 the septiembre2017 to set
	 */
	public void setSeptiembre2017(Float septiembre2017) {
		this.septiembre2017 = septiembre2017;
	}
	/**
	 * @return the octubre2017
	 */
	public Float getOctubre2017() {
		return octubre2017;
	}
	/**
	 * @param octubre2017 the octubre2017 to set
	 */
	public void setOctubre2017(Float octubre2017) {
		this.octubre2017 = octubre2017;
	}
	/**
	 * @return the noviembre2017
	 */
	public Float getNoviembre2017() {
		return noviembre2017;
	}
	/**
	 * @param noviembre2017 the noviembre2017 to set
	 */
	public void setNoviembre2017(Float noviembre2017) {
		this.noviembre2017 = noviembre2017;
	}
	/**
	 * @return the diciembre2017
	 */
	public Float getDiciembre2017() {
		return diciembre2017;
	}
	/**
	 * @param diciembre2017 the diciembre2017 to set
	 */
	public void setDiciembre2017(Float diciembre2017) {
		this.diciembre2017 = diciembre2017;
	}
	/**
	 * @return the enero2018
	 */
	public Float getEnero2018() {
		return enero2018;
	}
	/**
	 * @param enero2018 the enero2018 to set
	 */
	public void setEnero2018(Float enero2018) {
		this.enero2018 = enero2018;
	}
	/**
	 * @return the febrero2018
	 */
	public Float getFebrero2018() {
		return febrero2018;
	}
	/**
	 * @param febrero2018 the febrero2018 to set
	 */
	public void setFebrero2018(Float febrero2018) {
		this.febrero2018 = febrero2018;
	}
	/**
	 * @return the marzo2018
	 */
	public Float getMarzo2018() {
		return marzo2018;
	}
	/**
	 * @param marzo2018 the marzo2018 to set
	 */
	public void setMarzo2018(Float marzo2018) {
		this.marzo2018 = marzo2018;
	}
	/**
	 * @return the abril2018
	 */
	public Float getAbril2018() {
		return abril2018;
	}
	/**
	 * @param abril2018 the abril2018 to set
	 */
	public void setAbril2018(Float abril2018) {
		this.abril2018 = abril2018;
	}
	/**
	 * @return the mayo2018
	 */
	public Float getMayo2018() {
		return mayo2018;
	}
	/**
	 * @param mayo2018 the mayo2018 to set
	 */
	public void setMayo2018(Float mayo2018) {
		this.mayo2018 = mayo2018;
	}
	/**
	 * @return the junio2018
	 */
	public Float getJunio2018() {
		return junio2018;
	}
	/**
	 * @param junio2018 the junio2018 to set
	 */
	public void setJunio2018(Float junio2018) {
		this.junio2018 = junio2018;
	}
	/**
	 * @return the julio2018
	 */
	public Float getJulio2018() {
		return julio2018;
	}
	/**
	 * @param julio2018 the julio2018 to set
	 */
	public void setJulio2018(Float julio2018) {
		this.julio2018 = julio2018;
	}
	/**
	 * @return the agosto2018
	 */
	public Float getAgosto2018() {
		return agosto2018;
	}
	/**
	 * @param agosto2018 the agosto2018 to set
	 */
	public void setAgosto2018(Float agosto2018) {
		this.agosto2018 = agosto2018;
	}
	/**
	 * @return the septiembre2018
	 */
	public Float getSeptiembre2018() {
		return septiembre2018;
	}
	/**
	 * @param septiembre2018 the septiembre2018 to set
	 */
	public void setSeptiembre2018(Float septiembre2018) {
		this.septiembre2018 = septiembre2018;
	}
	/**
	 * @return the octubre2018
	 */
	public Float getOctubre2018() {
		return octubre2018;
	}
	/**
	 * @param octubre2018 the octubre2018 to set
	 */
	public void setOctubre2018(Float octubre2018) {
		this.octubre2018 = octubre2018;
	}
	/**
	 * @return the noviembre2018
	 */
	public Float getNoviembre2018() {
		return noviembre2018;
	}
	/**
	 * @param noviembre2018 the noviembre2018 to set
	 */
	public void setNoviembre2018(Float noviembre2018) {
		this.noviembre2018 = noviembre2018;
	}
	/**
	 * @return the diciembre2018
	 */
	public Float getDiciembre2018() {
		return diciembre2018;
	}
	/**
	 * @param diciembre2018 the diciembre2018 to set
	 */
	public void setDiciembre2018(Float diciembre2018) {
		this.diciembre2018 = diciembre2018;
	}
	/**
	 * @return the enero2019
	 */
	public Float getEnero2019() {
		return enero2019;
	}
	/**
	 * @param enero2019 the enero2019 to set
	 */
	public void setEnero2019(Float enero2019) {
		this.enero2019 = enero2019;
	}
	/**
	 * @return the febrero2019
	 */
	public Float getFebrero2019() {
		return febrero2019;
	}
	/**
	 * @param febrero2019 the febrero2019 to set
	 */
	public void setFebrero2019(Float febrero2019) {
		this.febrero2019 = febrero2019;
	}
	/**
	 * @return the marzo2019
	 */
	public Float getMarzo2019() {
		return marzo2019;
	}
	/**
	 * @param marzo2019 the marzo2019 to set
	 */
	public void setMarzo2019(Float marzo2019) {
		this.marzo2019 = marzo2019;
	}
	/**
	 * @return the abril2019
	 */
	public Float getAbril2019() {
		return abril2019;
	}
	/**
	 * @param abril2019 the abril2019 to set
	 */
	public void setAbril2019(Float abril2019) {
		this.abril2019 = abril2019;
	}
	/**
	 * @return the mayo2019
	 */
	public Float getMayo2019() {
		return mayo2019;
	}
	/**
	 * @param mayo2019 the mayo2019 to set
	 */
	public void setMayo2019(Float mayo2019) {
		this.mayo2019 = mayo2019;
	}
	/**
	 * @return the junio2019
	 */
	public Float getJunio2019() {
		return junio2019;
	}
	/**
	 * @param junio2019 the junio2019 to set
	 */
	public void setJunio2019(Float junio2019) {
		this.junio2019 = junio2019;
	}
	/**
	 * @return the julio2019
	 */
	public Float getJulio2019() {
		return julio2019;
	}
	/**
	 * @param julio2019 the julio2019 to set
	 */
	public void setJulio2019(Float julio2019) {
		this.julio2019 = julio2019;
	}
	/**
	 * @return the agosto2019
	 */
	public Float getAgosto2019() {
		return agosto2019;
	}
	/**
	 * @param agosto2019 the agosto2019 to set
	 */
	public void setAgosto2019(Float agosto2019) {
		this.agosto2019 = agosto2019;
	}
	/**
	 * @return the septiembre2019
	 */
	public Float getSeptiembre2019() {
		return septiembre2019;
	}
	/**
	 * @param septiembre2019 the septiembre2019 to set
	 */
	public void setSeptiembre2019(Float septiembre2019) {
		this.septiembre2019 = septiembre2019;
	}
	/**
	 * @return the octubre2019
	 */
	public Float getOctubre2019() {
		return octubre2019;
	}
	/**
	 * @param octubre2019 the octubre2019 to set
	 */
	public void setOctubre2019(Float octubre2019) {
		this.octubre2019 = octubre2019;
	}
	/**
	 * @return the noviembre2019
	 */
	public Float getNoviembre2019() {
		return noviembre2019;
	}
	/**
	 * @param noviembre2019 the noviembre2019 to set
	 */
	public void setNoviembre2019(Float noviembre2019) {
		this.noviembre2019 = noviembre2019;
	}
	/**
	 * @return the diciembre2019
	 */
	public Float getDiciembre2019() {
		return diciembre2019;
	}
	/**
	 * @param diciembre2019 the diciembre2019 to set
	 */
	public void setDiciembre2019(Float diciembre2019) {
		this.diciembre2019 = diciembre2019;
	}
	/**
	 * @return the enero2020
	 */
	public Float getEnero2020() {
		return enero2020;
	}
	/**
	 * @param enero2020 the enero2020 to set
	 */
	public void setEnero2020(Float enero2020) {
		this.enero2020 = enero2020;
	}
	/**
	 * @return the febrero2020
	 */
	public Float getFebrero2020() {
		return febrero2020;
	}
	/**
	 * @param febrero2020 the febrero2020 to set
	 */
	public void setFebrero2020(Float febrero2020) {
		this.febrero2020 = febrero2020;
	}
	/**
	 * @return the marzo2020
	 */
	public Float getMarzo2020() {
		return marzo2020;
	}
	/**
	 * @param marzo2020 the marzo2020 to set
	 */
	public void setMarzo2020(Float marzo2020) {
		this.marzo2020 = marzo2020;
	}
	/**
	 * @return the abril2020
	 */
	public Float getAbril2020() {
		return abril2020;
	}
	/**
	 * @param abril2020 the abril2020 to set
	 */
	public void setAbril2020(Float abril2020) {
		this.abril2020 = abril2020;
	}
	/**
	 * @return the mayo2020
	 */
	public Float getMayo2020() {
		return mayo2020;
	}
	/**
	 * @param mayo2020 the mayo2020 to set
	 */
	public void setMayo2020(Float mayo2020) {
		this.mayo2020 = mayo2020;
	}
	/**
	 * @return the junio2020
	 */
	public Float getJunio2020() {
		return junio2020;
	}
	/**
	 * @param junio2020 the junio2020 to set
	 */
	public void setJunio2020(Float junio2020) {
		this.junio2020 = junio2020;
	}
	/**
	 * @return the julio2020
	 */
	public Float getJulio2020() {
		return julio2020;
	}
	/**
	 * @param julio2020 the julio2020 to set
	 */
	public void setJulio2020(Float julio2020) {
		this.julio2020 = julio2020;
	}
	/**
	 * @return the agosto2020
	 */
	public Float getAgosto2020() {
		return agosto2020;
	}
	/**
	 * @param agosto2020 the agosto2020 to set
	 */
	public void setAgosto2020(Float agosto2020) {
		this.agosto2020 = agosto2020;
	}
	/**
	 * @return the septiembre2020
	 */
	public Float getSeptiembre2020() {
		return septiembre2020;
	}
	/**
	 * @param septiembre2020 the septiembre2020 to set
	 */
	public void setSeptiembre2020(Float septiembre2020) {
		this.septiembre2020 = septiembre2020;
	}
	/**
	 * @return the octubre2020
	 */
	public Float getOctubre2020() {
		return octubre2020;
	}
	/**
	 * @param octubre2020 the octubre2020 to set
	 */
	public void setOctubre2020(Float octubre2020) {
		this.octubre2020 = octubre2020;
	}
	/**
	 * @return the noviembre2020
	 */
	public Float getNoviembre2020() {
		return noviembre2020;
	}
	/**
	 * @param noviembre2020 the noviembre2020 to set
	 */
	public void setNoviembre2020(Float noviembre2020) {
		this.noviembre2020 = noviembre2020;
	}
	/**
	 * @return the diciembre2020
	 */
	public Float getDiciembre2020() {
		return diciembre2020;
	}
	/**
	 * @param diciembre2020 the diciembre2020 to set
	 */
	public void setDiciembre2020(Float diciembre2020) {
		this.diciembre2020 = diciembre2020;
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
	public Integer getControlCambio() {
		return controlCambio;
	}
	/**
	 * @param controlCambio the controlCambio to set
	 */
	public void setControlCambio(Integer controlCambio) {
		this.controlCambio = controlCambio;
	}


	
	
	

	
	
	
}
