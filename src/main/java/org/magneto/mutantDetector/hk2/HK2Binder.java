package org.magneto.mutantDetector.hk2;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.magneto.mutantDetector.database.MutantDao;
import org.magneto.mutantDetector.database.StatsDao;
import org.magneto.mutantDetector.services.impl.MutantServiceImpl;
import org.magneto.mutantDetector.services.impl.StatsServiceImpl;

public class HK2Binder extends AbstractBinder {

	public HK2Binder() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * TODO: Implementar ServiceLocator para bind automático con Annotation
	 */
	@Override
	protected void configure() {
		//bind(MutantService.class).to(MutantServiceImpl.class);
		 
		bind(StatsServiceImpl.class).to(StatsServiceImpl.class);
		bind(MutantServiceImpl.class).to(MutantServiceImpl.class);
		bind(MutantDao.class).to(MutantDao.class);
		//bind(StatsService.class).to(StatsServiceImpl.class);
		bind(StatsDao.class).to(StatsDao.class);
	}
}
