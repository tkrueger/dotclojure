(ns flow-storm
  (:require [flow-storm.api :as fs]
            [ci-fuzz.worker.k8s :as k8s]
            [ci-fuzz.worker.k8s-martian :as k8sm]))

(fs/local-connect)

(sort (k8sm/explore (k8sm/kube-proxy-client)))

(k8sm/request-for (k8sm/kube-proxy-client)
                  :list-core-v-1-namespaced-service
                  {:namespace "cifuzz"})
#rtrace
 (let [k8s-client (k8s/k8s-client {:server-url "http://localhost:8001"})
       client (k8sm/kube-proxy-client)
       request (k8sm/request-for client :list-core-v-1-namespaced-service
                                 {:namespace "cifuzz"})
       _ (tap> request)
       response (k8s/do-request k8s-client (assoc request :as :json))]
   response)
