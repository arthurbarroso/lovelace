(ns lovelace.search.requests
  (:require
   [cheshire.core :as json]
   [lovelace.search.specs :refer [validate-query]]
   [lovelace.utils :refer [make-request safe-post]]))

(defn search-post
  "Makes a POST request to Notion's API. Takes both the authentication token and the query as parameters"
  [token data]
  (safe-post
   "https://api.notion.com/v1/search"
   (make-request token data)))

(defn search-content
  "Makes use of Notion's search feature, making it possible to search content inside a given workspace.
  Takes the authentication token and a search query as parameters"
  [token query]
  (if (validate-query query)
    (let [response (search-post token (json/encode query))]
      (if (:error response)
        response
        (json/parse-string (:body response) true)))
    {:error "query didn't match the spec"}))
