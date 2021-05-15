(ns lovelace.search.requests
  (:require
   [lovelace.utils :refer [make-request safe-post]]))

(defn search-post
  "Makes a POST request to Notion's API. Takes both the authentication token and the query as parameters"
  [token data]
  (safe-post
   "https://api.notion.com/v1/search"
   (make-request token data)))
