.PHONY: test_all
test_all: docker_start
	bazel test //src/test/java/com/github/pliu/syncedcache:AllTests
	make docker_stop

.PHONY: test_agent
test_agent: docker_start
	bazel test //src/test/java/com/github/pliu/syncedcache/agent:AgentTests
	make docker_stop

.PHONY: test_config_server
test_config_server: docker_start
	bazel test //src/test/java/com/github/pliu/syncedcache/agent:ConfigServerTests
	make docker_stop

.PHONY: docker_start
docker_start:
	docker-compose -f ./docker/docker-compose.yml up -d

.PHONY: docker_stop
docker_stop:
	docker-compose -f ./docker/docker-compose.yml down
