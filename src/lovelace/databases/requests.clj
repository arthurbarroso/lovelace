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
  Takes the authentication token, the database's id and the query to search for data within the database.
  Is also able to take the optional parameters `page-size` and `start-cursor` for pagination"
  ([token id data]
   (http/post
    (str "https://api.notion.com/v1/databases/" id "/query")
    (make-request token data)))
  ([token id data page-size]
   (http/post
    (str "https://api.notion.com/v1/databases/" id "/query" "?page_size=" page-size)
    (make-request token data)))
  ([token id data page-size start-cursor]
   (http/post
    (str "https://api.notion.com/v1/databases/" id "/query" "?page_size=" page-size "&start_cursor=" start-cursor)
    (make-request token data))))

(defn query-database
  "Queries a Notion database. Takes the authentication token, the database's id and a query as parameters
  Is also able to take the optional parameters `page-size` and `start-cursor`"
  ([token id query]
   (if (validate-db-query query)
     (json/parse-string (:body (post-database token id (json/encode query))) true)
     {:error "query doesn't match the query spec"}))
  ([token id query page-size]
   (if (validate-db-query query)
     (json/parse-string (:body (post-database token id (json/encode query) page-size)) true)
     {:error "query doesn't match the query spec"}))
  ([token id query page-size start-cursor]
   (if (validate-db-query query)
     (json/parse-string (:body (post-database token id (json/encode query) page-size start-cursor)) true)
     {:error "query doesn't match the query spec"})))
