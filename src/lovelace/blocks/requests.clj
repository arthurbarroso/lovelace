(ns lovelace.blocks.requests
  (:require [clj-http.client :as http]
            [cheshire.core :as json]
            [lovelace.blocks.specs :refer [validate-page-size validate-block-body]]))

(defn get-block
  "Makes a GET request to Notion's block API and retrieves the data from a block.
  Takes the authentication token, the block's id and the page size as parameters"
  [token id page-size]
  (http/get
   (str "https://api.notion.com/v1/blocks/" id "/children?page_size=" page-size)
   {:headers {"Authorization" (str "Bearer " token)}}))

(defn retrieve-block
  "Retrieves data/children from a block.
  Takes the authentication token, the block's id and the page size as parameters"
  [token id page-size]
  (if (validate-page-size page-size)
    (json/parse-string (:body (get-block token id page-size)) true)
    {:error "page-size must be an integer and must be greater than zero"}))

(defn patch-block
  "Makes a PATCH request to Notion's API in order to append a children to a block.
  Takes the authentication token, the blocks's id, and the children to be added to that block"
  [token id data]
  (http/patch
   (str "https://api.notion.com/v1/blocks/" id "/children")
   {:headers {"Authorization" (str "Bearer " token)}
    :content-type :json
    :body data}))

(defn append-block-children
  "Appends children to a block based off of it's id.
  Takes the authentication token, a block's id and children to append to that block"
  [token id children]
  (if (validate-block-body children)
    (json/parse-string (:body (patch-block token id (json/encode children))) true)
    {:error "children didn't conform the spec"}))
