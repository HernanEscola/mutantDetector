package org.magneto.mutantDetector.services;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;
import org.magneto.mutantDetector.DTO.Stats;
import org.magneto.mutantDetector.database.StatsDao;
import org.magneto.mutantDetector.exceptions.DBException;
import org.magneto.mutantDetector.services.interfaces.StatsService;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class StatsServiceImpl implements StatsService {

	@Inject
	private StatsDao statsDao;

	public StatsServiceImpl() {
	}
	
	public StatsServiceImpl(StatsDao statsDao) {
		super();
		this.statsDao = statsDao;
	}

	public Stats getStats() throws DBException {
		return statsDao.getStats();
	}

	@Override
	public void incrementMutantCount() throws DBException {
		statsDao.addMutant();
		
	}

	@Override
	public void incrementHumanCount() throws DBException {
		// TODO Auto-generated method stub
		statsDao.addHuman();
	}

}