(ns lovelace.blocks.core
  (:require
   [cheshire.core :as json]
   [lovelace.blocks.specs :refer [validate-page-size validate-block-body]]
   [lovelace.blocks.requests :refer [get-block patch-block]]))

(defn retrieve-block-children
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
