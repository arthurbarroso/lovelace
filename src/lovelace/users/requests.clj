(ns lovelace.users.requests
  (:require [clj-http.client :as http]
            [cheshire.core :as json]
            [lovelace.utils :refer [make-request]]))

(defn fetch-user
  "Makes a GET request to Notion's API. Takes `token` and `id` as parameters."
  ([token id]
   (http/get
    (str "https://api.notion.com/v1/users/" id)
    (make-request token))))

(defn fetch-users
  "Makes a GET request to Notion's API. Takes `token` as a parameter. Is also able to take
  the optional parameters `page-size` and `start-cursor`"
  ([token]
   (http/get
    "https://api.notion.com/v1/users/"
    (make-request token)))
  ([token page-size]
   (http/get
    (str "https://api.notion.com/v1/users" "?page_size=" page-size)
    (make-request token)))
  ([token page-size start-cursor]
   (http/get
    (str "https://api.notion.com/v1/users" "?page_size=" page-size "&start_cursor" start-cursor)
    (make-request token))))

(defn get-all-users
  "Retrieves a list of users of a given workspace. Takes the authentication token as a parameter.
  Is also able to take the optional parameters `page-size` and `start-cursor`"
  ([token]
   (json/parse-string (:body (fetch-users token)) true))
  ([token page-size]
   (json/parse-string (:body (fetch-users token page-size)) true))
  ([token page-size start-cursor]
   (json/parse-string (:body (fetch-users token page-size start-cursor)) true)))

(defn get-single-user
  "Retrieves a single users of a given workspace. Takes the authentication token and the user's id as parameters."
  [token id]
  (json/parse-string (:body (fetch-user token id)) true))
