# lovelace

Lovelace is a library designed to act as a Clojure wrapper around [Notion's API](https://developers.notion.com/reference/intro). It uses [clj-http](https://github.com/dakrone/clj-http), [clojure.spec](https://clojure.org/guides/spec) and [cheshire](https://github.com/dakrone/cheshire)

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
                                {:children [{:object "block" :type "heading_2" :heading_2 {:text [{:type "text" :text {:content "chiclete"}}]}}]})
  (pages/create-page my-token {:parent {:database_id my-db-id} :properties {:Name {:title [{:text {:content "New Media Article"}}]}}})
  (pages/create-page my-token {:parent {:database_id my-db-id}
                               :properties {:Name {:title [{:text {:content "New Media Article"}}]}}
                               :children [{:object "block" :type "heading_2" :heading_2 {:text [{:type "text" :text {:content "chiclete"}}]}}]})
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

## License

The MIT License (MIT)

Copyright (c) 2015 Arthur Barroso

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
