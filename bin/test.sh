#! /bin/bash

java -cp target/classes -Djava.security.krb5.conf=conf/krb5.conf -Djava.security.auth.login.config=conf/gss.conf com.gooddata.authenticator.test.GssExample