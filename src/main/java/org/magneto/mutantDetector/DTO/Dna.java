package org.magneto.mutantDetector.DTO;

import io.swagger.annotations.ApiModel;

@ApiModel(description="Contenedor de la Cadena de ADN")
public class Dna {
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