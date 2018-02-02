package org.magneto.mutantDetector.services;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;
import org.magneto.mutantDetector.DTO.Dna;
import org.magneto.mutantDetector.business.mutantSequenceDetector.IMutantSequenceDetector;
import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantSequenceDetector;
import org.magneto.mutantDetector.database.MutantDao;
import org.magneto.mutantDetector.exceptions.DBException;
import org.magneto.mutantDetector.exceptions.InvalidDnaException;
import org.magneto.mutantDetector.services.interfaces.MutantService;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MutantServiceImpl implements MutantService {

	final public static int SEQUENCE_LENGTH = 4;
	final public static int N_SEQUENCES_TO_FIND = 2;

	@Inject
	private MutantDao dnaDao;
	@Inject
	private StatsServiceImpl statsService;

	private IMutantSequenceDetector mutantSequenceDetector = new MutantSequenceDetector();

	public MutantServiceImpl() {
		super();

	}

	public MutantServiceImpl(MutantDao dnaDao, StatsServiceImpl statsService) {
		this();
		this.dnaDao = dnaDao;
		this.statsService = statsService;
	}

	/**
	 * Analiza la cadena de de ADN en búsqueda del GEN Mutante
	 * 
	 * Asumo que recibo una cadena de ADN válida
	 * 
	 * @param dna
	 * @return true si la cadena es una cadena de ADN Mutante, sino false
	 * @throws DBException
	 *             Arrojada si ocurre alg'un error al querer registrar la cadena
	 */
	@Override
	public boolean analizeDna(Dna dnaData) throws InvalidDnaException, DBException {

//		if (!isValidDna(dnaData)) {
//			throw new InvalidDnaException();
//		}

		boolean isMutant = isMutant(dnaData);

		if (isMutant) {
			registerMutantDna(dnaData.getDna());
		} else {
			registerHuman();
		}

		return isMutant;
	}

	/**
	 * Ejecuta el código que analiza si hay alguna sequencia mutante
	 * 
	 * @param dnaData
	 * @return
	 */
	protected boolean isMutant(Dna dnaData) {
		String[] dna = dnaData.getDna();

		int found = mutantSequenceDetector.init(SEQUENCE_LENGTH, N_SEQUENCES_TO_FIND).detect(dna);
		return found == N_SEQUENCES_TO_FIND;
	}

	/**
	 * TODO: Implementar Validaciones
	 * 
	 * @param dna
	 * @return
	 */
	private boolean isValidDna(Dna dna) {
		// TODO Implementar
		return true;
	}

	/**
	 * Guarda la cadena de ADN e Incrementa en 1 el valor de ADNs mutantes
	 * analizados
	 * 
	 * @param dna
	 *            Srtring[] Cadena de ADN asumible válida y mutante
	 * 
	 * @throws DBException
	 */
	public void registerMutantDna(String[] dna) throws DBException {
		if (dnaDao.saveMutantDna(dna)) {
			log.debug("Registering a new Mutant DNA");
		} else {
			log.debug("Mutant DNA Already Registered");
		}
		statsService.incrementMutant();
	}

	public void registerHuman() throws DBException {
		log.debug("Discarding Human DNA");
		statsService.incrementHuman();
	}

}