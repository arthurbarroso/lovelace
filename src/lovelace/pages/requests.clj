(ns lovelace.pages.requests
  (:require [clj-http.client :as http]
            [cheshire.core :as json]
            [lovelace.pages.specs :refer [validate-page-creation]]
            [lovelace.utils :refer [make-request]]))

(defn get-page
  "Makes a GET request to Notion's API to retrieve data from a page.
  Takes in the authentication token and the page id as parameters"
  [token id]
  (http/get
   (str "https://api.notion.com/v1/pages/" id)
   (make-request token)))

(defn retrieve-page
  "Retrieves a page's data from Notion. Takes the authentication token and the page's unique id as parameters"
  [token id]
  (json/parse-string (:body (get-page token id)) true))

(defn post-page
  "Makes a POST request to Notion's API in order to create a new page.
  Takes the authentication token and the new page's content as parameters"
  [token data]
  (http/post
   "https://api.notion.com/v1/pages/"
   (make-request token data)))

(defn create-page
  "Creates a new page in Notion.
  Takes the authentication token and the new page's content/body as parameters"
  [token body]
  (if (validate-page-creation body)
    (json/parse-string (:body (post-page token (json/encode body))) true)
    {:error "page-body doesn't match the page spec"}))

(defn patch-page
  "Makes a PATCH request to Notion's API in order to change a page's properties.
  Takes the authentication token, the page's id, and the new page's properties as parameters"
  [token id data]
  (http/patch
   (str "https://api.notion.com/v1/pages/" id)
   (make-request token data)))

(defn update-page
  "Update a page's properties based off of it's unique id.
  Takes the authentication token, a page's id and a page's new properties as parameters"
  [token id body]
  (json/parse-string (:body (patch-page token id (json/encode body))) true))
