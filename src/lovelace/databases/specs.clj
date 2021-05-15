(ns lovelace.databases.specs
  (:require [clojure.spec.alpha :as s]))

(s/def ::select (s/keys :req-un [::equals]))

(s/def ::filter-item (s/keys :req-un [::property ::select]))
(s/def ::or (s/every ::filter-item))
(s/def ::and (s/every ::filter-item))
(s/def ::filter (s/keys :req-un [(or ::or ::and)]))

(s/def ::sorts (s/keys :req-un [::property ::direction]))
(s/def ::db-filter (s/keys :req-un [::filter]
                           :opt-un [::sorts]))

(defn validate-db-query [query]
  (s/valid? ::db-filter query))

(comment
  (s/valid? ::db-filter {:filter {:or [{:property 1 :select {:equals 9}} {:property 3 :select {:equals 4}}]}})
  (s/valid? ::db-filter {:filter {:and [{:property 1 :select {:equals 3}}]} :sorts {:property 3 :direction "s"}}))
