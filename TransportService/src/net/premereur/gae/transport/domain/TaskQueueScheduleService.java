package net.premereur.gae.transport.domain;

import static com.google.appengine.api.labs.taskqueue.TaskOptions.Builder.url;

import java.util.logging.Logger;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class TaskQueueScheduleService implements ScheduleService {

	@Inject
	private Logger logger;

	private final Queue quoteComputeQueue;

	private final String quoteComputeTaskURL;

	@Inject
	TaskQueueScheduleService(final @Named("quoteCompute")
	Queue quoteComputeQueue, final @Named("quoteComputeTask")
	String quoteComputeTaskURL) {
		super();
		this.quoteComputeQueue = quoteComputeQueue;
		this.quoteComputeTaskURL = quoteComputeTaskURL;
	}

	@Override
	public void scheduleQuoteComputation(QuoteRequest quoteRequest) {
		logger.entering(TaskQueueScheduleService.class.getSimpleName(), "scheduleQuoteCallback");
		quoteComputeQueue.add(url(quoteComputeTaskURL+'/'+quoteRequest.getId()));
	}

}
