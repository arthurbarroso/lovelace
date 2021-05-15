(ns lovelace.pages.requests
  (:require [clj-http.client :as http]
            [cheshire.core :as json]
            [lovelace.pages.specs :refer [validate-page-creation]]))

(defn get-page [token id]
  (http/get
   (str "https://api.notion.com/v1/pages/" id)
   {:headers {"Authorization" (str "Bearer " token)}}))

(defn retrieve-page [token id]
  (json/parse-string (:body (get-page token id)) true))

(defn post-page [token data]
  (http/post
   "https://api.notion.com/v1/pages/"
   {:headers {"Authorization" (str "Bearer " token)}
    :content-type :json
    :body data}))

(defn create-page [token body]
  (if (validate-page-creation body)
    (json/parse-string (:body (post-page token (json/encode body))) true)
    {:error "page-body doesn't match the page spec"}))
