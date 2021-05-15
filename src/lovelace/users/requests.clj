(ns lovelace.users.requests
  (:require
   [cheshire.core :as json]
   [lovelace.utils :refer [make-request safe-get]]))

(defn fetch-user
  "Makes a GET request to Notion's API. Takes `token` and `id` as parameters."
  ([token id]
   (safe-get
    (str "https://api.notion.com/v1/users/" id)
    (make-request token))))

(defn fetch-users
  "Makes a GET request to Notion's API. Takes `token` as a parameter. Is also able to take
  the optional parameters `page-size` and `start-cursor`"
  ([token]
   (safe-get
    "https://api.notion.com/v1/users/"
    (make-request token)))
  ([token page-size]
   (safe-get
    (str "https://api.notion.com/v1/users" "?page_size=" page-size)
    (make-request token)))
  ([token page-size start-cursor]
   (safe-get
    (str "https://api.notion.com/v1/users" "?page_size=" page-size "&start_cursor" start-cursor)
    (make-request token))))
