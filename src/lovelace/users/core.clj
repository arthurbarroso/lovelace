(ns lovelace.users.core
  (:require [cheshire.core :as json]
            [lovelace.users.requests :refer [fetch-user fetch-users]]))

(defn list-all-users
  "Retrieves a list of users of a given workspace. Takes the authentication token as a parameter.
    Is also able to take the optional parameters `page-size` and `start-cursor`"
  ([token]
   (let [response (fetch-users token)]
     (if (:error response)
       response
       (json/parse-string (:body response) true))))
  ([token page-size]
   (let [response (fetch-users token page-size)]
     (if (:error response)
       response
       (json/parse-string (:body response) true))))
  ([token page-size start-cursor]
   (let [response (fetch-users token page-size start-cursor)]
     (if (:error response)
       response
       (json/parse-string (:body response) true)))))

(defn retrieve-user
  "Retrieves a single users of a given workspace. Takes the authentication token and the user's id as parameters."
  [token id]
  (let [response (fetch-user token id)]
    (if (:error response)
      response
      (json/parse-string (:body response) true))))
