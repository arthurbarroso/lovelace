(ns lovelace.pages.specs
  (:require [clojure.spec.alpha :as s]))

(s/def ::parent (s/keys :req-un [::database_id]))
(s/def ::create-body (s/keys :req-un [::parent ::properties]))

(defn validate-page-creation [body]
  (s/valid? ::create-body body))

