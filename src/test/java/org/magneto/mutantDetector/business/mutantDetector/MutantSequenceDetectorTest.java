
package org.magneto.mutantDetector.business.mutantDetector;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.magneto.mutantDetector.business.mutantSequenceDetector.IMutantSequenceDetector;
import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantSequenceDetector;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.HorizontalMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.InverseObliqueMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.ObliqueMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.VerticalMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.utils.DnaInputTestCaseInput;

import lombok.extern.log4j.Log4j;

/**
 * TODO: Testear atómicamente cada implementación de MutantSequenceDetector
 * 
 * @author hescola
 *
 */
@Log4j
public class MutantSequenceDetectorTest {

	/**
	 * Separar cada caso en un test
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkMutantSequenceDetectorTest() throws Exception {

		try {
			List<DnaInputTestCaseInput> dnas = DnaInputTestCaseInput.getTestMatrices();

			/**
			 * hago dos iteraciones, una con el limite minimo para que sea mutante para ver
			 * si el las funciones cortan al encontrarlo, y la segunda iteracion es para ver
			 * si encuentra todas las variantes posible para cada direccion
			 */
			
			int[] minVal = { 2, Integer.MAX_VALUE };

			int sequenceLength = 4;
			IMutantSequenceDetector horizontalMsd = new HorizontalMutantSequenceDetectorImpl();
			IMutantSequenceDetector verticalMsd = new VerticalMutantSequenceDetectorImpl();
			IMutantSequenceDetector inverseObliquelMsd = new InverseObliqueMutantSequenceDetectorImpl();
			IMutantSequenceDetector obliquelMsd = new ObliqueMutantSequenceDetectorImpl();
			MutantSequenceDetector filter = new MutantSequenceDetector();
			for (DnaInputTestCaseInput dnaStruct : dnas) {
				String[] dna = dnaStruct.getDna();
				dna = filter.filter(dna);
				for (int sequencesToFind : minVal) {
					horizontalMsd.init(sequenceLength, sequencesToFind);
					Assertions.assertEquals(Math.min(sequencesToFind, dnaStruct.getHorizontalSequences()), horizontalMsd.detect(dna),
							"Cantidad de secuencias Horitonzales Detector");
					verticalMsd.init(sequenceLength, sequencesToFind);

					Assertions.assertEquals(Math.min(sequencesToFind, dnaStruct.getVerticalSequences()), verticalMsd.detect(dna), "Cantidad de secuencias Verticales Detector");

					inverseObliquelMsd.init(sequenceLength, sequencesToFind);
					Assertions.assertEquals(Math.min(sequencesToFind, dnaStruct.getInverseObliqueSequences()), inverseObliquelMsd.detect(dna),
							"Cantidad de secuencias Oblicuas Inversas");

					obliquelMsd.init(sequenceLength, sequencesToFind);
					Assertions.assertEquals(Math.min(sequencesToFind, dnaStruct.getObliqueSequences()), obliquelMsd.detect(dna), "Cantidad de secuencias Oblicuas");

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			Assertions.fail(e);
		}
	}

}
