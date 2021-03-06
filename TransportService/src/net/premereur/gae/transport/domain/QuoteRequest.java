package net.premereur.gae.transport.domain;

import static java.lang.Math.log;
import static java.lang.Math.max;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;

import com.google.inject.internal.Nullable;

@Entity
public class QuoteRequest {
	private static final int TARIF_DECREASE_UNIT = 6;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(QuoteRequest.class.getCanonicalName());

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date earliestShipmentTime;

	private Date latestShipmentTime;

	private Float weight;

	private Integer numPackages;

	private String customerReference;

	private String callbackURL;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="originator", fetch = FetchType.LAZY)
	private Set<Quote> quotes = Collections.emptySet();

	public QuoteRequest(Date earliestShipmentTime, Date latestShipmentTime, Float weight, Integer numPackages, String customerReference, @Nullable
	String callbackURL) {
		super();
		this.earliestShipmentTime = earliestShipmentTime;
		this.latestShipmentTime = latestShipmentTime;
		this.weight = weight;
		this.numPackages = numPackages;
		this.customerReference = customerReference;
		this.callbackURL = callbackURL;
	}

	@SuppressWarnings("unused")
	private QuoteRequest() {
		// For JPA
	}

	public Long getId() {
		return id;
	}

	// For unit testing
	void setId(Long id) {
		this.id = id;
	}

	public Date getEarliestShipmentTime() {
		return earliestShipmentTime;
	}

	public Date getLatestShipmentTime() {
		return latestShipmentTime;
	}

	public float getWeight() {
		return valueWithDefault(weight, 0f);
	}

	public int getNumPackages() {
		return valueWithDefault(numPackages, 1);
	}

	public String getCustomerReference() {
		return customerReference;
	}

	public String getCallbackURL() {
		return callbackURL;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	private static final <T> T valueWithDefault(T value, T defaultValue) {
		return value == null ? defaultValue : value;
	}

	public Collection<Quote> getQuotes() {
		computeQuotesIfNotAvailableYet();
		return quotes;
	}

	public void computeQuotesIfNotAvailableYet() {
		if (quotes.isEmpty()) {
			computeQuotes();			
		}
	}

	private void computeQuotes() {
		DateTime next = new DateTime(getEarliestShipmentTime());
		DateTime last = new DateTime(getLatestShipmentTime());
		DateTime validity = new DateTime().plusHours(8);
		quotes = new HashSet<Quote>();
		BigDecimal price;
		double basePrice = 5.0 * (1 + log(getNumPackages())) * log(max(2, getWeight()));
		double discount = 1;
		while (next.isBefore(last)) {
			price = new BigDecimal(basePrice * discount, new MathContext(2));
			final Date start = next.toDate();
			next = next.plusHours(TARIF_DECREASE_UNIT);
			final Date end = next.toDate();
			Quote quote = new Quote(this, validity.toDate(), price, start, end);
			quotes.add(quote);
			discount *= 0.95;
		}
		ServiceLocator.get().getQuoteRequestRepository().store(this);
	}

}
