# lovelace

Lovelace is a library designed to act as a Clojure wrapper around [Notion's API](https://developers.notion.com/reference/intro). It uses [clj-http](https://github.com/dakrone/clj-http), [clojure.spec](https://clojure.org/guides/spec) and [cheshire](https://github.com/dakrone/cheshire)

Available under `[org.clojars.arthurbarroso/lovelace "0.1.2"]`

## Example usage
```clojure
(ns cml.core
  (:require [lovelace.search.core :as search]
            [lovelace.blocks.core :as blocks]
            [lovelace.pages.core :as pages]
            [lovelace.databases.core :as databases]
            [lovelace.users.core :as users]))

(def my-token "C")
(def my-db-id "B")
(def my-block-id "D")
(def my-page-id "C")
(def my-user-id "D")

(comment
  (search/search my-token {:query "Media article"})
  (blocks/retrieve-block-children my-token my-block-id 100)
  (blocks/append-block-children my-token my-block-id
                                {:children [{:object "block"
                                :type "heading_2"
                                :heading_2 {:text [{:type "text" :text {:content "chiclete"}}]}}]})
  (pages/create-page my-token {:parent {:database_id my-db-id}
                                :properties {:Name {:title [{:text {:content "New Media Article"}}]}}})
  (pages/create-page my-token {:parent {:database_id my-db-id}
                               :properties {:Name {:title [{:text {:content "New Media Article"}}]}}
                               :children [
                               {:object "block" :type "heading_2" :heading_2 {:text [{:type "text" :text {:content "chiclete"}}]}}]})
  (pages/retrieve-page my-token my-page-id)
  (pages/update-page my-token my-page-id {:Status {:select {:name "Reading"}}})
  (databases/retrieve-database my-token my-db-id)
  (databases/query-database my-token my-db-id {:filter
                                               {:or
                                                [{:property "Name", :title {:equals "teste"}}
                                                 {:property "Name", :title {:equals "New Media Article"}}]}
                                               :sorts [{:property "Name", :direction "ascending"}]})
  (users/list-all-users my-token)
  (users/retrieve-user my-token my-user-id)
  (list-databases my-token 3 "cursor"))
```

All functions that returns lists of objects can also make use of pagination by taking `page-size` and `start-cursor` arguments. Please check the docstrings for `blocks/retrieve-block-children`, `databases/query-database` and `user/list-all-users` to learn more about it.
