(ns lovelace.search.requests
  (:require [clj-http.client :as http]
            [cheshire.core :as json]
            [lovelace.search.specs :refer [validate-query]]))

(defn search-post [token data]
  (http/post
   "https://api.notion.com/v1/search"
   {:headers {"Authorization" (str "Bearer " token)}
    :content-type :json
    :body data}))

(defn search-content [token query]
  (println query (json/encode query))
  (if (validate-query query)
    (json/parse-string (:body (search-post token (json/encode query))) true)
    {:error "query didn't match the spec"}))
