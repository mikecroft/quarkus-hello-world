export APP_NAME=quarkus-hello-world

# s2i build. Requires a .s2i/environment file with ARTIFACT_COPY_ARGS set.
oc apply -f target/kubernetes/openshift.yml

# Custom native image build. Will probably fail due to OOM without specifying bigger memory requests/limits
# oc patch bc/${APP_NAME} -p "{\"spec\":{\"strategy\":{\"dockerStrategy\":{\"dockerfilePath\":\"src/main/docker/Dockerfile.multistage\"}}}}"

oc start-build ${APP_NAME} --from-dir=. --follow