#!/bin/bash

#mvn clean install -DskipTests=true;
mkdir -p target/wildfly-10.1.0.Final/modules/system/layers/base/com/mysql/main

cp ~/.m2/repository/mysql/mysql-connector-java/5.1.40/mysql-connector-java-5.1.40.jar target/wildfly-10.1.0.Final/modules/system/layers/base/com/mysql/main

cat > target/wildfly-10.1.0.Final/modules/system/layers/base/com/mysql/main/module.xml <<EOL
<module xmlns="urn:jboss:module:1.3" name="com.mysql">
    <resources>
        <resource-root path="mysql-connector-java-5.1.40.jar"/>
    </resources>
    <dependencies>
        <module name="javax.api"/>
        <module name="javax.transaction.api"/>
    </dependencies>
</module>
EOL

cp standalone.xml target/wildfly-10.1.0.Final/standalone/configuration
