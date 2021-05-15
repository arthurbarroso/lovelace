(ns lovelace.pages.requests
  (:require
   [lovelace.utils :refer [make-request safe-get safe-post safe-patch]]))

(defn get-page
  "Makes a GET request to Notion's API to retrieve data from a page.
  Takes in the authentication token and the page id as parameters"
  [token id]
  (safe-get
   (str "https://api.notion.com/v1/pages/" id)
   (make-request token)))

(defn post-page
  "Makes a POST request to Notion's API in order to create a new page.
  Takes the authentication token and the new page's content as parameters"
  [token data]
  (safe-post
   "https://api.notion.com/v1/pages/"
   (make-request token data)))

(defn patch-page
  "Makes a PATCH request to Notion's API in order to change a page's properties.
  Takes the authentication token, the page's id, and the new page's properties as parameters"
  [token id data]
  (safe-patch
   (str "https://api.notion.com/v1/pages/" id)
   (make-request token data)))
