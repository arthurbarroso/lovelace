(ns lovelace.databases.specs
  (:require [clojure.spec.alpha :as s]))

(s/def ::filter-item (s/keys :req-un [::property]))
(s/def ::or (s/every ::filter-item))
(s/def ::and (s/every ::filter-item))
(s/def ::filter (s/keys :opt-un [::or ::and ::filter-item]))

(s/def ::sorts (s/every (s/keys :req-un [::property ::direction])))
(s/def ::db-filter (s/keys :req-un [::filter]
                           :opt-un [::sorts]))

(defn validate-db-query [query]
  (s/valid? ::db-filter query))
