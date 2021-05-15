(ns lovelace.users.requests
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

(defn fetch-users
  ([token]
   (http/get
    "https://api.notion.com/v1/users/"
    {:headers {"Authorization" (str "Bearer " token)}}))
  ([token id]
   (http/get
    (str "https://api.notion.com/v1/users/" id)
    {:headers {"Authorization" (str "Bearer " token)}})))

(defn get-all-users [token]
  (let [response (fetch-users token)
        parsed-response (json/parse-string (get-in response [:body]) true)
        results (:results parsed-response)
        has_more (:has_more parsed-response)
        next_cursor (:next_cursor parsed-response)]
    {:results results
     :has_more has_more
     :next_cursor next_cursor}))

(defn get-single-user [token id]
  (let [response (fetch-users token id)
        parsed-response (json/parse-string (get-in response [:body]) true)]
    {:results parsed-response}))
