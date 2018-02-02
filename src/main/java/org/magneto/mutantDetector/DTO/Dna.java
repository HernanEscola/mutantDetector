package org.magneto.mutantDetector.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Contenedor de la Cadena de ADN")
public class Dna {
	@ApiModelProperty(example= "["+// 
			"'aaaaa',"+ //
			"'addbd',"+ // 1 oblicua
			"'adddd',"+ // 1h
			"'aaddd',"+ //
			"'adddd' ]" , allowableValues="A,B,C,D") 
	private String[] dna;

	public Dna() {
		// TODO Auto-generated constructor stub
	}

	public String[] getDna() {
		return dna;
	}

	public void setDna(String[] dna) {
		this.dna = dna;
	}

}