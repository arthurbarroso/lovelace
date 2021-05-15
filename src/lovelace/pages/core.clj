(ns lovelace.pages.core
  (:require [cheshire.core :as json]
            [lovelace.pages.specs :refer [validate-page-creation]]
            [lovelace.pages.requests :refer [get-page post-page patch-page]]))

(defn retrieve-page
  "Retrieves a page's data from Notion. Takes the authentication token and the page's unique id as parameters"
  [token id]
  (let [response (get-page token id)]
    (if (:error response)
      response
      (json/parse-string (:body response) true))))

(defn create-page
  "Creates a new page in Notion.
  Takes the authentication token and the new page's content/body as parameters"
  [token body]
  (if (validate-page-creation body)
    (let [response (post-page token (json/encode body))]
      (if (:error response)
        response
        (json/parse-string (:body response) true)))
    {:error "page-body doesn't match the page spec"}))

(defn update-page
  "Update a page's properties based off of it's unique id.
  Takes the authentication token, a page's id and a page's new properties as parameters"
  [token id body]
  (let [response (patch-page token id (json/encode body))]
    (if (:error response)
      response
      (json/parse-string (:body response) true))))
