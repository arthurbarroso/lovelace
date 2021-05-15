(ns lovelace.search.specs
  (:require [clojure.spec.alpha :as s]
            [clojure.string :refer [blank?]]))

(s/def ::query #(and (string? %) (not (blank? %))))
(s/def ::sorts (s/every (s/keys :req-un [::property ::direction])))
(s/def ::body (s/keys :req-un [::query]
                      :opt-un [::sorts]))

(defn validate-query [query]
  (s/valid? ::body query))
