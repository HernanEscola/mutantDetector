package org.magneto.mutantDetector.DTO;

public class Stats {
    Integer count_mutant_dna;
    Integer count_human_dna;
    Float ratio;
 
	public Stats(Integer count_mutant_dna, Integer count_human_dna, Float ratio) {
		super();
		this.count_mutant_dna = count_mutant_dna;
		this.count_human_dna = count_human_dna;
		this.ratio = ratio;
	}

	public Stats() {
		// TODO Auto-generated constructor stub
	}
	
	public Integer getCount_mutant_dna() {
		return count_mutant_dna;
	}

	public void setCount_mutant_dna(Integer count_mutant_dna) {
		this.count_mutant_dna = count_mutant_dna;
	}

	public Integer getCount_human_dna() {
		return count_human_dna;
	}

	public void setCount_human_dna(Integer count_human_dna) {
		this.count_human_dna = count_human_dna;
	}

	public Float getRatio() {
		return ratio;
	}

	public void setRatio(Float ratio) {
		this.ratio = ratio;
	}
    
	
	
    
}