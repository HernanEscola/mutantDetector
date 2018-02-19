package org.magneto.mutantDetector.business.mutantDetector.impl;

/**
 * Detecta secuencias oblicuas de la longitud pasada en el contructor y corta la
 * búsqueda al encontrar la cantidad de secuencias indicadas Utiliza las dos
 * implementaciones de los Detectores de secuencias Oblicua para buscar con una
 * sola invocación
 * 
 * @author hescola
 *
 */
public class ObliqueSequenceDetectorImpl extends MultiDetectorImpl {

	public ObliqueSequenceDetectorImpl(int sequenceLength) {
		super();
		addDetector(new ObliqueVerticalSequenceDetectorImpl(sequenceLength));
		addDetector(new ObliqueHorizontalSequenceDetectorImpl(sequenceLength));
	}

}
