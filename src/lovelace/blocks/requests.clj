(ns lovelace.blocks.requests
  (:require
   [cheshire.core :as json]
   [lovelace.blocks.specs :refer [validate-page-size validate-block-body]]
   [lovelace.utils :refer [make-request safe-get safe-patch]]))

(defn get-block
  "Makes a GET request to Notion's block API and retrieves the data from a block.
  Takes the authentication token, the block's id and the page size as parameters.
  Also takes an optional `start-cursor` parameter to paginate."
  ([token id page-size]
   (safe-get
    (str "https://api.notion.com/v1/blocks/" id "/children?page_size=" page-size)
    (make-request token)))
  ([token id page-size start-cursor]
   (safe-get
    (str "https://api.notion.com/v1/blocks/" id "/children?page_size=" page-size "&start_cursor=" start-cursor)
    (make-request token))))

(defn retrieve-block
  "Retrieves data/children from a block.
  Takes the authentication token, the block's id and the page size as parameters.
  Also takes an optional `start-cursor` parameter to paginate."
  ([token id page-size]
   (if (validate-page-size page-size)
     (let [response (get-block token id page-size)]
       (if (:error response)
         response
         (json/parse-string (:body response) true)))
     {:error "page-size must be an integer and must be greater than zero"}))
  ([token id page-size start-cursor]
   (if (validate-page-size page-size)
     (let [response (get-block token id page-size start-cursor)]
       (if (:error response)
         response
         (json/parse-string (:body response) true)))
     {:error "page-size must be an integer and must be greater than zero"})))

(defn patch-block
  "Makes a PATCH request to Notion's API in order to append a children to a block.
  Takes the authentication token, the blocks's id, and the children to be added to that block"
  [token id data]
  (safe-patch
   (str "https://api.notion.com/v1/blocks/" id "/children")
   (make-request token data)))

(defn append-block-children
  "Appends children to a block based off of it's id.
  Takes the authentication token, a block's id and children to append to that block"
  [token id children]
  (if (validate-block-body children)
    (let [response (patch-block token id (json/encode children))]
      (if (:error response)
        response
        (json/parse-string (:body response) true)))
    {:error "children didn't conform the spec"}))
