(ns lovelace.search.requests
  (:require [clj-http.client :as http]
            [cheshire.core :as json]
            [lovelace.search.specs :refer [validate-query]]
            [lovelace.utils :refer [make-request]]))

(defn search-post
  "Makes a POST request to Notion's API. Takes both the authentication token and the query as parameters"
  [token data]
  (http/post
   "https://api.notion.com/v1/search"
   (make-request token data)))

(defn search-content
  "Makes use of Notion's search feature, making it possible to search content inside a given workspace.
  Takes the authentication token and a search query as parameters"
  [token query]
  (if (validate-query query)
    (json/parse-string (:body (search-post token (json/encode query))) true)
    {:error "query didn't match the spec"}))
