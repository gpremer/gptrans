package net.premereur.gae.transport.domain;

import java.util.Collection;

public interface DemandRepository {

	Demand getDemandForQuoteReference(String quoteReference) throws BusinessException;

	void store(Demand demand);

	Collection<Demand> findAll();
}
