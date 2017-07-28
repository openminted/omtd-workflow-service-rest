cd ..

# message-service connector
cd omtd-message-service-connector
rm -rf target/*
mvn clean install

cd ..

# build omtd-workflow-service
cd omtd-workflow-service
rm -rf target/*
mvn clean install -DskipTests=true

read -rsp $'Press enter to continue...\n'
cd ..

# build 
cd omtd-workflow-service-rest
rm -rf target/*
mvn clean install
