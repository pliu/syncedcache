# Http archive
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "2.1"

RULES_JVM_EXTERNAL_SHA = "515ee5265387b88e4547b34a57393d2bcb1101314bcc5360ec7a482792556f42"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@rules_jvm_external//:specs.bzl", "maven")

maven_install(
    artifacts = [
        maven.artifact(
            group = "com.orbitz.consul",
            artifact = "consul-client",
            version = "1.3.3",
        ),
        maven.artifact(
            group = "org.apache.zookeeper",
            artifact = "zookeeper",
            version = "3.5.5",
        ),
        maven.artifact(
            group = "com.sparkjava",
            artifact = "spark-core",
            version = "2.9.0",
        ),
        maven.artifact(
            group = "org.slf4j",
            artifact = "slf4j-api",
            version = "1.7.26",
        ),
        maven.artifact(
            group = "ch.qos.logback",
            artifact = "logback-classic",
            version = "1.2.3",
        ),
        "junit:junit:4.12",
    ],
    repositories = [
        # Private repositories are supported through HTTP Basic auth
        "https://jcenter.bintray.com/",
        "https://maven.google.com",
        "https://repo1.maven.org/maven2",
    ],
)
