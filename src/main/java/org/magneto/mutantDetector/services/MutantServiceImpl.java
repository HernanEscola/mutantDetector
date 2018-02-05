package org.magneto.mutantDetector.services;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;
import org.magneto.mutantDetector.DTO.Dna;
import org.magneto.mutantDetector.business.enums.EDnaType;
import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantSequenceDetector;
import org.magneto.mutantDetector.database.MutantDao;
import org.magneto.mutantDetector.exceptions.DBException;
import org.magneto.mutantDetector.exceptions.InvalidDnaException;
import org.magneto.mutantDetector.services.interfaces.MutantService;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MutantServiceImpl implements MutantService {

	final public static int MUTANT_SEQUENCE_LENGTH = 4;
	final public static int N_SEQUENCES_TO_FIND = 2;

	@Inject
	private MutantDao dnaDao;
	@Inject
	private StatsServiceImpl statsService;

	private MutantSequenceDetector mutantSequenceDetector = new MutantSequenceDetector();

	public MutantServiceImpl() {
		super();

	}

	public MutantServiceImpl(MutantDao dnaDao, StatsServiceImpl statsService) {
		this();
		this.dnaDao = dnaDao;
		this.statsService = statsService;
	}

	@Override
	public EDnaType analizeDna(Dna dnaData) throws DBException, InvalidDnaException {

		String[] dna = dnaData.getDna();

		if (!mutantSequenceDetector.isValid(dna, MUTANT_SEQUENCE_LENGTH)) {
			throw new InvalidDnaException();
		}

		boolean isMutant = isMutant(dna);

		EDnaType type = null;
		if (isMutant) {
			type = EDnaType.MUTANT;
			registerMutantDna(dna);
		} else {
			type = EDnaType.HUMAN;
			registerHuman();
		}

		return type;
	}

	/**
	 * Ejecuta el código que analiza si es mutante o no
	 * 
	 * @param dnaData
	 * @return
	 */
	public boolean isMutant(String[] dna) {
		int found = mutantSequenceDetector.init(MUTANT_SEQUENCE_LENGTH, N_SEQUENCES_TO_FIND).detect(dna);
		return found == N_SEQUENCES_TO_FIND;
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