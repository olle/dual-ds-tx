MVN:=./mvnw

.PHONY: run
run:
	${MVN} spring-boot:run

.PHONY: test t verify v
test t verify v:
	${MVN} clean verify
