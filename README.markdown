# A sample project for Google Appengine

## Intro

This is a sample project I'm developing to get acquainted with Cloud computing and Google Appengine in particular.

It demonstrates using a combination of 

* Google Appengine
* Google datastore with JPA
* Restlet 2.0
* Google Guice 2.0
* Warp Persist 2.0
* JAXB 2.1

## Use case

The sample system is a mockup for a package delivery provider interfacing with large customers (think Amazon). 

The system receives requests for quotes on a delivery service per shipment unit.

Assynchronously, a quote is sent to the original requestor.

If the quote is accepted (outside of this system) a request for transport is made. This request is responded to synchrounously.

Two more notifications are sent:

1. at pick-up time at the warehouse
1. at drop-off time at the customer

