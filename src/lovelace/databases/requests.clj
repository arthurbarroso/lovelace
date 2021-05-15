(ns lovelace.databases.requests
  (:require [clj-http.client :as http]
            [cheshire.core :as json]
            [lovelace.databases.specs :refer [validate-db-query]]
            [lovelace.utils :refer [make-request]]))

(defn fetch-database
  "Makes a GET request to Notion's database API and retrieves the data from a database.
  Takes the authentication token and the database's id as parameters"
  [token id]
  (http/get
   (str "https://api.notion.com/v1/databases/" id)
   (make-request token)))

(defn get-database
  "Retrieves data from a database based off of it's unique id.
  Takes the authentication token and the database's id as parameters"
  [token id]
  (json/parse-string (:body (fetch-database token id)) true))

(defn post-database
  "Makes a POST request to Notion's database API and searches data within a certain database.
  Takes the authentication token, the database's id and the query to search for data within the database"
  [token id data]
  (http/post
   (str "https://api.notion.com/v1/databases/" id "/query")
   (make-request token data)))

(defn query-database
  "Queries a Notion database. Takes the authentication token, the database's id and a query as parameters"
  [token id query]
  (if (validate-db-query query)
    (json/parse-string (:body (post-database token id (json/encode query))) true)
    {:error "query doesn't match the query spec"}))
