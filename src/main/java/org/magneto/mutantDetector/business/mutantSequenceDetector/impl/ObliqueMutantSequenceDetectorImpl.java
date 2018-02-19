package org.magneto.mutantDetector.business.mutantSequenceDetector.impl;

/**
 * Detecta secuencias oblicuas de la longitud pasada en el contructor y corta la
 * búsqueda al encontrar la cantidad de secuencias indicadas Utiliza las dos
 * implementaciones de los Detectores de secuencias Oblicua para buscar con una
 * sola invocación
 * 
 * @author hescola
 *
 */
public class ObliqueMutantSequenceDetectorImpl extends MultiSequenceDetectorImpl {

	public ObliqueMutantSequenceDetectorImpl(int sequenceLength) {
		super();
		addDetector(new ObliqueVerticalMutantSequenceDetectorImpl(sequenceLength));
		addDetector(new ObliqueHorizontalMutantSequenceDetectorImpl(sequenceLength));
	}

}
