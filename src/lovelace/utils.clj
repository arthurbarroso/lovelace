(ns lovelace.utils)

(defn make-request
  "Adds the necessary Headers and body to the requests"
  ([token]
   {:headers {"Authorization" (str "Bearer " token)
              "Notion-Version" "2021-05-13"}
    :content-type :json})
  ([token data]
   {:headers {"Authorization" (str "Bearer " token)
              "Notion-Version" "2021-05-13"}
    :content-type :json
    :body data}))
