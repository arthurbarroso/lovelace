(ns lovelace.databases.requests
  (:require
   [lovelace.utils :refer [make-request safe-get safe-post]]))

(defn fetch-database
  "Makes a GET request to Notion's database API and retrieves the data from a database.
  Takes the authentication token and the database's id as parameters"
  [token id]
  (safe-get
   (str "https://api.notion.com/v1/databases/" id)
   (make-request token)))

(defn post-database
  "Makes a POST request to Notion's database API and searches data within a certain database.
  Takes the authentication token, the database's id and the query to search for data within the database.
  Is also able to take the optional parameters `page-size` and `start-cursor` for pagination"
  ([token id data]
   (safe-post
    (str "https://api.notion.com/v1/databases/" id "/query")
    (make-request token data)))
  ([token id data page-size]
   (safe-post
    (str "https://api.notion.com/v1/databases/" id "/query" "?page_size=" page-size)
    (make-request token data)))
  ([token id data page-size start-cursor]
   (safe-post
    (str "https://api.notion.com/v1/databases/" id "/query" "?page_size=" page-size "&start_cursor=" start-cursor)
    (make-request token data))))
