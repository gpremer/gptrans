package net.premereur.gae.transport.domain;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.google.inject.Inject;
import com.wideplay.warp.persist.Transactional;

public class JPADemandRepository extends JPARepository implements DemandRepository {

	@Inject
	private Logger logger;

	@Inject
	public JPADemandRepository(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	@Transactional
	public void store(Demand demand) {
		getEntityManager().persist(demand);
	}

	@Override
	public Demand getDemandForQuoteReference(String quoteReference) throws BusinessException {
		try {
			return (Demand) getEntityManager().createQuery("SELECT FROM Demand WHERE quoteReference = :ref").setParameter("ref", quoteReference)
					.getSingleResult();
		} catch (PersistenceException e) {
			logger.warning(e.getMessage());
			throw new BusinessException(BusinessException.Reason.NO_SUCH_DEMAND, "Demand for reference " + quoteReference + " could not be found");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Demand> findAll() {
		return (List<Demand>) getEntityManager().createQuery("SELECT FROM Demand").getResultList();
	}

}
