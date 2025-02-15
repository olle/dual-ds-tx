MVN:=./mvnw

.PHONY: run
run:
	${MVN} spring-boot:run

.PHONY: test t verify v
test t verify v:
	${MVN} clean verify

.PHONY: down
down:
	docker compose down --volumes --remove-orphans

.PHONY: up
up:
	docker compose up -d