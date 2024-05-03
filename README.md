# Dalesbred + MariaDB Connector/J 3 + BINARY

This repository contains a test case that demonstrates that Dalesbred's ResultSetUtils throws an exception when a JDBC driver reports `byte[]` as the column class name, which is the case with BINARY type columns and MariaDB Connector/J 3.x that was a complete rewrite of the connector. Older 2.7.x series driver reports the column class name for BINARY columns as `[B` which `Class.forName(className)` can handle. 

A modified ResultSetUtils containing a workaround that checks if the reported column class name is `byte[]` and handles it separately instead of using `Class.forName(className)` is included. Uses System properties to enable/disable the workaround so that both the problem and it's fix can easily be demonstrated in the same branch.

Requires JDK 17 and Docker to run.
