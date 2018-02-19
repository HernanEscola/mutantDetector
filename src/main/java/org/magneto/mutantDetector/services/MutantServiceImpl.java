package org.magneto.mutantDetector.services;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;
import org.magneto.mutantDetector.DTO.Dna;
import org.magneto.mutantDetector.business.enums.EDnaType;
import org.magneto.mutantDetector.business.mutantSequenceDetector.DnaValidator;
import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantDetector;
import org.magneto.mutantDetector.database.MutantDao;
import org.magneto.mutantDetector.exceptions.DBException;
import org.magneto.mutantDetector.exceptions.InvalidDnaException;
import org.magneto.mutantDetector.services.interfaces.MutantService;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MutantServiceImpl implements MutantService {

	@Inject
	private MutantDao dnaDao;
	@Inject
	private StatsServiceImpl statsService;

	private MutantDetector mutantDetector = new MutantDetector();

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
		DnaValidator dnaValidator = new DnaValidator();
		dnaValidator.validate(dna);
		boolean isMutant = mutantDetector.isMutant(dna);

		EDnaType type;
		if (isMutant) {
			type = EDnaType.MUTANT;
			registerMutantDna(dna);
		} else {
			type = EDnaType.HUMAN;
			registerHuman();
		}

		return type;
	}

	public void registerMutantDna(String[] dna) throws DBException {
		if (dnaDao.saveMutantDna(dna)) {
			log.debug("Registering a new Mutant DNA");
		} else {
			log.debug("Mutant DNA Already Registered");
		}
		statsService.incrementMutantCount();
	}

	public void registerHuman() throws DBException {
		log.debug("Discarding Human DNA");
		statsService.incrementHumanCount();
	}

}