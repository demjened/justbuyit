# JustBuyIt!

**JustBuyIt!** - A simple application for the AppDirect Integration Challenge.

## Running instance
A running instance of JustBuyIt! is available on Heroku: https://justbuyit-appdirect.herokuapp.com .

**Please note this is running on a free dyno, therefore it may be temporarily inaccessible (the dyno needs 6 hours of sleep every 24 hours).**

## Features
* Integration with AppDirect's Subscription managemet, User assignment and Login services
* Spring based web application
* Spring MVC dispatching
* Extensible event processor framework
* Hibernate powered persistence with HSQLDB in-memory database
* OpenID authentication
* OAuth authorization for signing outgoing requests to AppDirect and verifying incoming requests
* FreeMarker template engine
* Simple Bootstrap UI

## Installation

Build the WAR package using Maven:
```
mvn package
```

Then deploy to a container. Launch the container with the following environment variables:
```
APP_CONFIG_MODE=dev|prod  # Currently only controls logging thresholds
OAUTH_CONSUMER_KEY=<app consumer key>
OAUTH_CONSUMER_SECRET=<app consumer secret>
```

## Notes

When implementing the application I went by the following assumptions regarding [AppDirect's event model](https://docs.appdirect.com/developer/distribution/event-notifications/subscription-events):
* Customer and Company are the same thing
* A Subscription is directly linked to a Company, so it can be represented merely as an attribute of the Company
* A User belongs to a Company, ie. 1 Company has N Users
* As a result of the above, cancelling a Subscription results in deleting the Company and all its Users from the app's registry (it can be re-added later)
