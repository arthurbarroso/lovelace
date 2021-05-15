(ns lovelace.search.core
  (:require [cheshire.core :as json]
            [lovelace.search.specs :refer [validate-query]]
            [lovelace.search.requests :refer [search-post]]))

(defn search
  "Makes use of Notion's search feature, making it possible to search content inside a given workspace.
              Takes the authentication token and a search query as parameters"
  [token query]
  (if (validate-query query)
    (let [response (search-post token (json/encode query))]
      (if (:error response)
        response
        (json/parse-string (:body response) true)))
    {:error "query didn't match the spec"}))
