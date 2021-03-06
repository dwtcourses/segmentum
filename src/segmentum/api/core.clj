(ns segmentum.api.core
  (:require [segmentum.util.imports :refer [resource]]
            [patika.core :as p]
            [compojure.core :as c]
            [compojure.route :as r]
            [segmentum.transformations.google-analytics :as transformation.ga]))


(resource hello
  :get ["/"]
  :content-type :text
  :handle-ok (fn [ctx]
               (clojure.pprint/pprint (-> ctx :request))
               "Hello Segmentum!!"))


(resource event
  :post ["/event/google"]
  :content-type :json
  :post! (fn [ctx]
           (let [event (clojure.walk/keywordize-keys (:request-data ctx))]
             (transformation.ga/transform event)))
  :handle-created (fn [ctx] {:success? true}))


(c/defroutes not-found (r/not-found "404!"))
