(ns lovelace.blocks.requests
  (:require
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

(defn patch-block
  "Makes a PATCH request to Notion's API in order to append a children to a block.
  Takes the authentication token, the blocks's id, and the children to be added to that block"
  [token id data]
  (safe-patch
   (str "https://api.notion.com/v1/blocks/" id "/children")
   (make-request token data)))
