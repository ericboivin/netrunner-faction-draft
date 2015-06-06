# Android Netrunner Identity Draft Application
Web app allowing asynchronous identity drafting for Netrunner


## Installation
This application has been built to run on Heroku at netrunner-faction-draft.herokuapp.com. However it is possible to run in a Web Server environment with a PostgreSQL Database and a SendGrid account for email sending. The following environment variables must be setup.
* DATABASE_URL
* SENDGRID_USERNAME
* SENDGRID_PASSWORD

The database schema is as follows:
```
CREATE TABLE draftjson
(
  code text NOT NULL,
  json json,
  CONSTRAINT pk_draftjson PRIMARY KEY (code)
)
```
