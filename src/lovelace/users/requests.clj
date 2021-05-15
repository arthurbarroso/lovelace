(ns lovelace.users.requests
  (:require [clj-http.client :as http]
            [cheshire.core :as json]
            [lovelace.utils :refer [make-request]]))

(defn fetch-users
  "Makes a GET request to Notion's API. Takes `token` or `token` and `id` as parameters.
  When used without the `id` parameter fetches all users, whilst when `id` is included fetches that single user"
  ([token]
   (http/get
    "https://api.notion.com/v1/users/"
    (make-request token)))
  ([token id]
   (http/get
    (str "https://api.notion.com/v1/users/" id)
    (make-request token))))

(defn get-all-users
  "Retrieves a list of users of a given workspace. Takes the authentication token as a parameter."
  [token]
  (json/parse-string (:body (fetch-users token)) true))

(defn get-single-user
  "Retrieves a single users of a given workspace. Takes the authentication token and the user's id as parameters."
  [token id]
  (json/parse-string (:body (fetch-users token id)) true))
