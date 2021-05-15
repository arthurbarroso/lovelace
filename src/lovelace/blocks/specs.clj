(ns lovelace.blocks.specs
  (:require [clojure.spec.alpha :as s]))

(s/def ::page-size #(and (int? %) (< 0 %)))

(defn validate-page-size [page-size]
  (s/valid? ::page-size page-size))

(s/def ::children (s/keys :req-un [::object ::type]))
(s/def ::body (s/keys :req-un [::children]))

(defn validate-block-body [body]
  (s/valid? ::body body))
