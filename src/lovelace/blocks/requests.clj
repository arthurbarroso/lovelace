(ns lovelace.blocks.requests
  (:require [clj-http.client :as http]
            [cheshire.core :as json]
            [lovelace.blocks.specs :refer [validate-page-size validate-block-body]]))

(defn get-block [token id page-size]
  (http/get
   (str "https://api.notion.com/v1/blocks/" id "/children?page_size=" page-size)
   {:headers {"Authorization" (str "Bearer " token)}}))

(defn retrieve-block [token id page-size]
  (if (validate-page-size page-size)
    (json/parse-string (:body (get-block token id page-size)) true)
    {:error "page-size must be an integer and must be greater than zero"}))

(defn patch-block [token id data]
  (http/patch
   (str "https://api.notion.com/v1/blocks/" id "/children")
   {:headers {"Authorization" (str "Bearer " token)}
    :content-type :json
    :body data}))

(defn append-block-children [token id children]
  (if (validate-block-body children)
    (json/parse-string (:body (patch-block token id (json/encode children))) true)
    {:error "children didn't conform the spec"}))
