(ns lovelace.databases.requests
  (:require [clj-http.client :as http]
            [cheshire.core :as json]
            [lovelace.databases.specs :refer [validate-db-query]]))

(defn fetch-database
  [token id]
  (http/get
   (str "https://api.notion.com/v1/databases/" id)
   {:headers {"Authorization" (str "Bearer " token)}}))

(defn get-database [token id]
  ;  {:title title
  ;   :id id
  ;   :created_time created_time
  ;   :last_edited_time last_edited_time
  ;   :properties properties
  ;   :Type Type
  ;   :Publisher Publisher
  ;   :Summary Summary
  ;   :Link Link
  ;   :Read Read
  ;   :Author Author
  ;   :Name Name
  ;   :Status Status
  ;   :Publishing Publishing]
  (json/parse-string (:body (fetch-database token id)) true))

(defn post-database [token id data]
  (http/post
   (str "https://api.notion.com/v1/databases/" id "/query")
   {:headers {"Authorization" (str "Bearer " token)}
    :content-type :json
    :body data}))

(defn query-database [token id query]
  (if (validate-db-query query)
    (json/parse-string (:body (post-database token id (json/encode query))) true)
    {:error "Your query doesn't match the query spec"}))
