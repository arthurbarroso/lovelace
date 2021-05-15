(ns lovelace.utils
  (:require [clj-http.client :as http]))

(defn make-request
  "Adds the necessary Headers and body to the requests"
  ([token]
   {:headers {"Authorization" (str "Bearer " token)
              "Notion-Version" "2021-05-13"}
    :content-type :json})
  ([token data]
   {:headers {"Authorization" (str "Bearer " token)
              "Notion-Version" "2021-05-13"}
    :content-type :json
    :body data}))

(defn safe-get [url req]
  (try
    (http/get
     url
     req)
    (catch Exception e {:error (.getMessage e)})))

(defn safe-post [url req]
  (try
    (http/post
     url
     req)
    (catch Exception e {:error (.getMessage e)})))
