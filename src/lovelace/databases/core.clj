(ns lovelace.databases.core
  (:require [cheshire.core :as json]
            [lovelace.databases.specs :refer [validate-db-query]]
            [lovelace.databases.requests :refer [fetch-database post-database fetch-databases]]))

(defn retrieve-database
  "Retrieves data from a database based off of it's unique id.
  Takes the authentication token and the database's id as parameters"
  [token id]
  (let [response (fetch-database token id)]
    (if (:error response)
      response
      (json/parse-string (:body response) true))))

(defn query-database
  "Queries a Notion database. Takes the authentication token, the database's id and a query as parameters
  Is also able to take the optional parameters `page-size` and `start-cursor`"
  ([token id query]
   (if (validate-db-query query)
     (let [response (post-database token id (json/encode query))]
       (if (:error response)
         response
         (json/parse-string (:body response) true)))
     {:error "query doesn't match the query spec"}))
  ([token id query page-size]
   (if (validate-db-query query)
     (let [response (post-database token id (json/encode query) page-size)]
       (if (:error response)
         response
         (json/parse-string (:body response) true)))
     {:error "query doesn't match the query spec"}))
  ([token id query page-size start-cursor]
   (if (validate-db-query query)
     (let [response (:body (post-database token id (json/encode query) page-size start-cursor))]
       (if (:error response)
         response
         (json/parse-string (:body response) true)))
     {:error "query doesn't match the query spec"})))

(defn list-databases
  "Queries Notion for databases this integration has access to. Takes the authentication token as parameter.
  Is also able to take the optional parameters `page-size` and `start-cursor`"
  ([token]
   (let [response (fetch-databases token)]
     (if (:error response)
       response
       (json/parse-string (:body response) true))))
  ([token page-size]
   (let [response (fetch-databases token page-size)]
     (if (:error response)
       response
       (json/parse-string (:body response) true))))
  ([token page-size start-cursor]
   (let [response (fetch-databases token page-size start-cursor)]
     (if (:error response)
       response
       (json/parse-string (:body response) true)))))
