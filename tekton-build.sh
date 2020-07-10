

# This specific version. 0.14.1 caused permissions problems
kubectl apply -f https://storage.googleapis.com/tekton-releases/pipeline/previous/v0.13.2/release.yaml


# create tasks
oc apply -f git-clone.yaml -f maven.yaml -f maven-graalvm.yaml -f kaniko.yaml


oc apply -f pipeline.yaml


oc apply -f pipeline-run.yaml


# Needed to run containers from some tasks
oc adm policy add-scc-to-user privileged -z default
# Needed for permission to push to the internal image registry
oc policy add-role-to-user edit -z default