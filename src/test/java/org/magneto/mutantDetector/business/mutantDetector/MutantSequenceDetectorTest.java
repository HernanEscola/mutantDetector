
package org.magneto.mutantDetector.business.mutantDetector;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.magneto.mutantDetector.business.mutantSequenceDetector.IMutantSequenceDetector;
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
	@org.junit.Test
	public void checkMutantSequenceDetectorTest() throws Exception {

		try {
			List<DnaInputTestCaseInput> dnas = DnaInputTestCaseInput.getTestMatrices();

			/**
			 * hago dos iteraciones, una con el limite minimo para que sea
			 * mutante para ver si el las funciones cortan al encontrarlo, y la
			 * segunda iteracion es para ver si encuentra todas las variantes
			 * posible para cada direccion
			 */

			int[] minVal = { 2, Integer.MAX_VALUE };

			int sequenceLength = 4;
			IMutantSequenceDetector horizontalMsd = new HorizontalMutantSequenceDetectorImpl();
			IMutantSequenceDetector verticalMsd = new VerticalMutantSequenceDetectorImpl();
			IMutantSequenceDetector inverseObliquelMsd = new InverseObliqueMutantSequenceDetectorImpl();
			IMutantSequenceDetector obliquelMsd = new ObliqueMutantSequenceDetectorImpl();
			for (DnaInputTestCaseInput dnaStruct : dnas) {
				String[] dna = dnaStruct.getDna();
				for (int sequencesToFind : minVal) {
					horizontalMsd.init(sequenceLength, sequencesToFind);
					Assert.assertEquals("Cantidad de secuencias Horitonzales Detector", Math.min(sequencesToFind, dnaStruct.getHorizontalSequences()), horizontalMsd.detect(dna));
					verticalMsd.init(sequenceLength, sequencesToFind);

					Assert.assertEquals("Cantidad de secuencias Verticales Detector", Math.min(sequencesToFind, dnaStruct.getVerticalSequences()), verticalMsd.detect(dna));

					inverseObliquelMsd.init(sequenceLength, sequencesToFind);
					Assert.assertEquals("Cantidad de secuencias Oblicuas Inversas", Math.min(sequencesToFind, dnaStruct.getInverseObliqueSequences()),
							inverseObliquelMsd.detect(dna));

					obliquelMsd.init(sequenceLength, sequencesToFind);
					Assert.assertEquals("Cantidad de secuencias Oblicuas", Math.min(sequencesToFind, dnaStruct.getObliqueSequences()), obliquelMsd.detect(dna));

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

}
